package com.revature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.revature.DAOs.ReimbursementDAO;
import com.revature.models.DTOs.IncomingReimDTO;
import com.revature.models.DTOs.OutgoingReimDTO;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.management.Query.in;


/**
This class is a Spring Boot controller responsible for
handling HTTP requests related to reimbursement functionalities
in an employee reimbursement system. It defines methods for submitting,
retrieving, updating, and deleting reimbursement requests.
The controller interacts with a service layer (ReimbursementService)
to perform business logic and data operations. Additionally,
it utilizes HTTP sessions for user authentication and authorization,
allowing only logged-in users to access certain endpoints.
The class is annotated with @RestController to mark it as a controller,
and it specifies request mappings, request methods, and additional annotations such as
@PostMapping, @GetMapping, @DeleteMapping, @PatchMapping, and @PutMapping to define
the HTTP endpoints and their functionalities.
 */

@RestController
@RequestMapping("/reimbursements")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ReimbursementController {
    private ReimbursementService reimbursementService;


    @Autowired
    public ReimbursementController(ReimbursementService reimbursementService) {
        this.reimbursementService = reimbursementService;
    }


    /**
    This method handles the HTTP POST request for submitting a new reimbursement.
    It expects a JSON object of type IncomingReimDTO in the request body,
    which represents the reimbursement details. Additionally, it requires an active HTTP session
    to identify the user submitting the reimbursement. If no user is logged in
    (i.e., if the userId attribute in the session is null), it returns a 401 status code
    with an appropriate error message.

    Once the user is authenticated, the method retrieves the userId from the session
    and attaches it to the reimbursement DTO. This ensures that the reimbursement is associated with
    the correct user. The method then invokes the addReimbursement method from the
    ReimbursementService to perform the addition of the reimbursement to the system.
    If successful, it returns a 201 status code with a success message indicating
    the amount of reimbursement submitted. If any validation or processing errors occur
    during the addition process, it returns a 400 status code with the corresponding error message.
     */
    @PostMapping
    public ResponseEntity<String> submitReimbursement(@RequestBody IncomingReimDTO reimDTO, HttpSession session){

        // If there is no registered user, userId will be null
        // We no longer need to attach userId on our HTTP endpoint
        //Each user has their own session, unique userId then
        //Once login is done, sent userId with the Reimbursement from the frontend
        // If the user is not logged in (if the userId is null), send back a 401
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("First, you must be logged in to submit reimbursement!");
        }

        //Now that we have user info saved (in our HTTP Session),
        // we can attach the stored user Id to the reimDTO
        reimDTO.setUserId((int) session.getAttribute("userId"));


        //try/catch for error handling
        // Some error thrown from ReimburseemntService class will be caught here
        try {
            Reimbursement reim = reimbursementService.addReimbursement(reimDTO);
            return ResponseEntity.status(201).body("Reimbursement amount: " + reim.getAmount() + " submitted!");

        }catch(IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }


    /**
     *  This method handles the HTTP GET request to retrieve all reimbursements.
     It checks if the user is logged in by verifying the presence of the userId attribute
     in the session. If the user is not logged in, it returns a 401 status code with an error message
     indicating the need for authentication.

     Upon successful login, the method retrieves the user's role and userId from the session.
     If the user is identified as a manager, it retrieves all reimbursements from the database
     using the getAllReimbursement method from the ReimbursementService and returns them in the
     response body with a 200 status code. Otherwise, if the user is an employee, it retrieves only
     the reimbursements associated with their userId using the getAllReimbursementByID method
     and returns them in the response body.
     */
    @GetMapping()
    public ResponseEntity<?> getAllReimbursement(HttpSession session) {

        //Login check
        if (session.getAttribute("userId") == null) {
            return ResponseEntity.status(401).body("You must be logged in to get your Reimbursements!");
        }
        // Retrieve user role and ID from session
        String role = (String) session.getAttribute("role");
        int sessionId = (int) session.getAttribute("userId");

        // Retrieve reimbursements based on user role
        if (role.equals("manager")) {
            // Retrieve all reimbursements for managers
            return ResponseEntity.ok(reimbursementService.getAllReimbursement());
        } else {
            // Retrieve reimbursements for employees by their session ID
            return ResponseEntity.ok(reimbursementService.getAllReimbursementByID(sessionId));
        }
    }

    /** This method handles the HTTP DELETE request for deleting a reimbursement by its ID.
     */
    @DeleteMapping("/{reimId}")
    public ResponseEntity<String> deleteReimbursement(@PathVariable int reimId, HttpSession session){

        // Login check
        // If user is not logged in, return 401 status code with an error message
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("You must be logged in to delete a Reimbursement!");
        }

        //Get the userId from the session
        int userId = (int) session.getAttribute("userId");

        //try/catch the service method, either returning a confirmation or error message
        try {
            // Invoke service method to delete reimbursement
            return ResponseEntity.ok(reimbursementService.deleteReimbursement(reimId));
        } catch (Exception e){
            // Return 400 status code with error message if an exception occurs
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    /**
    This method retrieves reimbursement tickets based on their status.
    It first checks if the user is logged in by verifying the presence of the
    userId attribute in the session. If the user is not logged in, it returns
    a 401 status code with an error message indicating the need for authentication.

    Upon successful login, it retrieves the user's role and userId from the session.
    If the user is identified as a manager, it retrieves all reimbursements by the
    specified status using the getReimbByStatus method from the ReimbursementService.
    If no reimbursements are found for the given status, it returns a 400 status code with an error message.

    If the user is an employee, it retrieves only the pending reimbursements associated with the employee's ID
     using the getReimbByStatusAndId method from the ReimbursementService. Again, if no reimbursements are found
     for the given status, it returns a 400 status code with an error message.
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getReimbByStatus(HttpSession session, @PathVariable String status){
        //Login check
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("First, you must be logged in!");
        }
        // Get user role and ID from session
        String role = (String) session.getAttribute("role");
        int sessionId = (int) session.getAttribute("userId");

//        if(reimbursementService.getReimbByStatusAndId(status,sessionId).isEmpty()){
//            return ResponseEntity.status(400).body("You don't have any " + status +  " reimbursement recently!");
//        }
        try {
            // Retrieve reimbursement tickets based on user role and status
            if(role.equals("manager")){
                // If user is a manager, retrieve all reimbursements by status
                if(!status.equals("ALL") && reimbursementService.getReimbByStatus(status).isEmpty()){
                    return ResponseEntity.status(400).body("You don't have any " + status +  " reimbursement recently!");
                }
                return ResponseEntity.ok().body(reimbursementService.getReimbByStatus(status));
            }else {
                if(!status.equals("ALL") && reimbursementService.getReimbByStatusAndId(status,sessionId).isEmpty() ){
                    return ResponseEntity.status(400).body("You don't have any " + status +  " reimbursement recently!");
                }
                // If user is an employee, retrieve only pending reimbursements associated with the employee's ID
                return ResponseEntity.ok().body(reimbursementService.getReimbByStatusAndId(status, sessionId));
            }
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

    }

    /**
    This method updates the status of a reimbursement ticket identified by the
     provided reimbursement ID (reimId). It takes the new status as input from
     the request body and performs the update.
     When you send a PATCH request with Axios using axios.patch() from frontend,
     the data is sent in the request body.
     @RequestBody,expects the data to be passed in the request body as JSON.
     */
    @PatchMapping("/{reimId}")
    public ResponseEntity<String> updateStatus(HttpSession session, @PathVariable int reimId, @RequestBody String status){
        // Check if user is logged in
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to update a user's role.");
            //return ResponseEntity.status(401).body("First, you must be logged in!");
        }
        // Get user role from session
        String role = (String) session.getAttribute("role");


        // Check if user is a manager
         if(!role.equals("manager")){
             // Return 401 status code with an error message if user is not a manager
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You do not have permission to update the status");
         }


        try{
            // Update reimbursement status using the service method
            reimbursementService.updateStatus(reimId,status);
            // Return 200 status code with success message if update is successful
            return ResponseEntity.ok().body("Reimbursement status updated to " + status + "!");
        }catch (IllegalArgumentException e){
            // Return 200 status code with error message if an IllegalArgumentException occurs
            return ResponseEntity.ok().body(e.getMessage());
        } catch (JsonProcessingException e) {
            // Throw a RuntimeException if JsonProcessingException occurs
            throw new RuntimeException(e);
        }
    }


    /**
    This method updates the description of a reimbursement identified by the provided reimbursement ID (reimbId).
     It takes the new description as input from the request body and performs the update.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateRole(HttpSession session, @PathVariable int reimbId, @RequestBody String desc){
        // Check if user is logged in
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to update a user's role.");
            //return ResponseEntity.status(401).body("First, you must be logged in!");
        }

        try{
            // Update reimbursement description using the service method
            return ResponseEntity.ok().body(reimbursementService.updateDescription(reimbId, desc));
        }catch (IllegalArgumentException | JsonProcessingException e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}
