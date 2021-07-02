import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import { Col, Container, Row } from "react-bootstrap";
import ListSellersComponent from "../components/ListSellersComponent";
import { AuthenticationService } from "../services/AuthenticationService";
import { SellerService } from "../services/SellerService";

const ListSellers = () => {

    const [sellers, setSellers] = useState([]);

    // useHistory Hook koji vraća history objekat iz React Router-a
    // Koristi se kako bi se izvršilo prebacivanja sa stranice na stranicu - a da to ostane u istorijatu
    const history = useHistory();


    useEffect(() => {
        getSellers();
    }, []);

    async function getSellers() {
        try {
            const response = await SellerService.getSellers();
            setSellers(response.data);
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    };

    const goToSellerPage = (id) => {
        history.push("/sellers/" + id);
    };

    const goToSellerDiscounts = (id) => {
        history.push("/sellers/discounts" + id);
    };

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Sellers</h1>
                    <ListSellersComponent
                        sellers = {sellers}
                        goToSellerPage = {goToSellerPage}
                        goToSellerDiscounts = {goToSellerDiscounts}
                    ></ListSellersComponent>
                </Col>
            </Row>
        </Container>
    );
}

export default ListSellers;