package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
public class TimeSlot {
    private int id;
    private String day;
    private int hour;

    public TimeSlot(String day, int hour) {
        this.day = day;
        this.hour = hour;
    }

    public TimeSlot(int id, String day, int hour) {
        this.id = id;
        this.day = day;
        this.hour = hour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeSlot)) return false;
        TimeSlot timeSlot = (TimeSlot) o;
        return id == timeSlot.id &&
                hour == timeSlot.hour &&
                day.equals(timeSlot.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, hour);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "id: " + id +
                "Day: '" + day + '\'' +
                ", Hour: " + hour +
                '}';
    }

}