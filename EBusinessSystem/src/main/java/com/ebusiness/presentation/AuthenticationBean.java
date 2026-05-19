package com.ebusiness.presentation;

import cqu.coit20259.ebusiness.business.EmailService;
import cqu.coit20259.ebusiness.business.UserAccountBean;
import jakarta.inject.Named;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Backing bean for authentication-related JSF pages.
 *
 * This class handles form data and page navigation for login, registration,
 * email verification, account recovery, password reset, and logout.
 *
 * Authentication logic is delegated to UserAccountBean in the business layer.
 *
 * @author Jerald Christopher Bucud
 */
@Named("authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private UserAccountBean userAccountService;

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

    public AuthenticationBean() {
        loggedIn = false;
    }

    /**
     * Authenticates the user through the UserAccount business layer.
     *
     * @return navigation outcome for the secured main dashboard
     */
    public String login() {

    String cleanEmailAddress = emailAddress == null ? "" : emailAddress.trim();
    String cleanPassword = password == null ? "" : password.trim();

    boolean validLogin = userAccountService.login(cleanEmailAddress, cleanPassword);

    if (validLogin) {
        loggedIn = true;

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("loggedIn", true);

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("loggedInUser", cleanEmailAddress);

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
     * Registers a new user account through the UserAccount business layer.
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
            String generatedVerificationCode = userAccountService.generateVerificationCode();

            userAccountService.registerUser(
                    firstName,
                    lastName,
                    username,
                    emailAddress,
                    password,
                    generatedVerificationCode,
                    "Customer"
            );

            emailService.sendVerificationCode(emailAddress, generatedVerificationCode);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Registration Submitted",
                            "A verification code has been sent to the registered email address."));

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

        if (username == null || username.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Verification Failed",
                            "Username is required."));

            return null;
        }

        if (verificationCode == null || verificationCode.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Verification Failed",
                            "Verification code is required."));

            return null;
        }

        boolean verified = userAccountService.verifyEmail(
                username.trim(),
                verificationCode.trim()
        );

        if (!verified) {
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

        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Account Recovery Failed",
                            "Registered email address is required."));

            return null;
        }

        try {
            String generatedRecoveryCode = userAccountService.startAccountRecovery(emailAddress.trim());

            emailService.sendRecoveryCode(emailAddress, generatedRecoveryCode);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Recovery Code Sent",
                            "A recovery code has been sent to the registered email address."));

            return "resetPassword?faces-redirect=true";

        } catch (Exception exception) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Account Recovery Failed",
                            exception.getMessage()));

            return null;
        }
    }

    /**
     * Resets the user password after recovery code entry.
     *
     * @return navigation outcome for login page
     */
    public String resetPassword() {

        if (emailAddress == null || emailAddress.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Password Reset Failed",
                            "Registered email address is required."));

            return null;
        }

        if (recoveryCode == null || recoveryCode.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Password Reset Failed",
                            "Recovery code is required."));

            return null;
        }

        if (newPassword == null || !newPassword.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Password Reset Failed",
                            "New password and confirmation password do not match."));

            return null;
        }

        boolean resetSuccessful = userAccountService.resetPassword(
                emailAddress.trim(),
                recoveryCode.trim(),
                newPassword
        );

        if (!resetSuccessful) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Password Reset Failed",
                            "The recovery code is incorrect."));

            return null;
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Password Reset",
                        "The password has been reset successfully."));

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