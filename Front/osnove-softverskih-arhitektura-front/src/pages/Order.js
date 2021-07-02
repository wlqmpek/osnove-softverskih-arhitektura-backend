import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import { Col, Container, Row, Modal, Button } from "react-bootstrap";
import { OrderService } from "../services/OrderService";
import { ArticleService } from "../services/ArticleService";
import OrderComponent from "../components/OrderComponent";

const Order = () => {
    const [order, setOrder] = useState({});
    const [articles, setArticles] = useState([]);

    const history = useHistory();

    const { id } = useParams();

    useEffect(() => {
        getOrder();
        getArticles();
    }, []);

    async function getOrder() {
        try {
            const response = await OrderService.getOrder(id);
            setOrder(response.data);
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    };

    async function getArticles() {
        try {
            const response = await ArticleService.getArticlesFromOrder(id);
            setArticles(response.data);
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    }

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Order</h1>
                    <OrderComponent
                        order={order}
                        articles={articles}
                    >

                    </OrderComponent>
                </Col>
            </Row>
        </Container>
    );
}

export default Order;