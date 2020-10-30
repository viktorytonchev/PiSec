package web.db;

import web.login.Security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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


}
