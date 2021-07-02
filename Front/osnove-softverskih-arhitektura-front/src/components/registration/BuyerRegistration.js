import {Alert, Form, Button, FormControl, FormGroup, FormLabel} from "react-bootstrap";

const BuyerRegistration = (props) => (
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
                <FormLabel>First Name</FormLabel>
                <FormControl
                    onChange={props.addInputChangeHandler("firstName")}
                    required
                    name="firstName"
                    value={props.buyer.firstName}
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Last Name</FormLabel>
                <FormControl
                    onChange={props.addInputChangeHandler("lastName")}
                    required
                    name="lastName"
                    value={props.buyer.lastName}
                    as="input"
                />
                <FormGroup>
                    <FormLabel>Username</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("username")}
                        required
                        name="username"
                        value={props.buyer.username}
                        as="input"
                    />
                </FormGroup>
                <FormGroup>
                    <FormLabel>Password</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("password")}
                        required
                        name="password"
                        value={props.buyer.password}
                        as="input"
                        type="password"
                    />
                </FormGroup>
                <FormGroup>
                    <FormLabel>R.Password</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("repeatedPassword")}
                        required
                        name="repeatedPassword"
                        value={props.buyer.repeatedPassword}
                        as="input"
                        type="password"
                    />
                </FormGroup>
                <FormGroup>
                    <FormLabel>Address</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("address")}
                        required
                        name="address"
                        value={props.buyer.address}
                        as="input"
                    />
                </FormGroup>
                <Button
                    variant="primary"
                    onClick={props.registerBuyer}
                >
                    Register Buyer
                </Button>
            </FormGroup>
        </Form>
    </>
);

export default BuyerRegistration;