package com.revature.services;

import com.revature.DAOs.UserDAO;
import com.revature.models.DTOs.IncomingUserDTO;
import com.revature.models.DTOs.OutgoingReimDTO;
import com.revature.models.DTOs.OutgoingUserDTO;
import com.revature.models.Reimbursement;
import com.revature.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Classes are used for data processing between controllers and DAOs
 * anything we need to do between HTTP and the Database is done here
 * Error handling
 * Data mutations
 * Data validations
 * more!
 *
 * The UserService class is annotated with @Service to mark it as a service component.
 * It declares a single dependency, UserDAO, which is injected via constructor injection.
 * Spring's dependency injection mechanism automatically injects an instance of UserDAO
 * when creating an instance of UserService, enabling the service to interact with the database through the DAO.
 */

/**
 * Service class responsible for handling user-related operations.
 * This class acts as an intermediary between controllers and DAOs,
 * providing business logic and data processing functionalities.
 */
@Service
public class UserService {

    // Service always calls for DAO, so DAO is a dependency for a Service
    // We need to inject UserDAO to use UserDAO methods
    // Autowire with constructor for dependency injection
    private UserDAO userDAO;

    /**
     * Constructor-based dependency injection for UserDAO.
     *
     * @param userDAO The UserDAO dependency to be injected.
     */
    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // This is to make sure everything is done right by user
    //Register user
    // Once we want to insert an user to the application,
    // our User class has user name, id, role, and a list of reimbursement
    // In frontend, while creating account no need to specify user id, role and reimbursement
    // We will create a Data Transfer Object (DTO)
    // It will allow us to pass only username and password to create account
    // we can set up other properties later.
    // We will create a DTOs package with Classes:
    //IncomingUserDTO: Sends the user to the backend
    //OutgoingUserDTO: Send the user from the backend

    /**
     * Registers a new user based on the provided IncomingUserDTO.
     *
     * @param userDTO The IncomingUserDTO containing user registration details.
     * @return The newly registered User object.
     * @throws IllegalArgumentException If registration parameters are invalid.
     */
    public User registerUser(IncomingUserDTO userDTO) throws IllegalArgumentException {

        //Check the username and password are not empty/null
        if (userDTO.getUsername() == null || userDTO.getUsername().isBlank() ) {
            throw new IllegalArgumentException("Username cannot be empty!");
        }
        // Check if the first name is blank or null
        if ( userDTO.getFirstName() == null || userDTO.getFirstName().isBlank()) {
            // If the first name is blank or null, throw an IllegalArgumentException with a message
            throw new IllegalArgumentException("Firstname cannot be empty!");
        }

        if ( userDTO.getLastName() == null || userDTO.getLastName().isBlank()) {
            throw new IllegalArgumentException("Lastname cannot be empty!");
        }

        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        if (!userDTO.getPassword().matches("^(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            throw new IllegalArgumentException("Password need to meet these conditions:\n" +
                    "1. At least 8 characters long\n" +
                    "2. Contains at least one lowercase letter\n" +
                    "3. Contains at least one digit\n" +
                    "4. Contains at least one special character (e.g., !@#$%^&*)!");
        }


        //also check that the username isn't vulgar
        // Vulgar terms are words or phrases that are considered offensive, crude, or inappropriate
        //To check if the username is vulgar, you would need to implement a more comprehensive validation mechanism.
        // This could involve comparing the username against a list of known vulgar terms or patterns,
        // using regular expressions, or utilizing a third-party service or library for profanity filtering.
        // Define a list of vulgar terms or patterns
        //List<String> vulgarTerms = Arrays.asList("vulgar1", "vulgar2", "vulgar3");
        //
        // Check if the username is vulgar
        //for (String term : vulgarTerms) {
        //    if (userDTO.getUsername().toLowerCase().contains(term)) {
        //        throw new IllegalArgumentException("Username cannot contain vulgar terms");
        //    }
        //}
        if (userDTO.getUsername().equals("None")) {
            throw new IllegalArgumentException("Username cannot be None");
        }

        if (userDTO.getFirstName().equals("None")) {
            throw new IllegalArgumentException("Firstname cannot be None");
        }

        if (userDTO.getLastName().equals("None")) {
            throw new IllegalArgumentException("Lastname cannot be None");
        }

        //we could definitely check if the DTO is null too,
        //but we're going to assume we've written good code
        if (userDTO == null) {
            throw new IllegalArgumentException("This probably won't get thrown if we've written good code");
        }

        //TODO: We could have made an exception handling service to clean all this up, which is typical

        //if all checks pass, we can create a new User based off the DTO and send it to the DAO
        User newUser = new User(userDTO.getUsername(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword());

        //save the user to the database and return that user at the same time
        return userDAO.save(newUser);

    }

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param userDTO The IncomingUserDTO containing user credentials.
     * @return An Optional containing the authenticated User object, or empty if authentication fails.
     * @throws IllegalArgumentException If authentication parameters are invalid.
     */
    //This service will facilitate login - get a user from the DAO (or null)
    public Optional<User> loginUser(IncomingUserDTO userDTO) throws IllegalArgumentException {

        // Validity checks
        //Check the username and password are not empty/null
        if (userDTO.getUsername().isBlank() || userDTO.getUsername() == null) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (userDTO.getPassword().isBlank() || userDTO.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        //if all checks pass, return a User OR null, and send it to the controller
        return userDAO.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());

    }

    /**
     * Deletes a user with the specified userId.
     *
     * @param userId The ID of the user to be deleted.
     * @throws IllegalArgumentException If the specified user is not found.
     */
    //this method will delete a user by id
    public void deleteUser(int userId) {

        // Validity checks, taken care of in UserController
        //make sure the deleter is logged in and is a manager
        //make sure the user to delete actually exists
        //make sure the deleter is not trying to delete themselves

        if (userDAO.findById(userId).isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        userDAO.deleteById(userId);
    }

    /**
     * Retrieves a user by their userId.
     *
     * @param userId The ID of the user to retrieve.
     * @return The OutgoingUserDTO representing the retrieved user.
     * @throws IllegalArgumentException If the specified user is not found.
     */
    public OutgoingUserDTO getUser(int userId) {

        Optional<User> opU = userDAO.findById(userId);
        if (opU.isEmpty()) {
            throw new IllegalArgumentException("User is not found");
        }
        return getUserDTO(opU.get());
    }

    /**
     * Retrieves all users in the system.
     *
     * @return A list of OutgoingUserDTOs representing all users in the system.
     * @throws IllegalArgumentException If no users are found in the system.
     */
    public List<OutgoingUserDTO> allUsers() {
        //get all User from the DB
        List<User> allUsers = userDAO.findAll();
        //for every User retrieved, we'll create a new OutgoingUserDTO
        //and add it to a List to be returned

        if(allUsers.isEmpty()){
            throw new IllegalArgumentException("No user found in the system!");
        }
        List<OutgoingUserDTO> outUsers = new ArrayList<>();

        for(User u : allUsers){
            outUsers.add(getUserDTO(u));
        }

        return outUsers;
    }

    /**
     * Updates the role of a user with the specified userId.
     * @param userId The ID of the user whose role is to be updated.
     * @param role   The new role to assign to the user.
     * @return The OutgoingUserDTO representing the updated user.
     * @throws IllegalArgumentException If the specified user is not found or the role is invalid.
     */
    public OutgoingUserDTO updateRole(int userId, String role) {
        Optional <User> opU = userDAO.findById(userId);

        if(opU.isEmpty()){
            throw new IllegalArgumentException("No user found");
        }

        //List<String> roles = new ArrayList<>(List.of("employee", "manager"));
        List<String> roles = new ArrayList<>(List.of("manager"));

        if(role==null || !roles.contains(role)){
        throw new IllegalArgumentException(role+ " is not a valid role for this action!");
        }

        User u = opU.get();
        u.setRole(role);
        userDAO.save(u);

        return getUserDTO(u);

    }

    /**
     * Converts a User object to an OutgoingUserDTO object.
     * @param user The User object to convert.
     * @return The converted OutgoingUserDTO object.
     */
    public OutgoingUserDTO getUserDTO(User user){

        return new OutgoingUserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getRole());
    }

}