package cqu.coit20259.ebusiness.business;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import cqu.coit20259.ebusiness.persistence.Customer;
import java.util.Random;
import cqu.coit20259.ebusiness.business.exception.CustomerAlreadyExistsException;

/**
 *
 * @author Cardoso Pepe
 */
@Stateless
public class CustomerBean {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    // Generates a simulated verification code for the customer's email.
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Creates a 6-digit code.
        return String.valueOf(code);
    }

    // Registers a new customer after checking if the email already exists.
    public boolean registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        // Checks if the email is already registered in the database.
        Long count = em.createQuery("SELECT COUNT(c) FROM Customer c WHERE c.email = :email", Long.class)
                .setParameter("email", customer.getEmail())
                .getSingleResult();

        if (count > 0) {

            throw new CustomerAlreadyExistsException("The email address is already registered.");
        }

        em.persist(customer);
        return true;
    }

    // Checks the customer's login details.
    public Customer login(String email, String password) {

        String cleanEmail = email == null ? "" : email.trim();
        String cleanPassword = password == null ? "" : password.trim();


        java.util.List<Customer> matchingCustomers = em.createQuery(
                "SELECT c FROM Customer c "
                + "WHERE LOWER(TRIM(c.email)) = :email "
                + "AND TRIM(c.password) = :password",
                Customer.class)
                .setParameter("email", cleanEmail.toLowerCase())
                .setParameter("password", cleanPassword)
                .getResultList();


        if (matchingCustomers.isEmpty()) {
            return null;
        }

        return matchingCustomers.get(0);
    }

    // Finds a customer by ID.
    public Customer findCustomerById(Long id) {
        return em.find(Customer.class, id);
    }

    // Returns all registered customers.
    public java.util.List<Customer> findAllCustomers() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }
}
