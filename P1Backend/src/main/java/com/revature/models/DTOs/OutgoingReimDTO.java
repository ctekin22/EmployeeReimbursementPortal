package com.revature.models.DTOs;

/**
 * Data Transfer Object (DTO) used for sending Reimbursement data with limited fields.
 * OutgoingReimDTO contains only reimbId, description, amount, status, and userId.
 * This DTO is designed to avoid sending the entire User object, keeping the password safe and preventing recursive issues.
 */
public class OutgoingReimDTO {

    private int reimbId;
    private String description;
    private String status;
    private int amount;
    private int userId;

    /**
     * Default constructor.
     */
    public OutgoingReimDTO() {
    }

    /**
     * Parameterized constructor to initialize OutgoingReimDTO with reimbId, description, status, amount, and userId.
     * @param reimbId The ID of the reimbursement.
     * @param description The description of the reimbursement.
     * @param status The status of the reimbursement.
     * @param amount The amount of the reimbursement.
     * @param userId The ID of the user associated with the reimbursement.
     */
    public OutgoingReimDTO(int reimbId, String description, String status, int amount, int userId) {
        this.reimbId = reimbId;
        this.description = description;
        this.status = status;
        this.amount = amount;
        this.userId =userId;
    }

    /**
     * Parameterized constructor to initialize OutgoingReimDTO with reimbId, description, status, and amount.
     * @param reimbId The ID of the reimbursement.
     * @param description The description of the reimbursement.
     * @param status The status of the reimbursement.
     * @param amount The amount of the reimbursement.
     */
    public OutgoingReimDTO(int reimbId, String description, String status, int amount) {
        this.reimbId = reimbId;
        this.description = description;
        this.status = status;
        this.amount = amount;
    }

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
     * Overrides the default toString method to provide a string representation of the object.
     * @return A string representation of the OutgoingReimDTO object.
     */
    @Override
    public String toString() {
        return "OutgoingReimDTO{" +
                "reimbId=" + reimbId +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                '}';
    }
}