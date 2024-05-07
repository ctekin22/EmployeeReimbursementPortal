package com.revature.controllers;

import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.User;
import com.revature.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller class responsible for handling HTTP requests related to user management functionalities
 * such as user registration, login, deletion, retrieval, listing all users, and updating user roles.
 * Uses Cross-Origin Resource Sharing (CORS) to allow requests from a specific origin (http://localhost:3000)
 * and credentials.
 */

/** Make it Spring bean and convert our responses from the database to convert to JSON directly.*/
@RestController

/** to specify endpoints to map HTTP request*/
@RequestMapping("/users")

/**
 * Approving our frontend to talk to this controller
 * we're ALSO saying that we're going to allow session data to be passed back and forth
 * We need to allowCredentials = "true" to access session data on backend and be able to perform submit action from frontend
 * Otherwise, even though we logged in we will not be able to submit or approve reimbursement
 * because our login session user information will not be saved and used within application.
 * We also need to allow credentials in our frontend login component and in each session dependent component
 * But it is gonna work on postman without allowCredentials = "true"
 * Our frontend id running on port 3000
 */
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    // Autowires UserService for handling user-related business logic
    private UserService userService;

    /**
     * Constructor for UserController.
     * @param userService UserService instance to be autowired.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * REGISTER
     * Handles HTTP POST request for registering a new user.
     * @param userDTO IncomingUserDTO containing user registration information.
     * @return ResponseEntity containing the registration status message.
     */
    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody IncomingUserDTO userDTO){

        //try to register the user
        // if anything goes wrong, the exception will be caught in catch block
        // and error messages will be displayed as we determined in UserService class

        try{
            userService.registerUser(userDTO);
            return ResponseEntity.status(201).body(userDTO.getUsername() + " was created!");
            //If this all works, send back a 201 CREATED, plus a confirmation message
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(400).body(e.getMessage());
            //If something goes wrong, send back a 400 BAD REQUEST, plus the error message
        }
    }

    /**
     * LOGIN
     * Handles HTTP POST request for user login.
     * @param userDTO IncomingUserDTO containing user login credentials.
     * @param session HttpSession for storing user information.
     * @return ResponseEntity containing the login status message or user information if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody IncomingUserDTO userDTO, HttpSession session){


        // How can I check if incoming user is a valid user?
        // We will check user with username and password
        // We will add a costume method for that to UserDAO
        // We will use HttpSession here. Once we log in our user information should be stored in the session
        // So our information can be used anywhere inside API as long as we logged in.
        //Get the User object from the service (which talks to the DB)
        Optional<User> optionalUser = userService.loginUser(userDTO);

        //If login fails (which will return an empty optional), tell the user they failed
            try {
                if(optionalUser.isEmpty()) {
                    return ResponseEntity.status(401).body("Login Failed!");
                }
            } catch (IllegalArgumentException e) {
                return ResponseEntity.ok().body(e.getMessage());
            }

        //If login succeeds store the user info in our session
        User u = optionalUser.get();

        //Storing the user info in our session
        session.setAttribute("userId", u.getUserId());
        //session.setAttribute("username", u.getUsername()); //probably won't use this

        session.setAttribute("role", u.getRole());

        //Finally, send back a 200 (OK) as well as a OutgoingUserDTO
        return ResponseEntity.ok(new OutgoingUserDTO(u.getUserId(), u.getUsername(), u.getRole()));
    }


    /**
     * Handles HTTP DELETE request for deleting a user by ID.
     * @param session HttpSession for performing user authentication.
     * @param userId ID of the user to be deleted.
     * @return ResponseEntity containing the deletion status message.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(HttpSession session, @PathVariable int userId){

        //make sure the deleter is logged in and is a manager
        //make sure the user to delete actually exists, taken care of in UserService
        //make sure the deleter is not trying to delete themselves

        //Login check
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("First, you must be logged in!");
        }
        //Get the userId from the session
        int sessionId = (int) session.getAttribute("userId");
        String username = userService.getUser(userId).getUsername();

        //Role check
        String role = (String) session.getAttribute("role");
        if(!role.equals("manager")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete a user with " + role + " role!");
       }

        //Do not delete yourself
        if(sessionId==userId){
            return ResponseEntity.status(401).body("You cannot delete yourself!");
        }

        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(username + " has been deleted!");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /**
     * Handles HTTP GET request for retrieving user by ID.
     * @param session HttpSession for performing user authentication.
     * @param userId ID of the user to be retrieved.
     * @return ResponseEntity containing the user information if found.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(HttpSession session, @PathVariable int userId) {

        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("First, you must be logged in!");
        }

        try {
            return ResponseEntity.ok().body(userService.getUser(userId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    /**
     * Handles HTTP GET request for retrieving all users.
     * @param session HttpSession for performing user authentication.
     * @return ResponseEntity containing the list of all users.
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers(HttpSession session){

        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(401).body("First, you must be logged in!");
        }

        //Role check
        String role = (String) session.getAttribute("role");
        if(!role.equals("manager")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to view all users with " + role + " role!");
        }

        try {
            return ResponseEntity.ok().body(userService.allUsers());
        }catch(IllegalArgumentException e){
            return ResponseEntity.ok().body(e.getMessage());
        }

    }

    /**
     * Handles HTTP PATCH request for updating user role.
     * @param session HttpSession for performing user authentication.
     * @param userId ID of the user whose role is to be updated.
     * @param role New role to be assigned to the user.
     * @return ResponseEntity containing the role update status message.
     */
    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateRole(HttpSession session, @PathVariable int userId, @RequestBody String role){
        if(session.getAttribute("userId") == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to update a user's role.");
            //return ResponseEntity.status(401).body("First, you must be logged in!");
        }

        try{
            return ResponseEntity.ok().body(userService.updateRole(userId, role));
        }catch (IllegalArgumentException e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

}
