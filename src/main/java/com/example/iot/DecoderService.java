package com.example.iot;

import org.springframework.stereotype.Service;

@Service
public class DecoderService {

    public SensorData decodePayload(String hexPayload) {
        try {
            byte[] bytes = hexStringToByteArray(hexPayload);

            double temperature = ((bytes[0] & 0xFF) + (bytes[1] & 0xFF) / 100.0);
            int humidity = (bytes[2] & 0xFF);
            int co2 = ((bytes[3] & 0xFF) << 8) | (bytes[4] & 0xFF);
            double batteryVoltage = ((bytes[5] & 0xFF) + (bytes[6] & 0xFF) / 100.0);

            String powerMode = (bytes[7] == 0x01) ? "Battery" : "External Power";

            return new SensorData(temperature, humidity, co2, batteryVoltage, powerMode);
        } catch (Exception e) {
            System.out.println("⚠️ Erreur de décodage du payload : " + hexPayload);
            return null;
        }
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
