
/**
 * The purpose of the ReimbursementInterface interface is to define a contract or blueprint 
 * for objects representing reimbursements in the application. Interfaces in TypeScript provide a way 
 * to explicitly specify the shape of an object, including the types of its properties and methods.
 * 
 * Interface representing a reimbursement object.
 * Defines the structure and types of properties for reimbursement objects.
 */

export interface ReimbursementInterface {
    reimbId: number; // Unique identifier for the reimbursement
    description: string; // Description of the reimbursement
    amount: number; // Amount of the reimbursement
    status: string; // Status of the reimbursement (e.g., PENDING, APPROVED, DENIED)
    userId?: number; // Optional field for the user ID associated with the reimbursement
}