package com.example.iot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PythonDecoderService {

    private static final String PYTHON_EXECUTABLE_PATH = "C:/Users/chaki/AppData/Local/Programs/Python/Python312/python.exe";
    private static final String PYTHON_SCRIPT_PATH = "C:/Users/chaki/IdeaProjects/IOT/IOT/Codec-Report-Batch-Python/br_uncompress.py";

    public List<SensorData> decodePayload(String hexPayload) {
        List<SensorData> decodedDataList = new ArrayList<>();

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    PYTHON_EXECUTABLE_PATH, PYTHON_SCRIPT_PATH,
                    "-a", "3",
                    "0,1,4,OCC", // Détection de mouvement
                    "1,10,7,T", // Température
                    "2,100,6,H", // Humidité
                    "3,10,6,CO2", // CO2
                    "4,10,6,COV", // COV
                    "5,10,6,LUX", // Luminosité
                    "6,10,6,P", // Pression
                    "-if", hexPayload);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder jsonOutput = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonOutput.append(line);
            }

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorOutput = new StringBuilder();
            while ((line = errorReader.readLine()) != null) {
                System.err.println("Python Error: " + line);
                errorOutput.append(line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Le script Python a échoué avec le code : " + exitCode);
                System.err.println("Erreur Python : " + errorOutput);
                return decodedDataList;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonOutput.toString());

            if (rootNode.has("dataset")) {
                Map<String, SensorData> sensorDataMap = new HashMap<>();

                for (JsonNode dataNode : rootNode.get("dataset")) {
                    String timestamp = dataNode.has("data_absolute_timestamp")
                            ? dataNode.get("data_absolute_timestamp").asText()
                            : "N/A";
                    String label = dataNode.get("data").get("label_name").asText();
                    double value = dataNode.get("data").get("value").asDouble();

                    // Clé unique basée sur le timestamp pour grouper les mesures
                    if (!sensorDataMap.containsKey(timestamp)) {
                        sensorDataMap.put(timestamp,
                                new SensorData(null, null, null, null, null, null, null, null, null, null));
                    }

                    SensorData sensorData = sensorDataMap.get(timestamp);
                    sensorData.setTimestamp(timestamp);

                    switch (label) {
                        case "T":
                            sensorData.setTemperature(value / 100); // °C
                            break;
                        case "H":
                            sensorData.setHumidity((int) (value / 100)); // %
                            break;
                        case "CO2":
                            sensorData.setCo2((int) value); // ppm
                            break;
                        case "COV":
                            sensorData.setCov((int) value); // Indice
                            break;
                        case "LUX":
                            sensorData.setLuminosity(value); // Lux
                            break;
                        case "P":
                            sensorData.setPressure(value / 10); // hPa
                            break;
                        case "OCC":
                            sensorData.setMotionDetected(value == 1); // Détection mouvement
                            break;
                        default:
                            System.out.println("🔹 Mesure inconnue ignorée : " + label);
                    }
                }

                decodedDataList.addAll(sensorDataMap.values());
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution du décodeur Python : " + e.getMessage());
            e.printStackTrace();
        }

        return decodedDataList;
    }
}
