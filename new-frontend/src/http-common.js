import axios from "axios";
import router from "@/router/router";

const http = axios.create({
    baseURL: "http://localhost:8816/",
    headers: {
        "Content-type": "application/json"
    }
});

// Add a request interceptor
http.interceptors.request.use(async config => {
    let token = localStorage.getItem('jwt-token');


    if (token) {
        config.headers['Authorization'] = 'Bearer ' + token;
    }

    return config;
}, error => {
    return Promise.reject(error);
});

http.interceptors.response.use(
    response => response, // Simply return the response if it's successful
    error => {
        if (error.response && error.response.status === 401) { // Adjust this condition based on your API's error codes
            console.log("i am here 1");
            localStorage.removeItem('isUserLoggedIn');
            router.push({ name: 'LoginPage' });
        }
        return Promise.reject(error);
    }
);

export default http;