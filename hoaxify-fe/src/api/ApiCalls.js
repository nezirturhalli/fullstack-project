import axios from "axios";
export const signUp  = (body) =>{
    return  axios.post('/users', body);
}