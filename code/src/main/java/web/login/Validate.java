package web.login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Validate {

    public static boolean checkUser(String pass) {
        boolean st = false;
        try {
            Class.forName("org.postgresql.Driver");

            //creating connection with the database
            Connection con = DriverManager.getConnection
                    ("jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + Security.DB_USER,
                            Security.DB_USER, Security.DB_PASSWORD);

            PreparedStatement ps = con.prepareStatement
                    ("SELECT * FROM " + Security.DB_USER + ".pisec.system WHERE password = ?");
            String hashedPw = Security.hash(pass);
            ps.setString(1, hashedPw);
            ResultSet rs = ps.executeQuery();
            st = rs.next();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return st;
    }

}