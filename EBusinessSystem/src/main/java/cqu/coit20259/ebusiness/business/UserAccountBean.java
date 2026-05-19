package cqu.coit20259.ebusiness.business;

import cqu.coit20259.ebusiness.persistence.UserAccount;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

/**
 * Provides business logic for user account registration, login, email
 * verification, account recovery, and password reset.
 *
 * This EJB separates authentication logic from customer management.
 *
 * @author Cardoso Pepe
 */
@Stateless
public class UserAccountBean {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    /**
     * Generates a six-digit verification or recovery code.
     *
     * @return generated six-digit code
     */
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    /**
     * Registers a new user account.
     *
     * @param firstName user's first name
     * @param lastName user's last name
     * @param username user's username
     * @param emailAddress user's email address
     * @param password user's plain password before hashing
     * @param verificationCode generated email verification code
     * @param groupName user's access group
     */
    public void registerUser(String firstName, String lastName, String username,
            String emailAddress, String password,
            String verificationCode, String groupName) {

        validateRequired(username, "Username is required.");
        validateRequired(emailAddress, "Email address is required.");
        validateRequired(password, "Password is required.");

        if (findByUsername(username) != null) {
            throw new IllegalArgumentException("The username is already registered.");
        }

        if (findByEmailAddress(emailAddress) != null) {
            throw new IllegalArgumentException("The email address is already registered.");
        }

        UserAccount userAccount = new UserAccount();
        userAccount.setUsername(username.trim());
        userAccount.setEmailAddress(emailAddress.trim());
        userAccount.setFirstName(firstName);
        userAccount.setLastName(lastName);
        userAccount.setPassword(hashPassword(password));
        userAccount.setVerificationCode(verificationCode);
        userAccount.setVerified(false);
        userAccount.setRecoveryCode(null);
        userAccount.setGroupName(groupName == null || groupName.trim().isEmpty()
                ? "Customer"
                : groupName.trim());

        em.persist(userAccount);
    }

    /**
     * Authenticates a user account.
     *
     * @param username username entered by the user
     * @param password password entered by the user
     * @return true if login details are valid
     */
    public boolean login(String emailAddress, String password) {

        if (emailAddress == null || password == null) {
            return false;
        }

        UserAccount userAccount = findByEmailAddress(emailAddress.trim());

        if (userAccount == null) {
            return false;
        }

        if (!userAccount.isVerified()) {
            return false;
        }

        String hashedPassword = hashPassword(password.trim());
        return hashedPassword.equals(userAccount.getPassword());
    }

    /**
     * Verifies a registered user account using the verification code.
     *
     * @param username username of the account
     * @param verificationCode verification code entered by the user
     * @return true if verification is successful
     */
    public boolean verifyEmail(String username, String verificationCode) {

        if (username == null || verificationCode == null) {
            return false;
        }

        UserAccount userAccount = findByUsername(username.trim());

        if (userAccount == null) {
            return false;
        }

        if (userAccount.getVerificationCode() == null) {
            return false;
        }

        if (!userAccount.getVerificationCode().equals(verificationCode.trim())) {
            return false;
        }

        userAccount.setVerified(true);
        userAccount.setVerificationCode(null);
        em.merge(userAccount);

        return true;
    }

    /**
     * Starts account recovery by generating and saving a recovery code.
     *
     * @param emailAddress registered email address
     * @return generated recovery code
     */
    public String startAccountRecovery(String emailAddress) {

        validateRequired(emailAddress, "Registered email address is required.");

        UserAccount userAccount = findByEmailAddress(emailAddress.trim());

        if (userAccount == null) {
            throw new IllegalArgumentException("No account was found for the entered email address.");
        }

        String recoveryCode = generateVerificationCode();
        userAccount.setRecoveryCode(recoveryCode);
        em.merge(userAccount);

        return recoveryCode;
    }

    /**
     * Resets the account password using the saved recovery code.
     *
     * @param emailAddress registered email address
     * @param recoveryCode recovery code entered by the user
     * @param newPassword new password before hashing
     * @return true if password reset is successful
     */
    public boolean resetPassword(String emailAddress, String recoveryCode, String newPassword) {

        if (emailAddress == null || recoveryCode == null || newPassword == null) {
            return false;
        }

        UserAccount userAccount = findByEmailAddress(emailAddress.trim());

        if (userAccount == null) {
            return false;
        }

        if (userAccount.getRecoveryCode() == null) {
            return false;
        }

        if (!userAccount.getRecoveryCode().equals(recoveryCode.trim())) {
            return false;
        }

        userAccount.setPassword(hashPassword(newPassword.trim()));
        userAccount.setRecoveryCode(null);
        em.merge(userAccount);

        return true;
    }

    /**
     * Finds a user account by username.
     *
     * @param username username to search
     * @return matching user account or null
     */
    public UserAccount findByUsername(String username) {

        List<UserAccount> results = em.createQuery(
                "SELECT u FROM UserAccount u WHERE LOWER(TRIM(u.username)) = :username",
                UserAccount.class)
                .setParameter("username", username == null ? "" : username.trim().toLowerCase())
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Finds a user account by email address.
     *
     * @param emailAddress email address to search
     * @return matching user account or null
     */
    public UserAccount findByEmailAddress(String emailAddress) {

        List<UserAccount> results = em.createQuery(
                "SELECT u FROM UserAccount u WHERE LOWER(TRIM(u.emailAddress)) = :emailAddress",
                UserAccount.class)
                .setParameter("emailAddress", emailAddress == null ? "" : emailAddress.trim().toLowerCase())
                .getResultList();

        return results.isEmpty() ? null : results.get(0);
    }

    /**
     * Creates a SHA-512 hash of the password.
     *
     * @param password plain password
     * @return hashed password in hexadecimal format
     */
    private String hashPassword(String password) {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();

            for (byte hashedByte : hashedBytes) {
                hexString.append(String.format("%02x", hashedByte));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Password hashing failed.", exception);
        }
    }

    /**
     * Validates that a required field is not empty.
     *
     * @param value value to check
     * @param message error message
     */
    private void validateRequired(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
