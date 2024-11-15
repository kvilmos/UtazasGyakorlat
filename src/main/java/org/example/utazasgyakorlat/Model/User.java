package org.example.utazasgyakorlat.Model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @NotEmpty
    @Size( min= 2, max=256)
    @Pattern(regexp = "[A-ZÁÉÍÓÖŐÚÜŰa-záéíóöőúüű]{2,256}")
    private String firstname;
    @NotNull
    @NotEmpty
    @Size(min=2, max=256)
    @Pattern(regexp =  "[A-ZÁÉÍÓÖŐÚÜŰa-záéíóöőúüű]{2,256}")
    private String lastname;
    @NotNull
    @NotEmpty
    @Size(min=6, max=256)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,7}$")
    private String email;
    @NotNull
    @NotEmpty
    @Size(min=6, max=256)
    private String password;
    private String role;

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
