import React, { useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { UserInterface } from "../../interfaces/UserInterface"
import axios, { AxiosError } from "axios"
import { User } from "../Reimbursement/User"
import "./Employees.css"

/**
 * Component for displaying and managing employees.
 */
export const Employees: React.FC = () => {

    const [users, setUsers] = useState<UserInterface[]>([]) //start with empty array
    const navigate = useNavigate()

    // Fetch all users when the component renders
    useEffect(() => {
        getAllUsers()
    }, []) //empty array so this triggers on component load and state change

    
    /**
     * Function to fetch all users from the server.
     */
    //GET request to server to get all users
    const getAllUsers = async () => {
      try {
          // GET request to fetch users data from the server
          const response = await axios.get("http://localhost:8080/users", { withCredentials: true });
          // Populate the users state with fetched data
          setUsers(response.data);
      } catch (error) {
          handleAxiosError(error); // Handle Axios error
      }
  };

    /**
     * Function to delete a user by ID.
     * @param userId The ID of the user to be deleted.
     */
      const deleteUser = async(userId:number|undefined) => {
    
        if(userId==undefined){
            console.log("Reimbursement ID " + userId)
        }
    
        try{
          // DELETE request to delete user by ID
          const response = await axios.delete("http://localhost:8080/users/" + userId, {withCredentials:true})
          // Alert message on successful deletion
          .then((response) => alert(response.data))
          // Refresh the users list
          //.then(() => getAllUsers())
          getAllUsers()
        }catch (error) {
          handleAxiosError (error)
        }   
      }

    /**
     * Function to handle Axios errors.
     * @param error The Axios error object.
     */
    const handleAxiosError = (error: any) => {
      if (axios.isAxiosError(error)) {
          const axiosError = error as AxiosError;
          alert(axiosError.response?.data); // Display error message
      }
  };
        return (
            <div className="emp-page">
              <button className="log-button" onClick={() => {navigate("/")}}>Log out</button>
          
              <h1>Employees</h1>
          
              <table>
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>USERNAME</th>
                    <th>ACTION</th>
                  </tr>
                </thead>
                <tbody>
                  {/* Map through the users array to display each user */}
                  {users.map((user, index) => (
                    <tr key={index}>
                      <td>{user.userId}</td>
                      <td>{user.username}</td>
                      <td>
                        {/* Button to delete the user by ID */}
                        <button className="del-button" onClick={() => deleteUser(user.userId)}>DELETE</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
              <div className="navbar">
                {/* Button to navigate back to the manager portal */}
                <button className="back-button" onClick={() => navigate("/manager-portal")}>BACK</button>
              </div>
          
            </div>
          )
        }

function handleAxiosError(error: unknown) {
  throw new Error("Function not implemented.")
}
