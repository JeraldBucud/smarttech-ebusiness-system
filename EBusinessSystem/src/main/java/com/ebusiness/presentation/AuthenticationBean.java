package com.ebusiness.presentation;

import cqu.coit20259.ebusiness.persistence.Customer;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import cqu.coit20259.ebusiness.business.EmailService;

/**
 * Backing bean for authentication-related JSF pages.
 *
 * This class stores temporary authentication form data and provides placeholder
 * action methods for login, registration, email verification, account recovery,
 * password reset, and logout.
 *
 * The actual authentication, email verification, account recovery, and
 * persistence logic will be connected later through the business tier.
 *
 * @author Jerald Christopher Bucud
 */
@Named("authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private cqu.coit20259.ebusiness.business.CustomerBean customerService;

    @EJB
    private EmailService emailService;

    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String verificationCode;
    private String recoveryCode;
    private String newPassword;
    private boolean loggedIn;
    private String generatedVerificationCode;
    private String generatedRecoveryCode;

    /**
     * Creates an authentication backing bean with the user logged out by
     * default.
     */
    public AuthenticationBean() {
        loggedIn = false;
    }

     /**
     * Authenticates the user through the business tier.
     *
     * @return navigation outcome for the secured main dashboard
     */
    public String login() {

        String cleanEmail = emailAddress == null ? "" : emailAddress.trim();
        String cleanPassword = password == null ? "" : password.trim();


        Customer customer = customerService.login(cleanEmail, cleanPassword);

        if (customer != null) {
            loggedIn = true;
            username = customer.getEmail();

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("loggedIn", true);

            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .getSessionMap()
                    .put("loggedInUser", username);

            return "main?faces-redirect=true";
        }

        loggedIn = false;


        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Login Failed",
                        "Invalid email address or password."));

        return null;
    }

    /**
     * Registers the user through the business tier.
     *
     * @return navigation outcome for email verification
     */
    public String register() {

        if (password == null || !password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Registration Failed",
                            "Password and confirmation password do not match."));

            return null;
        }

        try {
            Customer customer = new Customer();

            customer.setEmail(emailAddress);
            customer.setPassword(password);

            /*
             * The final Customer entity is expected to include these fields.
             */
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setUsername(username);

            generatedVerificationCode = customerService.generateVerificationCode();

            customerService.registerCustomer(customer);

            emailService.sendVerificationCode(emailAddress, generatedVerificationCode);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Registration Submitted",
                            "A verification code has been generated for this account."));

            return "emailVerification?faces-redirect=true";

        } catch (Exception exception) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Registration Failed",
                            exception.getMessage()));

            return null;
        }
    }

    /**
     * Verifies the email verification code entered by the user.
     *
     * @return navigation outcome for login page
     */
    public String verifyEmail() {

        if (verificationCode == null || verificationCode.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Verification Failed",
                            "Verification code is required."));

            return null;
        }

        if (generatedVerificationCode == null
                || !generatedVerificationCode.equals(verificationCode.trim())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Verification Failed",
                            "The verification code is incorrect."));

            return null;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Email Verified",
                        "The account has been verified successfully."));

        return "login?faces-redirect=true";
    }

    /**
     * Starts account recovery for the entered email address.
     *
     * @return navigation outcome for password reset page
     */
    public String recoverAccount() {

        generatedRecoveryCode = customerService.generateVerificationCode();
        
        emailService.sendRecoveryCode(emailAddress, generatedRecoveryCode);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Recovery Code Generated",
                        "A recovery code has been generated for the registered email address."));

        return "resetPassword?faces-redirect=true";
    }

    /**
     * Resets the user password after recovery code entry.
     *
     * @return navigation outcome for login page
     */
    public String resetPassword() {

        if (recoveryCode == null || recoveryCode.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Password Reset Failed",
                            "Recovery code is required."));

            return null;
        }

        if (generatedRecoveryCode == null
                || !generatedRecoveryCode.equals(recoveryCode.trim())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Password Reset Failed",
                            "The recovery code is incorrect."));

            return null;
        }

        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Password Reset Failed",
                            "New password and confirmation password do not match."));

            return null;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Password Reset",
                        "The password reset request has been accepted."));

        return "login?faces-redirect=true";
    }

    /**
     * Logs the user out and invalidates the current HTTP session.
     *
     * @return navigation outcome for logout page
     */
    public String logout() {
        loggedIn = false;

        FacesContext facesContext = FacesContext.getCurrentInstance();

        if (facesContext != null) {
            jakarta.servlet.http.HttpServletRequest request
                    = (jakarta.servlet.http.HttpServletRequest) facesContext
                            .getExternalContext()
                            .getRequest();

            jakarta.servlet.http.HttpSession session = request.getSession(false);

            if (session != null) {
                session.invalidate();
            }
        }

        return "logout?faces-redirect=true";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getRecoveryCode() {
        return recoveryCode;
    }

    public void setRecoveryCode(String recoveryCode) {
        this.recoveryCode = recoveryCode;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}
