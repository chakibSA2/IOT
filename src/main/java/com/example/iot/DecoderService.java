package com.example.iot;

import org.springframework.stereotype.Service;

@Service
public class DecoderService {

    public SensorData decodePayload(String hexPayload) {
        byte[] bytes = hexStringToByteArray(hexPayload);

        // Extraction des valeurs selon la structure du capteur
        int temperatureRaw = ((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF);
        double temperature = temperatureRaw / 10.0; // Exemple : 298 → 29.8°C

        int humidity = bytes[4] & 0xFF; // Exemple : 94%

        int co2 = bytes[6] & 0xFF; // Exemple : 120 ppm

        return new SensorData(temperature, humidity, co2);
    }

    private byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }
}

