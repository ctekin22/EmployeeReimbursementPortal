package com.revature.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Represents a user model with attributes like userId, username, firstName, lastName, password, role, and list of reimbursements.
 * The User class is annotated with JPA annotations to define the entity and map it to the database table.
 */
@Component
@Entity
@Table(name="users")
public class User {
    //userId, username, password, no args, all args minus id, all args, getter/setter, tostring

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String username;

    private String firstName;
    private String lastName;
    private String password;
    private String role = "employee";


    // Setting Cascade user side with ALL this time, less work to delete
    // If we use cascade DETACH, when we delete the user
    // the reimbursements will still exist but assigned to null for that user id
    // Once we delete a user all its reimbursements will be deleted; CascadeType.ALL
    // This bidirectional relationships between entities will cause infinite recursion when we get a user.
    // user-> reimbursement->user -> reimbursement .. so on
    // To resolve this, you can use @JsonIgnore

    /**
     * Establishes a One-to-Many relationship between User and Reimbursement entities. Sets Cascade user side with ALL
     * This relationship indicates that one user can have multiple reimbursements associated with them.
     * We use @JsonIgnore to prevent infinite recursion when fetching a user with associated reimbursements.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reimbursement> reimbursement;

    public User() {
        super(); //this calls to the parent constructor, it happens anyway
    }

    // constructor without id but anything else
    // will facilitates inserting database while registering
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public User(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public User(int userId, String username, String firstName, String lastName, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    /**
     * Retrieves the user's ID.
     * @return The user's ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's ID.
     * @param userId The user's ID.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the user's username.
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the user's username.
     * @param username The user's username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the user's first name.
     * @return The user's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the user's first name.
     * @param firstName The user's first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the user's last name.
     * @return The user's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's last name.
     * @param lastName The user's last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password The user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the user's role.
     * @return The user's role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the user's role.
     * @param role The user's role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieves the list of reimbursements associated with the user.
     * @return The list of reimbursements associated with the user.
     */
    public List<Reimbursement> getReimbursement() {
        return reimbursement;
    }

    /**
     * Sets the list of reimbursements associated with the user.
     * @param reimbursement The list of reimbursements associated with the user.
     */
    public void setReimbursement(List<Reimbursement> reimbursement) {
        this.reimbursement = reimbursement;
    }

    /**
     * Overrides the default toString method to provide a string representation of the object.
     * @return A string representation of the User object.
     */
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", reimbursement=" + reimbursement +
                '}';
    }
}






