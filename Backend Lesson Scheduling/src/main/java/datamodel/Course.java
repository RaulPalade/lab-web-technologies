package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
public class Course {
    private int id;
    private final String title;

    public Course(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Course(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return id == course.id &&
                title.equals(course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id: " + id +
                "Title: '" + title + '\'' +
                '}';
    }
}