package com.ebusiness.presentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for tablet-related JSF pages.
 *
 * This class stores temporary tablet form data and provides placeholder action
 * methods for creating and searching tablet records.
 *
 * The actual persistence and retrieval logic will be connected later through
 * the business tier.
 *
 * @author Jerald Christopher Bucud
 */
@Named("tabletBean")
@RequestScoped
public class TabletBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String brand;
    private String model;
    private String displaySize;
    private String weight;
    private String operatingSystem;
    private String connectivity;
    private String wifiCapability;
    private String storageCapacity;
    private String stylusSupport;
    private String batteryCapacity;
    private String stockQuantity;
    private String searchKeyword;

    /**
     * Placeholder action for creating a tablet record.
     *
     * @return navigation outcome for the tablet stock list page
     */
    public String createTablet() {
        return "listTablets?faces-redirect=true";
    }

    /**
     * Placeholder action for searching tablet records.
     *
     * @return navigation outcome for the tablet search page
     */
    public String searchTablet() {
        return "searchTablet";
    }

    /**
     * Provides temporary tablet rows for the JSF table display.
     *
     * This placeholder data will later be replaced by results returned from the
     * business tier.
     *
     * @return list of tablet rows for display
     */
    public List<TabletRow> getTabletRows() {

        List<TabletRow> tablets = new ArrayList<>();

        tablets.add(new TabletRow(
                "Sample Brand",
                "Sample Tablet Model",
                "11 inch",
                "128 GB",
                "Yes",
                "8000 mAh",
                "100"
        ));

        tablets.add(new TabletRow(
                "Demo Brand",
                "Demo Tablet Pro",
                "12.9 inch",
                "256 GB",
                "Yes",
                "10000 mAh",
                "50"
        ));

        return tablets;
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

    public String getStorageCapacity() {
        return storageCapacity;
    }

    public void setStorageCapacity(String storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    public String getStylusSupport() {
        return stylusSupport;
    }

    public void setStylusSupport(String stylusSupport) {
        this.stylusSupport = stylusSupport;
    }

    public String getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(String batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
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
     * Simple display row used by the tablet JSF table.
     *
     * This class is temporary presentation-layer display data and will be
     * replaced or mapped from entity results after EJB integration.
     */
    public static class TabletRow {

        private final String brand;
        private final String model;
        private final String displaySize;
        private final String storageCapacity;
        private final String stylusSupport;
        private final String batteryCapacity;
        private final String stockQuantity;

        public TabletRow(String brand,
                String model,
                String displaySize,
                String storageCapacity,
                String stylusSupport,
                String batteryCapacity,
                String stockQuantity) {
            this.brand = brand;
            this.model = model;
            this.displaySize = displaySize;
            this.storageCapacity = storageCapacity;
            this.stylusSupport = stylusSupport;
            this.batteryCapacity = batteryCapacity;
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

        public String getStorageCapacity() {
            return storageCapacity;
        }

        public String getStylusSupport() {
            return stylusSupport;
        }

        public String getBatteryCapacity() {
            return batteryCapacity;
        }

        public String getStockQuantity() {
            return stockQuantity;
        }
    }
}
