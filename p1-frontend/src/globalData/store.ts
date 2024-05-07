import { ReimbursementInterface } from "../interfaces/ReimbursementInterface";
import { UserInterface } from "../interfaces/UserInterface";

/* This is a rudimentary implementation of a store, which is basically global data storage
any data that you want to store globally (visible to the entire app) can reside here
look into the context APi to see a more industry standard way of doing this (we'll talk later) */
export const state:any = {

    //we typically want to store user session info on the front end
    //for personalization as well as role-based security control
    // this part should match with IncomingDTO interface field.
    userSessionData: {
        userId:0,
        username:"",
        firstName:"",
        lastName:"",
        role:"",  //<- This would be used to determine if a user can do certain things
    } as UserInterface,


    //Think about your requirements when storing state globally
    //you only NEED to globally store data you intend to use in multiple components
    //but you could optimize you code by using global storage to reduce calls to your API

    //we could also store things like base URLs (which I won't use)
    baseUrl:"http://localhost:8080",
    baseReimbursementUrl:"http://localhost:8080/reimbursements"

    //TODO: store our incoming JWT 

}