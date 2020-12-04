package datamodel;

import java.util.Objects;

public class Booking {
    private User user;
    private TimeSlot timeSlot;
    private TeacherCourse teacherCourse;
    private boolean deleted;
    private boolean completed;
    
    public Booking(User user, TimeSlot timeSlot, TeacherCourse teacherCourse, boolean deleted, boolean completed) {
        this.user = user;
        this.timeSlot = timeSlot;
        this.teacherCourse = teacherCourse;
        this.deleted = deleted;
        this.completed = completed;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public TeacherCourse getTeacherCourse() {
        return teacherCourse;
    }

    public void setTeacherCourse(TeacherCourse teacherCourse) {
        this.teacherCourse = teacherCourse;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
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