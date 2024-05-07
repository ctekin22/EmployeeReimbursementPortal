/**
 * Interface representing a user object.
 * Defines the structure and types of properties for user objects.
 */
export interface UserInterface {
    userId?: number;      // Optional: Unique identifier for the user
    username: string;     // Username of the user
    firstName?: string;   // Optional: First name of the user
    lastName?: string;    // Optional: Last name of the user
    password?: string;    // Optional: Password of the user
    role?: string;        // Optional: Role of the user (e.g., employee, manager)
}