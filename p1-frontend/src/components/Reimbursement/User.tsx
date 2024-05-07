import { UserInterface } from "../../interfaces/UserInterface"

// Functional Component to display a single user
export const User: React.FC<UserInterface> = (user:UserInterface) => {
    //console.log(user)

    return(
        <div className="user-container">
               
            <table>
            <tbody>
                <tr>
                    <td>{user.userId}</td>
                    <td>{user.username}</td>
                    <td>{user.firstName}</td>
                    <td>{user.lastName}</td>
                    <td>{user.role}</td>
                 </tr>
            </tbody>
            </table>
        </div>
    )

}