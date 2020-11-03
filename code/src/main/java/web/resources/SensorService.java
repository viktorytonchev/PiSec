package web.resources;


import jdk.jshell.Snippet;
import web.db.DatabaseQueries;
import web.login.Security;
import web.model.Sensor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/sensors")
public class SensorService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sensor> getAllSensors() {
        return DatabaseQueries.getAllSensors();
    }

}