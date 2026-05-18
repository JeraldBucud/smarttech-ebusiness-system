package com.ebusiness.presentation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for order-related JSF pages.
 *
 * This class stores temporary order form data and provides placeholder action
 * methods for creating and searching customer orders.
 *
 * The actual order creation, stock update, persistence, and retrieval logic
 * will be connected later through the business tier.
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

    /**
     * Provides temporary order rows for the JSF table display.
     *
     * This placeholder data will later be replaced by results returned from the
     * business tier.
     *
     * @return list of order rows for display
     */
    public List<OrderRow> getOrderRows() {

        List<OrderRow> orders = new ArrayList<>();

        orders.add(new OrderRow(
                "ORD-001",
                "Sample Customer",
                "Tablet",
                "Sample Tablet Model",
                "2",
                "Created"
        ));

        orders.add(new OrderRow(
                "ORD-002",
                "Sample Customer",
                "Smartwatch",
                "Sample Smartwatch Model",
                "1",
                "Created"
        ));

        return orders;
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

    /**
     * Simple display row used by the order JSF table.
     *
     * This class is temporary presentation-layer display data and will be
     * replaced or mapped from entity results after EJB integration.
     */
    public static class OrderRow {

        private final String orderId;
        private final String customerName;
        private final String productType;
        private final String productModel;
        private final String quantity;
        private final String status;

        public OrderRow(String orderId,
                String customerName,
                String productType,
                String productModel,
                String quantity,
                String status) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.productType = productType;
            this.productModel = productModel;
            this.quantity = quantity;
            this.status = status;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getProductType() {
            return productType;
        }

        public String getProductModel() {
            return productModel;
        }

        public String getQuantity() {
            return quantity;
        }

        public String getStatus() {
            return status;
        }
    }
}
