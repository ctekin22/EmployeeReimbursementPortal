package com.revature.DAOs;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface responsible for defining data access methods related to User entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 * Uses Spring Data JPA for easy database interaction.
 */
@Repository
public interface UserDAO extends JpaRepository<User,Integer> {


    /**
     * Retrieves an optional user by username and password.
     * @param username Username of the user to be retrieved.
     * @param password Password of the user to be retrieved.
     * @return Optional containing the user with the specified username and password, if found.
     */
    public Optional<User> findByUsernameAndPassword(String username, String password);
    //We need to add a custom method to find a user by username and password
    //This is what we'll use to check for valid login credentials

}
