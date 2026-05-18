package com.ebusiness.presentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Backing bean for smartwatch-related JSF pages.
 *
 * This class stores temporary smartwatch form data and provides placeholder
 * action methods for creating and searching smartwatch records.
 *
 * The actual persistence and retrieval logic will be connected later
 * through the business tier.
 *
 * @author Jerald Christopher Bucud
 */
@Named("smartwatchBean")
@RequestScoped
public class SmartwatchBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brand;
    private String model;
    private String displaySize;
    private String weight;
    private String operatingSystem;
    private String connectivity;
    private String wifiCapability;
    private String healthMonitoring;
    private String fitnessTracking;
    private String wearableConnectivity;
    private String stockQuantity;
    private String searchKeyword;

    /**
     * Placeholder action for creating a smartwatch record.
     *
     * @return navigation outcome for the smartwatch stock list page
     */
    public String createSmartwatch() {
        return "listSmartwatches?faces-redirect=true";
    }

    /**
     * Placeholder action for searching smartwatch records.
     *
     * @return navigation outcome for the smartwatch search page
     */
    public String searchSmartwatch() {
        return "searchSmartwatch";
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(String displaySize) {
        this.displaySize = displaySize;
    }


    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }


    public String getConnectivity() {
        return connectivity;
    }

    public void setConnectivity(String connectivity) {
        this.connectivity = connectivity;
    }


    public String getWifiCapability() {
        return wifiCapability;
    }

    public void setWifiCapability(String wifiCapability) {
        this.wifiCapability = wifiCapability;
    }


    public String getHealthMonitoring() {
        return healthMonitoring;
    }

    public void setHealthMonitoring(String healthMonitoring) {
        this.healthMonitoring = healthMonitoring;
    }


    public String getFitnessTracking() {
        return fitnessTracking;
    }

    public void setFitnessTracking(String fitnessTracking) {
        this.fitnessTracking = fitnessTracking;
    }


    public String getWearableConnectivity() {
        return wearableConnectivity;
    }

    public void setWearableConnectivity(String wearableConnectivity) {
        this.wearableConnectivity = wearableConnectivity;
    }


    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }


    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}