import { UserInterface } from "../../interfaces/UserInterface"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import axios, { AxiosError } from "axios"
import "./Register.css"

/**
 * Component for user registration.
 */
export const Register: React.FC = () => {
        
    // Set initial state for user data(from UserInterface)/
    const[user, setUser] = useState<UserInterface>({
        username:"",
        password:"",
        firstName:"",
        lastName:""
    })
    
    //useNavigate to navigate between components
    // Hook to navigate between components
    const navigate = useNavigate()
    
// Function to store input box values
const storeValues = (input: any) => {
    // Check which input field has changed and update the corresponding state in the 'user' object
    
    // If the input that has changed is the "username" input, update the 'username' state
    if (input.target.name === "username") {
        setUser((user) => ({ ...user, username: input.target.value }));
    } 
    // If the input that has changed is the "firstname" input, update the 'firstname' state
    else if (input.target.name === "firstName") {
        setUser((user) => ({ ...user, firstName: input.target.value }));
    } 
    // If the input that has changed is the "lastname" input, update the 'lastname' state
    else if (input.target.name === "lastName") {
        setUser((user) => ({ ...user, lastName: input.target.value }));
    } 
    // For any other input, update the 'password' state
    else {
        setUser((user) => ({ ...user, password: input.target.value }));
    }
}

    // Function to send a POST request with user data to register a user in the backend
    //! Remember, requests to our Java server will only work with @CrossOrigin in our Controllers
    const register = async () => {

        console.log(user)

       // Send a POST request to the backend with user data
       try{
        const response = await axios.post("http://localhost:8080/users", user)
        // Alert success message
        alert(response.data) //"{user} was created!"

        // Navigate back to the login page after registration
        navigate("/")
    }catch(error){
        if (axios.isAxiosError(error)) {
          // Handle AxiosError
          const axiosError = error as AxiosError;
          alert(axiosError.response?.data)
         }
        }
    }

    return(
        <div className="register">

        <div className="reg-text-container">
            <h1>Create an Account for free!</h1>
            {/* Input fields for user registration */}
            <div className="reg-container">
                <input type="text" placeholder="username" name="username" onChange={storeValues}/>
            </div>
            <div className="reg-container">
                <input type="text" placeholder="firstname" name="firstName" onChange={storeValues}/>
            </div>
            <div className="reg-container">
                <input type="text" placeholder="lastname" name="lastName" onChange={storeValues}/>
            </div>
            <div className="reg-container">
                <input type="password" placeholder="password" name="password" onChange={storeValues}/>
            </div>


            <button className="reg-button" onClick={register}>Submit</button>
            <button className="reg-button" onClick={() => navigate("/")}>Back</button>

        </div>

        </div>
    )
}