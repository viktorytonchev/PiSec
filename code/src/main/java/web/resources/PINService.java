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
    @Path("/{newPIN}")
    public void changePIN(@PathParam("newPIN") String newPIN) {
        DatabaseQueries.changePIN(newPIN);
    }

}
