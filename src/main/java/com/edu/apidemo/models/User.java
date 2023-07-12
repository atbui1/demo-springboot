package com.edu.apidemo.models;

import com.edu.apidemo.repositories.RoleRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tblUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    private String fullname;
    private int yearOfBirth;
    private String password;
    private int status;
    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "tblUser_Role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // end many to many
    public User() {
    }

    public User(String username, String fullname, int yearOfBirth, String password, int status) {
        this.username = username;
        this.fullname = fullname;
        this.yearOfBirth = yearOfBirth;
        this.password = password;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//    public void addRole(Role role) {
//        roles.add(role);
//        role.getUsers().add(this);
//    }
//    public void removeRole(Role role) {
//        roles.remove(role);
//        role.getUsers().remove(role);
//    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                '}';
    }

    public void addRoleIntoUser(Role role) {
        roles.add(role);
    }

    public void deleteRoleIntoUser(Role role) {
        roles.remove(role);
    }
}
