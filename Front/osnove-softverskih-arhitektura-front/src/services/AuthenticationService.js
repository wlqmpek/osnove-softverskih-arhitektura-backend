import SprintsAxiosClient from "./clients/AxiosClient";
import { TokenService } from "./TokenService";

export const AuthenticationService = {
    login,
    logout,
    getRole,
    getUserId
};

async function login(userCredentials) {
    TokenService.removeToken();
    try {
        const response = await SprintsAxiosClient.post(
            "auth/login",
            userCredentials
        );
        const decoded_token = TokenService.decodeToken(response.data.jwt);
        console.log("Decoded " +decoded_token)
        if (decoded_token) {
            TokenService.setToken(response.data.jwt);
            window.location.assign("/");
        } else {
            console.error("Invalid token");
        }
    } catch (error) {
        console.error(error);
        throw error;
    }
}

function logout() {
    TokenService.removeToken();
    window.location.assign("/");
}

function getRole() {
    const token = TokenService.getToken();
    const decoded_token = token ? TokenService.decodeToken(token) : null;
    if (decoded_token) {
        console.log("Role " + decoded_token.roles[0].authority)
        return decoded_token.roles[0].authority;
    } else {
        return null;
    }
}

function getUserId() {
    const token = TokenService.getToken();
    const decoded_token = token ? TokenService.decodeToken(token) : null;
    if (decoded_token) {
        return decoded_token.userId;
    } else {
        return null;
    }
}
