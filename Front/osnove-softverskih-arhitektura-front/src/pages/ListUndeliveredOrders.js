import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import { Col, Container, Row } from "react-bootstrap";
import ListUndeliveredOrdersComponent from "../components/ListUndeliveredOrdersComponent";
import { OrderService } from "../services/OrderService";
// import {render} from "react-dom";
// import ListSellerArticlesComponent from "../components/ListSellerArticlesComponent";

const ListUndeliveredOrders = () => {
    const [orders, setOrders] = useState([]);

    const history = useHistory();

    useEffect(() => {
        getOrders();
    }, []);

    async function getOrders() {
        try {
            const response = await OrderService.getUndeliveredOrders();
            setOrders(response.data);
        } catch (error) {
        console.error(`Error: ${error}`);
        }
    };

    async function confirmOrderDelivered(orderId) {
        try {
            const response = await OrderService.setDelivered(orderId);
            setOrders(orders.filter((order) => order.orderId !== orderId));
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    }

    const goToOrderPage = (id) => {
        history.push("/orders/" + id);
    };

    return (
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Undelivered Orders</h1>
                    <ListUndeliveredOrdersComponent
                        orders = {orders}
                        confirmOrderDelivered={confirmOrderDelivered}
                        goToOrderPage={goToOrderPage}
                    >
                    </ListUndeliveredOrdersComponent>
                </Col>
            </Row>
        </Container>
    );
}

export default ListUndeliveredOrders;