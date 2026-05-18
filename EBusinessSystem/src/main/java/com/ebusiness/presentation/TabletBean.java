package com.ebusiness.presentation;

import cqu.coit20259.ebusiness.business.ProductFacade;
import cqu.coit20259.ebusiness.persistence.Tablet;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for tablet-related JSF pages.
 *
 * This class stores tablet form data, calls the business tier for product
 * creation and retrieval, and prepares tablet display rows for JSF pages.
 *
 * @author Jerald Christopher Bucud
 */
@Named("tabletBean")
@RequestScoped
public class TabletBean implements Serializable {

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
    private String storageCapacity;
    private String stylusSupport;
    private String batteryCapacity;
    private String stockQuantity;
    private String searchKeyword;

    /**
     * Creates a tablet record through the business tier.
     *
     * @return navigation outcome for the tablet stock list page
     */
    public String createTablet() {

        Tablet tablet = new Tablet();

        tablet.setBrand(brand);
        tablet.setModel(model);
        tablet.setDisplaySize(displaySize);
        tablet.setWeight(weight);
        tablet.setOperatingSystem(operatingSystem);
        tablet.setConnectivity(connectivity);
        tablet.setWifiCapability(wifiCapability);
        tablet.setStorageCapacity(storageCapacity);
        tablet.setStylusSupport(stylusSupport);
        tablet.setBatteryCapacity(batteryCapacity);
        tablet.setStock(Integer.parseInt(stockQuantity));

        productFacade.createTablet(tablet);

        return "listTablets?faces-redirect=true";
    }

    /**
     * Keeps the user on the tablet search page after a search request.
     *
     * @return navigation outcome for the tablet search page
     */
    public String searchTablet() {
        return "searchTablet";
    }

    /**
     * Retrieves tablet rows for JSF list table display.
     *
     * @return list of tablet rows for display
     */
    public List<TabletRow> getTabletRows() {

        List<TabletRow> tabletRows = new ArrayList<>();

        List<Tablet> tablets = productFacade.findAllTablets();

        for (Tablet tablet : tablets) {
            tabletRows.add(new TabletRow(
                    tablet.getBrand(),
                    tablet.getModel(),
                    tablet.getDisplaySize(),
                    tablet.getStorageCapacity(),
                    tablet.getStylusSupport(),
                    tablet.getBatteryCapacity(),
                    String.valueOf(tablet.getStock())
            ));
        }

        return tabletRows;
    }

    /**
     * Retrieves filtered tablet rows for JSF search table display.
     *
     * This method performs presentation-level filtering only. The search can
     * later be moved into the business tier if required.
     *
     * @return filtered tablet rows for display
     */
    public List<TabletRow> getFilteredTabletRows() {

        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return getTabletRows();
        }

        String keyword = searchKeyword.trim().toLowerCase();
        List<TabletRow> filteredRows = new ArrayList<>();

        for (TabletRow tablet : getTabletRows()) {
            if (tablet.getBrand().toLowerCase().contains(keyword)
                    || tablet.getModel().toLowerCase().contains(keyword)
                    || tablet.getDisplaySize().toLowerCase().contains(keyword)
                    || tablet.getStorageCapacity().toLowerCase().contains(keyword)
                    || tablet.getStylusSupport().toLowerCase().contains(keyword)
                    || tablet.getBatteryCapacity().toLowerCase().contains(keyword)) {
                filteredRows.add(tablet);
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
     * Simple display row used by tablet JSF tables.
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