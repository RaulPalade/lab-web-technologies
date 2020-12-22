package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 */
public class Course {
    private final String title;

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
        return title.equals(course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Course{" +
                "Title: '" + title + '\'' +
                '}';
    }

}