import axios, { AxiosError } from "axios"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { UserInterface } from "../../interfaces/UserInterface"
import { state } from "../../globalData/store"
import "./Login.css"

/**
 * Functional component representing the login page.
 * Allows users to log in to the system.
 */
export const Login: React.FC = () => {

//defining a state object for our user data
const[user, setUser] = useState<UserInterface>({
    username:"",
    password:""
})

//we need a useNavigate hook to allow us to navigate between components... no more manual URL changes!
const navigate = useNavigate()

//function to store input box values
const storeValues = (input:any) => {

    //if the input that has changed is the "username" input, change the value of username in the user state object

    if(input.target.name === "username"){
        setUser((user) => ({...user, username:input.target.value}))
    } else {
        setUser((user) => ({...user, password:input.target.value}))
    }

}

//this function will (EVENTUALLY) gather username and password, and send a POST to our java server
const login = async () => {

    //TODO: We could (should) validate user input here as well as backend 

    //Send a POST request to the backend for login
    //NOTE: with credentials is what lets us save/send user session info
    const response = await axios.post("http://localhost:8080/users/login", 
    user,
    {withCredentials:true})
    .then((response) => {

        //if the login was successful, log the user in and store their info in global state
        state.userSessionData = response.data
        
        console.log(state.userSessionData)

        alert("Welcome, " + state.userSessionData.username)

        //use our useNavigate hook to switch views 
        const role = state.userSessionData.role;

        // Navigate to appropriate view based on user role
        if (role === 'manager') {
            navigate("/manager-portal");
        } else if (role === 'employee') {
            navigate("/submit");
        } else {
     
        }})
    .catch(() =>{alert("Please, Enter a valid username and password!")})
         }
    
    return(
        <div className="login">
            <div className="text-container">
                <h1>Welcome to the Reimbursement System</h1>
                <h2>Log in/Register to Submit and View Reimbursements!</h2>

                <div className="input-container">
                    <input className="us" type="text" placeholder="username" name="username" onChange={storeValues}/>
                </div>

                <div className="input-container">
                    <input className="us" type="password" placeholder="password" name="password" onChange={storeValues}/>
                </div>

                <button className="login-button" onClick={login}>Login</button>
                <button className="login-button" onClick={() => navigate("/register")}>Register</button>
            </div>

        </div>
 
    )
}