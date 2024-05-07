package com.revature.DAOs;

import com.revature.models.DTOs.OutgoingReimDTO;
import com.revature.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface responsible for defining data access methods related to Reimbursement entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 * Uses Spring Data JPA for easy database interaction.
 */
@Repository
public interface ReimbursementDAO extends JpaRepository<Reimbursement, Integer> {

    /**
     * Retrieves a list of reimbursements by user ID.
     * @param userId ID of the user whose reimbursements are to be retrieved.
     * @return List of reimbursements associated with the specified user ID.
     */
    public List<Reimbursement> findByUserUserId(int userId);

    /**
     * Retrieves a list of reimbursements by status.
     * @param status Status of the reimbursements to be retrieved.
     * @return List of reimbursements with the specified status.
     */
    public List<Reimbursement> findByStatus(String status);

    /**
     * Retrieves a list of reimbursements by status and user ID.
     * @param status Status of the reimbursements to be retrieved.
     * @param userId ID of the user whose reimbursements are to be retrieved.
     * @return List of reimbursements with the specified status associated with the specified user ID.
     */
    public List<Reimbursement> findByStatusAndUserUserId(String status, int userId);

    /**
     * Retrieves a list of reimbursements by status and user role.
     * @param status Status of the reimbursements to be retrieved.
     * @param role Role of the user whose reimbursements are to be retrieved.
     * @return List of reimbursements with the specified status associated with users having the specified role.
     */
    public List<Reimbursement> findByStatusAndUserRole(String status, String role);

}
