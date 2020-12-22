package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 */
public class TimeSlot {
    private String day;
    private int hour;

    public TimeSlot(String day, int hour) {
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
        return hour == timeSlot.hour &&
                day.equals(timeSlot.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, hour);
    }

    @Override
    public String toString() {
        return "TimeSlot{" +
                "Day: '" + day + '\'' +
                ", Hour: " + hour +
                '}';
    }

}