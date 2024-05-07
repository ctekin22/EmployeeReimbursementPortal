package com.revature.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAOs.ReimbursementDAO;
import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingReimDTO;
import com.revature.models.DTOs.OutgoingReimDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service layer for handling reimbursement-related operations.
 * This class contains methods to interact with the DAO layer to perform CRUD operations on reimbursements.
 * It also handles business logic related to reimbursement management.
 */
@Service
public class ReimbursementService {
    // Service always calls for DAO, so DAO is a dependency for a Service
    // We need to inject ReimbursementDAO to use ReimbursementDAO methods
    // Autowire with constructor for dependency injection
    private ReimbursementDAO reimbursementDAO;
    private UserDAO userDAO;

    /**
     * Constructor for ReimbursementService.
     * @param reimbursementDAO The data access object for reimbursement entities.
     * @param userDAO The data access object for user entities.
     */
    @Autowired
    public ReimbursementService(ReimbursementDAO reimbursementDAO,UserDAO userDAO) {
        this.reimbursementDAO = reimbursementDAO;
        this.userDAO = userDAO;
    }

    /**
     * Adds a new reimbursement to the database.
     * @param reimDTO The DTO containing reimbursement details.
     * @return The newly created reimbursement object.
     * @throws IllegalArgumentException If the reimbursement data is invalid.
     */
    public Reimbursement addReimbursement(IncomingReimDTO reimDTO) throws IllegalArgumentException{

        //There aren't actual meaningful checks we can do on this
        //Because we assume valid Reimbursement data is coming from the Reimbursement API (not user input)

        //We could check for things like valid Reimbursement amount and description valid userID
        //or maybe we can only allow each user to have a limited x amount of Reimbursement
        //OR only one of each type of Reimbursement

        //But for now, we'll just insert a Reimbursement
        // and limit the amount than can be Reimbursement

        // Check for valid reimbursement data
        if(reimDTO.getAmount() <= 0 ){
            throw new IllegalArgumentException("Enter a valid amount!");
        }

        if(reimDTO.getAmount() > 20000  ){
            throw new IllegalArgumentException("Reimbursement Amount cannot be bigger than 20000!");
        }

        if(reimDTO.getDescription().equals("None")){
            throw new IllegalArgumentException("Description cannot be None!");
        }

        // Create a new reimbursement object
        Reimbursement reim = new Reimbursement(reimDTO.getDescription(), reimDTO.getAmount(), null);

        // Set the user for the reimbursement
        //User u = userDAO.findById(reimDTO.getUserId()).get();
        Optional <User> opUser = userDAO.findById(reimDTO.getUserId());
        User u;
        if(opUser.isPresent()){
            u = opUser.get();
            //Set the user in the Reimbursement object
            reim.setUser(u);
        }

        // Save and return the reimbursement
        return reimbursementDAO.save(reim);
    }

    /**
     * Retrieves all reimbursements from the database.
     * @return A list of DTOs representing all reimbursements.
     */
    public List<OutgoingReimDTO> getAllReimbursement(){
        //for every reimbursement retrieved, we'll create a new OutgoingReimDTO
        //and add it to a List to be returned
        List<OutgoingReimDTO> outReimbursement = new ArrayList<>();

        List<Reimbursement> allReimbursement= reimbursementDAO.findAll();

        // Convert reimbursement entities to DTOs
        for(Reimbursement r : allReimbursement){
            OutgoingReimDTO outP = new OutgoingReimDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getStatus(),
                    r.getAmount(),
                    r.getUser().getUserId());

            outReimbursement.add(outP);
        }
        return outReimbursement;
    }



    /**
     * Retrieves all reimbursements associated with a specific user ID from the database.
     * @param userId The ID of the user whose reimbursements are to be retrieved.
     * @return A list of DTOs representing all reimbursements associated with the user.
     */
    public List<OutgoingReimDTO> getAllReimbursementByID(int userId){
        List<OutgoingReimDTO> outReimbursement = new ArrayList<>();

        // Retrieve all reimbursements associated with the user ID
        List<Reimbursement> allReimbursement = reimbursementDAO.findByUserUserId(userId);

        // Convert reimbursement entities to DTOs
        for(Reimbursement r : allReimbursement){
            OutgoingReimDTO outP = new OutgoingReimDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getStatus(),
                    r.getAmount(),
                    r.getUser().getUserId());

            outReimbursement.add(outP);
        }
        return outReimbursement;
    }

    /**
     * Deletes a reimbursement entry from the database by its ID.
     * @param reimId The ID of the reimbursement to be deleted.
     * @return A message indicating the success of the deletion operation.
     * @throws NoSuchElementException If the reimbursement with the given ID is not found in the database.
     */
    public String deleteReimbursement(int reimId){


        Optional<Reimbursement> optionalReim = reimbursementDAO.findById(reimId);

        // Check if the reimbursement with the given ID exists
        if(optionalReim.isEmpty()){
            throw new NoSuchElementException("Reimbursement not found! Can't delete");
        }

        //if the Reimbursement is present, extract the Reimbursement from the optional
        //make sure the Reimbursement being deleted belongs to the user deleting it
        //delete the Reimbursement from the User's List of Reimbursement
        // If we only delete from reimbursement table in the database but not
        // from user's reimbursement list, reimbursement will be still in the database
        // that means, it will be not deleted; due to our ManytoOne and OnetoMany bidirectional relationship
        // We need this bidirectional relationship because once we delete the user. We want to delete all its reimbursements

        // If the reimbursement is present, extract it from the optional
        Reimbursement reim = optionalReim.get();


        //The Reimbursement won't fully delete until you remove it from BOTH tables!

        // Remove the reimbursement from the user's list of reimbursements
        reim.getUser().getReimbursement().remove(reim); // Delete from user's list
        reimbursementDAO.deleteById(reimId); // Delete from reimbursement table

        return "Reimbursement " + reim.getReimbId() + " with amount " + reim.getAmount() + " was deleted!";
    }

    /**
     * Retrieves reimbursement tickets with a specific status for a given user ID.
     * @param status The status of the reimbursement tickets to retrieve (PENDING, APPROVED, or DENIED).
     * @param userId The ID of the user for whom reimbursement tickets are to be retrieved.
     * @return A list of OutgoingReimDTO objects representing the reimbursement tickets.
     * @throws IllegalArgumentException If the provided status is not valid.
     */
    public List<OutgoingReimDTO> getReimbByStatusAndId(String status, int userId){

        // Valid status options
        ArrayList <String> statuses = new  ArrayList<>(List.of("PENDING", "APPROVED", "DENIED"));

        // Check if the provided status is valid
        if(status==null || !statuses.contains(status)) {
            throw new IllegalArgumentException("Please, select a valid status option!");
        }

        // Retrieve all reimbursements with the specified status and user ID from the database
        List<Reimbursement>  allReimbursement = reimbursementDAO.findByStatusAndUserUserId(status, userId);

        //for every reimbursement retrieved, we'll create a new OutgoingReimDTO
        //and add it to a List to be returned
        // Create a list to store the OutgoingReimDTO objects
        List<OutgoingReimDTO> outReimbursement = new ArrayList<>();

        // Convert each reimbursement to an OutgoingReimDTO and add it to the list
        for(Reimbursement r : allReimbursement){
            OutgoingReimDTO outP = new OutgoingReimDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getStatus(),
                    r.getAmount(),
                    r.getUser().getUserId());

            outReimbursement.add(outP);
        }
        return outReimbursement;
    }

    /**
     * Retrieves reimbursement tickets with a specific status.
     * @param status The status of the reimbursement tickets to retrieve (ALL, PENDING, APPROVED, or DENIED).
     * @return A list of OutgoingReimDTO objects representing the reimbursement tickets.
     * @throws IllegalArgumentException If the provided status is not valid.
     */
    public List<OutgoingReimDTO> getReimbByStatus(String status){
        ArrayList <String> statuses = new  ArrayList<>(List.of("ALL","PENDING", "APPROVED", "DENIED"));

        if(status==null || !statuses.contains(status)) {
            throw new IllegalArgumentException("Please, select a valid status option!");
        }

        //get all Reimbursement from the DB
        List<Reimbursement>  allReimbursement = reimbursementDAO.findByStatus(status);

        //for every reimbursement retrieved, we'll create a new OutgoingReimDTO
        //and add it to a List to be returned
        List<OutgoingReimDTO> outReimbursement = new ArrayList<>();

        for(Reimbursement r : allReimbursement){
            OutgoingReimDTO outP = new OutgoingReimDTO(
                    r.getReimbId(),
                    r.getDescription(),
                    r.getStatus(),
                    r.getAmount(),
                    r.getUser().getUserId());

            outReimbursement.add(outP);
        }
        return outReimbursement;
    }

    // Update Status
    public void updateStatus(int reimbId, String status) throws JsonProcessingException {
        Optional <Reimbursement> opR = reimbursementDAO.findById(reimbId);

        if(opR.isEmpty()){
            throw new IllegalArgumentException("No reimbursement found!");
        }

 //       List<String> statuses = new ArrayList<>(List.of("PENDING", "APPROVED", "DENIED"));

//        if(!statuses.contains(status)){
//            throw new IllegalArgumentException("No status named " + status+ " is found!");
//        }

        Reimbursement r = opR.get();

        if(r.getStatus().equals(status)){
            throw new IllegalArgumentException("Status is already " + status+ ". No action taken!");
        }

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON string to JsonNode
        JsonNode jsonNode = objectMapper.readTree(status);

        // Extract value of the "status" field
        String statusValue = jsonNode.get("status").asText();
        r.setStatus(statusValue);
        reimbursementDAO.save(r);

    }

    /**
     * Updates the status of a reimbursement ticket.
     * @param reimbId The ID of the reimbursement ticket to update.
     * @param status The new status to set for the reimbursement ticket.
     * @throws IllegalArgumentException If no reimbursement ticket is found with the provided ID, or if the provided status is already the current status of the reimbursement ticket.
     * @throws JsonProcessingException If there is an error processing the JSON string.
     */
    public Reimbursement updateDescription(int reimbId, String description) throws JsonProcessingException {
        Optional <Reimbursement> opR = reimbursementDAO.findById(reimbId);

        if(opR.isEmpty()){
            throw new IllegalArgumentException("No reimbursement found!");
        }

        Reimbursement r = opR.get();

        // Create ObjectMapper instance to parse JSON string
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON string to JsonNode
        JsonNode jsonNode = objectMapper.readTree(description);

        // Extract value of the "status" field
        String descValue = jsonNode.get("description").asText();
        r.setDescription(descValue);
        return reimbursementDAO.save(r);

    }

}
