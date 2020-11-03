package web.db;

import notifications.NotificationMailer;
import web.login.Security;
import web.model.Sensor;
import web.model.System;
import web.model.Email;

import java.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// All database queries go here
public class DatabaseQueries {
    /**
     * Returns email of user by given uid
     *
     * @param eid eid of user to get email
     * @return email of this user
     */
    public static String getEmailFromEid(int eid) {
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");

            //creating connection with the database
            Connection con;
            con = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
                    dbuser, passwd);

            PreparedStatement ps = con.prepareStatement
                    ("select e.email from " + Security.DB_USER + ".pisec.Email " + "AS e" +
                            " where e.eid = ?");

            ps.setInt(1, eid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                con.close();
                return rs.getString("email");
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static System getSystem(){
        System result = new System();

        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query = "SELECT * FROM " + dbuser + ".pisec.system s";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            int sid;
            String pin;
            String name;
            String password;
            boolean armed;
            boolean alarm;
            while (resultSet.next()) {
                sid = resultSet.getInt("sid");
                pin = resultSet.getString("pin");
                name = resultSet.getString("name");
                armed = resultSet.getBoolean("armed");
                alarm = resultSet.getBoolean("alarm");
                password = resultSet.getString("password");
                result = new System(sid, armed, alarm, name, password, pin);
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Email> getAllEmails() {
        List<Email> result = new ArrayList<>();

        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query = "SELECT * FROM " + dbuser + ".pisec.Email e ORDER BY e.eid";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();

            int eid;
            String emailString;
            int sid;
            while (resultSet.next()) {
                eid = resultSet.getInt("eid");
                emailString = resultSet.getString("email");
                sid = resultSet.getInt("sid");

                Email email = new Email(eid, sid, emailString);

                result.add(email);
            }

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void armDisarmSystem(){
        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query = "SELECT s.armed, s.alarm FROM " + dbuser + ".pisec.system s" ;
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();

            boolean armed = false;
            boolean alarm = false;
            while (resultSet.next()) {
                armed = resultSet.getBoolean("armed");
                alarm = resultSet.getBoolean("alarm");
            }

            query = "UPDATE " + dbuser + ".pisec.system SET armed = ?";
            PreparedStatement st1 = conn.prepareStatement(query);
            st1.setBoolean(1, !armed);
            st1.executeUpdate();

            if(alarm){
                java.lang.System.out.println("test1");
                query = "UPDATE " + dbuser + ".pisec.system SET alarm = FALSE, reset_alarm = TRUE";
                PreparedStatement st2 = conn.prepareStatement(query);
                st2.executeUpdate();
            }

            conn.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getEid(){
        Connection connection;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        int resultId = 1;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
                    dbuser, passwd);
            connection.setAutoCommit(true);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query = "SELECT eid \n" +
                    "FROM " + dbuser + ".pisec.email \n" +
                    "ORDER BY eid DESC\n" +
                    "LIMIT 1";

            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                resultId = rs.getInt("eid");
                resultId++;

            }
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return resultId;
    }

    public static List<Sensor> getAllSensors(){
        List<Sensor> result = new ArrayList<>();

        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query = "SELECT * FROM " + dbuser + ".pisec.sensor s ORDER BY s.name";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            int sid;
            String name;
            boolean armed;
            boolean open;
            while (resultSet.next()) {
                sid = resultSet.getInt("sid");
                name = resultSet.getString("name");
                armed = resultSet.getBoolean("armed");
                open = resultSet.getBoolean("open");
                Sensor sensor = new Sensor(sid, armed, open, name);
                result.add(sensor);
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void changePIN(String newPIN){
        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query;


            query = "UPDATE " + dbuser + ".pisec.System " +
                    "SET pin = " + newPIN + "WHERE sid = " + 0 + ";";


            PreparedStatement st = conn.prepareStatement(query);
            try {
                st.executeQuery();
            } catch (SQLException exception) {
//                exception.printStackTrace();
            }

            NotificationMailer.executeSendChangedPINNotification();

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to add a project to the database
     * @param email - email to be added
     */
    public static void addEmailToDB(Email email) {

        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query;


            query = "INSERT INTO " + dbuser + ".pisec.Email (eid, sid, email)" +
                    "VALUES ('" + email.getEid() + "', '" + email.getSid() + "', '?');";

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, email.getEmail());
            try {
                st.executeUpdate();
            } catch (SQLException ignored) {
            }

            NotificationMailer.executeSendNewEmailNotification(email.getEmail());

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmail(int eid){
        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        String emailRecipient = DatabaseQueries.getEmailFromEid(eid);
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query;

            query = "DELETE FROM " + dbuser + ".pisec.Email " + "AS e " +
                    "WHERE e.eid = " + eid + ";";

            PreparedStatement st = conn.prepareStatement(query);
            try {
                st.executeUpdate();
            } catch (SQLException ignored) {
                java.lang.System.out.println(ignored.getMessage());
            }

            NotificationMailer.executeSendDeletedEmailNotification(emailRecipient);
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
