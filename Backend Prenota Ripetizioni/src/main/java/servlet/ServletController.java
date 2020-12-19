package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import datamodel.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


// TODO (1): Control administrator access to resources
@WebServlet(name = "ServletController", urlPatterns = {"/ServletController"})
public class ServletController extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        DAO.registerDriver();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "login":
                    String emailUser = request.getParameter("email");
                    String passwordUser = request.getParameter("password");
                    System.out.println(emailUser);
                    System.out.println(passwordUser);

                    if ((emailUser != null && passwordUser != null) && DAO.loginUser(new User(emailUser, passwordUser))) {
                        HttpSession session = request.getSession();
                        String jSessionId = session.getId();
                        session.setAttribute("emailUser", emailUser);
                        session.setMaxInactiveInterval(30 * 60);
                        Cookie userCookie = new Cookie("userCookie", emailUser);
                        userCookie.setMaxAge(30 * 60);
                        response.addCookie(userCookie);
                        System.out.println("Login Success with jSessionID: " + jSessionId);
                    } else {
                        System.out.println("Wrong Email or Password");
                        response.sendError(response.getStatus(), "Wrong Email or Password");
                    }
                    break;

                case "logout":
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

                case "insert-user":
                    break;

                case "insert-teacher":
                    break;

                case "insert-course":
                    break;

                case "assign-teaching":
                    break;

                case "insert-booking":
                    break;

                case "insert-time-slot":
                    break;

                case "deactivate-user":
                    break;

                case "deactivate-teacher":
                    break;

                case "deactivate-course":
                    break;

                case "deactivate-teaching":
                    break;

                case "delete-booking":
                    break;

                case "complete-booking":
                    break;

                case "deactivate-time-slot":
                    break;

                case "activate-user":
                    break;

                case "activate-teacher":
                    break;

                case "activate-course":
                    break;

                case "activate-teaching":
                    break;

                case "activate-time-slot":
                    break;
            }
        } else {
            System.err.println("NULL ACTION");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("Non loggato");
            response.sendError(401, "Unauthorized");
        } else {
            switch (action) {
                case "list-users":
                    ArrayList<User> users = DAO.queryUsers();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(users));
                    out.flush();
                    break;

                case "list-teachers":
                    ArrayList<Teacher> teachers = DAO.queryTeachers();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(teachers));
                    out.flush();
                    break;

                case "list-courses":
                    ArrayList<Course> courses = DAO.queryCourses();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(courses));
                    out.flush();
                    break;

                case "list-teacher-courses":
                    ArrayList<TeacherCourse> teacherCourses = DAO.queryTeacherCourse();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(teacherCourses));
                    out.flush();
                    break;

                case "list-bookings":
                    ArrayList<Booking> bookings = DAO.queryBookings();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(bookings));
                    out.flush();
                    break;

                case "list-teacher-by-course":
                    ArrayList<Teacher> teacherByCourse = new ArrayList<>();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    out.println(gson.toJson(teacherByCourse));
                    out.flush();
                    break;
            }
        }
    }
}