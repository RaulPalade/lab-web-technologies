package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
public class TeacherCourse {
    private final Teacher teacher;
    private final Course course;

    public TeacherCourse(Teacher teacher, Course course) {
        this.teacher = teacher;
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Course getCourse() {
        return course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeacherCourse)) return false;
        TeacherCourse teacherCourse = (TeacherCourse) o;
        return teacher.equals(teacherCourse.teacher) &&
                course.equals(teacherCourse.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacher, course);
    }

    @Override
    public String toString() {
        return "Teaching{" +
                "Teacher: " + teacher.getName() + " " + teacher.getSurname() + " " + teacher.getEmail() +
                ", Course: " + course.getTitle() +
                '}';
    }
}