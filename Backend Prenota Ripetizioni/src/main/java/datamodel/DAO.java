package datamodel;

import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;

public class DAO {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/prenotaripetizioni";
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

    public static boolean insertUser(User user) {
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

    public static boolean deactivateUser(User user) {
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update user set Active = 0 where Name = ? and Surname = ? and Email = ?");
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
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
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update user set Active = 1 where Name = ? and Surname = ? and Email = ?");
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getEmail());
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

    public static boolean insertTeacher(Teacher teacher) {
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

    public static boolean deactivateTeacher(Teacher teacher) {
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update teacher set Active = 0 where Name = ? and Surname = ? and Email = ?");
            statement.setString(1, teacher.getName());
            statement.setString(2, teacher.getSurname());
            statement.setString(3, teacher.getEmail());
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
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update teacher set Active = 1 where Name = ? and Surname = ? and Email = ?");
            statement.setString(1, teacher.getName());
            statement.setString(2, teacher.getSurname());
            statement.setString(3, teacher.getEmail());
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

    public static boolean deactivateCourse(Course course) {
        Connection connection = null;
        int rowAffected = 0;
        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update course set Active = 0 where Title = ?;");
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
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update course set Active = 1 where Title = ?");
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

    // TODO (1): Define insert time slot method
    public static boolean insertTimeSlot(TimeSlot timeSlot) {
        return false;
    }

    // TODO (2): Define remove time slot method
    public static boolean deactivateTimeSlot(TimeSlot timeSlot) {
        return false;
    }

    public static boolean activateTimeSlot(TimeSlot timeSlot) {
        Connection connection = null;
        int rowAffected = 0;

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update time_slot set Active = 1 where Day = ? and Hour = ?");
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

    public static boolean deactivateTeaching(Teacher teacher, Course course) {
        Connection connection = null;
        int rowAffected = 0;

        int idTeacher = getIdTeacher(teacher.getEmail());
        int idCourse = getIdCourse(course.getTitle());

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update teacher_course set Active = 0 where IdTeacher = ? and IdCourse = ?");
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
        Connection connection = null;
        int rowAffected = 0;

        int idTeacher = getIdTeacher(teacherCourse.getTeacher().getEmail());
        int idCourse = getIdCourse(teacherCourse.getCourse().getTitle());

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update teacher_course set Active = 1 where IdTeacher = ? and IdCourse = ?");
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

    public static ArrayList<Teacher> viewTeacherByCourse(Course course) {
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

    public static boolean insertBooking(User user, TimeSlot timeSlot, Teacher teacher, Course course) {
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

    public static boolean deleteBooking(User user, TimeSlot timeSlot, Teacher teacher, Course course) {
        Connection connection = null;
        int rowAffected = 0;

        int idUser = getIdUser(user.getEmail());
        int idTimeSlot = getIdTimeSlot(timeSlot.getDay(), timeSlot.getHour());
        int idTeacher = getIdTeacher(teacher.getEmail());
        int idCourse = getIdCourse(course.getTitle());

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update booking set Deleted = 1, Completed = 1 where IdUser = ? and IdTimeSlot = ? and IdTeacher = ? and IdCourse = ?");
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

    public static boolean completeBooking(Booking booking) {
        Connection connection = null;
        int rowAffected = 0;

        int idUser = getIdUser(booking.getUser().getEmail());
        int idTimeSlot = getIdTimeSlot(booking.getTimeSlot().getDay(), booking.getTimeSlot().getHour());
        int idTeacher = getIdTeacher(booking.getTeacherCourse().getTeacher().getEmail());
        int idCourse = getIdCourse(booking.getTeacherCourse().getCourse().getTitle());

        try {
            connection = DAO.connect();
            PreparedStatement statement = connection.prepareStatement("update booking set Completed = 1 where IdUser = ? and IdTimeSlot = ? and IdTeacher = ? and IdCourse = ?");
            statement.setInt(1, idUser);
            statement.setInt(1, idTimeSlot);
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