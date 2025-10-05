package com.example.lab3_iot;

public class ForecastItem {
    private String date;
    private double maxTemp;
    private double minTemp;
    private String condition;
    private String iconUrl;

    public ForecastItem(String date, double maxTemp, double minTemp, String condition, String iconUrl) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.condition = condition;
        this.iconUrl = iconUrl;
    }

    public String getDate() { return date; }
    public double getMaxTemp() { return maxTemp; }
    public double getMinTemp() { return minTemp; }
    public String getCondition() { return condition; }
    public String getIconUrl() { return iconUrl; }
}
