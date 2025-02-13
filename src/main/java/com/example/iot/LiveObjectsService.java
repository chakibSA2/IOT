package com.example.iot;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class LiveObjectsService {
    private static final String API_URL = "https://liveobjects.orange-business.com/api/v1/";
    private static final String API_KEY = "YOUR_API_KEY"; // Remplace avec ta clé API

    private final RestTemplate restTemplate;
    private final DecoderService decoderService;

    public LiveObjectsService(DecoderService decoderService) {
        this.restTemplate = new RestTemplate();
        this.decoderService = decoderService;
    }

    public SensorData getDecodedData(String deviceId) {
        String url = API_URL + "data/streams/" + deviceId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-Key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

        if (response.getBody() != null) {
            // Récupération du payload brut
            String hexPayload = response.getBody().get("value").asText();

            // Décodage des données
            return decoderService.decodePayload(hexPayload);
        }
        return null;
    }
}

