package web.resources;

import notifications.NotificationMailer;
import org.junit.experimental.theories.FromDataPoints;
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
public class EmailService {

//    public static int pid = 0; //OBSOLETE



    /**
     * Returns an eid for a new email.
     * @return - new pid
     */
    public int getEid() {
        return DatabaseQueries.getEid();
    }


    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void createEmailFromForm(@FormParam("exampleInputEmail1") String emailString){
//                                      ,final @CookieParam("sid") String sid) {

//        int sid = Integer.parseInt(sid);

        int eid = this.getEid();
        int sid = 0;

        Email email = new Email(eid, sid, emailString);
        DatabaseQueries.addEmailToDB(email);

    }



    @Path("deleteEmail/{eid}")
    @DELETE
    public void deleteEmail(@PathParam("eid") int eid){
        DatabaseQueries.deleteEmail(eid);
    }

}

