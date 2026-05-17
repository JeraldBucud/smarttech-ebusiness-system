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
@Table(name = "SMARTPHONES")
public class Smartphone implements Serializable {
    
    @Id
    private Long id;
    private String brand;
    private String model;
    private double price;
    private int stock;

    // Default constructor required by JPA.
    public Smartphone() {}

    // Getter and setter methods for smartphone data.
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
}

