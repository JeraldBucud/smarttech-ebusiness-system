package com.ebusiness.presentation;

import cqu.coit20259.ebusiness.persistence.Order;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Backing bean for order-related JSF pages.
 *
 * This class stores order form data, calls the business tier for order
 * creation and retrieval, and prepares order display rows for JSF pages.
 *
 * The business tier handles order creation, stock deduction, order deletion,
 * and persistence operations.
 *
 * @author Jerald Christopher Bucud
 */
@Named("orderBean")
@RequestScoped
public class OrderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private cqu.coit20259.ebusiness.business.OrderBean orderService;

    private String customer;
    private String productType;
    private String product;
    private String quantity;
    private String orderNotes;
    private String searchKeyword;

    /**
     * Creates an order through the business tier.
     *
     * @return navigation outcome for the order list page
     */
    public String createOrder() {

        try {
            Order order = new Order();

            Long productId = Long.parseLong(product);
            int orderQuantity = Integer.parseInt(quantity);

            order.setProductId(productId);
            order.setProductType(productType);
            order.setQuantity(orderQuantity);

            orderService.createOrder(order, productId, productType);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Order Created",
                            "The order has been created successfully."));

            return "listOrders?faces-redirect=true";

        } catch (NumberFormatException exception) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Invalid Order Data",
                            "Product and quantity values must be valid numbers."));

            return null;

        } catch (Exception exception) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Order Creation Failed",
                            exception.getMessage()));

            return null;
        }
    }

    /**
     * Keeps the user on the order search page after a search request.
     *
     * @return navigation outcome for the order search page
     */
    public String searchOrder() {
        return "searchOrder";
    }

    /**
     * Retrieves order rows for JSF list table display.
     *
     * @return list of order rows for display
     */
    public List<OrderRow> getOrderRows() {

        List<OrderRow> orderRows = new ArrayList<>();

        List<Order> orders = orderService.findAllOrders();

        for (Order order : orders) {
            orderRows.add(new OrderRow(
                    String.valueOf(order.getId()),
                    getDisplayCustomerName(order),
                    getDisplayCustomerEmail(order),
                    order.getProductType(),
                    getDisplayBrand(order),
                    getDisplayProductModel(order),
                    String.valueOf(order.getQuantity()),
                    getDisplayStatus(order),
                    getDisplayStockImpact(order),
                    getDisplayOrderNotes(order)
            ));
        }

        return orderRows;
    }

    /**
     * Provides a selected order for the order details page.
     *
     * This currently returns the first available order until row-level
     * selection is connected.
     *
     * @return selected order row for display
     */
    public OrderRow getSelectedOrder() {

        List<OrderRow> orders = getOrderRows();

        if (orders.isEmpty()) {
            return new OrderRow(
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
            );
        }

        return orders.get(0);
    }

    /**
     * Retrieves filtered order rows for JSF search table display.
     *
     * @return filtered order rows for display
     */
    public List<OrderRow> getFilteredOrderRows() {

        if (searchKeyword == null || searchKeyword.trim().isEmpty()) {
            return getOrderRows();
        }

        String keyword = searchKeyword.trim().toLowerCase();
        List<OrderRow> filteredRows = new ArrayList<>();

        for (OrderRow order : getOrderRows()) {
            if (order.getOrderId().toLowerCase().contains(keyword)
                    || order.getCustomerName().toLowerCase().contains(keyword)
                    || order.getCustomerEmail().toLowerCase().contains(keyword)
                    || order.getProductType().toLowerCase().contains(keyword)
                    || order.getBrand().toLowerCase().contains(keyword)
                    || order.getProductModel().toLowerCase().contains(keyword)
                    || order.getStatus().toLowerCase().contains(keyword)) {
                filteredRows.add(order);
            }
        }

        return filteredRows;
    }

    /**
     * Provides display customer name for an order row.
     *
     * @param order order entity
     * @return customer display name
     */
    private String getDisplayCustomerName(Order order) {
        if (customer != null && !customer.trim().isEmpty()) {
            return customer;
        }

        return "Customer " + order.getId();
    }

    /**
     * Provides display customer email for an order row.
     *
     * @param order order entity
     * @return customer display email
     */
    private String getDisplayCustomerEmail(Order order) {
        return "customer" + order.getId() + "@email.com";
    }

    /**
     * Provides display product brand for an order row.
     *
     * @param order order entity
     * @return product brand display text
     */
    private String getDisplayBrand(Order order) {
        return "Product Brand";
    }

    /**
     * Provides display product model for an order row.
     *
     * @param order order entity
     * @return product model display text
     */
    private String getDisplayProductModel(Order order) {
        return "Product ID " + order.getProductId();
    }

    /**
     * Provides display status for an order row.
     *
     * @param order order entity
     * @return order status
     */
    private String getDisplayStatus(Order order) {
        return "Created";
    }

    /**
     * Provides stock impact text for an order row.
     *
     * @param order order entity
     * @return stock impact display text
     */
    private String getDisplayStockImpact(Order order) {
        return "Stock quantity reduced by " + order.getQuantity()
                + " after order creation.";
    }

    /**
     * Provides order notes text for an order row.
     *
     * @param order order entity
     * @return order notes display text
     */
    private String getDisplayOrderNotes(Order order) {
        if (orderNotes != null && !orderNotes.trim().isEmpty()) {
            return orderNotes;
        }

        return "Order created through the e-business system.";
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
     * Simple display row used by order JSF tables.
     */
    public static class OrderRow {

        private final String orderId;
        private final String customerName;
        private final String customerEmail;
        private final String productType;
        private final String brand;
        private final String productModel;
        private final String quantity;
        private final String status;
        private final String stockImpact;
        private final String orderNotes;

        public OrderRow(String orderId,
                        String customerName,
                        String customerEmail,
                        String productType,
                        String brand,
                        String productModel,
                        String quantity,
                        String status,
                        String stockImpact,
                        String orderNotes) {
            this.orderId = orderId;
            this.customerName = customerName;
            this.customerEmail = customerEmail;
            this.productType = productType;
            this.brand = brand;
            this.productModel = productModel;
            this.quantity = quantity;
            this.status = status;
            this.stockImpact = stockImpact;
            this.orderNotes = orderNotes;
        }

        public String getOrderId() {
            return orderId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getCustomerEmail() {
            return customerEmail;
        }

        public String getProductType() {
            return productType;
        }

        public String getBrand() {
            return brand;
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

        public String getStockImpact() {
            return stockImpact;
        }

        public String getOrderNotes() {
            return orderNotes;
        }
    }
}