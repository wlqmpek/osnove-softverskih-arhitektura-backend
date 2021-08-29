import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import { Col, Container, Row, Modal, Button } from "react-bootstrap";
import { DiscountService } from "../services/DiscountService";
import ListSellersDiscountsComponent from "../components/ListSellersDicountsComponent";

const ListSellersDiscounts = () => {

    const [discounts, setDiscounts] = useState([]);

    const history = useHistory();

    useEffect(() => {
        getSellersDiscounts();
    }, []);

    const { id } = useParams();

    async function getSellersDiscounts() {
        try {
            const response = await DiscountService.getSellersDiscounts(id);
            setDiscounts(response.data);
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    }

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Discounts</h1>
                    <ListSellersDiscountsComponent
                        discounts = {discounts}
                    >
                    </ListSellersDiscountsComponent>
                </Col>
            </Row>
        </Container>
    );

}

export default ListSellersDiscounts;