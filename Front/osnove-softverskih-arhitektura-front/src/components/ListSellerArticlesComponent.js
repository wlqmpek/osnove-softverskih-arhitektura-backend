import React from "react";
import { Button, ButtonGroup, Table } from 'react-bootstrap';
import { AuthenticationService } from "../services/AuthenticationService";
import ListSellersComponent from "./ListSellersComponent";

const ListSellerArticlesComponent = (props) => (
    <>
        <Table striped bordered hover style={{marginTop: 50, marginBottom: 50}}>
            <thead className="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>On Sale</th>
                    <th>Amount</th>
                </tr>
            </thead>
            <tbody>
                {props.articles.map((article) =>{
                    return(
                        <tr key={article.articleId}>
                            <td>{article.name}</td>
                            <td>{article.price}</td>
                            <td>
                                <div>
                                    <p>{article.onSale ? 'true' : 'false'}</p>
                                </div>
                            </td>
                            <td>
                                <input type="number" onBlur={props.handleInputChange(article)} />
                            </td>
                            <td>
                                <Button
                                    className="btn btn-danger"
                                    block
                                    onClick={() => props.goToArticlePage(article.seller)}
                                >
                                    More Details
                                </Button>
                            </td>
                        </tr>
                    );
                })}
            </tbody>
        </Table>
    </>
);

export default ListSellerArticlesComponent;