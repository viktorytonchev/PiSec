package web.resources;


import notifications.NotificationMailer;

import web.db.DatabaseQueries;
import web.login.Security;


import javax.servlet.http.HttpServlet;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/changePIN")
public class PINService {


    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void changePIN(@FormParam("PIN") String pin) {
        DatabaseQueries.changePin(pin);
    }

}
