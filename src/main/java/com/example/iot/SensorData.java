package com.example.iot;

import java.sql.Timestamp;

public class SensorData {
    private Double temperature;
    private Integer humidity;
    private Double pressure;
    private Integer co2;
    private Integer cov;
    private Double luminosity;
    private Double batteryVoltage;
    private String powerMode;
    private Boolean motionDetected;
    private String timestamp;

    public SensorData(Double temperature, Integer humidity, Double pressure, Integer co2, Integer cov, Double luminosity,
                      Double batteryVoltage, String powerMode, Boolean motionDetected, String timestamp) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.co2 = co2;
        this.cov = cov;
        this.luminosity = luminosity;
        this.batteryVoltage = batteryVoltage;
        this.powerMode = powerMode;
        this.motionDetected = motionDetected;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Double getTemperature() { return temperature; }
    public Integer getHumidity() { return humidity; }
    public Double getPressure() { return pressure; }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getCo2() { return co2; }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public void setCo2(Integer co2) {
        this.co2 = co2;
    }

    public void setCov(Integer cov) {
        this.cov = cov;
    }

    public void setLuminosity(Double luminosity) {
        this.luminosity = luminosity;
    }

    public void setBatteryVoltage(Double batteryVoltage) {
        this.batteryVoltage = batteryVoltage;
    }

    public void setPowerMode(String powerMode) {
        this.powerMode = powerMode;
    }

    public void setMotionDetected(Boolean motionDetected) {
        this.motionDetected = motionDetected;
    }

    public Integer getCov() { return cov; }
    public Double getLuminosity() { return luminosity; }
    public Double getBatteryVoltage() { return batteryVoltage; }
    public String getPowerMode() { return powerMode; }
    public Boolean getMotionDetected() { return motionDetected; }

    @Override
    public String toString() {
        return "SensorData{" +
                "temperature=" + temperature +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", co2=" + co2 +
                ", cov=" + cov +
                ", luminosity=" + luminosity +
                ", batteryVoltage=" + batteryVoltage +
                ", powerMode='" + powerMode + '\'' +
                ", motionDetected=" + motionDetected +
                '}';
    }
}
