import {Alert, Form, Button, FormControl, FormGroup, FormLabel} from "react-bootstrap";

const SellerRegistration = (props) => (
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
                    value={props.seller.firstName}
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Last Name</FormLabel>
                <FormControl
                    onChange={props.addInputChangeHandler("lastName")}
                    required
                    name="lastName"
                    value={props.seller.lastName}
                    as="input"
                />
                <FormGroup>
                    <FormLabel>Username</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("username")}
                        required
                        name="username"
                        value={props.seller.username}
                        as="input"
                    />
                </FormGroup>
                <FormGroup>
                    <FormLabel>Password</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("password")}
                        required
                        name="password"
                        value={props.seller.password}
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
                        value={props.seller.repeatedPassword}
                        as="input"
                        type="password"
                    />
                </FormGroup>
                <FormGroup>
                    <FormLabel>Email</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("email")}
                        required
                        name="email"
                        value={props.seller.email}
                        as="input"
                    />
                </FormGroup>
                <FormGroup>
                    <FormLabel>Address</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("address")}
                        required
                        name="address"
                        value={props.seller.address}
                        as="input"
                    />
                </FormGroup>
                <FormGroup>
                    <FormLabel>Name</FormLabel>
                    <FormControl
                        onChange={props.addInputChangeHandler("name")}
                        required
                        name="name"
                        value={props.seller.name}
                        as="input"
                    />
                </FormGroup>
                <Button
                    variant="primary"
                    onClick={props.registerSeller}
                >
                    Register Seller
                </Button>
            </FormGroup>
        </Form>
    </>
);

export default SellerRegistration;