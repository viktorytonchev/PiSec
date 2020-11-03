package web.resources;

import web.db.DatabaseQueries;
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
    public java.util.List<Email> getAllEmails() {
        return DatabaseQueries.getAllEmails();
    }

}
