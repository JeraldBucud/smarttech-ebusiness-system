package com.ebusiness.presentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Backing bean for order-related JSF pages.
 *
 * This class stores temporary order form data and provides placeholder
 * action methods for creating and searching customer orders.
 *
 * The actual order creation, stock update, persistence, and retrieval
 * logic will be connected later through the business tier.
 *
 * @author Jerald Christopher Bucud
 */
@Named("orderBean")
@RequestScoped
public class OrderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String customer;
    private String productType;
    private String product;
    private String quantity;
    private String orderNotes;
    private String searchKeyword;

    /**
     * Placeholder action for creating an order.
     *
     * @return navigation outcome for the order list page
     */
    public String createOrder() {
        return "listOrders?faces-redirect=true";
    }

    /**
     * Placeholder action for searching order records.
     *
     * @return navigation outcome for the order search page
     */
    public String searchOrder() {
        return "searchOrder";
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderNotes() {
        return orderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        this.orderNotes = orderNotes;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }
}