import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import { Col, Container, Row, Modal, Button } from "react-bootstrap";
import ListSellerArticlesComponent from "../components/ListSellerArticlesComponent";
import { AuthenticationService } from "../services/AuthenticationService";
import { ArticleService } from "../services/ArticleService";
import { OrderService } from "../services/OrderService";

const ListSellerArticles = () => {
    const [articles, setArticles] = useState([]);

    const [articleQuantity, setArticleQuantity] = useState([]);

    const [show, setShow] = useState(false);

    // useHistory Hook koji vraća history objekat iz React Router-a
    // Koristi se kako bi se izvršilo prebacivanja sa stranice na stranicu - a da to ostane u istorijatu
    const history = useHistory();

    useEffect(() => {
        getSellerArticles();
    }, []);

    // useParams Hook iz React Routera - preko nje je moguće dobiti paramet koji se zahteva na ruti, poput ID-ja
    const { id } = useParams();

    async function getSellerArticles() {
        try {
            const response = await ArticleService.getArticlesFromSeller(id);
            setArticles(response.data);
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    };

    const goToArticlePage = (articleId) => {
        console.log("Hey " +articleId);
        history.push("/articles/seller/" + articleId);
    };

    const handleInputChange = (article) => (event) => {
        const val = event.target.value;
        const newArticleQuantity = articleQuantity.filter((element) => element.article.articleId !== article.articleId);
        newArticleQuantity.push({article: article, quantity:val});
        setArticleQuantity(newArticleQuantity);
        console.log(articleQuantity);
    }

    function total() {
        var total = 0;
        articleQuantity.forEach(element => {
            if(element.quantity !== 0) {
                var art = articles.find(article => {
                    return article.articleId === element.article.articleId;
                })
                total += parseInt(element.quantity * art.price);
            }
        });
        return total;
    }

    async function placeOrder() {
        const order = articleQuantity.filter(element => element.quantity > 0);
        console.log(order);
        const objekat = {articleQuantity:order.map(({article, quantity}) => ({articleId:article.articleId, quantity:quantity}))};
        console.log(objekat);
        try {
            await OrderService.createOrder(objekat);
            history.push("");
        } catch (error) {
            console.error(`Error: ${error}`);
        }
    }

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Articles</h1>
                    <ListSellerArticlesComponent
                        articles = {articles}
                        goToArticlePage={goToArticlePage}
                        handleInputChange={handleInputChange}
                    >
                    </ListSellerArticlesComponent>
                </Col>
            </Row>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <Button variant="primary" onClick={handleShow}>
                        Finish Shoping
                    </Button>
                </Col>
            </Row>

            <Modal
                show={show}
                onHide={handleClose}
                backdrop="static"
                keyboard={false}
            >
                <Modal.Header closeButton>
                    <Modal.Title>Modal title</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {
                        articleQuantity.filter(article => article.quantity > 0).map((aq) => {
                            return(<p key={aq.article.articleId}>{aq.article.name}  &nbsp;  &nbsp; => &nbsp; &nbsp;{aq.article.price} * {aq.quantity} = {aq.article.price * aq.quantity}</p>);
                        })
                    }
                </Modal.Body>
                <Modal.Footer>
                    <p>Total value = {total()}</p>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={placeOrder}>Order!</Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
}

export default ListSellerArticles;