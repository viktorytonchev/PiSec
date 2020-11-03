package web.resources;

import web.db.DatabaseQueries;
import web.login.Security;
import web.model.Email;
import web.model.System;

import javax.annotation.processing.Generated;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/system")
public class SystemService {

    public static boolean
    onlyDigits(String str, int n)
    {
        boolean result = false;
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(str.charAt(i))) {
                result = true;
            }
        }
        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public System getSystem(){
        System result = DatabaseQueries.getSystem();

        return result;
    }

    @Path("{pin}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public boolean checkPin(@PathParam("pin") String pinString){
        boolean result = false;

        System system = DatabaseQueries.getSystem();
        String pin = system.getPin();

        if(onlyDigits(pin, pin.length()) && pin.equals(pinString)){
            result = true;
        }

        return result;
    }

    @Path("arm")
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void armDisarmSystem(){

        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query = "SELECT s.armed FROM " + dbuser + ".pisec.system s" ;
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



}
