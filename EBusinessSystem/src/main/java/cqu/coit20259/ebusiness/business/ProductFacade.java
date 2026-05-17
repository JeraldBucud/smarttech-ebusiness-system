/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    // Método para buscar un producto por ID (puede ser Laptop o Smartphone)
    public Object findProduct(Long id, String type) {
        if ("laptop".equalsIgnoreCase(type)) {
            // Asumiendo que tu compañero de persistencia llame a la entidad 'Laptop'
            return em.find(cqu.coit20259.ebusiness.persistence.Laptop.class, id);
        } else {
            // Asumiendo que se llame 'Smartphone'
            return em.find(cqu.coit20259.ebusiness.persistence.Smartphone.class, id);
        }
    }

    // REGLA DE NEGOCIO: Reducir stock cuando se crea una orden
    public boolean deductStock(Long productId, String type, int quantity) {
        if ("laptop".equalsIgnoreCase(type)) {
            var laptop = em.find(cqu.coit20259.ebusiness.persistence.Laptop.class, productId);
            if (laptop != null && laptop.getStock() >= quantity) {
                laptop.setStock(laptop.getStock() - quantity);
                em.merge(laptop); // Actualiza en la BD
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
        return false; // No hay suficiente stock o no existe el producto
    }

    // REGLA DE NEGOCIO: Devolver stock si se elimina una orden
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
    
    // Obtener la lista de todas las Laptops
    public List<cqu.coit20259.ebusiness.persistence.Laptop> findAllLaptops() {
        return em.createQuery("SELECT l FROM Laptop l", cqu.coit20259.ebusiness.persistence.Laptop.class).getResultList();
    }

    // Obtener la lista de todos los Smartphones
    public List<cqu.coit20259.ebusiness.persistence.Smartphone> findAllSmartphones() {
        return em.createQuery("SELECT s FROM Smartphone s", cqu.coit20259.ebusiness.persistence.Smartphone.class).getResultList();
    }

    // Registrar una nueva Laptop (Panel de administración)
    public void createLaptop(cqu.coit20259.ebusiness.persistence.Laptop laptop) {
        em.persist(laptop);
    }

    // Registrar un nuevo Smartphone (Panel de administración)
    public void createSmartphone(cqu.coit20259.ebusiness.persistence.Smartphone phone) {
        em.persist(phone);
    }
}