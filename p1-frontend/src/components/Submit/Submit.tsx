import { useState } from "react"
import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface"
import { useNavigate } from "react-router-dom"
import axios, { AxiosError } from "axios"
import { Reimbursement } from "../Reimbursement/Reimbursement"
import { text } from "stream/consumers"
import { state } from "../../globalData/store"
import "./Submit.css"

// Functional Component for submitting a reimbursement
export const Submit: React.FC = () => {

    //A variable to store user input for finding a reimbursement
    const [userInput, setUserInput] = useState(0)

    //we need to store reimbursement state to use when rendering the Reimbursement Component
    // State to store user input for description and amount
    const [reimbursement, setReimbursement] = useState<ReimbursementInterface>({
        reimbId:0,
        description:"",
        amount:0,
        status:"PENDING"
    })

    //we need our useNavigate hook to programmatically switch endpoints (which switches components)
    const navigate = useNavigate()

    //a function that stores the user input (Which we need for our GET request)
    // Function to gather user input for description and amount
    const gatherInput = (input:any) => {
        //setUserInput(input.target.value) //set the userInput to what's in the input box

        if(input.target.name === "description"){
            setReimbursement((reimbursement) => ({...reimbursement, description:input.target.value}))
        }
           
        if(input.target.name === "amount"){
            setReimbursement((reimbursement) => ({...reimbursement, amount:input.target.value}))
        }        
        
    }
    // Function to submit a reimbursement
    const getReimbursement= async () => {

        console.log(userInput)

        //sending our request to reimbursement API using the userInput as the reimbursement id to search for
        const response = await axios.post("http://localhost:8080/reimbursements" + reimbursement)


        //let's set our Reimbursement state with the incoming data
        //submitReimbursement((reimbursement) => ({...reimbursement, description:response.description})) //only changing the name
        ///submitReimbursement((reimbursement) => ({...reimbursement, amount:response.amount})) //only changing the image

        alert(response.data) 
    
    }

    // this function will send the existingreimbursement to the Database
    const submitReimbursement= async () => {

             try{   
                const response = await axios.post("http://localhost:8080/reimbursements", 
                reimbursement,
                {withCredentials:true})
                .then((response) => {
                    alert( " Your reimbursement is  " + reimbursement.status) 
                    //alert(state.userSessionData.username + " caught " + reimbursement.status)
                    //wouldn't "response.data" be way shorter and simpler to write? yes
                })
                .then(() => {
                    state.lastSubmittedReimbursement = reimbursement//sending our local state to global state 
                })
            } catch (error) {
                if (axios.isAxiosError(error)) {
                  // Handle AxiosError
                  const axiosError = error as AxiosError;
                  alert(axiosError.response?.data) }

    }
}

    return(
        <div className="home-page">
            <button className="log-button" onClick={() => {navigate("/")}}>Log out</button>

            <h3>Submit a Reimbursement!</h3>

            <div className="home-container">
                <input className="desc" type="text" placeholder="Enter Description" name="description" onChange={gatherInput}/>
                <input className="amount" type="text" placeholder="Enter Amount" name="amount" onChange={gatherInput}/>
                <button className="poke-button" onClick={submitReimbursement}>Submit</button>
                {/*<Reimbursement {...reimbursement}> </Reimbursement>*/}
            </div> 
            <br/>
            <br/>
            <div className="navbar">
                <button className="poke-button" onClick={() => {navigate("/collection")}}>See All Reimbursements</button>
                {/*<button className="poke-button" onClick={() => {navigate("/")}}>Back to Login</button>*/}
            </div>

 
            
        </div>
        
    )
}