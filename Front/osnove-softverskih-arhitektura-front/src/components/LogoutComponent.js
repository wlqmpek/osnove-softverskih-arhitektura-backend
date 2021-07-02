import React, {useEffect} from "react";
import { useHistory } from "react-router";
import { AuthenticationService } from "../services/AuthenticationService";

const Logout = () => {

    const history = useHistory();

    useEffect(() => {
        AuthenticationService.logout();
        history.push("/");
    }, []);

    return(
        <>
        </>
    );

}

export default Logout;