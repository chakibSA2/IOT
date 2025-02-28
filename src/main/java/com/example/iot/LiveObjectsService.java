package com.example.iot;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class LiveObjectsService {
    private static final String API_URL = "https://liveobjects.orange-business.com/api/v0/data/streams/";
    private static final String API_KEY = "37bd0b0f0e0d4f74a234bb350b7b65d6";

    private final RestTemplate restTemplate;
    private final PythonDecoderService pythonDecoderService;
    private final ObjectMapper objectMapper;

    public LiveObjectsService(PythonDecoderService pythonDecoderService) {
        this.restTemplate = new RestTemplate();
        this.pythonDecoderService = pythonDecoderService;
        this.objectMapper = new ObjectMapper();
    }

    public List<SensorData> getDecodedSensorData(String deviceId) {
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

        if (sensorDataList.isEmpty()) {
            throw new RuntimeException("Aucune donnée capteur trouvée pour l'appareil " + deviceId);
        }

        return sensorDataList;
    }
}