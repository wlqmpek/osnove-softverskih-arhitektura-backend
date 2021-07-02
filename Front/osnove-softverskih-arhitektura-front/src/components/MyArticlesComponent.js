import React from "react";
import { Button, ButtonGroup, Table } from 'react-bootstrap';

const MyArticlesComponent = (props) => (
    <>
        <Table striped bordered hover style={{marginTop: 50, marginBottom: 50}}>
            <thead className="thead-dark">
            <tr>
                <th>Article ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>On Sale</th>
                <th>Description</th>
                <th>Edit Article</th>
                <th>Delete Article</th>
            </tr>
            </thead>
            <tbody>
            {props.articles.map((article) =>{
                return(
                    <tr key={article.articleId}>
                        <td>{article.articleId}</td>
                        <td>{article.name}</td>
                        <td>{article.price}</td>
                        <td>
                            <div>
                                <p>{article.onSale ? 'true' : 'false'}</p>
                            </div>
                        </td>
                        <td>
                            <p>{article.description}</p>
                        </td>
                        <td>
                            <Button
                                className="btn btn-info"
                                block
                                onClick={() => props.editArticlePage(article.articleId)}
                            >
                                Edit Article
                            </Button>
                        </td>
                        <td>
                            <Button
                                className="btn btn-danger"
                                block
                                onClick={() => props.deleteArticle(article.articleId)}
                            >
                                Delete Article
                            </Button>
                        </td>
                    </tr>
                );
            })}
            </tbody>
        </Table>
    </>
);

export default MyArticlesComponent;