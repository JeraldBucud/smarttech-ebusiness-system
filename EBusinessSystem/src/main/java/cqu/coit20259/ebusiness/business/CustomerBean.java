/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.coit20259.ebusiness.business;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import cqu.coit20259.ebusiness.persistence.Customer;
import java.util.Random;

/**
 *
 * @author Cardoso Pepe
 */

@Stateless
public class CustomerBean {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    // REGLA DE NEGOCIO: Generar código de verificación simulado para el email
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(code);
    }

    // REGLA DE NEGOCIO: Registrar cliente de forma segura
    public boolean registerCustomer(Customer customer) {
        // Verificar si el email ya existe en la base de datos
        Long count = em.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.email = :email", Long.class)
                .setParameter("email", customer.getEmail())
                .getSingleResult();
        
        if (count > 0) {
            return false; // El correo ya está registrado
        }
        
        em.persist(customer);
        return true;
    }

    // REGLA DE NEGOCIO: Validar credenciales de inicio de sesión
    public Customer login(String email, String password) {
        try {
            return em.createQuery("SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password", Customer.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Credenciales incorrectas o usuario no encontrado
        }
    }
    
    // Buscar un cliente específico por su ID
    public Customer findCustomerById(Long id) {
        return em.find(Customer.class, id);
    }

    // Obtener la lista de todos los clientes registrados
    public java.util.List<Customer> findAllCustomers() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
}