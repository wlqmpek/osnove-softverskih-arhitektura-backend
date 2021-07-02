import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import { Col, Container, Row } from "react-bootstrap";
import ListDeliveredOrdersComponent from "../components/ListDeliveredOrdersComponent";
import { OrderService } from "../services/OrderService";



const ListDeliveredOrders = () => {
    const [orders, setOrders] = useState([]);

    const history = useHistory();

    useEffect(() => {
        getOrders();
    }, []);

    async function getOrders() {
        try {
            const response = await OrderService.getDeliveredOrders();
            setOrders(response.data);
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    };

    const leaveFeedback = (id) => {
        history.push("/order_feedback/" + id);
    };

    const goToOrderPage = (id) => {
        history.push("/orders/" + id);
    };

    console.log(orders)


    return (
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Delivered Orders</h1>
                    <ListDeliveredOrdersComponent
                        orders = {orders}
                        goToOrderPage={goToOrderPage}
                        leaveFeedback={leaveFeedback}
                    >
                    </ListDeliveredOrdersComponent>
                </Col>
            </Row>
        </Container>
    );
}

export default ListDeliveredOrders;