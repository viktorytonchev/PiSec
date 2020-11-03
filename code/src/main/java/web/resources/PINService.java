package web.resources;


import notifications.NotificationMailer;

import web.login.Security;


import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/changePIN")
public class PINService {

//
//    @POST
//    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
//    public void changePIN(@FormParam("PIN") String pin) {
//        Connection conn;
//        String dbuser = Security.DB_USER;
//        String passwd = Security.DB_PASSWORD;
//        try {
//            Class.forName("org.postgresql.Driver");
//            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
//                    dbuser, passwd);
//            conn.setAutoCommit(true);
//            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
//            String query;
//
//
//            query = "UPDATE " + dbuser + ".pisec.System " +
//                    "SET pin = " + pin + "WHERE sid = " + 0 + ";";
//
//
//            PreparedStatement st = conn.prepareStatement(query);
//            try {
//                st.executeQuery();
//            } catch (SQLException exception) {
////                exception.printStackTrace();
//            }
//
//            NotificationMailer.executeSendChangedPINNotification();
//
//            conn.close();
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    @POST
    @Path("/{newPIN}")
    public void changePIN(@PathParam("newPIN") String newPIN) {
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

}
