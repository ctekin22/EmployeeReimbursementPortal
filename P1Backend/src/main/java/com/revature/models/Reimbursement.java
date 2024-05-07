package com.revature.models;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

// We will create a frontend interface to model reimbursement
// without id but with name, status, user

/**
 * Represents a reimbursement model with attributes like reimbId, description, status, amount, and user.
 * The user attribute is optional initially as reimbursement might not be associated with a user immediately.
 */
@Component
@Entity
@Table(name="reimbursement")
public class Reimbursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reimbId;
    private String description;
    private String status= "PENDING";
    private int amount;

    // Many reimbursements can belong to a user,
    // we put @OnetoMany relationship from User side
    // We need @ManytoOne relationship from reimbursement side for complementary
    // We did not specify the CASCADE type
    // We only specified cascade one side which is User side.
    // We can delete a reimbursement without worrying user now.

    /**
     * Many reimbursements can belong to a user.
     * We establish a Many-to-One relationship from the reimbursement side.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId") //foreign key
    private User user;

    /**
     * Default constructor.
     */
    public Reimbursement() {
    }

    /**
     * Parameterized constructor to initialize Reimbursement with all attributes.
     * @param reimbId The ID of the reimbursement.
     * @param description The description of the reimbursement.
     * @param status The status of the reimbursement.
     * @param amount The amount of the reimbursement.
     * @param user The user associated with the reimbursement.
     */
    public Reimbursement(int reimbId, String description, String status, int amount, User user) {
        this.reimbId = reimbId;
        this.description = description;
        this.status = status;
        this.amount = amount;
        this.user = user;
    }

    /**
     * Parameterized constructor to initialize Reimbursement without reimbId.
     * @param description The description of the reimbursement.
     * @param amount The amount of the reimbursement.
     * @param user The user associated with the reimbursement.
     */
    public Reimbursement(String description, int amount, User user) {
        this.description = description;
        this.amount = amount;
        this.user = user;
    }

    // Getters and setters

    /**
     * Getter for reimbId.
     * @return The ID of the reimbursement.
     */
    public int getReimbId() {
        return reimbId;
    }

    /**
     * Setter for reimbId.
     * @param reimbId The ID of the reimbursement.
     */
    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    /**
     * Getter for description.
     * @return The description of the reimbursement.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter for description.
     * @param description The description of the reimbursement.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for status.
     * @return The status of the reimbursement.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter for status.
     * @param status The status of the reimbursement.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter for amount.
     * @return The amount of the reimbursement.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter for amount.
     * @param amount The amount of the reimbursement.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Getter for user.
     * @return The user associated with the reimbursement.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for user.
     * @param user The user associated with the reimbursement.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Overrides the default toString method to provide a string representation of the object.
     * @return A string representation of the Reimbursement object.
     */
    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId=" + reimbId +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", user=" + user +
                '}';
    }
}






