package datamodel;

import java.util.Objects;

/**
 * @author Raul Palade
 * @project Backend Lesson Scheduling
 */
public class User {
    private String name;
    private String surname;
    private final String email;
    private String password;
    private boolean administrator;

    public User(String name, String surname, String email, String password, boolean amministratore) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.administrator = amministratore;
    }

    public User(String email) {
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

    public boolean isAmministratore() {
        return administrator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return administrator == user.administrator &&
                name.equals(user.name) &&
                surname.equals(user.surname) &&
                email.equals(user.email) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, email, password, administrator);
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
                "Name: '" + name + '\'' +
                ", Surname: '" + surname + '\'' +
                ", Email: '" + email + '\'' +
                ", Password: '" + password + '\'' +
                ", User: " + tipo +
                '}';
    }

}