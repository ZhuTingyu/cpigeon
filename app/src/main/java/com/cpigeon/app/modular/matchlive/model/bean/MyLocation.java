package com.cpigeon.app.modular.matchlive.model.bean;

/**
 * Created by Administrator on 2017/7/12.
 */

public class MyLocation {
    private int raceid;//赛事id
    private double latitude;
    private double longitude;
    private String windDirection;//风向
    private Long getReportTime;//发布时间
    private String weather;//天气
    private String temperature;//温度

    public Long getGetReportTime() {
        return getReportTime;
    }

    public void setGetReportTime(Long getReportTime) {
        this.getReportTime = getReportTime;
    }

    public int getRaceid() {
        return raceid;
    }

    public void setRaceid(int raceid) {
        this.raceid = raceid;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
