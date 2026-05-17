package cqu.coit20259.ebusiness.business;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author Cardoso Pepe
 */
@Stateless
public class ProductFacade {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    // Finds a product by ID depending on its type.
    public Object findProduct(Long id, String type) {
        if ("laptop".equalsIgnoreCase(type)) {
            // Searches for a laptop product.
            return em.find(cqu.coit20259.ebusiness.persistence.Laptop.class, id);
        } else {
            // Searches for a smartphone product.
            return em.find(cqu.coit20259.ebusiness.persistence.Smartphone.class, id);
        }
    }

    // Reduces product stock when a new order is created.
    public boolean deductStock(Long productId, String type, int quantity) {
        if ("laptop".equalsIgnoreCase(type)) {
            var laptop = em.find(cqu.coit20259.ebusiness.persistence.Laptop.class, productId);
            if (laptop != null && laptop.getStock() >= quantity) {
                laptop.setStock(laptop.getStock() - quantity);
                em.merge(laptop); // Saves the updated stock in the database.
                return true;
            }
        } else {
            var phone = em.find(cqu.coit20259.ebusiness.persistence.Smartphone.class, productId);
            if (phone != null && phone.getStock() >= quantity) {
                phone.setStock(phone.getStock() - quantity);
                em.merge(phone);
                return true;
            }
        }
        return false; // Returns false if the product does not exist or stock is not enough.
    }

    // Restores product stock when an order is removed.
    public void restoreStock(Long productId, String type, int quantity) {
        if ("laptop".equalsIgnoreCase(type)) {
            var laptop = em.find(cqu.coit20259.ebusiness.persistence.Laptop.class, productId);
            if (laptop != null) {
                laptop.setStock(laptop.getStock() + quantity);
                em.merge(laptop);
            }
        } else {
            var phone = em.find(cqu.coit20259.ebusiness.persistence.Smartphone.class, productId);
            if (phone != null) {
                phone.setStock(phone.getStock() + quantity);
                em.merge(phone);
            }
        }
    }
    
    // Returns all laptop products.
    public List<cqu.coit20259.ebusiness.persistence.Laptop> findAllLaptops() {
        return em.createQuery("SELECT l FROM Laptop l", cqu.coit20259.ebusiness.persistence.Laptop.class).getResultList();
    }

    // Returns all smartphone products.
    public List<cqu.coit20259.ebusiness.persistence.Smartphone> findAllSmartphones() {
        return em.createQuery("SELECT s FROM Smartphone s", cqu.coit20259.ebusiness.persistence.Smartphone.class).getResultList();
    }

    // Creates a new laptop product.
    public void createLaptop(cqu.coit20259.ebusiness.persistence.Laptop laptop) {
        em.persist(laptop);
    }

    // Creates a new smartphone product.
    public void createSmartphone(cqu.coit20259.ebusiness.persistence.Smartphone phone) {
        em.persist(phone);
    }
}