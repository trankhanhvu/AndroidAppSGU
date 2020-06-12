package com.example.nationinfo;

public class ItemList {
    private String countryCode, countryName, capital, countryFlag;
    private int population;
    private double areaKm;

    public ItemList() {
    }

    public ItemList(String countryCode, String countryName, String capital, int population, double areaKm) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.capital = capital;
        this.population = population;
        this.areaKm = areaKm;
        this.countryFlag = "http://api.geonames.org/flags/x/" + countryCode.toLowerCase() + ".gif";
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCapital() {
        return capital;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public int getPopulation() {
        return population;
    }

    public double getAreaKm() {
        return areaKm;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setCountryFlag(String countryCode) {
        this.countryFlag = "http://api.geonames.org/flags/x/" + countryCode.toLowerCase() + ".gif";
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setAreaKm(double areaKm) {
        this.areaKm = areaKm;
    }
}
