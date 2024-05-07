import { useEffect, useState } from "react"
import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface"
import axios, { AxiosError } from "axios"
import { Reimbursement } from "../Reimbursement/Reimbursement"
import "./Update.css"
import { useNavigate } from "react-router-dom"
import { Session } from "inspector"
import { state } from "../../globalData/store"


/*
 * Functional Component for updating reimbursement status by manager
 * This component fetches reimbursement data with 'PENDING' status from the server
 * Allows the manager to approve or deny reimbursement requests
 */
export const UpdateManager: React.FC = () => {
    //We could have stored a base URL here for cleaner requesting
    //const baseUrl = "http://localhost:8080/pokemon" 

    // State to store reimbursement data
    const [reimbursements, setReimbursement] = useState<ReimbursementInterface[]>([]) //start with empty array
    const navigate = useNavigate()
  

    // Function to fetch reimbursements with 'PENDING' status from the server
    const getReimbursementStatus= async () => {

      const status= "PENDING"
      //our GET request (remember to send withCredentials to confirm the user is logged in)
      //const response = await axios.get("http://localhost:8080/reimbursements/status/" + status, {withCredentials:true})
    
      try {
        const response = await axios.get(`http://localhost:8080/reimbursements/status/${status}`, { withCredentials: true });
        setReimbursement(response.data);
      } catch (error) {
          if (axios.isAxiosError(error)) {
            // Handle AxiosError
            const axiosError = error as AxiosError;
            alert(axiosError.response?.data) 
        } 
        }   
}
         // Function to update reimbursement status (APPROVED or DENIED)
        const updateReimbursement = async(status:string, reimbId:number) => {
            
            try {
                const response = await axios.patch("http://localhost:8080/reimbursements/" + reimbId,{status},{withCredentials:true})
                alert("REIMBURSEMENT "+ status + "!")
            }catch (error) {
                if (axios.isAxiosError(error)) {
                // Handle AxiosError
                    const axiosError = error as AxiosError;
                    alert("Update failed!") 
                } 
            }

           // Refresh reimbursements if any exist after update
           if (reimbursements.length !== 0) {
            getReimbursementStatus();
           } else {
            navigate("/collections");
          }

        }

        // Fetch reimbursements with 'PENDING' status when component mounts
        useEffect(() => {
            getReimbursementStatus(); // Call getReimbursementStatus when component mounts
        }, []); // Empty dependency array means it only runs once on mount

        return (
            <div className="update-man-container">
                {/* Log out button */}
                <button className="log-button" onClick={() => {navigate("/")}}>Log out</button>
            {/* Table to display reimbursement data */}
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>DESCRIPTION</th>
                    <th>AMOUNT</th>
                    <th>STATUS</th>
                    <th>ACTION</th> {/* Header for the action column */}
                  </tr>
                </thead>
                <tbody>
                  {/* Map through reimbursements and display each as a table row */}
                  {reimbursements.map((reim, index) => (
                    <tr key={index}>
                      <td>{reim.reimbId}</td>
                      <td>{reim.description}</td>
                      <td>{reim.amount}</td>
                      <td>{reim.status}</td>
                      <td>
                        {/* Buttons to approve or deny reimbursement */}
                        <button className="deny-button" onClick={() => updateReimbursement("DENIED", reim.reimbId)}>DENY</button>
                        <button className="approve-button" onClick={() => updateReimbursement("APPROVED", reim.reimbId)}>APPROVE</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
             
              <div>
                <button className="back-button" onClick={() => navigate("/collection")}>BACK</button>
              </div>
            </div>
          );
        }