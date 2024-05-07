Employee Reimbursement System (ERS)
The Employee Reimbursement System (ERS) is a Java Full Stack application designed to streamline the reimbursement process within an organization. It provides a platform for employees to submit reimbursement requests which can then be reviewed and approved or denied by managers. The system utilizes a React-based front end that communicates via HTTP to a Spring-based back end, with data stored in the PostgreSQL database.

Features

Login
Login Authentication: Users who are not logged in can only attempt to log in. Access to other user stories is restricted until successful authentication.

Employee User Stories
Registration
Create an Account: Users can register for an account, with the default role set to employee.
Reimbursement Management
Submit Reimbursement: Employees can create new reimbursement requests.
View Reimbursements: Employees can see all their reimbursement tickets.
View Pending Reimbursements: Employees can view only their pending reimbursement tickets.

Manager User Stories
Reimbursement Management
View All Reimbursements: Managers can see all reimbursement requests.
View Pending Reimbursements: Managers can see all pending reimbursement requests.
Resolve Reimbursements: Managers can update the status of a reimbursement from PENDING to APPROVED or DENIED.
User Management
View All Users: Managers can view all users registered in the system.
Delete User: Managers can delete a user, including any related reimbursements.

Tech Stack

Back End: Java, Spring Boot, Spring Data, Node.js , PostgreSQL

Front End: React, TypeScript, HTML,CSS

Contributors
Cansu TEKIN
