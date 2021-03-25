package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
public class User {
    private int id;
    private String name;
    private String surname;
    private final String email;
    private String password;
    private boolean administrator;

    public User(int id, String name, String surname, String email, boolean administrator) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.administrator = administrator;
    }

    public User(String name, String surname, String email, String password, boolean administrator) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.administrator = administrator;
    }

    public User(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email) {
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

    public boolean isAdministrator() {
        return administrator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id &&
                administrator == user.administrator &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, password, administrator);
    }

    @Override
    public String toString() {
        String tipo;
        if (!this.administrator) {
            tipo = "User";
        } else {
            tipo = "Amministratore";
        }

        return "User{" +
                "id: " + id +
                "Name: '" + name + '\'' +
                ", Surname: '" + surname + '\'' +
                ", Email: '" + email + '\'' +
                ", Password: '" + password + '\'' +
                ", User: " + tipo +
                '}';
    }
}