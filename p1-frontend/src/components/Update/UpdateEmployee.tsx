import { useNavigate } from "react-router-dom"

// Functional Component for updating an employee
export const UpdateEmployee: React.FC = () => {

    const navigate = useNavigate()
    
    return(
        <div>
            <h2> IN PROCESS.. </h2>
            <button className="poke-button" onClick={() => navigate("/collection")}>Click to Back Reimbursements</button>
        </div>
    )
}