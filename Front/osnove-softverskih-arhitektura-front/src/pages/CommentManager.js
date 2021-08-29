import React, {useEffect, useState} from "react";
import { useHistory, useParams } from "react-router";
import {Col, Container, FormControl, Row} from "react-bootstrap";
import { OrderService } from "../services/OrderService";
import { BuyerService } from "../services/BuyerService";
import CommentManagerComponent from "../components/CommentManagerComponent";
import ListSellerArticlesComponent from "../components/ListSellerArticlesComponent";

const CommentManager = () => {

    const [orders, setOrders] = useState([]);
    const [buyers, setBuyers] = useState([]);

    const history = useHistory();

    //First useEffect used only for fetching Orders!
    useEffect(() => {
        getOrders();
    }, []);

    //Second useEffect used for when state "orders" is updated.
    //This is the example of running two useEffect!
    useEffect(() => {
        if(orders.length > 0) {
            getBuyers();
        }
    }, [orders]);


    async function getOrders() {
        try {
            const response = await OrderService.getDeliveredAndCommented();
            setOrders(response.data);
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    }

    //Iterate over each order and get the buyer.
    async function getBuyers() {
        console.log("Orders size " + orders.length);
        try {
            orders.forEach(async (order) => {
                const response = await BuyerService.getBuyer(order.buyer);

                setBuyers(oldArray => [...oldArray, response.data]);
            });
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    }

    //I dont quite understand how this work
    //This is example of passing event + params
    //from the child to the parent and also being async
    const setHiddenComment = (param) => async (event) => {
        const val = event.target.checked;
        console.log("Value " + val);
        console.log(param);

        try {
            await OrderService.setArchiveComment(param.orderId, {archivedComment: val});
            getOrders();
            console.log("Feedback sent!");
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    }


    console.log(buyers)

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Nataly's Comment Manager</h1>
                    <CommentManagerComponent
                        orders = {orders}
                        buyers={buyers}
                        handleCheckedInputState={setHiddenComment}
                    >
                    </CommentManagerComponent>
                </Col>
            </Row>
        </Container>
    );
}

export default CommentManager;
