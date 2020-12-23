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

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
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

        String userName;
        String userSurname;
        String userEmail;

        String teacherName;
        String teacherSurname;
        String teacherEmail;
        String password;

        String title;

        String day;
        int hour;

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "login":
                    userEmail = request.getParameter("email");
                    password = request.getParameter("password");
                    System.out.println(userEmail);
                    System.out.println(password);

                    if ((userEmail != null && password != null) && DAO.loginUser(new User(userEmail, password))) {
                        HttpSession session = request.getSession();
                        String jSessionId = session.getId();
                        session.setAttribute("emailUser", userEmail);
                        session.setMaxInactiveInterval(30 * 60);
                        Cookie userCookie = new Cookie("userCookie", userEmail);
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
                    userName = request.getParameter("name");
                    userSurname = request.getParameter("surname");
                    userEmail = request.getParameter("email");
                    boolean administrator = Boolean.parseBoolean(request.getParameter("administrator"));
                    password = "password1";

                    if (userName != null && userSurname != null && userEmail != null) {
                        if (DAO.insertUser(new User(userName, userSurname, userEmail, password, administrator))) {
                            System.out.println("User registered");
                            response.setStatus(201);
                        } else {
                            if (DAO.checkIfUserExists(new User(userName, userSurname, userEmail, password, administrator))) {
                                System.out.println("User already exists");
                                response.sendError(409);
                            }
                        }
                    }
                    break;

                case "activate-user":
                    userName = request.getParameter("name");
                    userSurname = request.getParameter("surname");
                    userEmail = request.getParameter("email");

                    if (userName != null && userSurname != null && userEmail != null) {
                        if (DAO.activateUser(new User(userName, userSurname, userEmail))) {
                            System.out.println("User activated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during user activation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "deactivate-user":
                    userName = request.getParameter("name");
                    userSurname = request.getParameter("surname");
                    userEmail = request.getParameter("email");

                    if (userName != null && userSurname != null && userEmail != null) {
                        if (DAO.deactivateUser(new User(userName, userSurname, userEmail))) {
                            System.out.println("User deactivated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during user deactivation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "insert-teacher":
                    teacherName = request.getParameter("name");
                    teacherSurname = request.getParameter("surname");
                    teacherEmail = request.getParameter("email");
                    password = "password1";

                    if (teacherName != null && teacherSurname != null && teacherEmail != null) {
                        if (DAO.insertTeacher(new Teacher(teacherName, teacherSurname, teacherEmail, password))) {
                            System.out.println("Teacher registered");
                            response.setStatus(201);
                        } else {
                            if (DAO.checkIfTeacherExists(new Teacher(teacherName, teacherSurname, teacherEmail, password))) {
                                System.out.println("Teacher already exists");
                                response.sendError(409);
                            }
                        }
                    }
                    break;

                case "activate-teacher":
                    teacherName = request.getParameter("name");
                    teacherSurname = request.getParameter("surname");
                    teacherEmail = request.getParameter("email");

                    if (teacherName != null && teacherSurname != null && teacherEmail != null) {
                        if (DAO.activateTeacher(new Teacher(teacherName, teacherSurname, teacherEmail))) {
                            System.out.println("Teacher activated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during teacher activation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "deactivate-teacher":
                    teacherName = request.getParameter("name");
                    teacherSurname = request.getParameter("surname");
                    teacherEmail = request.getParameter("email");

                    if (teacherName != null && teacherSurname != null && teacherEmail != null) {
                        if (DAO.deactivateTeacher(new Teacher(teacherName, teacherSurname, teacherEmail))) {
                            System.out.println("Teacher deactivated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during teacher deactivation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "insert-course":
                    title = request.getParameter("title");

                    if (title != null) {
                        if (DAO.insertCourse(new Course(title))) {
                            System.out.println("Course registered");
                            response.setStatus(201);
                        } else {
                            if (DAO.checkIfCourseExists(new Course(title))) {
                                System.out.println("Course already exists");
                                response.sendError(409);
                            }
                        }
                    }
                    break;

                case "activate-course":
                    title = request.getParameter("title");

                    if (title != null) {
                        if (DAO.activateCourse(new Course(title))) {
                            System.out.println("Course activated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during course activation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "deactivate-course":
                    title = request.getParameter("title");

                    if (title != null) {
                        if (DAO.deactivateCourse(new Course(title))) {
                            System.out.println("Course deactivated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during course deactivation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "insert-time-slot":
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));

                    if (day != null) {
                        if (DAO.insertTimeSlot(new TimeSlot(day, hour))) {
                            System.out.println("TimeSlot registered");
                            response.setStatus(201);
                        } else {
                            if (DAO.checkIfTimeSlotExists(new TimeSlot(day, hour))) {
                                System.out.println("TimeSlot already exists");
                                response.sendError(409);
                            }
                        }
                    }
                    break;

                case "activate-time-slot":
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));

                    if (day != null) {
                        if (DAO.activateTimeSlot(new TimeSlot(day, hour))) {
                            System.out.println("TimeSlot activated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during time slot activation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "deactivate-time-slot":
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));

                    if (day != null) {
                        if (DAO.deactivateTimeSlot(new TimeSlot(day, hour))) {
                            System.out.println("TimeSlot deactivated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during time slot deactivation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "assign-teaching":
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (teacherEmail != null && title != null) {
                        if (DAO.assignTeaching(new Teacher(teacherEmail), new Course(title))) {
                            System.out.println("Teacher assignment registered");
                            response.setStatus(201);
                        } else {
                            if (DAO.checkIfTeachingExists(new TeacherCourse(new Teacher(teacherEmail), new Course(title)))) {
                                response.sendError(409);
                                System.out.println("Teacher assignment already exists");
                            }
                        }
                    }
                    break;

                case "activate-teaching":
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (teacherEmail != null && title != null) {
                        if (DAO.activateTeaching(new TeacherCourse(new Teacher(teacherEmail), new Course(title)))) {
                            System.out.println("Teaching activated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during teaching activation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "deactivate-teaching":
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (teacherEmail != null && title != null) {
                        if (DAO.deactivateTeaching(new TeacherCourse(new Teacher(teacherEmail), new Course(title)))) {
                            System.out.println("Teaching deactivated");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during teaching deactivation");
                            response.sendError(400);
                        }
                    }
                    break;

                case "insert-booking":
                    userEmail = request.getParameter("user-email");
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (userEmail != null && day != null && teacherEmail != null && title != null) {
                        if (DAO.insertBooking(new User(userEmail), new TimeSlot(day, hour), new Teacher(teacherEmail), new Course(title))) {
                            System.out.println("Booking registered");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during booking insertion");
                            response.sendError(400);
                        }
                    }
                    break;

                case "delete-booking":
                    userEmail = request.getParameter("user-email");
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (userEmail != null && day != null && teacherEmail != null && title != null) {
                        if (DAO.deleteBooking(new Booking(new User(userEmail), new TimeSlot(day, hour), new TeacherCourse(new Teacher(teacherEmail), new Course(title))))) {
                            System.out.println("Booking deleted");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error during booking deletion");
                            response.sendError(400);
                        }
                    }

                    break;

                case "complete-booking":
                    userEmail = request.getParameter("user-email");
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (userEmail != null && day != null && teacherEmail != null && title != null) {
                        if (DAO.completeBooking(new Booking(new User(userEmail), new TimeSlot(day, hour), new TeacherCourse(new Teacher(teacherEmail), new Course(title))))) {
                            System.out.println("Booking completed");
                            response.setStatus(201);
                        } else {
                            System.out.println("Error while completing the booking");
                            response.sendError(400);
                        }
                    }
                    break;
            }
        } else {
            response.sendError(400, "Bad Request");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (action != null) {
            switch (action) {
                case "list-users":
                    ArrayList<User> users = DAO.queryUsers();
                    out.println(gson.toJson(users));
                    out.flush();
                    break;

                case "list-teachers":
                    ArrayList<Teacher> teachers = DAO.queryTeachers();
                    out.println(gson.toJson(teachers));
                    out.flush();
                    break;

                case "list-teacher-availability":
                    String teacherEmail = request.getParameter("teacher-email");
                    if (teacherEmail != null) {
                        ArrayList<TimeSlot> timeSlots = DAO.queryTeacherAvailability(new Teacher(teacherEmail));
                        out.println(gson.toJson(timeSlots));
                        out.flush();
                    }

                case "list-courses":
                    ArrayList<Course> courses = DAO.queryCourses();
                    out.println(gson.toJson(courses));
                    out.flush();
                    break;

                case "list-teacher-courses":
                    ArrayList<TeacherCourse> teacherCourses = DAO.queryTeacherCourse();
                    out.println(gson.toJson(teacherCourses));
                    out.flush();
                    break;

                case "list-bookings":
                    ArrayList<Booking> bookings = DAO.queryBookings();
                    out.println(gson.toJson(bookings));
                    out.flush();
                    break;

                case "list-personal-bookings":
                    String userEmail = request.getParameter("user-email");
                    if (userEmail != null) {
                        ArrayList<Booking> personalBookings = DAO.queryPersonalBooking(new User(userEmail));
                        out.println(gson.toJson(personalBookings));
                        out.flush();
                    } else {
                        System.out.println("Null user-email parameter");
                        response.sendError(401);
                    }
                    break;

                case "list-teacher-by-course":
                    String title = request.getParameter("title");
                    if (title != null) {
                        ArrayList<Teacher> teacherByCourse = DAO.viewTeacherByCourse(new Course(title));
                        out.println(gson.toJson(teacherByCourse));
                        out.flush();
                    } else {
                        System.out.println("Null title parameter");
                        response.sendError(401);
                    }
                    break;
            }
        } else {
            response.sendError(400, "Bad Request");
        }
    }
}