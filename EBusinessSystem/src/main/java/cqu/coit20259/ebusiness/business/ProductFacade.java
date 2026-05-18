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
        if ("tablet".equalsIgnoreCase(type)) {
            // Searches for a tablet product.
            return em.find(cqu.coit20259.ebusiness.persistence.Tablet.class, id);
        } else {
            // Searches for a smartwatch product.
            return em.find(cqu.coit20259.ebusiness.persistence.Smartwatch.class, id);
        }
    }

    // Reduces product stock when a new order is created.
    public boolean deductStock(Long productId, String type, int quantity) {
        if ("tablet".equalsIgnoreCase(type)) {
            var tablet = em.find(cqu.coit20259.ebusiness.persistence.Tablet.class, productId);
            if (tablet != null && tablet.getStock() >= quantity) {
                tablet.setStock(tablet.getStock() - quantity);
                em.merge(tablet); // Saves the updated stock in the database.
                return true;
            }
        } else {
            var smartwatch = em.find(cqu.coit20259.ebusiness.persistence.Smartwatch.class, productId);
            if (smartwatch != null && smartwatch.getStock() >= quantity) {
                smartwatch.setStock(smartwatch.getStock() - quantity);
                em.merge(smartwatch);
                return true;
            }
        }
        return false; // Returns false if the product does not exist or stock is not enough.
    }

    // Restores product stock when an order is removed.
    public void restoreStock(Long productId, String type, int quantity) {
        if ("tablet".equalsIgnoreCase(type)) {
            var tablet = em.find(cqu.coit20259.ebusiness.persistence.Tablet.class, productId);
            if (tablet != null) {
                tablet.setStock(tablet.getStock() + quantity);
                em.merge(tablet);
            }
        } else {
            var smartwatch = em.find(cqu.coit20259.ebusiness.persistence.Smartwatch.class, productId);
            if (smartwatch != null) {
                smartwatch.setStock(smartwatch.getStock() + quantity);
                em.merge(smartwatch);
            }
        }
    }
    
    // Returns all tablet products.
    public List<cqu.coit20259.ebusiness.persistence.Tablet> findAllTablets() {
        return em.createQuery("SELECT t FROM Tablet t", cqu.coit20259.ebusiness.persistence.Tablet.class).getResultList();
    }

    // Returns all smartwatch products.
    public List<cqu.coit20259.ebusiness.persistence.Smartwatch> findAllSmartwatches() {
        return em.createQuery("SELECT s FROM Smartwatch s", cqu.coit20259.ebusiness.persistence.Smartwatch.class).getResultList();
    }

    // Creates a new tablet product.
    public void createTablet(cqu.coit20259.ebusiness.persistence.Tablet tablet) {
        em.persist(tablet);
    }

    // Creates a new smartwatch product.
    public void createSmartwatch(cqu.coit20259.ebusiness.persistence.Smartwatch smartwatch) {
        em.persist(smartwatch);
    }
}