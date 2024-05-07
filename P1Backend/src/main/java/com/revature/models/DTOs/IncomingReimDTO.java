package com.revature.models.DTOs;

import com.revature.models.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Data Transfer Object (DTO) used for incoming reimbursement requests.
 * Contains fields for description, amount, and userId.
 * Simplifies the process of sending reimbursement data from the frontend to the backend.
 */
public class IncomingReimDTO {
    private String description;
    private int amount;
    private int userId;

    /**
     * Default constructor.
     */
    public IncomingReimDTO() {
    }

    /**
     * Parameterized constructor to initialize IncomingReimDTO with description, amount, and userId.
     * @param description Description of the reimbursement.
     * @param amount Amount of the reimbursement.
     * @param userId ID of the user associated with the reimbursement.
     */
    public IncomingReimDTO(String description, int amount, int userId) {
        this.description = description;
        this.amount = amount;
        this.userId = userId;
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
     * Getter for userId.
     * @return The ID of the user associated with the reimbursement.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for userId.
     * @param userId The ID of the user associated with the reimbursement.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Overrides the default toString method to provide a string representation of the object.
     * @return A string representation of the IncomingReimDTO object.
     */
    @Override
    public String toString() {
        return "IncomingReimDTO{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", userId=" + userId +
                '}';
    }
}
