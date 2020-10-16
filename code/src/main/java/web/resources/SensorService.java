package web.resources;


import jdk.jshell.Snippet;
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
        List<Sensor> result = new ArrayList<>();

        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl:5432/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String query = "SELECT * FROM " + dbuser + ".pisec.sensor s ORDER BY s.name";
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            int sid;
            String name;
            boolean armed;
            boolean open;
            while (resultSet.next()) {
                sid = resultSet.getInt("sid");
                name = resultSet.getString("name");
                armed = resultSet.getBoolean("armed");
                open = resultSet.getBoolean("open");
                Sensor sensor = new Sensor(sid, armed, open, name);
                result.add(sensor);
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}