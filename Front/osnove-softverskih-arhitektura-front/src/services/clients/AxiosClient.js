import axios from "axios";
import { TokenService } from "../TokenService";
import { AuthenticationService } from "../AuthenticationService";

// API klijent se kreira ka specificnom endpoint-u, uz sve ono sto je uvek neophodno slati.
const AxiosClient = axios.create({
    baseURL: `${process.env.REACT_APP_OSA_BACKEND_SERVER}`,
});

// Dodaj token na svaki zahtev ka backendu, ako je korisnik ulogovan.
AxiosClient.interceptors.request.use(function success(config) {
    const token = TokenService.getToken();
    console.log("Axious Client " + token);
    if(token) {
        if (TokenService.didTokenExpire()) {
            console.log("Token Expired.");
            // AuthenticationService.logout();
            return false;
        }
        config.headers["Authorization"] = "Bearer " + token;
    }
    return config;
}, error => {
    console.log(error)
    Promise.reject(error);
});

// U slučaju da se sa Sprints backenda vrati forbidden, token je istekao te izloguj korisnika
AxiosClient.interceptors.response.use(
    function success(response) {
        return response;
    },
    function failure(error) {
        const token = TokenService.getToken();
        if (token) {
            if (error.response && error.response.status === 403) {
                AuthenticationService.logout();
            }
        }
        throw error;
    }
);

export default AxiosClient;

