package com.ebusiness.presentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for customer-related JSF pages.
 *
 * This class stores temporary customer form data and provides placeholder
 * action methods for creating and searching customer records.
 *
 * The actual persistence and retrieval logic will be connected later through
 * the business tier.
 *
 * @author Jerald Christopher Bucud
 */
@Named("customerBean")
@RequestScoped
public class CustomerBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String address;
    private String searchKeyword;

    /**
     * Placeholder action for creating a customer record.
     *
     * @return navigation outcome for the customer list page
     */
    public String createCustomer() {
        return "listCustomers?faces-redirect=true";
    }

    /**
     * Placeholder action for searching customer records.
     *
     * @return navigation outcome for the customer search page
     */
    public String searchCustomer() {
        return "searchCustomer";
    }

    /**
     * Provides temporary customer rows for the JSF table display.
     *
     * This placeholder data will later be replaced by results returned from the
     * business tier.
     *
     * @return list of customer rows for display
     */
    public List<CustomerRow> getCustomerRows() {

        List<CustomerRow> customers = new ArrayList<>();

        customers.add(new CustomerRow(
                "CUST-001",
                "Sample",
                "Customer",
                "sample.customer@email.com",
                "0400 000 000",
                "Sample customer address"
        ));

        customers.add(new CustomerRow(
                "CUST-002",
                "Demo",
                "User",
                "demo.user@email.com",
                "0411 111 111",
                "Demo customer address"
        ));

        return customers;
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
     * Simple display row used by the customer JSF table.
     *
     * This class is temporary presentation-layer display data and will be
     * replaced or mapped from entity results after EJB integration.
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
