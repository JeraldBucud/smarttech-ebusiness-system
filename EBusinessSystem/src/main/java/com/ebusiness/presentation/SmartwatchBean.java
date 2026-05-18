package com.ebusiness.presentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for smartwatch-related JSF pages.
 *
 * This class stores temporary smartwatch form data and provides placeholder
 * action methods for creating and searching smartwatch records.
 *
 * The actual persistence and retrieval logic will be connected later through
 * the business tier.
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

    /**
     * Provides temporary smartwatch rows for the JSF table display.
     *
     * This placeholder data will later be replaced by results returned from the
     * business tier.
     *
     * @return list of smartwatch rows for display
     */
    public List<SmartwatchRow> getSmartwatchRows() {

        List<SmartwatchRow> smartwatches = new ArrayList<>();

        smartwatches.add(new SmartwatchRow(
                "Sample Brand",
                "Sample Smartwatch Model",
                "1.9 inch",
                "Heart Rate",
                "Step Counter",
                "Bluetooth",
                "100"
        ));

        smartwatches.add(new SmartwatchRow(
                "Demo Brand",
                "Demo Watch Pro",
                "2.0 inch",
                "Heart Rate and Sleep Tracking",
                "Steps and Workout Modes",
                "Bluetooth and NFC",
                "75"
        ));

        return smartwatches;
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

    /**
     * Simple display row used by the smartwatch JSF table.
     *
     * This class is temporary presentation-layer display data and will be
     * replaced or mapped from entity results after EJB integration.
     */
    public static class SmartwatchRow {

        private final String brand;
        private final String model;
        private final String displaySize;
        private final String healthMonitoring;
        private final String fitnessTracking;
        private final String wearableConnectivity;
        private final String stockQuantity;

        public SmartwatchRow(String brand,
                String model,
                String displaySize,
                String healthMonitoring,
                String fitnessTracking,
                String wearableConnectivity,
                String stockQuantity) {
            this.brand = brand;
            this.model = model;
            this.displaySize = displaySize;
            this.healthMonitoring = healthMonitoring;
            this.fitnessTracking = fitnessTracking;
            this.wearableConnectivity = wearableConnectivity;
            this.stockQuantity = stockQuantity;
        }

        public String getBrand() {
            return brand;
        }

        public String getModel() {
            return model;
        }

        public String getDisplaySize() {
            return displaySize;
        }

        public String getHealthMonitoring() {
            return healthMonitoring;
        }

        public String getFitnessTracking() {
            return fitnessTracking;
        }

        public String getWearableConnectivity() {
            return wearableConnectivity;
        }

        public String getStockQuantity() {
            return stockQuantity;
        }
    }
}
