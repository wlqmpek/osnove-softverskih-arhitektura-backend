import {Alert, Form, Button, FormControl, FormGroup, FormLabel} from "react-bootstrap";

const LoginComponent = (props) => (
    <>
        {props.showAlert.success && (
            <Alert variant="success" onClose={() => props.setShowAlert(null)} dismissible>
                {props.showAlert.message}
            </Alert>
        )}
        {props.showAlert.success === false && (
            <Alert
                variant="danger"
                onClose={() => props.setShowAlert(null)}
                dismissible
            >
                {props.showAlert.message}
            </Alert>
        )}
        <Form>
            <FormGroup>
                <FormLabel>Username</FormLabel>
                <FormControl
                    onChange={props.addInputChangeHandler("username")}
                    required
                    name="username"
                    value={props.credentials.username}
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Password</FormLabel>
                <FormControl
                    onChange={props.addInputChangeHandler("password")}
                    required
                    name="password"
                    value={props.credentials.password}
                    as="input"
                    type="password"
                />
            </FormGroup>
            <FormGroup>
                <Button
                    variant="primary"
                    onClick={props.login}
                >
                    Login
                </Button>
            </FormGroup>
            <FormGroup>
                <Button
                    variant="primary"
                    onClick={props.register}
                >
                    Register
                </Button>
            </FormGroup>
        </Form>
    </>
);

export default LoginComponent;