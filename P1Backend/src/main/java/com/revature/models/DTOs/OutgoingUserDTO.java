package com.revature.models.DTOs;

/**
 * Data Transfer Object (DTO) used for sending limited user information to the front end.
 * OutgoingUserDTO contains only userId, username, and role.
 * This DTO is designed to avoid sending the user's password over HTTP.
 */
public class OutgoingUserDTO {

    private int userId;
    private String username;
    private String role;

    /**
     * Default constructor.
     */
    public OutgoingUserDTO() {
    }

    /**
     * Parameterized constructor to initialize OutgoingUserDTO with userId, username, and role.
     * @param userId The ID of the user.
     * @param username The username of the user.
     * @param role The role of the user.
     */
    public OutgoingUserDTO(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    /**
     * Getter for userId.
     * @return The ID of the user.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for userId.
     * @param userId The ID of the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
     * Getter for role.
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter for role.
     * @param role The role of the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Overrides the default toString method to provide a string representation of the object.
     * @return A string representation of the OutgoingUserDTO object.
     */
    @Override
    public String toString() {
        return "OutgoingUserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
