package web.db;

import web.login.Security;
import web.model.System;

import java.sql.*;

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

}
