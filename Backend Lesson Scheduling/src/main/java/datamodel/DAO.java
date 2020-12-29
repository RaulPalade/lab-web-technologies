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
    public static final String MYSQL_PASSWORD = "";
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

    public static @NotNull ArrayList<User> queryUsers() {
        Connection connection = null;
        ArrayList<User> users = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from user where active = ?");
            statement.setBoolean(1, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("Name"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
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
            statement.setBoolean(5, user.isAmministratore());
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

    public static @NotNull ArrayList<Teacher> queryTeachers() {
        Connection connection = null;
        ArrayList<Teacher> teachers = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from teacher where active = ?");
            statement.setBoolean(1, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher(resultSet.getString("Name"),
                        resultSet.getString("Surname"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"));
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

    public static ArrayList<TimeSlot> queryTeacherAvailability(Teacher teacher) {
        Connection connection = null;
        ArrayList<TimeSlot> availableSlots = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select Day, Hour\n" +
                    "from time_slot\n" +
                    "where Active = ?\n" +
                    "  and (Day, Hour) not in\n" +
                    "      (select Day, Hour\n" +
                    "       from teacher\n" +
                    "                join booking b on teacher.IdTeacher = b.IdTeacher\n" +
                    "                join time_slot ts on b.IdTimeSlot = ts.IdTimeSlot\n" +
                    "       where teacher.Email = ?\n" +
                    "         and teacher.Active = ?\n" +
                    "         and Deleted = ?\n" +
                    "         and Completed = ?);");
            statement.setBoolean(1, true);
            statement.setString(2, teacher.getEmail());
            statement.setBoolean(3, true);
            statement.setBoolean(4, false);
            statement.setBoolean(5, false);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TimeSlot timeSlot = new TimeSlot(resultSet.getString(1), resultSet.getInt(2));
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

    public static @NotNull ArrayList<Course> queryCourses() {
        Connection connection = null;
        ArrayList<Course> courses = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from course where active = ?");
            statement.setBoolean(1, true);
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

    public static ArrayList<TimeSlot> queryTimeSlots() {
        Connection connection = null;
        ArrayList<TimeSlot> timeSlots = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select * from time_slot where active = ?");
            statement.setBoolean(1, true);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                TimeSlot timeSlot = new TimeSlot(resultSet.getString("Day"), resultSet.getInt("Hour"));
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
            PreparedStatement statement = connection.prepareStatement("select t.Name, t.Surname, t.Email, c.Title\n" +
                    "from teacher_course join teacher t on t.IdTeacher = teacher_course.IdTeacher join course c on c.IdCourse = teacher_course.IdCourse;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                Course course = new Course(resultSet.getString(4));
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

    public static ArrayList<Teacher> viewTeacherByCourse(Course course) {
        if (course == null) {
            return new ArrayList<>();
        }

        Connection connection = null;
        int idCourse = getIdCourse(course.getTitle());
        ArrayList<Teacher> teachers = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select Name, Surname, Title\n" +
                    "from teacher join teacher_course tc on teacher.IdTeacher = tc.IdTeacher join course c on c.IdCourse = tc.IdCourse\n" +
                    "where c.IdCourse = ?;");
            statement.setInt(1, idCourse);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher(resultSet.getString("Name"), resultSet.getString("Surname"));
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

    public static ArrayList<Booking> queryBookings() {
        Connection connection = null;
        ArrayList<Booking> bookings = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select u.Name, u.Surname, u.Email, ts.Day, ts.Hour, t.Name, t.Surname, t.Email, c.Title, Deleted, Completed\n" +
                    "from booking\n" +
                    "         join user u on u.IdUser = booking.IdUser\n" +
                    "         join time_slot ts on ts.IdTimeSlot = booking.IdTimeSlot\n" +
                    "         join teacher_course tc on tc.IdTeacher = booking.IdTeacher and tc.IdCourse = booking.IdCourse\n" +
                    "         join teacher t on t.IdTeacher = tc.IdTeacher\n" +
                    "         join course c on c.IdCourse = tc.IdCourse;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                TimeSlot timeSlot = new TimeSlot(resultSet.getString(4), resultSet.getInt(5));
                Teacher teacher = new Teacher(resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));
                Course course = new Course(resultSet.getString(9));
                TeacherCourse teacherCourse = new TeacherCourse(teacher, course);

                Booking booking = new Booking(user, timeSlot, teacherCourse, resultSet.getBoolean(10), resultSet.getBoolean(11));
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

    public static ArrayList<Booking> queryPersonalBooking(User user) {
        Connection connection = null;
        ArrayList<Booking> personalBookings = new ArrayList<>();

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select ts.Day, ts.Hour, t.Name, t.Surname, t.Email, c.Title, Deleted, Completed\n" +
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
                TimeSlot timeSlot = new TimeSlot(resultSet.getString(1), resultSet.getInt(2));
                Teacher teacher = new Teacher(resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
                Course course = new Course(resultSet.getString(6));
                TeacherCourse teacherCourse = new TeacherCourse(teacher, course);

                Booking booking = new Booking(timeSlot, teacherCourse, resultSet.getBoolean(7), resultSet.getBoolean(8));
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

        return rowAffected != 0;
    }

    private static int getIdUser(String email) {
        Connection connection = null;
        int id = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("select IdUser from user where Email = ?");
            statement.setString(1, email);
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
            PreparedStatement statement = connection.prepareStatement("select IdTeacher from teacher where Email = ?");
            statement.setString(1, email);
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
            PreparedStatement statement = connection.prepareStatement("select IdCourse from course where Title = ?");
            statement.setString(1, title);
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
            PreparedStatement statement = connection.prepareStatement("select IdTimeSlot from time_slot where Day = ? and Hour = ?");
            statement.setString(1, day);
            statement.setInt(2, hour);
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