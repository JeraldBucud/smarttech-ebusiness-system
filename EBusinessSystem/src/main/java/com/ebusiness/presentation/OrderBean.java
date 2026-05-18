package com.ebusiness.presentation;

import cqu.coit20259.ebusiness.business.ProductFacade;
import cqu.coit20259.ebusiness.persistence.Customer;
import cqu.coit20259.ebusiness.persistence.Order;
import cqu.coit20259.ebusiness.persistence.Smartwatch;
import cqu.coit20259.ebusiness.persistence.Tablet;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
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
 * @author Jerald Christopher Bucud
 */
@Named("orderBean")
@ViewScoped
public class OrderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private ProductFacade productFacade;

    @EJB
    private cqu.coit20259.ebusiness.business.CustomerBean customerService;

    @EJB
    private cqu.coit20259.ebusiness.business.OrderBean orderService;

    private String customer;
    private String productType;
    private String product;
    private String quantity;
    private String orderNotes;
    private String searchKeyword;
    private String selectedOrderId;

    /**
     * Creates an order through the business tier.
     *
     * @return navigation outcome for the order list page
     */
    public String createOrder() {

        try {
            Order order = new Order();

            Long customerId = Long.parseLong(customer);
            Long productId = Long.parseLong(product);
            int orderQuantity = Integer.parseInt(quantity);

            order.setCustomerId(customerId);
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
                            "Customer, product, and quantity values must be valid."));

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
     * @return null to remain on the same JSF view
     */
    public String searchOrder() {
        return null;
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
                    safeText(order.getProductType()),
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
     * Provides the selected order for the order details page.
     *
     * @return selected order row for display
     */
    public OrderRow getSelectedOrder() {

        List<OrderRow> orders = getOrderRows();

        if (orders.isEmpty()) {
            return new OrderRow("", "", "", "", "", "", "", "", "", "");
        }

        if (selectedOrderId == null || selectedOrderId.trim().isEmpty()) {
            return orders.get(0);
        }

        for (OrderRow order : orders) {
            if (order.getOrderId().equals(selectedOrderId)) {
                return order;
            }
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
            if (safeText(order.getOrderId()).toLowerCase().contains(keyword)
                    || safeText(order.getCustomerName()).toLowerCase().contains(keyword)
                    || safeText(order.getCustomerEmail()).toLowerCase().contains(keyword)
                    || safeText(order.getProductType()).toLowerCase().contains(keyword)
                    || safeText(order.getBrand()).toLowerCase().contains(keyword)
                    || safeText(order.getProductModel()).toLowerCase().contains(keyword)
                    || safeText(order.getQuantity()).toLowerCase().contains(keyword)
                    || safeText(order.getStatus()).toLowerCase().contains(keyword)
                    || safeText(order.getStockImpact()).toLowerCase().contains(keyword)
                    || safeText(order.getOrderNotes()).toLowerCase().contains(keyword)) {
                filteredRows.add(order);
            }
        }

        return filteredRows;
    }

    /**
     * Provides customer dropdown options from registered customers.
     *
     * @return customer select options
     */
    public List<SelectItem> getCustomerOptions() {

        List<SelectItem> options = new ArrayList<>();
        options.add(new SelectItem("", "Select Customer"));

        List<Customer> customers = customerService.findAllCustomers();

        for (Customer customerRecord : customers) {
            String firstName = safeText(customerRecord.getFirstName());
            String lastName = safeText(customerRecord.getLastName());
            String fullName = (firstName + " " + lastName).trim();

            if (fullName.isEmpty()) {
                fullName = safeText(customerRecord.getEmail());
            }

            String label = customerRecord.getId() + " - "
                    + fullName
                    + " (" + safeText(customerRecord.getEmail()) + ")";

            options.add(new SelectItem(String.valueOf(customerRecord.getId()), label));
        }

        return options;
    }

    /**
     * Provides product dropdown options based on selected product type.
     *
     * @return product select options
     */
    public List<SelectItem> getProductOptions() {

        List<SelectItem> options = new ArrayList<>();
        options.add(new SelectItem("", "Select Product"));

        if (productType == null || productType.trim().isEmpty()) {
            return options;
        }

        if ("Tablet".equalsIgnoreCase(productType)) {
            List<Tablet> tablets = productFacade.findAllTablets();

            for (Tablet tablet : tablets) {
                String label = safeText(tablet.getBrand()) + " "
                        + safeText(tablet.getModel())
                        + " - Stock: " + tablet.getStock();

                options.add(new SelectItem(String.valueOf(tablet.getId()), label));
            }

            return options;
        }

        if ("Smartwatch".equalsIgnoreCase(productType)) {
            List<Smartwatch> smartwatches = productFacade.findAllSmartwatches();

            for (Smartwatch smartwatch : smartwatches) {
                String label = safeText(smartwatch.getBrand()) + " "
                        + safeText(smartwatch.getModel())
                        + " - Stock: " + smartwatch.getStock();

                options.add(new SelectItem(String.valueOf(smartwatch.getId()), label));
            }
        }

        return options;
    }

    private String getDisplayCustomerName(Order order) {

        if (order.getCustomerId() == null) {
            return "";
        }

        Customer customerRecord = customerService.findCustomerById(order.getCustomerId());

        if (customerRecord == null) {
            return "Customer " + order.getCustomerId();
        }

        String firstName = safeText(customerRecord.getFirstName());
        String lastName = safeText(customerRecord.getLastName());
        String fullName = (firstName + " " + lastName).trim();

        if (fullName.isEmpty()) {
            return safeText(customerRecord.getEmail());
        }

        return fullName;
    }

    private String getDisplayCustomerEmail(Order order) {

        if (order.getCustomerId() == null) {
            return "";
        }

        Customer customerRecord = customerService.findCustomerById(order.getCustomerId());

        if (customerRecord == null) {
            return "";
        }

        return safeText(customerRecord.getEmail());
    }

    private String getDisplayBrand(Order order) {

        Object productRecord = productFacade.findProduct(
                order.getProductId(),
                order.getProductType()
        );

        if (productRecord instanceof Tablet) {
            return safeText(((Tablet) productRecord).getBrand());
        }

        if (productRecord instanceof Smartwatch) {
            return safeText(((Smartwatch) productRecord).getBrand());
        }

        return "";
    }

    private String getDisplayProductModel(Order order) {

        Object productRecord = productFacade.findProduct(
                order.getProductId(),
                order.getProductType()
        );

        if (productRecord instanceof Tablet) {
            return safeText(((Tablet) productRecord).getModel());
        }

        if (productRecord instanceof Smartwatch) {
            return safeText(((Smartwatch) productRecord).getModel());
        }

        return "";
    }

    private String getDisplayStatus(Order order) {
        return "Created";
    }

    private String getDisplayStockImpact(Order order) {
        return "Stock quantity reduced by " + order.getQuantity()
                + " after order creation.";
    }

    private String getDisplayOrderNotes(Order order) {
        if (orderNotes != null && !orderNotes.trim().isEmpty()) {
            return orderNotes;
        }

        return "Order created through the e-business system.";
    }
    
        /**
     * Deletes an order through the business tier.
     *
     * The business layer restores the ordered product stock before removing
     * the order record.
     *
     * @param orderId selected order ID
     * @return navigation outcome for refreshing the order list
     */
    public String deleteOrder(String orderId) {

        try {
            Long selectedOrderId = Long.parseLong(orderId);

            orderService.deleteOrder(selectedOrderId);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Order Deleted",
                            "The order has been deleted successfully."));

            return "listOrders?faces-redirect=true";

        } catch (NumberFormatException exception) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Delete Failed",
                            "The selected order ID is invalid."));

            return null;

        } catch (Exception exception) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Delete Failed",
                            exception.getMessage()));

            return null;
        }
    }

    private String safeText(String value) {
        return value == null ? "" : value;
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
        this.product = null;
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

    public String getSelectedOrderId() {
        return selectedOrderId;
    }

    public void setSelectedOrderId(String selectedOrderId) {
        this.selectedOrderId = selectedOrderId;
    }

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