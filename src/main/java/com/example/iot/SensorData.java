package com.example.iot;

public class SensorData {
    private double temperature;
    private int humidity;
    private int co2;

    public SensorData(double temperature, int humidity, int co2) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
    }

    public double getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCo2() {
        return co2;
    }
}

