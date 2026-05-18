package cqu.coit20259.ebusiness.business;

import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Provides email sending functions for registration verification
 * and account recovery.
 *
 * This service sends emails through the local FakeSMTP server during
 * development and testing.
 *
 * @author Jerald Christopher Bucud
 */
@Stateless
public class EmailService {

    private static final String SMTP_HOST = "localhost";
    private static final String SMTP_PORT = "2525";
    private static final String FROM_EMAIL = "noreply@ebusinesssystem.local";

    /**
     * Sends an email verification code to a registered user.
     *
     * @param recipientEmail recipient email address
     * @param verificationCode generated verification code
     */
    public void sendVerificationCode(String recipientEmail, String verificationCode) {
        String subject = "E-Business System Email Verification";
        String body = "Your E-Business System verification code is: "
                + verificationCode;

        sendEmail(recipientEmail, subject, body);
    }

    /**
     * Sends an account recovery code to a registered user.
     *
     * @param recipientEmail recipient email address
     * @param recoveryCode generated recovery code
     */
    public void sendRecoveryCode(String recipientEmail, String recoveryCode) {
        String subject = "E-Business System Account Recovery";
        String body = "Your E-Business System recovery code is: "
                + recoveryCode;

        sendEmail(recipientEmail, subject, body);
    }

    /**
     * Sends an email using the local FakeSMTP server.
     *
     * @param recipientEmail recipient email address
     * @param subject email subject
     * @param body email body
     */
    private void sendEmail(String recipientEmail, String subject, String body) {

        try {
            Properties properties = new Properties();
            properties.put("mail.smtp.host", SMTP_HOST);
            properties.put("mail.smtp.port", SMTP_PORT);
            properties.put("mail.smtp.auth", "false");
            properties.put("mail.smtp.starttls.enable", "false");

            Session session = Session.getInstance(properties);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (Exception exception) {
            throw new RuntimeException("Email could not be sent: "
                    + exception.getMessage(), exception);
        }
    }
}