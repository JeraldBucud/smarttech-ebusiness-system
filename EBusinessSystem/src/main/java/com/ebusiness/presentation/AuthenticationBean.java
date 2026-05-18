package com.ebusiness.presentation;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Backing bean for authentication-related JSF pages.
 *
 * This class stores temporary authentication form data and provides
 * placeholder action methods for login, registration, email verification,
 * account recovery, password reset, and logout.
 *
 * The actual authentication, email verification, account recovery,
 * and persistence logic will be connected later through the business tier.
 *
 * @author Jerald Christopher Bucud
 */
@Named("authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * Creates an authentication backing bean with the user logged out by default.
     */
    public AuthenticationBean() {
        loggedIn = false;
    }

    /**
     * Placeholder login action.
     *
     * @return navigation outcome for the secured main dashboard
     */
    public String login() {
        loggedIn = true;
        return "main?faces-redirect=true";
    }

    /**
     * Placeholder registration action.
     *
     * @return navigation outcome for email verification
     */
    public String register() {
        return "emailVerification?faces-redirect=true";
    }

    /**
     * Placeholder email verification action.
     *
     * @return navigation outcome for login page
     */
    public String verifyEmail() {
        return "login?faces-redirect=true";
    }

    /**
     * Placeholder account recovery action.
     *
     * @return navigation outcome for password reset page
     */
    public String recoverAccount() {
        return "resetPassword?faces-redirect=true";
    }

    /**
     * Placeholder password reset action.
     *
     * @return navigation outcome for login page
     */
    public String resetPassword() {
        return "login?faces-redirect=true";
    }

    /**
     * Placeholder logout action.
     *
     * @return navigation outcome for logout page
     */
    public String logout() {
        loggedIn = false;
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