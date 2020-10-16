package web.resources;

import web.db.DatabaseQueries;
import web.login.Security;
import web.model.Email;


import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.Set;
//import java.util.Date;

@Path("/registerEmail")
public class EmailService extends HttpServlet {

//    public static int pid = 0; //OBSOLETE



    /**
     * Returns a pid for a new project
     * @return - new pid
     */
    public int getEid() {
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


    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void createEmailFromForm(@FormParam("exampleInputEmail1") String emailString){
//                                      ,final @CookieParam("sid") String sid) {

//        int sid = Integer.parseInt(sid);

        int eid = this.getEid();
        int sid = 0;

        Email email = new Email(eid, sid, emailString);
        addEmailToDB(email);

    }

    /**
     * Method used to add a project to the database
     * @param email - email to be added
     */
    public void addEmailToDB(Email email) {

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
                            "VALUES ('" + email.getEid() + "', '" + email.getSid() + "', '" + email.getEmail() + "');";


            PreparedStatement st = conn.prepareStatement(query);
            try {
                st.executeQuery();
            } catch (SQLException ignored) {
            }

//            NotificationMailer.executeSendNewProject(project.getPid());

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}

