import React, { useEffect, useState } from "react";
import { useHistory, useParams } from "react-router";
import { Col, Container, Row, Modal, Button } from "react-bootstrap";
import ArticleDetailsComponent from "../components/ArticleDetailsComponent";
import { AuthenticationService } from "../services/AuthenticationService";
import { ArticleService } from "../services/ArticleService";
import ListSellerArticlesComponent from "../components/ListSellerArticlesComponent";

const ArticleDetails = () => {
    const [article, setArticle] = useState({});

    useEffect(() => {
        getArticle();
    }, []);


    const { id } = useParams();

    async function getArticle() {
        try {
            const response = await ArticleService.getArticle(id);
            setArticle(response.data);
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    };

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Article</h1>
                    <ArticleDetailsComponent
                        article={article}
                    />
                </Col>
            </Row>
        </Container>
    );
}

export default ArticleDetails;