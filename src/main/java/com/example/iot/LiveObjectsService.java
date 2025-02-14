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
    private static final String API_KEY = "";

    private final RestTemplate restTemplate;
    private final DecoderService decoderService;
    private final ObjectMapper objectMapper;

    public LiveObjectsService(DecoderService decoderService) {
        this.restTemplate = new RestTemplate();
        this.decoderService = decoderService;
        this.objectMapper = new ObjectMapper();
    }

    public List<SensorData> getDecodedData(String deviceId) {
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

                    // Vérifie si le message contient une erreur
                    if (valueNode.has("error")) {
                        System.out.println("⚠️ Erreur dans le message : " + valueNode.get("error").asText());
                        continue;
                    }

                    // Récupérer le payload
                    if (valueNode.has("payload")) {
                        String hexPayload = valueNode.get("payload").asText();
                        SensorData sensorData = decoderService.decodePayload(hexPayload);

                        if (sensorData != null) {
                            sensorDataList.add(sensorData);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du traitement des données LiveObjects", e);
        }

        if (sensorDataList.isEmpty()) {
            throw new RuntimeException("Aucune donnée valide reçue pour le capteur " + deviceId);
        }

        return sensorDataList;
    }
}
