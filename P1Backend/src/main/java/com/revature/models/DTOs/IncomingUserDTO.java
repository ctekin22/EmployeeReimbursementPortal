package com.revature.models.DTOs;

//What's a DTO? A Data Transfer Object!
//We don't want to have to specify User Id or a List of Reimbursement for Login/Registration
//DTOs let us store ONLY the relevant information for a given operation

//Big Picture: This will let a user just submit username/password for login/registration

/**
 * Data Transfer Object (DTO) used for incoming user data during login and registration.
 * Contains fields for username, first name, last name, and password.
 * DTOs allow sending only relevant information for specific operations.
 * IncomingUserDTO is designed to send minimal user information to the backend.
 */
public class IncomingUserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String password;

    //private String email (IF THIS EXISTED)

    //private String someOtherRelevant Field

    //then from here it's just generate boilerplate code-------------


    /**
     * Default constructor.
     */
    public IncomingUserDTO() {
    }

    /**
     * Parameterized constructor to initialize IncomingUserDTO with username, first name, last name, and password.
     * @param username The username of the user.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param password The password of the user.
     */
    public IncomingUserDTO(String username, String firstName, String lastName, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }


    /**
     * Getter for username.
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username.
     * @param username The username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for first name.
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for first name.
     * @param firstName The first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for last name.
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter for last name.
     * @param lastName The last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter for password.
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Overrides the default toString method to provide a string representation of the object.
     * @return A string representation of the IncomingUserDTO object.
     */
    @Override
    public String toString() {
        return "IncomingUserDTO{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
