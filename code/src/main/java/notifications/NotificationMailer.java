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

    //Unfinished
//    private DeadlineApproachingNotificator notificator = null;

    /**
     * Thread is run on war deployment.
     */
//    private ResetNotificationCounterThread resetNotificationCounterThread = null;

//    /**
//     * Sends a link to reset a password with a token to a user
//     *
//     * @param uid   id of the user requesting the pw reset
//     * @param token token identifying the request
//     */
//    private static void sendPasswordReset(int uid, String token) {
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "465");
//        prop.put("mail.smtp.ssl.enable", "true");
//        prop.put("mail.smtp.socketFactory.port", "465");
//        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//        Session session = Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username + server,
//                        password);
//            }
//        });
//
//        Message message = new MimeMessage(session);
//        try {
//            message.setFrom(new InternetAddress(username + server));
//            String recipientAddress = DatabaseQueries.getEmailFromId(uid);
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientAddress));
//            message.setSubject("Link to reset your password");
//            String msg = "Please follow this link to reset your password:" +
//                    " http://env-di-team35.paas.hosted-by-previder.com/lifecycle/reset?token=" + token + "<br>" +
//                    "The link will expire in 10 minutes. <br>"
//                    + "Lifecycle notifications";
//            MimeBodyPart mbp = new MimeBodyPart();
//            mbp.setContent(msg, "text/html");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mbp);
//            message.setContent(multipart);
//            Transport.send(message);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    /**
//     * Starts a thread to execute {@link #sendPasswordReset(int, String)}
//     */
//    public static void executeSendPasswordReset(int uid, String token) {
//        SendPasswordResetThread thread = new SendPasswordResetThread(uid, token);
//        thread.start();
//    }
//
//    /**
//     * Sends a mail indicating that a staff member edited a project
//     *
//     * @param editor id of the editor
//     * @param pid    id of the project
//     */
//    private static void sendStaffEdit(int editor, int pid) {
//        int sup = DatabaseQueries.getSupervisorId(pid);
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "465");
//        prop.put("mail.smtp.ssl.enable", "true");
//        prop.put("mail.smtp.socketFactory.port", "465");
//        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//        Session session = Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username + server,
//                        password);
//            }
//        });
//
//        Message message = new MimeMessage(session);
//        try {
//            //notification to managing student
//            message.setFrom(new InternetAddress(username + server));
//            int sid = DatabaseQueries.getManagingStudentUid(pid);
//            String recipientAddress = DatabaseQueries.getEmailFromId(sid);
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientAddress));
//            String editorRole = DatabaseQueries.getStaffRole(editor);
//            String editorName = DatabaseQueries.getNameFromId(editor);
//            String projectName = DatabaseQueries.getProjectName(pid);
//            message.setSubject("Changes to a project you supervise");
//            String msg = "A " + editorRole + " " + editorName + " made changes to your project " + projectName + ".<br>"
//                    + "Lifecycle notifications";
//            MimeBodyPart mbp = new MimeBodyPart();
//            mbp.setContent(msg, "text/html");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mbp);
//            message.setContent(multipart);
//            Transport.send(message);
//
//            //notification to managing supervisor
//            //skip if the editor is the supervisor
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            if (editor != sup) {
//                //mailing supervisor
//                String recipientAddress = DatabaseQueries.getEmailFromId(sup);
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(recipientAddress));
//                String editorRole = DatabaseQueries.getStaffRole(editor);
//                String editorName = DatabaseQueries.getNameFromId(editor);
//                String projectName = DatabaseQueries.getProjectName(pid);
//                message.setSubject("Changes to a project you supervise");
//                String msg = "A " + editorRole + " " + editorName + " made changes to the project " + projectName
//                        + " you supervise. <br>"
//                        + "Lifecycle notifications";
//                MimeBodyPart mbp = new MimeBodyPart();
//                mbp.setContent(msg, "text/html");
//                Multipart multipart = new MimeMultipart();
//                multipart.addBodyPart(mbp);
//                message.setContent(multipart);
//                Transport.send(message);
//            }
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Starts a thread to execute {@link #sendNewProject(int)}
//     */
//    public static void executeSendNewProject(int pid) {
//        SendNewProjectThread thread = new SendNewProjectThread(pid);
//        thread.start();
//    }
//
//    /**
//     * Sends a notification to secretaries indicating that a new project was created
//     * Checks for the maximum notification per day and sends if the limit is not exceeded
//     *
//     * @param pid id of the new project
//     */
//    private static void sendNewProject(int pid) {
//        if (notificationsSent.size() == maxNotifications.size()) {
//            for (String staff : notificationsSent.keySet()) {
//                if (notificationsSent.get(staff) < maxNotifications.get(staff)) {
//                    Properties prop = new Properties();
//                    prop.put("mail.smtp.auth", "true");
//                    prop.put("mail.smtp.starttls.enable", "true");
//                    prop.put("mail.smtp.host", "smtp.gmail.com");
//                    prop.put("mail.smtp.port", "465");
//                    prop.put("mail.smtp.ssl.enable", "true");
//                    prop.put("mail.smtp.socketFactory.port", "465");
//                    prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//                    Session session = Session.getInstance(prop, new Authenticator() {
//                        @Override
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(username + server,
//                                    password);
//                        }
//                    });
//
//                    Message message = new MimeMessage(session);
//                    try {
//                        //notification to managing student
//                        message.setFrom(new InternetAddress(username + server));
//                        int uid = DatabaseQueries.getUidFromMid(staff);
//                        String recipientAddress = DatabaseQueries.getEmailFromId(uid);
//                        message.setRecipients(Message.RecipientType.TO,
//                                InternetAddress.parse(recipientAddress));
//                        String projectName = DatabaseQueries.getProjectName(pid);
//                        message.setSubject("A new project was added");
//                        String msg = "A new project " + projectName + " was just added! " +
//                                "<br>Lifecycle notifications";
//                        MimeBodyPart mbp = new MimeBodyPart();
//                        mbp.setContent(msg, "text/html");
//                        Multipart multipart = new MimeMultipart();
//                        multipart.addBodyPart(mbp);
//                        message.setContent(multipart);
//                        Transport.send(message);
//
//                    } catch (MessagingException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                int sent = notificationsSent.get(staff);
//                notificationsSent.remove(staff);
//                notificationsSent.put(staff, sent + 1);
//            }
//        } else {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            sendNewProject(pid);
//        }
//    }
//
//    /**
//     * Starts a thread to execute {@link #sendStaffEdit(int, int)}
//     */
//    public static void executeSendEditStaff(int editor, int pid) {
//        SendStaffEditThread thread = new SendStaffEditThread(editor, pid);
//        thread.start();
//    }
//
//
//    /**
//     * Starts a thread to execute {@link #sendStudentEdit(int)}
//     */
//    public static void executeSendStudentEdit(int pid) {
//        SendStudentEditThread thread = new SendStudentEditThread(pid);
//        thread.start();
//    }
//
//    /**
//     * Sends a notification to the supervisor of this project indicating that a student has made changes
//     *
//     * @param pid id of the project
//     */
//    private static void sendStudentEdit(int pid) {
//        int sup = DatabaseQueries.getSupervisorId(pid);
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "465");
//        prop.put("mail.smtp.ssl.enable", "true");
//        prop.put("mail.smtp.socketFactory.port", "465");
//        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//        Session session = Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username + server,
//                        password);
//            }
//        });
//
//        Message message = new MimeMessage(session);
//        try {
//            //mailing supervisor
//            String recipientAddress = DatabaseQueries.getEmailFromId(sup);
//            String projectName = DatabaseQueries.getProjectName(pid);
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientAddress));
//            message.setSubject("Changes to a project you supervise");
//            String msg = "A student made changes to the project " + projectName
//                    + " you supervise. <br>"
//                    + "Lifecycle notifications";
//            MimeBodyPart mbp = new MimeBodyPart();
//            mbp.setContent(msg, "text/html");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mbp);
//            message.setContent(multipart);
//            Transport.send(message);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Starts a thread to execute {@link #sendApplicationReceived(int, int)}
//     */
//    public static void executeSendApplicationReceived(int applicant, int pid) {
//        SendApplicationReceivedThread thread = new SendApplicationReceivedThread(applicant, pid);
//        thread.start();
//    }
//
//    /**
//     * Sends a notification to supervisor indicating that a student applied to this project
//     *
//     * @param applicant id of applicant
//     * @param pid       id of project they applied to
//     */
//    private static void sendApplicationReceived(int applicant, int pid) {
//        int sup = DatabaseQueries.getSupervisorId(pid);
//
//        if (sup != -1) {
//
//            Properties prop = new Properties();
//            prop.put("mail.smtp.auth", "true");
//            prop.put("mail.smtp.starttls.enable", "true");
//            prop.put("mail.smtp.host", "smtp.gmail.com");
//            prop.put("mail.smtp.port", "465");
//            prop.put("mail.smtp.ssl.enable", "true");
//            prop.put("mail.smtp.socketFactory.port", "465");
//            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//            Session session = Session.getInstance(prop, new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username + server,
//                            password);
//                }
//            });
//
//            Message message = new MimeMessage(session);
//            try {
//                //mailing supervisor
//                String recipientAddress = DatabaseQueries.getEmailFromId(sup);
//                String projectName = DatabaseQueries.getProjectName(pid);
//                String applicantName = DatabaseQueries.getNameFromId(applicant);
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse(recipientAddress));
//                message.setSubject("Application received");
//                String msg = "A student " + applicantName + " applied to the project " + projectName
//                        + " you supervise. <br>"
//                        + "Lifecycle notifications";
//                MimeBodyPart mbp = new MimeBodyPart();
//                mbp.setContent(msg, "text/html");
//                Multipart multipart = new MimeMultipart();
//                multipart.addBodyPart(mbp);
//                message.setContent(multipart);
//                Transport.send(message);
//
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * Starts a thread to execute {@link #sendFinishRequestReceived(int)}
//     */
//    public static void executeFinishRequestReceived(int pid) {
//        SendFinishRequestThread thread = new SendFinishRequestThread(pid);
//        thread.start();
//    }
//
//    /**
//     * Sends a notification to student and supervisor indicating that student requested to finish their project
//     *
//     * @param pid id of project
//     */
//    private static void sendFinishRequestReceived(int pid) {
//        int sup = DatabaseQueries.getSupervisorId(pid);
//
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", "true");
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.port", "465");
//        prop.put("mail.smtp.ssl.enable", "true");
//        prop.put("mail.smtp.socketFactory.port", "465");
//        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//        Session session = Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username + server,
//                        password);
//            }
//        });
//
//        Message message = new MimeMessage(session);
//        try {
//            //mailing supervisor
//            String recipientAddress = DatabaseQueries.getEmailFromId(sup);
//            String projectName = DatabaseQueries.getProjectName(pid);
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientAddress));
//            message.setSubject("Finish request received");
//            String msg = "A student has requested to finish the project " + projectName
//                    + " you supervise. <br>"
//                    + "Lifecycle notifications";
//            MimeBodyPart mbp = new MimeBodyPart();
//            mbp.setContent(msg, "text/html");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mbp);
//            message.setContent(multipart);
//            Transport.send(message);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        message = new MimeMessage(session);
//        try {
//            //mailing student
//            int uid = DatabaseQueries.getManagingStudentUid(pid);
//            String recipientAddress = DatabaseQueries.getEmailFromId(uid);
//            String projectName = DatabaseQueries.getProjectName(pid);
//            message.setRecipients(Message.RecipientType.TO,
//                    InternetAddress.parse(recipientAddress));
//            message.setSubject("Finish request received");
//            String msg = "You requested to finish the project " + projectName
//                    + ". The supervisor was informed. Please wait on their decision. <br>"
//                    + "Lifecycle notifications";
//            MimeBodyPart mbp = new MimeBodyPart();
//            mbp.setContent(msg, "text/html");
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mbp);
//            message.setContent(multipart);
//            Transport.send(message);
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Thread class to send a password reset link
//     */
//    static class SendPasswordResetThread extends Thread {
//        int uid;
//        String token;
//
//        SendPasswordResetThread(int uid, String token) {
//            this.uid = uid;
//            this.token = token;
//        }
//
//        @Override
//        public void run() {
//            sendPasswordReset(uid, token);
//        }
//    }
//
//    /**
//     * Thread to execute {@link #sendFinishRequestReceived(int)}
//     */
//    static class SendFinishRequestThread extends Thread {
//        int pid;
//
//        SendFinishRequestThread(int pid) {
//            this.pid = pid;
//        }
//
//        @Override
//        public void run() {
//            sendFinishRequestReceived(pid);
//        }
//    }
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce){
//        return;
//    }

//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        if (notificator == null || !(notificator.isAlive())) {
//            notificator = new DeadlineApproachingNotificator();
//            notificator.start();
//        }
//        if (resetNotificationCounterThread == null || !(resetNotificationCounterThread.isAlive())) {
//            resetNotificationCounterThread = new ResetNotificationCounterThread();
//            resetNotificationCounterThread.start();
//        }
//    }

//    @Override
//    public void contextDestroyed(ServletContextEvent sce){
//        return;
//    }

//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        try {
//            notificator.interrupt();
//        } catch (Exception ignored) {
//        }
//        try {
//            resetNotificationCounterThread.interrupt();
//        } catch (Exception ignored) {
//        }
//    }

//    //Unfinished
//    static class DeadlineApproachingNotificator extends Thread {
//        @Override
//        public void run() {
//            //todo finish
//        }
//    }
////
//    /**
//     * Thread used to reset the {@link #notificationsSent} to 0
//     */
//    static class ResetNotificationCounterThread extends Thread {
//        ResetNotificationCounterThread() {
//            notificationsSent = new ConcurrentHashMap<>();
//            maxNotifications = new ConcurrentHashMap<>();
//        }
//
//        private static void resetNotificationsSent() {
//            notificationsSent.replaceAll((k, v) -> 0);
//        }
//
//        @SuppressWarnings({"InfiniteLoopStatement", "BusyWait"})
//        @Override
//        public void run() {
//            while (true) {
//                Calendar calendar = Calendar.getInstance();
//                int today = calendar.get(Calendar.DAY_OF_WEEK);
//                DisplayProjectService service = new DisplayProjectService();
//                List<Staff> staff = service.getAllStaff();
//                Set<Staff> secretaries = new HashSet<>();
//
//                for (Staff staffUser : staff) {
//                    if (staffUser.getPost().equals("secretary")) {
//                        secretaries.add(staffUser);
//                        if (!notificationsSent.containsKey(staffUser.getM_number())) {
//                            notificationsSent.put(staffUser.getM_number(), 0);
//                        }
//                    }
//                }
//                for (Staff secretary : secretaries) {
//                    maxNotifications.remove(secretary.getM_number());
//                    maxNotifications.put(secretary.getM_number(), DatabaseQueries.getMaxNotifications(secretary));
//                }
//                try {
//                    Thread.sleep(1000 * 60 * 10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Calendar newCalendar = Calendar.getInstance();
//                int nextDay = newCalendar.get(Calendar.DAY_OF_WEEK);
//                if (today != nextDay) {
//                    resetNotificationsSent();
//                }
//            }
//        }
//    }
//
//    /**
//     * Thread to execute {@link #sendNewProject(int)}
//     */
//    static class SendNewProjectThread extends Thread {
//        int pid;
//
//        SendNewProjectThread(int pid) {
//            this.pid = pid;
//        }
//
//        @Override
//        public void run() {
//            sendNewProject(pid);
//        }
//    }
//
//    /**
//     * Thread to execute {@link #sendStaffEdit(int, int)}
//     */
//    static class SendStaffEditThread extends Thread {
//        int editor;
//        int pid;
//
//        SendStaffEditThread(int editor, int pid) {
//            this.editor = editor;
//            this.pid = pid;
//        }
//
//        @Override
//        public void run() {
//            sendStaffEdit(editor, pid);
//        }
//    }
//
//    /**
//     * Thread to execute {@link #sendStudentEdit(int)}
//     */
//    static class SendStudentEditThread extends Thread {
//        int pid;
//
//        SendStudentEditThread(int pid) {
//            this.pid = pid;
//        }
//
//        @Override
//        public void run() {
//            sendStudentEdit(pid);
//        }
//
//    }
//
//    /**
//     * Thread to execute {@link #sendApplicationReceived(int, int)}
//     */
//    static class SendApplicationReceivedThread extends Thread {
//        int applicant;
//        int pid;
//
//        SendApplicationReceivedThread(int applicant, int pid) {
//            this.applicant = applicant;
//            this.pid = pid;
//        }
//
//        @Override
//        public void run() {
//            sendApplicationReceived(applicant, pid);
//        }
//    }


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
