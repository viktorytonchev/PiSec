package notifications;

//import di.db.DatabaseQueries;
import web.login.Security;
//import di.model.Staff;
//import di.resources.DisplayProjectService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Listener class, run when the war is deployed.
 * Handles notifications, emails
 */
@SuppressWarnings("DuplicatedCode")
public class NotificationMailer  {

    private static final String username = Security.MAIL_USERNAME;
    private static final String password = Security.MAIL_PASSWORD;
    private static final String server = Security.MAIL_POSTFIX;

    static Map<String, Integer> notificationsSent;
    static Map<String, Integer> maxNotifications;

    /**
     * Sends a confirmation for successful addition to newly added email
     *
     * @param emailRecipient   email address of the newly added to the system email
     */
    private static void sendNewEmailNotification(String emailRecipient){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username + server,
                        password);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username + server));
            String recipientAddress = emailRecipient;
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientAddress));

            message.setSubject("Your email has been added to the PiSec System");
            String msg = "Greetings, <br> <br>" +
                    "Congratulations! Your email address has been added to the PiSec system. From now on you will receive email notifications for updates in the system. <br> <br>" +
                    "With hope to make your home feel safer, <br>the PiSec team";
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(msg, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbp);
            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    /**
     * Starts a thread to execute {@link #sendNewEmailNotification(String)}
     */
    public static void executeSendNewEmailNotification(String emailRecipient) {
        SendNewEmailNotificationThread thread = new SendNewEmailNotificationThread(emailRecipient);
        thread.start();
    }

    /**
     * Thread class to send a password reset link
     */
    static class SendNewEmailNotificationThread extends Thread {
        String emailRecipient;

        SendNewEmailNotificationThread(String emailRecipient) {
            this.emailRecipient = emailRecipient;
        }

        @Override
        public void run() {
            sendNewEmailNotification(emailRecipient);
        }
    }


    /**
     * Sends an email recipient a notification that their email has been deleted from the system
     *
     * @param emailRecipient   email address of the email deleted from the system
     */
    private static void sendDeletedEmailNotification(String emailRecipient){
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username + server,
                        password);
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(username + server));
            String recipientAddress = emailRecipient;
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipientAddress));

            message.setSubject("Your email has been removed from the PiSec System");
            String msg = "Greetings, <br> <br>" +
                    "We send you this email to confirm your email has been deleted from the PiSec system. From now on you will not receive email notifications for updates in the system." +
                    "It's sad to see you go! Remember, you can always add your email again from the system. <br><br>"+
                    "With hope to make your home feel safer, <br>the PiSec team";
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setContent(msg, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbp);
            message.setContent(multipart);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    /**
     * Starts a thread to execute {@link #sendNewEmailNotification(String)}
     */
    public static void executeSendDeletedEmailNotification(String emailRecipient) {
        SendDeletedEmailNotificationThread thread = new SendDeletedEmailNotificationThread(emailRecipient);
        thread.start();
    }

    /**
     * Thread class to send a password reset link
     */
    static class SendDeletedEmailNotificationThread extends Thread {
        String emailRecipient;

        SendDeletedEmailNotificationThread(String emailRecipient) {
            this.emailRecipient = emailRecipient;
        }

        @Override
        public void run() {
            sendDeletedEmailNotification(emailRecipient);
        }
    }


}
