package datamodel;

import java.util.Objects;

public class Teacher {
    private String name;
    private String surname;
    private String email;
    private String password;

    public Teacher(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public Teacher(String email) {
        this.email = email;
    }

    public Teacher(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Teacher(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return name.equals(teacher.name) &&
                surname.equals(teacher.surname) &&
                email.equals(teacher.email) &&
                password.equals(teacher.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, password);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "Name: '" + name + '\'' +
                ", Surname: '" + surname + '\'' +
                ", Email: '" + email + '\'' +
                ", Password: '" + password + '\'' +
                '}';
    }

}