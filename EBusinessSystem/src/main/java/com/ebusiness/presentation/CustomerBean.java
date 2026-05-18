package com.ebusiness.presentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Backing bean for customer-related JSF pages.
 *
 * This class stores temporary customer form data and provides placeholder
 * action methods for creating and searching customer records.
 *
 * The actual persistence and retrieval logic will be connected later
 * through the business tier.
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
}