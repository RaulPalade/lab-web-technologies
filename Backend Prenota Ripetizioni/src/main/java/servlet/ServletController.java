package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datamodel.DAO;
import datamodel.Teacher;
import datamodel.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "ServletController", urlPatterns = "/ServletController")
public class ServletController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        DAO.registerDriver();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        switch (action) {
            case "/login":
                PrintWriter out = response.getWriter();

                String emailUser = request.getParameter("emailUser");
                String passwordUser = request.getParameter("passwordUser");

                if ((emailUser != null && passwordUser != null) && DAO.loginUser(new User(emailUser, passwordUser))) {
                    HttpSession session = request.getSession();
                    String jSessionId = session.getId();
                    session.setAttribute("emailUser", emailUser);
                    session.setMaxInactiveInterval(30 * 60);
                    Cookie userCookie = new Cookie("userCookie", emailUser);
                    userCookie.setMaxAge(30 * 60);
                    response.addCookie(userCookie);
                } else {
                    response.sendError(response.getStatus(), "Wrong Email or Password");
                }
                break;

            case "/logout":
                response.setContentType("text/html");
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("JSESSIONID")) {
                            System.out.println("JSESSIONID = " + cookie.getValue());
                        }
                    }
                }

                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                    response.setStatus(200);
                }
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        String action = request.getParameter("action");

        switch (action) {
            case "listUsers":
                if (isLogged(request, response)) {
                    response.sendError(404, "Unauthorized");
                } else {
                    ArrayList<User> users = DAO.queryUsers();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(users));
                    out.flush();
                }
                break;

            case "listTeacher":
                if (isLogged(request, response)) {
                    response.sendError(404, "Unauthorized");
                } else {
                    ArrayList<Teacher> teachers = DAO.queryTeachers();
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(teachers));
                    out.flush();
                }
                break;

            case "listCourse":
                break;

            case "listTeacherCourse":
                break;

            case "listBooking":
                break;
        }
    }

    private boolean isLogged(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("emailUser") != null;
    }
}