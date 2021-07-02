import { ArticleService } from "../services/ArticleService";
import React, { useEffect, useState } from "react";
import { useHistory } from "react-router";
import { Col, Container, Row, Modal, Button } from "react-bootstrap";
import MyArticlesComponent from "../components/MyArticlesComponent";
import { AuthenticationService } from "../services/AuthenticationService";

const MyArticles = () => {
    const [articles, setArticles] = useState([]);

    const history = useHistory();

    useEffect(() => {

        getArticles();
    }, []);

    async function getArticles() {
        try {
            const response = await ArticleService.getArticlesFromSeller(AuthenticationService.getUserId());
            setArticles(response.data);
        } catch (error) {
            console.log(`Error: ${error}`);
        }
    };

    const editArticlePage = (articleId) => {
        console.log("Hey " +articleId);
        history.push("/articles/edit/" + articleId);
    };

    const deleteArticle = async (articleId) => {
        //TODO: Implement this!
        try {
            await ArticleService.deleteArticle(articleId);
            setArticles(articles.filter((article) => article.articleId !== articleId));
        } catch (error) {
            alert(`Error ${error}`);
        }
    };

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2 }} style={{ textAlign: "center" }}>
                    <h1>Articles</h1>
                    <MyArticlesComponent
                        articles = {articles}
                        editArticlePage={editArticlePage}
                        deleteArticle={deleteArticle}
                    >
                    </MyArticlesComponent>
                </Col>
            </Row>
        </Container>
    );
}

export default MyArticles;

