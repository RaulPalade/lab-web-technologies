package datamodel;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
public class DAO {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/lessonscheduling";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWORD = "";
    private static final int SALT = 12;

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            System.out.println("Driver registered correctly");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, MYSQL_USER, MYSQL_PASSWORD);
    }

    private static @NotNull ArrayList<User> queryUsers(boolean active) {
        Connection connection = null;
        ArrayList<User> users = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from user where active = ?");
            statement.setBoolean(1, active);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt("IdUser"),
                        resultSet.getString("Name"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Email"),
                        resultSet.getBoolean("Administrator"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return users;
    }

    public static @NotNull ArrayList<User> queryActiveUsers() {
        return queryUsers(true);
    }

    public static @NotNull ArrayList<User> queryDeactivatedUsers() {
        return queryUsers(false);
    }

    public static boolean loginUser(User user) {
        if (user == null) {
            return false;
        }

        Connection connection = null;
        boolean loginResult = false;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select Email, Password from user where Email = ?");
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                loginResult = resultSet.getString(1).equals(user.getEmail()) &&
                        BCrypt.checkpw(user.getPassword(), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return loginResult;
    }

    public static boolean isAdmin(User user) {
        if (user == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select Administrator from user where active = ? and Email = ?");
            statement.setBoolean(1, true);
            statement.setString(2, user.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rowAffected = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean insertUser(User user) {
        if (user == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;
        String salt = BCrypt.gensalt(SALT);

        try {
            connection = DAO.connect();
            String hashedPassword = BCrypt.hashpw(user.getPassword(), salt);
            System.out.println(hashedPassword);
            PreparedStatement statement = connection.prepareStatement("insert into user (Name, Surname, Email, Password, Administrator) values (?, ?, ?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isAdministrator());
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean activateUser(User user) {
        return user != null && changeUserStatus(user, true);
    }

    public static boolean deactivateUser(User user) {
        return user != null && changeUserStatus(user, false);
    }

    private static boolean changeUserStatus(User user, boolean status) {
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update user set Active = ? where Name = ? and Surname = ? and Email = ?");
            statement.setBoolean(1, status);
            statement.setString(2, user.getName());
            statement.setString(3, user.getSurname());
            statement.setString(4, user.getEmail());
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean checkIfUserExists(User user) {
        if (user == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select exists (select * from user where Email = ?)");
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rowAffected = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    private static @NotNull ArrayList<Teacher> queryTeachers(boolean active) {
        Connection connection = null;
        ArrayList<Teacher> teachers = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from teacher where active = ?");
            statement.setBoolean(1, active);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher(resultSet.getInt("IdTeacher"),
                        resultSet.getString("Name"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Email"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return teachers;
    }

    public static @NotNull ArrayList<Teacher> queryActiveTeachers() {
        return queryTeachers(true);
    }

    public static @NotNull ArrayList<Teacher> queryDeactivatedTeachers() {
        return queryTeachers(false);
    }

    public static ArrayList<TimeSlot> queryTeacherAvailability(Teacher teacher) {
        Connection connection = null;
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select IdTimeSlot, Day, Hour\n" +
                    "from time_slot\n" +
                    "where Active = ?\n" +
                    "  and (Day, Hour) not in\n" +
                    "      (select Day, Hour\n" +
                    "       from booking\n" +
                    "                join time_slot ts on booking.IdTimeSlot = ts.IdTimeSlot\n" +
                    "                join teacher_course tc on tc.IdTeacher = booking.IdTeacher and tc.IdCourse = booking.IdCourse\n" +
                    "                join teacher t on tc.IdTeacher = t.IdTeacher\n" +
                    "       where booking.Completed = ?\n" +
                    "         and booking.Deleted = ?\n" +
                    "         and t.Email = ?);");
            statement.setBoolean(1, true);
            statement.setBoolean(2, false);
            statement.setBoolean(3, false);
            statement.setString(4, teacher.getEmail());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TimeSlot timeSlot = new TimeSlot(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
                availableSlots.add(timeSlot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return availableSlots;
    }

    public static boolean insertTeacher(Teacher teacher) {
        if (teacher == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;
        String salt = BCrypt.gensalt(SALT);
        try {
            connection = DAO.connect();
            String hashedPassword = BCrypt.hashpw(teacher.getPassword(), salt);
            PreparedStatement statement = connection.prepareStatement("insert into teacher (Name, Surname, Email, Password) values (?, ?, ?, ?)");
            statement.setString(1, teacher.getName());
            statement.setString(2, teacher.getSurname());
            statement.setString(3, teacher.getEmail());
            statement.setString(4, hashedPassword);
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean activateTeacher(Teacher teacher) {
        return teacher != null && changeTeacherStatus(teacher, true);
    }

    public static boolean deactivateTeacher(Teacher teacher) {
        return teacher != null && changeTeacherStatus(teacher, false);
    }

    private static boolean changeTeacherStatus(Teacher teacher, boolean status) {
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update teacher set Active = ? where Name = ? and Surname = ? and Email = ?");
            statement.setBoolean(1, status);
            statement.setString(2, teacher.getName());
            statement.setString(3, teacher.getSurname());
            statement.setString(4, teacher.getEmail());
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean checkIfTeacherExists(Teacher teacher) {
        if (teacher == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select exists (select * from teacher where Email = ?)");
            statement.setString(1, teacher.getEmail());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rowAffected = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    private static @NotNull ArrayList<Course> queryCourses(boolean active) {
        Connection connection = null;
        ArrayList<Course> courses = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from course where active = ?");
            statement.setBoolean(1, active);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getInt("IdCourse"), resultSet.getString("Title"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return courses;
    }

    public static @NotNull ArrayList<Course> queryActiveCourses() {
        return queryCourses(true);
    }

    public static @NotNull ArrayList<Course> queryDeactivatedCourses() {
        return queryCourses(false);
    }

    public static boolean insertCourse(Course course) {
        if (course == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;
        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("insert into course (Title) values (?)");
            statement.setString(1, course.getTitle());
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean activateCourse(Course course) {
        return course != null && changeCourseStatus(course, true);
    }

    public static boolean deactivateCourse(Course course) {
        return course != null && changeCourseStatus(course, false);
    }

    private static boolean changeCourseStatus(Course course, boolean status) {
        Connection connection = null;
        int rowAffected = 0;
        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update course set Active = ? where Title = ?;");
            statement.setBoolean(1, status);
            statement.setString(2, course.getTitle());
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean checkIfCourseExists(Course course) {
        if (course == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select exists (select * from course where Title = ?)");
            statement.setString(1, course.getTitle());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rowAffected = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    private static ArrayList<TimeSlot> queryTimeSlots(boolean active) {
        Connection connection = null;
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from time_slot where active = ?");
            statement.setBoolean(1, active);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TimeSlot timeSlot = new TimeSlot(resultSet.getInt("IdTimeSlot"), resultSet.getString("Day"), resultSet.getInt("Hour"));
                timeSlots.add(timeSlot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return timeSlots;
    }

    public static @NotNull ArrayList<TimeSlot> queryActiveTimeSlots() {
        return queryTimeSlots(true);
    }

    public static @NotNull ArrayList<TimeSlot> queryDeactivatedTimeSlots() {
        return queryTimeSlots(false);
    }

    public static boolean insertTimeSlot(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;
        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("insert into time_slot (Day, Hour) values (?, ?)");
            statement.setString(1, timeSlot.getDay());
            statement.setInt(2, timeSlot.getHour());
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean activateTimeSlot(TimeSlot timeSlot) {
        return timeSlot != null && changeTimeSlotStatus(timeSlot, true);
    }

    public static boolean deactivateTimeSlot(TimeSlot timeSlot) {
        return timeSlot != null && changeTimeSlotStatus(timeSlot, false);
    }

    private static boolean changeTimeSlotStatus(TimeSlot timeSlot, boolean status) {
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update time_slot set Active = ? where Day = ? and Hour = ?");
            statement.setBoolean(1, status);
            statement.setString(2, timeSlot.getDay());
            statement.setInt(3, timeSlot.getHour());
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean checkIfTimeSlotExists(TimeSlot timeSlot) {
        if (timeSlot == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select exists (select * from time_slot where Day = ? and Hour = ?)");
            statement.setString(1, timeSlot.getDay());
            statement.setInt(2, timeSlot.getHour());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rowAffected = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static ArrayList<TeacherCourse> queryTeacherCourse() {
        Connection connection = null;
        ArrayList<TeacherCourse> teacherCourses = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select t.IdTeacher, t.Name, t.Surname, t.Email, c.IdCourse, c.Title\n" +
                    "from teacher_course join teacher t on t.IdTeacher = teacher_course.IdTeacher join course c on c.IdCourse = teacher_course.IdCourse;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                Course course = new Course(resultSet.getInt(5), resultSet.getString(6));
                TeacherCourse teacherCourse = new TeacherCourse(teacher, course);
                teacherCourses.add(teacherCourse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return teacherCourses;
    }

    public static boolean assignTeaching(Teacher teacher, Course course) {
        if (teacher == null || course == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        int idTeacher = getIdTeacher(teacher.getEmail());
        int idCourse = getIdCourse(course.getTitle());
        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("insert into teacher_course (IdTeacher, IdCourse) values (?, ?)");
            statement.setInt(1, idTeacher);
            statement.setInt(2, idCourse);
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean activateTeaching(TeacherCourse teacherCourse) {
        return teacherCourse != null && changeTeachingStatus(teacherCourse, true);
    }

    public static boolean deactivateTeaching(TeacherCourse teacherCourse) {
        return teacherCourse != null && changeTeachingStatus(teacherCourse, false);
    }

    private static boolean changeTeachingStatus(TeacherCourse teacherCourse, boolean status) {
        Connection connection = null;
        int rowAffected = 0;

        int idTeacher = getIdTeacher(teacherCourse.getTeacher().getEmail());
        int idCourse = getIdCourse(teacherCourse.getCourse().getTitle());

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update teacher_course set Active = ? where IdTeacher = ? and IdCourse = ?");
            statement.setBoolean(1, status);
            statement.setInt(2, idTeacher);
            statement.setInt(3, idCourse);
            rowAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    public static boolean checkIfTeachingExists(TeacherCourse teacherCourse) {
        if (teacherCourse == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        int idTeacher = getIdTeacher(teacherCourse.getTeacher().getEmail());
        int idCourse = getIdCourse(teacherCourse.getCourse().getTitle());

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select exists (select * from teacher_course where IdTeacher = ? and IdCourse = ?)");
            statement.setInt(1, idTeacher);
            statement.setInt(2, idCourse);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                rowAffected = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return rowAffected != 0;
    }

    private static ArrayList<Teacher> viewTeacherByCourse(Course course, boolean active) {
        if (course == null) {
            return new ArrayList<>();
        }

        Connection connection = null;
        int idCourse = getIdCourse(course.getTitle());
        ArrayList<Teacher> teachers = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select Name, Surname, Email\n" +
                    "from teacher join teacher_course tc on teacher.IdTeacher = tc.IdTeacher join course c on c.IdCourse = tc.IdCourse\n" +
                    "where tc.Active = ? and c.IdCourse = ?;");
            statement.setBoolean(1, active);
            statement.setInt(2, idCourse);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher(resultSet.getString("Name"), resultSet.getString("Surname"), resultSet.getString("email"));
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return teachers;
    }

    public static ArrayList<Teacher> viewActiveTeacherByCourse(Course course) {
        return viewTeacherByCourse(course, true);
    }

    public static ArrayList<Teacher> viewDeactivatedTeacherByCourse(Course course) {
        return viewTeacherByCourse(course, false);
    }

    public static ArrayList<Course> viewCourseByTeacher(Teacher teacher) {
        if (teacher == null) {
            return new ArrayList<>();
        }

        Connection connection = null;
        int idTeacher = getIdTeacher(teacher.getEmail());
        ArrayList<Course> courses = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select Title\n" +
                    "from course\n" +
                    "         join teacher_course tc on course.IdCourse = tc.IdCourse\n" +
                    "         join teacher t on tc.IdTeacher = t.IdTeacher\n" +
                    "where tc.Active = ? and t.IdTeacher = ?;");
            statement.setBoolean(1, true);
            statement.setInt(2, idTeacher);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getString("Title"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return courses;
    }

    public static ArrayList<Course> viewCourseNotTaughtByTeacher(Teacher teacher) {
        if (teacher == null) {
            return new ArrayList<>();
        }

        Connection connection = null;
        int idTeacher = getIdTeacher(teacher.getEmail());
        ArrayList<Course> courses = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select Title\n" +
                    "from course\n" +
                    "where Title not in (select Title\n" +
                    "                       from course\n" +
                    "                                join teacher_course tc on course.IdCourse = tc.IdCourse\n" +
                    "                                join teacher t on tc.IdTeacher = t.IdTeacher\n" +
                    "                       where tc.Active = ?\n" +
                    "                         and t.IdTeacher = ?);");
            statement.setBoolean(1, true);
            statement.setInt(2, idTeacher);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course(resultSet.getString("Title"));
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return courses;
    }

    public static ArrayList<Booking> queryAllBookings() {
        Connection connection = null;
        ArrayList<Booking> bookings = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select u.idUser, u.Name, u.Surname, u.Email, " +
                    "ts.idTimeSlot, ts.Day, ts.Hour, " +
                    "t.idTeacher, t.Name, t.Surname, t.Email, " +
                    "c.idCourse, c.Title, Deleted, Completed\n" +
                    "from booking\n" +
                    "         join user u on u.IdUser = booking.IdUser\n" +
                    "         join time_slot ts on ts.IdTimeSlot = booking.IdTimeSlot\n" +
                    "         join teacher_course tc on tc.IdTeacher = booking.IdTeacher and tc.IdCourse = booking.IdCourse\n" +
                    "         join teacher t on t.IdTeacher = tc.IdTeacher\n" +
                    "         join course c on c.IdCourse = tc.IdCourse;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4));
                TimeSlot timeSlot = new TimeSlot(resultSet.getInt(5), resultSet.getString(6), resultSet.getInt(7));
                Teacher teacher = new Teacher(resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11));
                Course course = new Course(resultSet.getInt(12), resultSet.getString(13));
                TeacherCourse teacherCourse = new TeacherCourse(teacher, course);

                Booking booking = new Booking(user, timeSlot, teacherCourse, resultSet.getBoolean(14), resultSet.getBoolean(15));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return bookings;
    }

    public static ArrayList<Booking> queryAllActiveBookings() {
        ArrayList<Booking> activeBookings = new ArrayList<>();
        ArrayList<Booking> bookings = DAO.queryAllBookings();
        for (Booking b : bookings) {
            if (!b.isCompleted() && !b.isDeleted()) {
                activeBookings.add(b);
            }
        }
        return activeBookings;
    }

    public static ArrayList<Booking> queryAllCompletedBookings() {
        ArrayList<Booking> completedBookings = new ArrayList<>();
        ArrayList<Booking> bookings = DAO.queryAllBookings();
        for (Booking b : bookings) {
            if (b.isCompleted()) {
                completedBookings.add(b);
            }
        }
        return completedBookings;
    }

    public static ArrayList<Booking> queryAllDeletedBookings() {
        ArrayList<Booking> deletedBookings = new ArrayList<>();
        ArrayList<Booking> bookings = DAO.queryAllBookings();
        for (Booking b : bookings) {
            if (b.isDeleted()) {
                deletedBookings.add(b);
            }
        }
        return deletedBookings;
    }

    public static ArrayList<Booking> queryPersonalBookings(User user) {
        if (user == null) {
            return new ArrayList<>();
        }

        Connection connection = null;
        ArrayList<Booking> personalBookings = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select ts.IdTimeSlot, ts.Day, ts.Hour, " +
                    "t.idTeacher, t.Name, t.Surname, t.Email, " +
                    "c.idCourse, c.Title, Deleted, Completed\n" +
                    "from booking\n" +
                    "         join user u on u.IdUser = booking.IdUser\n" +
                    "         join time_slot ts on ts.IdTimeSlot = booking.IdTimeSlot\n" +
                    "         join teacher_course tc on tc.IdTeacher = booking.IdTeacher and tc.IdCourse = booking.IdCourse\n" +
                    "         join teacher t on t.IdTeacher = tc.IdTeacher\n" +
                    "         join course c on c.IdCourse = tc.IdCourse\n" +
                    "where u.Email = ?;");
            statement.setString(1, user.getEmail());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TimeSlot timeSlot = new TimeSlot(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3));
                Teacher teacher = new Teacher(resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
                Course course = new Course(resultSet.getInt(8), resultSet.getString(9));
                TeacherCourse teacherCourse = new TeacherCourse(teacher, course);

                Booking booking = new Booking(timeSlot, teacherCourse, resultSet.getBoolean(10), resultSet.getBoolean(11));
                personalBookings.add(booking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return personalBookings;
    }

    public static ArrayList<Booking> queryPersonalActiveBookings(User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        ArrayList<Booking> personalActiveBookings = new ArrayList<>();
        ArrayList<Booking> bookings = DAO.queryPersonalBookings(user);
        for (Booking b : bookings) {
            if (!b.isCompleted() && !b.isDeleted()) {
                personalActiveBookings.add(b);
            }
        }

        return personalActiveBookings;
    }

    public static ArrayList<Booking> queryPersonalCompletedBookings(User user) {
        if (user == null) {
            return new ArrayList<>();
        }

        ArrayList<Booking> personalCompletedBookings = new ArrayList<>();
        ArrayList<Booking> bookings = DAO.queryPersonalBookings(user);
        for (Booking b : bookings) {
            if (b.isCompleted()) {
                personalCompletedBookings.add(b);
            }
        }

        return personalCompletedBookings;
    }

    public static ArrayList<Booking> queryPersonalDeletedBookings(User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        ArrayList<Booking> personalDeletedBookings = new ArrayList<>();
        ArrayList<Booking> bookings = DAO.queryPersonalBookings(user);
        for (Booking b : bookings) {
            if (b.isDeleted()) {
                personalDeletedBookings.add(b);
            }
        }

        return personalDeletedBookings;
    }

    public static boolean insertBooking(User user, TimeSlot timeSlot, Teacher teacher, Course course) {
        if (user == null || timeSlot == null || teacher == null || course == null) {
            return false;
        }

        Connection connection = null;
        int rowAffected = 0;

        int idUser = getIdUser(user.getEmail());
        int idTimeSlot = getIdTimeSlot(timeSlot.getDay(), timeSlot.getHour());
        int idTeacher = getIdTeacher(teacher.getEmail());
        int idCourse = getIdCourse(course.getTitle());

        if (idUser != 0 && idTimeSlot != 0 && idTeacher != 0 && idCourse != 0) {
            try {
                connection = DAO.connect();
                PreparedStatement statement = connection.prepareStatement("insert into booking (IdUser, IdTimeSlot, IdTeacher, IdCourse) values (?, ?, ?, ?)");
                statement.setInt(1, idUser);
                statement.setInt(2, idTimeSlot);
                statement.setInt(3, idTeacher);
                statement.setInt(4, idCourse);
                rowAffected = statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return false;
        }

        return rowAffected != 0;
    }

    public static boolean deleteBooking(Booking booking) {
        return booking != null && changeBookingStatus(booking, true, false);
    }

    public static boolean completeBooking(Booking booking) {
        return booking != null && changeBookingStatus(booking, false, true);
    }

    private static boolean changeBookingStatus(Booking booking, boolean deletedStatus, boolean completedStatus) {
        Connection connection = null;
        int rowAffected = 0;

        int idUser = getIdUser(booking.getUser().getEmail());
        int idTimeSlot = getIdTimeSlot(booking.getTimeSlot().getDay(), booking.getTimeSlot().getHour());
        int idTeacher = getIdTeacher(booking.getTeacherCourse().getTeacher().getEmail());
        int idCourse = getIdCourse(booking.getTeacherCourse().getCourse().getTitle());

        if (idUser != 0 && idTimeSlot != 0 && idTeacher != 0 && idCourse != 0) {
            try {
                connection = DAO.connect();
                PreparedStatement statement = connection.prepareStatement("update booking set Deleted = ?, Completed = ? where IdUser = ? and IdTimeSlot = ? and IdTeacher = ? and IdCourse = ?");
                statement.setBoolean(1, deletedStatus);
                statement.setBoolean(2, completedStatus);
                statement.setInt(3, idUser);
                statement.setInt(4, idTimeSlot);
                statement.setInt(5, idTeacher);
                statement.setInt(6, idCourse);
                rowAffected = statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return false;
        }

        return rowAffected != 0;
    }

    private static int getIdUser(String email) {
        Connection connection = null;
        int id = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select IdUser from user where Email = ? and active = ?");
            statement.setString(1, email);
            statement.setBoolean(2, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }

    private static int getIdTeacher(String email) {
        Connection connection = null;
        int id = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select IdTeacher from teacher where Email = ? and active = ?");
            statement.setString(1, email);
            statement.setBoolean(2, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }

    private static int getIdCourse(String title) {
        Connection connection = null;
        int id = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select IdCourse from course where Title = ? and active = ?");
            statement.setString(1, title);
            statement.setBoolean(2, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }

    private static int getIdTimeSlot(String day, int hour) {
        Connection connection = null;
        int id = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select IdTimeSlot from time_slot where Day = ? and Hour = ? and active = ?");
            statement.setString(1, day);
            statement.setInt(2, hour);
            statement.setBoolean(3, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return id;
    }
}