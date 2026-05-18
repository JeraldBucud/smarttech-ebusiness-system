package com.ebusiness.presentation;

import cqu.coit20259.ebusiness.persistence.Customer;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for customer-related JSF pages.
 *
 * This class stores customer form data, calls the business tier for customer
 * creation and retrieval, and prepares customer display rows for JSF pages.
 *
 * @author Jerald Christopher Bucud
 */
@Named("customerBean")
@RequestScoped
public class CustomerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private cqu.coit20259.ebusiness.business.CustomerBean customerService;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private String searchKeyword;

    /**
     * Creates a customer record through the business tier.
     *
     * @return navigation outcome for the customer list page
     */
    public String createCustomer() {

        try {
            Customer customer = new Customer();

            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(emailAddress);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);

            /*
             * Password and username are not required for manually created
             * customer records in the customer management page.
             */
            customer.setUsername(emailAddress);
            customer.setPassword("");

            customerService.registerCustomer(customer);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Customer Created",
                            "The customer record has been created successfully."));

            return "listCustomers?faces-redirect=true";

        } catch (Exception exception) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Customer Creation Failed",
                            exception.getMessage()));

            return null;
        }
    }

    /**
     * Keeps the user on the customer search page after a search request.
     *
     * @return navigation outcome for the customer search page
     */
    public String searchCustomer() {
        return "searchCustomer";
    }

    /**
     * Retrieves customer rows for JSF list table display.
     *
     * @return list of customer rows for display
     */
    public List<CustomerRow> getCustomerRows() {

        List<CustomerRow> customerRows = new ArrayList<>();

        List<Customer> customers = customerService.findAllCustomers();

        for (Customer customer : customers) {
            customerRows.add(new CustomerRow(
                    String.valueOf(customer.getId()),
                    safeText(customer.getFirstName()),
                    safeText(customer.getLastName()),
                    safeText(customer.getEmail()),
                    safeText(customer.getPhoneNumber()),
                    safeText(customer.getAddress())
            ));
        }

        return customerRows;
    }

    /**
     * Provides a selected customer for the customer details page.
     *
     * This currently returns the first available customer until row-level
     * selection is connected.
     *
     * @return selected customer row for display
     */
    public CustomerRow getSelectedCustomer() {

        List<CustomerRow> customers = getCustomerRows();

        if (customers.isEmpty()) {
            return new CustomerRow("", "", "", "", "", "");
        }

        return customers.get(0);
    }

    /**
     * Retrieves filtered customer rows for JSF search table display.
     *
     * @return filtered customer rows for display
     */
    public List<CustomerRow> getFilteredCustomerRows() {

        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return getCustomerRows();
        }

        String keyword = searchKeyword.trim().toLowerCase();
        List<CustomerRow> filteredRows = new ArrayList<>();

        for (CustomerRow customer : getCustomerRows()) {
            if (safeText(customer.getCustomerId()).toLowerCase().contains(keyword)
                    || safeText(customer.getFirstName()).toLowerCase().contains(keyword)
                    || safeText(customer.getLastName()).toLowerCase().contains(keyword)
                    || safeText(customer.getEmailAddress()).toLowerCase().contains(keyword)
                    || safeText(customer.getPhoneNumber()).toLowerCase().contains(keyword)
                    || safeText(customer.getAddress()).toLowerCase().contains(keyword)) {
                filteredRows.add(customer);
            }
        }

        return filteredRows;
    }

    /**
     * Converts null text values into an empty string.
     *
     * @param value text value
     * @return safe text value
     */
    private String safeText(String value) {
        return value == null ? "" : value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    /**
     * Simple display row used by customer JSF tables.
     */
    public static class CustomerRow {

        private final String customerId;
        private final String firstName;
        private final String lastName;
        private final String emailAddress;
        private final String phoneNumber;
        private final String address;

        public CustomerRow(String customerId,
                           String firstName,
                           String lastName,
                           String emailAddress,
                           String phoneNumber,
                           String address) {
            this.customerId = customerId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getAddress() {
            return address;
        }
    }
}