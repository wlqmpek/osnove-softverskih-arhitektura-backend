import React, {useState} from "react";
import { useHistory } from "react-router";
import {Col, Container, FormControl, FormGroup, FormLabel, Row} from "react-bootstrap";
import { AuthenticationService } from "../services/AuthenticationService";
import ChangePasswordComponent from "../components/ChangePasswordComponent";

const ChangePassword = () => {
    const [changePasswordObject, setChangePasswordObject] = useState({
        currentPassword: "",
        newPassword: "",
        repeatedNewPassword: ""
    });

    const history = useHistory();

    async function changePassword() {
        try {
            await AuthenticationService.changePassword(changePasswordObject);
            history.push("/logout");
        } catch (error) {
            //Message for the developer.
            console.error(`Error: ${error}`);
        }
    }

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setChangePasswordObject({...changePasswordObject, [name]: val });
    };

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2}} style={{ textAlign: "center" }}>
                    <h1>Change Password</h1>
                    <ChangePasswordComponent
                        changePasswordObject={changePasswordObject}
                        handleInputChange={handleFormInputChange}
                        changePassword={changePassword}
                    />
                </Col>
            </Row>
        </Container>
    );
}

export default ChangePassword;