import { useEffect, useState } from "react"
import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface"
import axios, { AxiosError } from "axios"
import { Reimbursement } from "../Reimbursement/Reimbursement"
import "./Collection.css"
import { useNavigate } from "react-router-dom"
import { Session } from "inspector"
import { state } from "../../globalData/store"



/**
 * Component to display and manage collection of reimbursements.
 */
export const Collection: React.FC = () => {

    //We could have stored a base URL here for cleaner requesting
    //const baseUrl = "http://localhost:8080/pokemon" 

    const role = state.userSessionData.role;

    //we'll store state that consists of an Array of ReimbursementInterface objects
    // State to store reimbursements
    const [reimbursements, setReimbursement] = useState<ReimbursementInterface[]>([]) //start with empty array
     
    // Hook to navigate between components
    const navigate = useNavigate()
    
    //I want to get all Reimbursement when the component renders, so we'll use useEffect
    // Function to get all reimbursements when the component renders
    useEffect(() => {
        getAllReimbursement()
    }, []) //empty array so this triggers on component load and state change

    //Function to get all reimbursements from the server
    const getAllReimbursement= async () => {

        try {
        // Send GET request to server to get all reimbursements(remember to send withCredentials to confirm the user is logged in)
        const response = await axios.get("http://localhost:8080/reimbursements", {withCredentials:true})

        //populate the Reimbursement state  
        setReimbursement(response.data)
        } catch (error) {
            // Handle errors
            if (axios.isAxiosError(error)) {
                const axiosError = error as AxiosError;
                alert(axiosError.response?.data);
            }
        }
    }

    //Delete Reimbursement by id
    // Function to delete a reimbursement by ID
    const deleteReimbursement = async(reimbId:number|undefined) => {

        //TODO: throw some error if reimd is typeof undefined
        // Check if reimbId is undefined
        if(reimbId==undefined){
            console.log("Reimbursement ID " + reimbId)
        }

        // Send DELETE request to server to delete reimbursement by ID
        const response = await axios.delete("http://localhost:8080/reimbursements/" + reimbId, {withCredentials:true})
        .then((response) => alert(response.data))
         // Refresh the reimbursements list
        .then(() => getAllReimbursement())
        .catch(
            //TODO: we could have some catches here for the errors that can pop up
            
        )

    }

        //GET request to server to get all reimbursements by status
        const getReimbursementStatus= async (event: React.ChangeEvent<HTMLSelectElement>) => {

            const status= event.target.value;
            console.log(state.userSessionData)

            if(status=="ALL"){
                getAllReimbursement()
            }

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
                  getAllReimbursement()
                } 
            }
        }
        //Handle navigation based on user role
        //Function to handle navigation back to manager portal or submission page
        const handleReim = () => {
                
            //const role = state.userSessionData.role;
    
            if (role === 'manager') {
                navigate("/update-manager");
            } else if (role === 'employee') {
                navigate("/update-employee");
            } else {
                //navigate("/login");
            }
        }
        // Function to handle selection of reimbursement status
        const handleRole = () => {
    
            if (role === 'manager') {
                navigate("/manager-portal");
            } else if (role === 'employee') {
                navigate("/submit");
            } else {
                //navigate("/login");
            }
        }
    
    return(
        <div className="collection-container">

            <select id="status" name="status" onChange={getReimbursementStatus}>
                {/* Dropdown for selecting reimbursement status */}
                <option value="ALL">ALL</option>
                <option value="PENDING">PENDING</option>
                <option value="DENIED">DENIED</option>
                <option value="APPROVED">APPROVED</option>
            </select>
            {/* Table to display reimbursements */}
            <table className="table-container">
      <thead>
        <tr>
          <th>ID</th>
          <th>DESCRIPTION</th>
          <th>AMOUNT</th>
          <th>STATUS</th>
          <th>USER-ID</th>
          <th>ACTION</th> {/* Header for the action column */}
        </tr>
      </thead>
      <tbody>
        {reimbursements.map((reim, index) => (
          <tr key={index}>
            <td>{reim.reimbId}</td>
            <td>{reim.description}</td>
            <td>{reim.amount}</td>
            <td>{reim.status}</td>
            <td>{reim.userId}</td>
            <td>
                 {/* Button to delete reimbursement */}
              <button className="col-button" onClick={() => deleteReimbursement(reim.reimbId)}>DELETE</button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
      {/* Buttons for updating and navigating back */}
            <div>
                <button className="col1-button" onClick={() => handleReim() }>UPDATE</button>
                <button className="col1-button" onClick={() => handleRole()}>BACK</button>
                <button className="log-button" onClick={() => {navigate("/")}}>Log out</button>
            </div>

            {/* If you need to render multiple things in map(), they need to be in a <div> */}

        </div>
    )
}


