/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.coit20259.ebusiness.business;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import cqu.coit20259.ebusiness.persistence.Order; // Entidad de tu compañero de persistencia
import java.util.List;

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

    // REGLA DE NEGOCIO COMPLETA: Crear Orden con validación de Stock
    public String createOrder(Order order, Long productId, String productType) {
        // 1. Intentar descontar el stock primero
        boolean stockDeducted = productFacade.deductStock(productId, productType, order.getQuantity());
        
        if (!stockDeducted) {
            // Regla de negocio rota: No hay suficiente inventario
            return "ERROR: No hay suficiente stock disponible para completar el pedido.";
        }
        
        // 2. Si hay stock, se guarda la orden de manera persistente
        em.persist(order);
        return "SUCCESS: Orden creada exitosamente.";
    }

    // REGLA DE NEGOCIO COMPLETA: Eliminar Orden y restaurar inventario
    public void deleteOrder(Long orderId) {
        Order order = em.find(Order.class, orderId);
        if (order != null) {
            // Restaurar las unidades al inventario antes de borrar la orden
            productFacade.restoreStock(order.getProductId(), order.getProductType(), order.getQuantity());
            
            // Eliminar la orden de la base de datos
            em.remove(order);
        }
    }

    // Obtener todas las órdenes para la tabla del administrador
    public List<Order> findAllOrders() {
        return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    }
    
    // Buscar una orden por su ID
    public Order findOrderById(Long id) {
        return em.find(Order.class, id);
    }
}