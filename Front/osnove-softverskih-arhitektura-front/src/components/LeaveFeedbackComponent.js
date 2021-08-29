import React from "react";
import {Form, FormControl, FormGroup, FormLabel, FormCheck, Button, ButtonGroup, Table } from 'react-bootstrap';

const LeaveFeedbackComponent = (props) => (
    <>
        <Form>
            <FormGroup>
                <FormLabel>Comment</FormLabel>
                <FormControl
                    onChange={props.handleFormInputChange("comment")}
                    required
                    name="comment"
                    value={props.orderFeedback.comment}
                    as="textarea"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Rating</FormLabel>
                <FormControl
                    onChange={props.handleSelectInputState("rating")}
                    required
                    name="rating"
                    value={props.orderFeedback.rating}
                    as="select"
                >
                    <option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                </FormControl>
            </FormGroup>
            <Form.Group id="formGridCheckbox">
                <Form.Check name="anonymusComment" type="checkbox" label="Anonymus Comment" onChange={props.handleCheckedInputState("anonymusComment")} checked={props.orderFeedback.anonymusComment} />
            </Form.Group>
            <Button
                variant="primary"
                onClick={props.leaveOrderFeedback}
            >
                Send Feedback
            </Button>
        </Form>
    </>
);


export default LeaveFeedbackComponent;