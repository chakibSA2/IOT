package com.example.iot;

import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class LiveObjectsService {
    private static final String API_URL = "https://liveobjects.orange-business.com/api/v0/data/streams/";
    private static final String API_KEY = "19bd9ad1fc984239bf900e89d0754a28";
    private static final String DATA_FILE = "sensor_data.json";

    private final RestTemplate restTemplate;
    private final PythonDecoderService pythonDecoderService;
    private final ObjectMapper objectMapper;

    private final List<String> deviceIds = List.of("device1", "device2", "device3");
    public LiveObjectsService(PythonDecoderService pythonDecoderService) {
        this.restTemplate = new RestTemplate();
        this.pythonDecoderService = pythonDecoderService;
        this.objectMapper = new ObjectMapper();
    }

    public List<SensorData> getDecodedSensorData(String deviceId) {
        List<SensorData> sensorDataList = loadFromFile();
        List<SensorData> newSensorData = fetchSensorDataFromAPI(deviceId);

        if (!newSensorData.isEmpty()) {
            sensorDataList.addAll(newSensorData);
            saveToFile(sensorDataList);
        }

        return sensorDataList;
    }

    @Scheduled(fixedRate = 600000)
    public void updateSensorDataAutomatically() {
        System.out.println("Mise à jour automatique des données...");
        List<SensorData> allSensorData = loadFromFile();

        for (String deviceId : deviceIds) {
            List<SensorData> newSensorData = fetchSensorDataFromAPI(deviceId);
            if (!newSensorData.isEmpty()) {
                allSensorData.addAll(newSensorData);
            }
        }

        saveToFile(allSensorData);
    }

    private List<SensorData> fetchSensorDataFromAPI(String deviceId) {
        String url = API_URL + deviceId + "?limit=100";

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        List<SensorData> sensorDataList = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            for (JsonNode messageNode : rootNode) {
                if (messageNode.has("value")) {
                    JsonNode valueNode = messageNode.get("value");

                    if (valueNode.has("payload")) {
                        String hexPayload = valueNode.get("payload").asText();

                        // 🔥 Appel du décodeur Python pour obtenir les données
                        List<SensorData> decodedData = pythonDecoderService.decodePayload(hexPayload);
                        sensorDataList.addAll(decodedData);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du traitement des données LiveObjects", e);
        }

        return sensorDataList;
    }

    private void saveToFile(List<SensorData> sensorData) {
        try (FileWriter file = new FileWriter(DATA_FILE)) {
            objectMapper.writeValue(file, sensorData);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'enregistrement des données", e);
        }
    }

    private List<SensorData> loadFromFile() {
        try {
            File file = new File(DATA_FILE);
            if (!file.exists()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(Files.readAllBytes(Paths.get(DATA_FILE)),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, SensorData.class));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du chargement des données", e);
        }
    }
}
