package cqu.coit20259.ebusiness.business;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import cqu.coit20259.ebusiness.persistence.Order;
import java.util.List;
import cqu.coit20259.ebusiness.business.exception.InsufficientStockException;

/**
 *
 * @author Cardoso Pepe
 */
@Stateless
public class OrderBean {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @EJB
    private ProductFacade productFacade;

    // Creates a new order after checking if enough stock is available.
    public String createOrder(Order order, Long productId, String productType) throws InsufficientStockException {
        // Deducts the product stock before saving the order.
        boolean stockDeducted = productFacade.deductStock(productId, productType, order.getQuantity());
        
        if (!stockDeducted) {
            throw new InsufficientStockException("Not enough stock available for this product.");
        }
        
        // Saves the order in the database when stock is available.
        em.persist(order);
        return "SUCCESS: Orden creada exitosamente.";
    }

    // Deletes an order and restores the product stock.
    public void deleteOrder(Long orderId) {
        Order order = em.find(Order.class, orderId);
        if (order != null) {
            // Adds the ordered quantity back to the product stock.
            productFacade.restoreStock(order.getProductId(), order.getProductType(), order.getQuantity());
            
            // Removes the order from the database.
            em.remove(order);
        }
    }

    // Returns all orders for the administrator table.
    public List<Order> findAllOrders() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }
    
    // Finds an order by its ID.
    public Order findOrderById(Long id) {
        return em.find(Order.class, id);
    }
}