package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import datamodel.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

    private boolean isLogged(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null;
    }

    private boolean isLoggedAndAdmin(HttpServletRequest request) {
        if (isLogged(request)) {
            String currentUser = request.getSession().getAttribute("emailUser").toString();
            return DAO.isAdmin(new User(currentUser));
        } else {
            return false;
        }
    }

    private String readJSONRequest(HttpServletRequest request) throws IOException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
            buffer.append(System.lineSeparator());
        }

        return buffer.toString();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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

        switch (action) {
            case "login":
                String jsonString = readJSONRequest(request);
                JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();

                userEmail = jsonObject.get("email").getAsString();
                System.out.println(userEmail);
                password = jsonObject.get("password").getAsString();
                System.out.println(password);

                /*userEmail = request.getParameter("email");
                password = request.getParameter("password");*/

                System.out.println("------");
                System.out.println(userEmail);
                System.out.println(password);

                if ((userEmail != null && !userEmail.isBlank() && password != null && !password.isBlank()) && DAO.loginUser(new User(userEmail, password))) {
                    HttpSession session = request.getSession();
                    String jSessionId = session.getId();
                    session.setAttribute("emailUser", userEmail);
                    boolean isAdmin = DAO.isAdmin(new User(userEmail));
                    session.setMaxInactiveInterval(30 * 60);
                    Cookie userCookie = new Cookie("userCookie", userEmail);
                    userCookie.setMaxAge(30 * 60);
                    response.addCookie(userCookie);
                    PrintWriter out = response.getWriter();
                    out.println(isAdmin);
                    System.out.println("Login Success with jSessionID: " + jSessionId);
                } else {
                    System.out.println("Wrong Email or Password");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
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
                    response.setStatus(HttpServletResponse.SC_OK);
                }
                break;

            case "insert-user":
                if (isLoggedAndAdmin(request)) {
                    userName = request.getParameter("name");
                    userSurname = request.getParameter("surname");
                    userEmail = request.getParameter("email");
                    boolean administrator = Boolean.parseBoolean(request.getParameter("administrator"));
                    password = "password1";

                    if (userName != null && !userName.isBlank() && userSurname != null && !userSurname.isBlank() && userEmail != null && !userEmail.isBlank()) {
                        if (DAO.insertUser(new User(userName, userSurname, userEmail, password, administrator))) {
                            response.setStatus(HttpServletResponse.SC_CREATED);
                        } else {
                            if (DAO.checkIfUserExists(new User(userName, userSurname, userEmail, password, administrator))) {
                                response.sendError(HttpServletResponse.SC_CONFLICT);
                            }
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "activate-user":
                if (isLoggedAndAdmin(request)) {
                    userName = request.getParameter("name");
                    userSurname = request.getParameter("surname");
                    userEmail = request.getParameter("email");

                    if (userName != null && !userName.isBlank() && userSurname != null && !userSurname.isBlank() && userEmail != null && !userEmail.isBlank()) {
                        if (DAO.activateUser(new User(userName, userSurname, userEmail))) {
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "deactivate-user":
                if (isLoggedAndAdmin(request)) {
                    userName = request.getParameter("name");
                    userSurname = request.getParameter("surname");
                    userEmail = request.getParameter("email");

                    if (userName != null && !userName.isBlank() && userSurname != null && !userSurname.isBlank() && userEmail != null && !userEmail.isBlank()) {
                        if (DAO.deactivateUser(new User(userName, userSurname, userEmail))) {
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "insert-teacher":
                if (isLoggedAndAdmin(request)) {
                    teacherName = request.getParameter("name");
                    teacherSurname = request.getParameter("surname");
                    teacherEmail = request.getParameter("email");
                    password = "password1";

                    if (teacherName != null && !teacherName.isBlank() && teacherSurname != null && !teacherSurname.isBlank() && teacherEmail != null && !teacherEmail.isBlank()) {
                        if (DAO.insertTeacher(new Teacher(teacherName, teacherSurname, teacherEmail, password))) {
                            System.out.println("Teacher registered");
                            response.setStatus(HttpServletResponse.SC_CREATED);
                        } else {
                            if (DAO.checkIfTeacherExists(new Teacher(teacherName, teacherSurname, teacherEmail, password))) {
                                System.out.println("Teacher already exists");
                                response.sendError(HttpServletResponse.SC_CONFLICT);
                            }
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "activate-teacher":
                if (isLoggedAndAdmin(request)) {
                    teacherName = request.getParameter("name");
                    teacherSurname = request.getParameter("surname");
                    teacherEmail = request.getParameter("email");

                    if (teacherName != null && !teacherName.isBlank() && teacherSurname != null && !teacherSurname.isBlank() && teacherEmail != null && !teacherEmail.isBlank()) {
                        if (DAO.activateTeacher(new Teacher(teacherName, teacherSurname, teacherEmail))) {
                            System.out.println("Teacher activated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "deactivate-teacher":
                if (isLoggedAndAdmin(request)) {
                    teacherName = request.getParameter("name");
                    teacherSurname = request.getParameter("surname");
                    teacherEmail = request.getParameter("email");

                    if (teacherName != null && !teacherName.isBlank() && teacherSurname != null && !teacherSurname.isBlank() && teacherEmail != null && !teacherEmail.isBlank()) {
                        if (DAO.deactivateTeacher(new Teacher(teacherName, teacherSurname, teacherEmail))) {
                            System.out.println("Teacher deactivated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "insert-course":
                if (isLoggedAndAdmin(request)) {
                    title = request.getParameter("title");

                    if (title != null && !title.isBlank()) {
                        if (DAO.insertCourse(new Course(title))) {
                            System.out.println("Course registered");
                            response.setStatus(HttpServletResponse.SC_CREATED);
                        } else {
                            if (DAO.checkIfCourseExists(new Course(title))) {
                                System.out.println("Course already exists");
                                response.sendError(HttpServletResponse.SC_CONFLICT);
                            }
                        }
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "activate-course":
                if (isLoggedAndAdmin(request)) {
                    title = request.getParameter("title");

                    if (title != null && !title.isBlank()) {
                        if (DAO.activateCourse(new Course(title))) {
                            System.out.println("Course activated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "deactivate-course":
                if (isLoggedAndAdmin(request)) {
                    title = request.getParameter("title");

                    if (title != null && !title.isBlank()) {
                        if (DAO.deactivateCourse(new Course(title))) {
                            System.out.println("Course deactivated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "insert-time-slot":
                if (isLoggedAndAdmin(request)) {
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));

                    if (day != null && !day.isBlank() && !String.valueOf(hour).isBlank()) {
                        if (DAO.insertTimeSlot(new TimeSlot(day, hour))) {
                            System.out.println("TimeSlot registered");
                            response.setStatus(HttpServletResponse.SC_CREATED);
                        } else {
                            if (DAO.checkIfTimeSlotExists(new TimeSlot(day, hour))) {
                                System.out.println("TimeSlot already exists");
                                response.sendError(HttpServletResponse.SC_CONFLICT);
                            }
                        }
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "activate-time-slot":
                if (isLoggedAndAdmin(request)) {
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));

                    if (day != null && !day.isBlank() && !String.valueOf(hour).isBlank()) {
                        if (DAO.activateTimeSlot(new TimeSlot(day, hour))) {
                            System.out.println("TimeSlot activated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "deactivate-time-slot":
                if (isLoggedAndAdmin(request)) {
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));

                    if (day != null && !day.isBlank() && !String.valueOf(hour).isBlank()) {
                        if (DAO.deactivateTimeSlot(new TimeSlot(day, hour))) {
                            System.out.println("TimeSlot deactivated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "assign-teaching":
                if (isLoggedAndAdmin(request)) {
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (teacherEmail != null && !teacherEmail.isBlank() && title != null && !title.isBlank()) {
                        if (DAO.assignTeaching(new Teacher(teacherEmail), new Course(title))) {
                            System.out.println("Teacher assignment registered");
                            response.setStatus(HttpServletResponse.SC_CREATED);
                        } else {
                            if (DAO.checkIfTeachingExists(new TeacherCourse(new Teacher(teacherEmail), new Course(title)))) {
                                response.sendError(HttpServletResponse.SC_CONFLICT);
                                System.out.println("Teacher assignment already exists");
                            }
                        }
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "activate-teaching":
                if (isLoggedAndAdmin(request)) {
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (teacherEmail != null && !teacherEmail.isBlank() && title != null && !title.isBlank()) {
                        if (DAO.activateTeaching(new TeacherCourse(new Teacher(teacherEmail), new Course(title)))) {
                            System.out.println("Teaching activated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "deactivate-teaching":
                if (isLoggedAndAdmin(request)) {
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (teacherEmail != null && !teacherEmail.isBlank() && title != null && !title.isBlank()) {
                        if (DAO.deactivateTeaching(new TeacherCourse(new Teacher(teacherEmail), new Course(title)))) {
                            System.out.println("Teaching deactivated");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "insert-booking":
                if (isLogged(request)) {
                    userEmail = request.getParameter("user-email");
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (userEmail != null && !userEmail.isBlank() && day != null && !day.isBlank() && !String.valueOf(hour).isBlank() && teacherEmail != null && !teacherEmail.isBlank() && title != null && !title.isBlank()) {
                        if (DAO.insertBooking(new User(userEmail), new TimeSlot(day, hour), new Teacher(teacherEmail), new Course(title))) {
                            System.out.println("Booking registered");
                            response.setStatus(HttpServletResponse.SC_CREATED);
                        } else {
                            System.out.println("Error during booking insertion");
                            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "delete-booking":
                if (isLogged(request)) {
                    userEmail = request.getParameter("user-email");
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (userEmail != null && !userEmail.isBlank() && day != null && !day.isBlank() && !String.valueOf(hour).isBlank() && teacherEmail != null && !teacherEmail.isBlank() && title != null && !title.isBlank()) {
                        if (DAO.deleteBooking(new Booking(new User(userEmail), new TimeSlot(day, hour), new TeacherCourse(new Teacher(teacherEmail), new Course(title))))) {
                            System.out.println("Booking deleted");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "complete-booking":
                if (isLogged(request)) {
                    userEmail = request.getParameter("user-email");
                    day = request.getParameter("day");
                    hour = Integer.parseInt(request.getParameter("hour"));
                    teacherEmail = request.getParameter("teacher-email");
                    title = request.getParameter("title");

                    if (userEmail != null && !userEmail.isBlank() && day != null && !day.isBlank() && !String.valueOf(hour).isBlank() && teacherEmail != null && !teacherEmail.isBlank() && title != null && !title.isBlank()) {
                        if (DAO.completeBooking(new Booking(new User(userEmail), new TimeSlot(day, hour), new TeacherCourse(new Teacher(teacherEmail), new Course(title))))) {
                            System.out.println("Booking completed");
                            response.setStatus(HttpServletResponse.SC_OK);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    }
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        String action = request.getParameter("action");
        PrintWriter out = response.getWriter();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        switch (action) {
            case "list-active-users":
                if (isLoggedAndAdmin(request)) {
                    ArrayList<User> users = DAO.queryActiveUsers();
                    out.println(gson.toJson(users));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-deactivated-users":
                if (isLoggedAndAdmin(request)) {
                    ArrayList<User> users = DAO.queryDeactivatedUsers();
                    out.println(gson.toJson(users));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-active-teachers":
                ArrayList<Teacher> activeTeachers = DAO.queryActiveTeachers();
                out.println(gson.toJson(activeTeachers));
                out.flush();
                break;

            case "list-deactivated-teachers":
                ArrayList<Teacher> deactivatedTeachers = DAO.queryDeactivatedTeachers();
                out.println(gson.toJson(deactivatedTeachers));
                out.flush();
                break;

            case "list-teacher-availability":
                String teacherEmail = request.getParameter("teacher-email");
                if (teacherEmail != null && !teacherEmail.isBlank()) {
                    ArrayList<TimeSlot> timeSlots = DAO.queryTeacherAvailability(new Teacher(teacherEmail));
                    out.println(gson.toJson(timeSlots));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;

            case "list-teacher-by-course":
                String title = request.getParameter("title");
                if (title != null && !title.isBlank()) {
                    ArrayList<Teacher> teacherByCourse = DAO.viewTeacherByCourse(new Course(title));
                    out.println(gson.toJson(teacherByCourse));
                    out.flush();
                } else {
                    System.out.println("Null title parameter");
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;

            case "list-active-courses":
                ArrayList<Course> activeCourses = DAO.queryActiveCourses();
                out.println(gson.toJson(activeCourses));
                out.flush();
                break;

            case "list-deactivated-courses":
                ArrayList<Course> deactivatedCourses = DAO.queryDeactivatedCourses();
                out.println(gson.toJson(deactivatedCourses));
                out.flush();
                break;

            case "list-teacher-courses":
                ArrayList<TeacherCourse> teacherCourses = DAO.queryTeacherCourse();
                out.println(gson.toJson(teacherCourses));
                out.flush();
                break;

            case "list-all-bookings":
                if (isLoggedAndAdmin(request)) {
                    ArrayList<Booking> bookings = DAO.queryAllBookings();
                    out.println(gson.toJson(bookings));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-all-active-bookings":
                if (isLoggedAndAdmin(request)) {
                    ArrayList<Booking> activeBookings = DAO.queryAllActiveBookings();
                    out.println(gson.toJson(activeBookings));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-all-completed-bookings":
                if (isLoggedAndAdmin(request)) {
                    ArrayList<Booking> completedBookings = DAO.queryAllCompletedBookings();
                    out.println(gson.toJson(completedBookings));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-all-deleted-bookings":
                if (isLoggedAndAdmin(request)) {
                    ArrayList<Booking> deletedBookings = DAO.queryAllDeletedBookings();
                    out.println(gson.toJson(deletedBookings));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-personal-bookings":
                if (isLogged(request)) {
                    String thisUser = request.getSession().getAttribute("emailUser").toString();
                    ArrayList<Booking> personalBookings = DAO.queryPersonalBookings(new User(thisUser));
                    out.println(gson.toJson(personalBookings));
                    out.flush();
                } else {
                    System.out.println("Null user-email parameter");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-personal-active-bookings":
                if (isLogged(request)) {
                    String thisUser = request.getSession().getAttribute("emailUser").toString();
                    ArrayList<Booking> personalActiveBookings = DAO.queryPersonalActiveBookings(new User(thisUser));
                    out.println(gson.toJson(personalActiveBookings));
                    out.flush();
                } else {
                    System.out.println("Null user-email parameter");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-personal-completed-bookings":
                if (isLogged(request)) {
                    String thisUser = request.getSession().getAttribute("emailUser").toString();
                    ArrayList<Booking> personalCompletedBookings = DAO.queryPersonalCompletedBookings(new User(thisUser));
                    out.println(gson.toJson(personalCompletedBookings));
                    out.flush();
                } else {
                    System.out.println("Null user-email parameter");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-personal-deleted-bookings":
                if (isLogged(request)) {
                    String thisUser = request.getSession().getAttribute("emailUser").toString();
                    ArrayList<Booking> personalDeletedBookings = DAO.queryPersonalDeletedBookings(new User(thisUser));
                    out.println(gson.toJson(personalDeletedBookings));
                    out.flush();
                } else {
                    System.out.println("Null user-email parameter");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-active-time-slots":
                if (isLogged(request)) {
                    ArrayList<TimeSlot> activeTimeSlots = DAO.queryActiveTimeSlots();
                    out.println(gson.toJson(activeTimeSlots));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            case "list-deactivated-time-slots":
                if (isLogged(request)) {
                    ArrayList<TimeSlot> deactivatedTimeSlots = DAO.queryDeactivatedTimeSlots();
                    out.println(gson.toJson(deactivatedTimeSlots));
                    out.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}