import React, {useState} from "react";
import { useHistory } from "react-router";
import { Col, Container, Row } from "react-bootstrap";
import { AuthenticationService } from "../services/AuthenticationService";
import LoginComponent from "../components/LoginComponent";

const Login = () => {
    const [credentials, setCredentials] = useState({
        username: "",
        password: ""
    });

    const [showAlert, setShowAlert] = useState({ success: null, message: "" });

    const history = useHistory();

    async function login() {
        try {
            await AuthenticationService.login(credentials);

            setCredentials({
                username: "",
                password: ""
            });

            //Message for successful registration
            setShowAlert({ success: true, message: "Login successful!" });

        } catch (error) {
            //Message for the developer.
            console.error(`Error: ${error}`);

            //Message for the user.
            setShowAlert({
                success: false,
                message: "Error during login!",
            });
        }
    }

    async function register() {
        history.push("/register");
    }

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        //TODO: Ovde izmeniti tako da se menja state o Selleru ili Buyeru u zavisnosti od selektovanog.
        setCredentials({...credentials, [name]: val });
    };

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2}} style={{ textAlign: "center" }}>
                    <h1>Login</h1>
                    <LoginComponent
                        addInputChangeHandler={handleFormInputChange}
                        login={login}
                        register={register}
                        credentials={credentials}
                        showAlert={showAlert}
                        setShowAlert={setShowAlert}
                    />
                </Col>
            </Row>
        </Container>
    );
};

export default Login;