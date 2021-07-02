import React, {useState} from "react";
import { useHistory, useParams } from "react-router";
import {Col, Container, FormControl, Row} from "react-bootstrap";
import { OrderService } from "../services/OrderService";
import LoginComponent from "../components/LoginComponent";
import LeaveFeedbackComponent from "../components/LeaveFeedbackComponent";

const LeaveFeedback = () => {

    const [orderFeedback, setOrderFeedback] = useState({
        comment: "",
        rating: 1,
        anonymusComment: true
    });

    const history = useHistory();

    const { id } = useParams();

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setOrderFeedback({...orderFeedback, [name]: val});
    }

    const handleSelectInputChange = (name) => (event) => {
        const val = parseInt(event.target.value);
        setOrderFeedback({...orderFeedback, [name]: val});
    }

    const handleCheckedInputChange = (name) => (event) => {
        const val = event.target.checked;
        setOrderFeedback({...orderFeedback, [name]: val});
    }

    async function leaveOrderFeedback() {
        try {
            await OrderService.buyerFeedback(id, orderFeedback);

            console.log("Feedback sent!");
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    }

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2}} style={{ textAlign: "center" }}>
                    <h1>Order Feedback</h1>
                    <LeaveFeedbackComponent
                        orderFeedback={orderFeedback}
                        handleFormInputChange={handleFormInputChange}
                        leaveOrderFeedback={leaveOrderFeedback}
                        handleCheckedInputState={handleCheckedInputChange}
                        handleSelectInputState={handleSelectInputChange}
                        // showAlert={showAlert}
                        // setShowAlert={setShowAlert}
                    />
                </Col>
            </Row>
        </Container>
    );
}

export default LeaveFeedback;