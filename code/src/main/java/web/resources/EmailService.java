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
     * REST Method tied to the path lifecycle/rest/editPoject used to edit a project with given form parameters
     * @param description - description of the project
     * @param companyName - company name of the project(if external)
     * @param publicString - whether the project is public or not
     * @param thesisLink - thesis link for finished projects
     * @param projectName - name of the project
     * @param projectProfile - profile of the project
     * @param projectGoal - goal of the project
     * @param uidString - uid of the user editing/ who is working on the project
     */
    @POST //I am aware that this should be a PUT method but it is POST because html forms don't support PUT
    @Path("/editProject")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void editProject(@FormParam("description") String description,
                            @FormParam("company") String companyName,
                            @FormParam("public") String publicString,
                            @FormParam("thesis-link") String thesisLink,
                            @FormParam("project-name") String projectName,
                            @FormParam("project-profile") String projectProfile,
                            @FormParam("project-goal") String projectGoal,
                            final @CookieParam("uid") String uidString) {

        int uid = Integer.parseInt(uidString);

        Connection conn;
        String dbuser = Security.DB_USER;
        String passwd = Security.DB_PASSWORD;
        String sid = "";
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            sid = DatabaseQueries.getSidFromUid(uid);
            if (DatabaseQueries.getIsInternalFromStudentUid(uid) && companyName != null && !companyName.equals("")) {
                PreparedStatement ps = conn.prepareStatement
                        ("UPDATE " + dbuser + ".lifecycle.project p " +
                                "set research_group = ? " +
                                "where managed_by_student = ?");
                ps.setString(1, companyName);
                ps.setString(2, sid);
                try {
                    ps.executeQuery();
                } catch (SQLException ignored) {
                }
            } else if (companyName != null && !companyName.equals("")) {
                PreparedStatement ps = conn.prepareStatement
                        ("UPDATE " + dbuser + ".lifecycle.project p " +
                                "set company = ? " +
                                "where managed_by_student = ?");
                ps.setString(1, companyName);
                ps.setString(2, sid);
                try {
                    ps.executeQuery();
                } catch (SQLException ignored) {
                }
            }
            if (description != null && !description.equals("")) {
                PreparedStatement ps = conn.prepareStatement
                        ("UPDATE " + dbuser + ".lifecycle.project p " +
                                "set description = ? " +
                                "where managed_by_student = ?");
                ps.setString(1, description);
                ps.setString(2, sid);
                try {
                    ps.executeQuery();
                } catch (SQLException ignored) {
                }
            }
            if (thesisLink != null && !thesisLink.equals("")) {
                PreparedStatement ps = conn.prepareStatement
                        ("UPDATE " + dbuser + ".lifecycle.project p " +
                                "set thesis_link = ? " +
                                "where managed_by_student = ?");
                ps.setString(1, thesisLink);
                ps.setString(2, sid);
                try {
                    ps.executeQuery();
                } catch (SQLException ignored) {
                }
            }
            if (projectName != null && !projectName.equals("")) {
                PreparedStatement ps = conn.prepareStatement
                        ("UPDATE " + dbuser + ".lifecycle.project p " +
                                "set name = ? " +
                                "where managed_by_student = ?");
                ps.setString(1, projectName);
                ps.setString(2, sid);
                try {
                    ps.executeQuery();
                } catch (SQLException ignored) {
                }
            }
            if (projectProfile != null && !projectProfile.equals("")) {
                PreparedStatement ps = conn.prepareStatement
                        ("UPDATE " + dbuser + ".lifecycle.project p " +
                                "set profile = ? " +
                                "where managed_by_student = ?");
                ps.setString(1, projectProfile);
                ps.setString(2, sid);
                try {
                    ps.executeQuery();
                } catch (SQLException ignored) {
                }
            }
            if (projectGoal != null && !projectGoal.equals("")) {
                PreparedStatement ps = conn.prepareStatement
                        ("UPDATE " + dbuser + ".lifecycle.project p " +
                                "set goal = ? " +
                                "where managed_by_student = ?");
                ps.setString(1, projectGoal);
                ps.setString(2, sid);
                try {
                    ps.executeQuery();
                } catch (SQLException ignored) {
                }
            }
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://bronto.ewi.utwente.nl/" + dbuser,
                    dbuser, passwd);
            conn.setAutoCommit(true);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            if (!(publicString == null) && publicString.equals("yes")) {
                String query = "UPDATE " + dbuser + ".lifecycle.project SET public = TRUE WHERE managed_by_student = ?";
                PreparedStatement st = conn.prepareStatement(query);
                sid = DatabaseQueries.getSidFromUid(uid);
                st.setString(1, sid);
                st.executeQuery();
            }

            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        sid = DatabaseQueries.getSidFromUid(uid);

        NotificationMailer.executeSendStudentEdit(DatabaseQueries.getPidFromManagingStudent(sid));

    }

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

    /*
    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/acceptProject")
    public void acceptProject(@FormParam("isAccepted") String approval, @FormParam("studentName") String studentName,
                              @FormParam("studentID") String studentID, @FormParam("projectID") String pid,
                              final @CookieParam("uid") String uidString) {
        int uid = Integer.parseInt(uidString);

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

            System.out.println("sName: " + studentName);
            System.out.println("sID" + studentID);

            String query = "";

            if (approval.equals("accept")) {
                query = " DELETE FROM " + dbuser + ".lifecycle.application " +
                        "WHERE pid = " + pid + "; " +
                        "UPDATE " + dbuser + ".lifecycle.project " +
                        "SET managed_by_student = " + "'" + studentID + "', " +
                        "is_approved = TRUE " +
                        "WHERE pid = " + pid + " ;";
            } else if (approval.equals("refuse")) {
                //the entry in the application table with the student is deleted[v]
                query = "DELETE FROM " + dbuser + ".lifecycle.application " +
                        "WHERE s_number = " + "'" + studentID + "'" + ";";
            } else {
                connection.close();
                return;
            }

            PreparedStatement st = connection.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                resultId = rs.getInt("pid");
                resultId++;

            }
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    */


    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    public void createProjectFromForm(@FormParam("email") String email)
//                                      ,final @CookieParam("sid") String sid) {

//        int sid = Integer.parseInt(sid);

        int eid = this.getEid();
        Set<UserType> types = DatabaseQueries.getUserTypeFromUid(uid);
        boolean isStudent = false;

        if (types != null && types.contains(UserType.STUDENT)) {
            isStudent = true;
        }

        String id = "";
        Project project;
        if (isStudent) {
            id = DatabaseQueries.getSidFromUid(uid);
            if (internalExternal.equals("internal")) {
                project = new Project(this.getPid(), type, startDate, endDate, description,
                        goal, profile, 1, name, researchGroupName, id, null);
            } else {
                project = new Project(this.getPid(), type, startDate, endDate, description,
                        goal, profile, 0, name, companyName, id, null);
            }
            if (isPublic.equals("yes")) {
                project.setIsPublic(1);
            } else {
                project.setIsPublic(0);
            }
        } else {
            id = DatabaseQueries.getStaffIDFromUid(uid);
            if (internalExternal.equals("internal")) {
                project = new Project(this.getPid(), type, startDate, endDate, description,
                        goal, profile, 1, name, researchGroupName, null, id);
            } else {
                project = new Project(this.getPid(), type, startDate, endDate, description,
                        goal, profile, 0, name, companyName, null, id);
            }
        }
        addProjectToDB(project);
    }

    /**
     * Method used to add a project to the database
     * @param project - project to be added
     */
    public void addProjectToDB(Project project) {

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
            if (project.getManaged_by_student() != null) {
                if (project.getIs_internal() == 1) {

                    query = "INSERT INTO " + dbuser + ".lifecycle.Project (pid, start_date, end_date, description," +
                            " goal, profile, name, type, research_group, company, is_internal, managed_by_student) " +
                            "VALUES ('" + project.getPid() + "', '" + project.getStart_date() + "', '" +
                            project.getEnd_date() + "', '" + project.getDescription() + "', '" + project.getGoal() + "', '" +
                            project.getProfile() + "', '" + project.getName() + "', '" + project.getType() + "', '" +
                            project.getResearchGroupOrCompany() + "', '" + null + "', '" + 1 + "', '" + project.getManaged_by_student() + "');";

                } else {
                    query = "INSERT INTO " + dbuser + ".lifecycle.Project (pid, start_date, end_date, description," +
                            " goal, profile, name, type, research_group, company, is_internal, managed_by_student) " +
                            "VALUES ('" + project.getPid() + "', '" + project.getStart_date() + "', '" +
                            project.getEnd_date() + "', '" + project.getDescription() + "', '" + project.getGoal() + "', '" +
                            project.getProfile() + "', '" + project.getName() + "', '" + project.getType() + "', '" + null + "', '" +
                            project.getResearchGroupOrCompany() + "', '" + 0 + "', '" + project.getManaged_by_student() + "');";
                }
            } else {
                if (project.getIs_internal() == 1) {

                    query = "INSERT INTO " + dbuser + ".lifecycle.Project (pid, start_date, end_date, description," +
                            " goal, profile, name, type, research_group, company, is_internal, managed_by_staff) " +
                            "VALUES ('" + project.getPid() + "', '" + project.getStart_date() + "', '" +
                            project.getEnd_date() + "', '" + project.getDescription() + "', '" + project.getGoal() + "', '" +
                            project.getProfile() + "', '" + project.getName() + "', '" + project.getType() + "', '" +
                            project.getResearchGroupOrCompany() + "', '" + null + "', '" + 1 + "', '" + project.getManaged_by_staff() + "');";

                } else {
                    query = "INSERT INTO " + dbuser + ".lifecycle.Project (pid, start_date, end_date, description," +
                            " goal, profile, name, type, research_group, company, is_internal, managed_by_staff) " +
                            "VALUES ('" + project.getPid() + "', '" + project.getStart_date() + "', '" +
                            project.getEnd_date() + "', '" + project.getDescription() + "', '" + project.getGoal() + "', '" +
                            project.getProfile() + "', '" + project.getName() + "', '" + project.getType() + "', '" + null + "', '" +
                            project.getResearchGroupOrCompany() + "', '" + 0 + "', '" + project.getManaged_by_staff() + "');";
                }
            }
            if (project.getIsPublic() == 1) {
                System.out.println("test");
                query += "UPDATE lifecycle.Project SET public = TRUE WHERE pid = '" + project.getPid() + "';";
            }
            PreparedStatement st = conn.prepareStatement(query);
            try {
                st.executeQuery();
            } catch (SQLException ignored) {
            }

            NotificationMailer.executeSendNewProject(project.getPid());

            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}

