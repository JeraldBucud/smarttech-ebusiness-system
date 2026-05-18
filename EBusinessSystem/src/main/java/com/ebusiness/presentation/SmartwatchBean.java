package com.ebusiness.presentation;

import cqu.coit20259.ebusiness.business.ProductFacade;
import cqu.coit20259.ebusiness.persistence.Smartwatch;
import jakarta.ejb.EJB;
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
    
    @EJB
    private ProductFacade productFacade;
    
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
     * Creates a smartwatch record through the business tier.
     *
     * @return navigation outcome for the smartwatch stock list page
     */
    public String createSmartwatch() {

        Smartwatch smartwatch = new Smartwatch();

        smartwatch.setBrand(brand);
        smartwatch.setModel(model);
        smartwatch.setDisplaySize(displaySize);
        smartwatch.setWeight(weight);
        smartwatch.setOperatingSystem(operatingSystem);
        smartwatch.setConnectivity(connectivity);
        smartwatch.setWifiCapability(wifiCapability);
        smartwatch.setHealthMonitoring(healthMonitoring);
        smartwatch.setFitnessTracking(fitnessTracking);
        smartwatch.setWearableConnectivity(wearableConnectivity);
        smartwatch.setStock(Integer.parseInt(stockQuantity));

        productFacade.createSmartwatch(smartwatch);

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
     * Retrieves smartwatch rows for JSF list table display.
     *
     * @return list of smartwatch rows for display
     */
    public List<SmartwatchRow> getSmartwatchRows() {

        List<SmartwatchRow> smartwatchRows = new ArrayList<>();

        List<Smartwatch> smartwatches = productFacade.findAllSmartwatches();

        for (Smartwatch smartwatch : smartwatches) {
            smartwatchRows.add(new SmartwatchRow(
                    smartwatch.getBrand(),
                    smartwatch.getModel(),
                    smartwatch.getDisplaySize(),
                    smartwatch.getHealthMonitoring(),
                    smartwatch.getFitnessTracking(),
                    smartwatch.getWearableConnectivity(),
                    String.valueOf(smartwatch.getStock())
            ));
        }

        return smartwatchRows;
    }
    
        /**
     * Retrieves filtered smartwatch rows for JSF search table display.
     *
     * This method performs presentation-level filtering only. The search can
     * later be moved into the business tier if required.
     *
     * @return filtered smartwatch rows for display
     */
    public List<SmartwatchRow> getFilteredSmartwatchRows() {

        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return getSmartwatchRows();
        }

        String keyword = searchKeyword.trim().toLowerCase();
        List<SmartwatchRow> filteredRows = new ArrayList<>();

        for (SmartwatchRow smartwatch : getSmartwatchRows()) {
            if (smartwatch.getBrand().toLowerCase().contains(keyword)
                    || smartwatch.getModel().toLowerCase().contains(keyword)
                    || smartwatch.getDisplaySize().toLowerCase().contains(keyword)
                    || smartwatch.getHealthMonitoring().toLowerCase().contains(keyword)
                    || smartwatch.getFitnessTracking().toLowerCase().contains(keyword)
                    || smartwatch.getWearableConnectivity().toLowerCase().contains(keyword)) {
                filteredRows.add(smartwatch);
            }
        }

        return filteredRows;
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
