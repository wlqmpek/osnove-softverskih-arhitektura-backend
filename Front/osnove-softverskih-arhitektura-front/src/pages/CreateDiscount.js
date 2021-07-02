import { ArticleService } from "../services/ArticleService";
import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import { Col, Container, Row, Modal, Button } from "react-bootstrap";
import CreateDiscountComponent from "../components/CreateDiscountComponent";
import { AuthenticationService } from "../services/AuthenticationService";
import MyArticlesComponent from "../components/MyArticlesComponent";
import {DiscountService} from "../services/DiscountService";

const CreateDiscount = () => {
    const [articles, setArticles] = useState([]);

    const [discount, setDiscount] = useState({
        percentage: 1,
        startDate: new Date(),
        endDate: new Date(),
        text: "",
        articles: []
    });

    const history = useHistory();

    useEffect(() => {
        getArticles();
    }, []);

    useEffect(() => {
        console.log(discount)
    }, [discount]);


    async function getArticles() {
        try {
            const response = await ArticleService.getArticlesFromSeller(AuthenticationService.getUserId());
            setArticles(response.data);
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    };

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value;
        setDiscount({...discount, [name]: val});
    }

    const handleCheckedInputChange = (id) => (event) => {
        const val = event.target.checked;
        console.log(val);
        console.log(id)
        if (val) {
            const art = discount.articles.filter(a => a !== id);
            art.push(id);
            setDiscount({...discount, articles: art});
        } else {
            const art = discount.articles.filter(a => a !== id);
            setDiscount({...discount, articles: art});
        }
    }

    const setDate = (dates) => {
        const [startDate, endDate] = dates;
        setDiscount({...discount, startDate: startDate, endDate: endDate});
    }

    async function createDiscount() {
        try {
            await DiscountService.createDiscount(discount);

            console.log("Feedback sent!");
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    }

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Create Discount</h1>
                    <CreateDiscountComponent
                        articles = {articles}
                        discount = {discount}
                        handleFormInputChange={handleFormInputChange}
                        handleCheckInputChange={handleCheckedInputChange}
                        setDate={setDate}
                        createDiscount={createDiscount}
                    >
                    </CreateDiscountComponent>
                </Col>
            </Row>
        </Container>
    );
}

export default CreateDiscount;