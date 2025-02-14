package com.example.iot;

public class SensorData {
    private double temperature;
    private int humidity;
    private int co2;
    private double batteryVoltage;
    private String powerMode;

    public SensorData(double temperature, int humidity, int co2, double batteryVoltage, String powerMode) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2 = co2;
        this.batteryVoltage = batteryVoltage;
        this.powerMode = powerMode;
    }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }

    public int getCo2() { return co2; }
    public void setCo2(int co2) { this.co2 = co2; }

    public double getBatteryVoltage() { return batteryVoltage; }
    public void setBatteryVoltage(double batteryVoltage) { this.batteryVoltage = batteryVoltage; }

    public String getPowerMode() { return powerMode; }
    public void setPowerMode(String powerMode) { this.powerMode = powerMode; }

    @Override
    public String toString() {
        return "SensorData{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", co2=" + co2 +
                ", batteryVoltage=" + batteryVoltage +
                ", powerMode='" + powerMode + '\'' +
                '}';
    }
}
