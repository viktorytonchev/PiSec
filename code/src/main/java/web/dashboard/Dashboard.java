package web.dashboard;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession() != null && req.getSession ().getAttribute("logged_in") != null && (boolean) req.getSession().getAttribute("logged_in")) {
            RequestDispatcher rd = req.getRequestDispatcher("mainPage.html");
            rd.include(req, resp);
        } else{
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.include(req, resp);
        }
    }
}
