import { useEffect, useState } from "react"
import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface"
import { useNavigate } from "react-router-dom"
import axios from "axios"
import { Reimbursement } from "../Reimbursement/Reimbursement"
import { text } from "stream/consumers"
import { state } from "../../globalData/store"
import React from "react"
import { UserInterface } from "../../interfaces/UserInterface"
import './ManagerPortal.css';

/**
 * Component for managing reimbursements and employees.
 */
export const ManagerPortal: React.FC = () => {
    const navigate = useNavigate()
    return(
        <div className="man-page">

            <h1>Welcome to Reimbursement Management Portal!</h1>
            <div className="man-port">
                {/* Button to navigate to see all reimbursements */}
                <button className="hom-button" onClick={() => {navigate("/collection")}}>See All Reimbursements</button>
                {/* Button to navigate to see all employees */}
                <button className="hom-button" onClick={() => {navigate("/employees")}}>See All Employees</button>
                 {/* Button to log out */}
                <button className="log-button" onClick={() => {navigate("/")}}>Log out</button>
            </div>

        </div>
        
    )
}