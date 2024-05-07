import { ReimbursementInterface } from "../../interfaces/ReimbursementInterface"
import "./Reimbursement.css"

// Functional Component to display a single reimbursement
export const Reimbursement: React.FC<ReimbursementInterface> = (reimbursement:ReimbursementInterface) => {
    console.log(reimbursement)

    return(
        <div className="reimbursement-container">
            <table>
            <tbody>
                <tr>
                    <td>{reimbursement.reimbId}</td>
                    <td>{reimbursement.description}</td>
                    <td>{reimbursement.amount}</td>
                    <td>{reimbursement.status}</td>
                    <td>{reimbursement.userId}</td>
                 </tr>
            </tbody>
            </table>
        </div>
    )

}