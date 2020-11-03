package web.resources;

import web.db.DatabaseQueries;
import web.login.Security;
import web.model.Email;
import web.model.System;

import javax.annotation.processing.Generated;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.crypto.Data;
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
        DatabaseQueries.armDisarmSystem();
    }



}
