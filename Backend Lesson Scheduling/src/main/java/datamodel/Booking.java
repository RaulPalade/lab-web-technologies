package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
public class Booking {
    private User user;
    private final TimeSlot timeSlot;
    private final TeacherCourse teacherCourse;
    private boolean deleted;
    private boolean completed;

    public Booking(User user, TimeSlot timeSlot, TeacherCourse teacherCourse, boolean deleted, boolean completed) {
        this.user = user;
        this.timeSlot = timeSlot;
        this.teacherCourse = teacherCourse;
        this.deleted = deleted;
        this.completed = completed;
    }

    public Booking(TimeSlot timeSlot, TeacherCourse teacherCourse, boolean deleted, boolean completed) {
        this.timeSlot = timeSlot;
        this.teacherCourse = teacherCourse;
        this.deleted = deleted;
        this.completed = completed;
    }

    public Booking(User user, TimeSlot timeSlot, TeacherCourse teacherCourse) {
        this.user = user;
        this.timeSlot = timeSlot;
        this.teacherCourse = teacherCourse;
    }

    public User getUser() {
        return user;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return deleted == booking.deleted &&
                completed == booking.completed &&
                user.equals(booking.user) &&
                timeSlot.equals(booking.timeSlot) &&
                teacherCourse.equals(booking.teacherCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, timeSlot, teacherCourse, deleted, completed);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "User: " + user.getName() + " " + user.getSurname() + " " + user.getEmail() +
                ", TimeSlot: " + timeSlot.getDay() + " " + timeSlot.getHour() +
                ", Teacher: " + teacherCourse.getTeacher().getName() + " " + teacherCourse.getTeacher().getSurname() + teacherCourse.getTeacher().getEmail() +
                ", Course: " + teacherCourse.getCourse().getTitle() +
                '}';
    }
}