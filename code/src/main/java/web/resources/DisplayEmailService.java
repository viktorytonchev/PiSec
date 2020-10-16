package web.resources;

import web.login.Security;
import web.model.Email;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/displayEmails")
public class DisplayEmailService {

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public java.util.List<Email> getAllProjects() {
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
            String query = "SELECT * FROM " + dbuser + ".pisec.email e ORDER BY e.eid";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            int eid; //id of the email
            String email; //email
            int sid; //sid of system that holds the email
            while (resultSet.next()) {
                eid = resultSet.getInt("eid");
                email = resultSet.getString("email");
                sid = resultSet.getInt("sid");
               Email email1 = new Email(eid, sid, email);
                result.add(email1);
            }

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
