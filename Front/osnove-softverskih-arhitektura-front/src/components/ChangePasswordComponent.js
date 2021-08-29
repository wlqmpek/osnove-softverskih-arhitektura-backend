import {Alert, Form, Button, FormControl, FormGroup, FormLabel} from "react-bootstrap";

const ChangePasswordComponent = (props) => (
    <>
        <Form>
            <FormGroup>
                <FormLabel>Current Password</FormLabel>
                <FormControl
                    onChange={props.handleInputChange("currentPassword")}
                    required
                    // name="currentPassword"
                    value={props.changePasswordObject.currentPassword}
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>New Password</FormLabel>
                <FormControl
                    onChange={props.handleInputChange("newPassword")}
                    required
                    // name="currentPassword"
                    value={props.changePasswordObject.newPassword}
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Repeated New Password</FormLabel>
                <FormControl
                    onChange={props.handleInputChange("repeatedNewPassword")}
                    required
                    // name="currentPassword"
                    value={props.changePasswordObject.repeatedNewPassword}
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <Button
                    variant="primary"
                    onClick={props.changePassword}
                >
                    Change Password
                </Button>
            </FormGroup>
        </Form>

    </>
);

export default ChangePasswordComponent;