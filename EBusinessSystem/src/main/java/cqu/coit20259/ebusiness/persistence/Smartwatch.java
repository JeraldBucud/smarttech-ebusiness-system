package cqu.coit20259.ebusiness.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Cardoso Pepe
 */
@Entity
@Table(name = "SMARTWATCHES")
public class Smartwatch implements Serializable {

    @Id
    private Long id;
    private String brand;
    private String model;
    private double price;
    private int stock;
    private String displaySize;
    private String weight;
    private String operatingSystem;
    private String connectivity;
    private String wifiCapability;
    private String healthMonitoring;
    private String fitnessTracking;
    private String wearableConnectivity;

    // Default constructor required by JPA.
    public Smartwatch() {
    }

    // Getter and setter methods for smartwatch data.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
}
