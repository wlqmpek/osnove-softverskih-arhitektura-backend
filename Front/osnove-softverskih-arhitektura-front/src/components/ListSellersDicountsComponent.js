import React from "react";
import { Button, ButtonGroup, Table } from 'react-bootstrap';

const ListSellersDicountsComponent = (props) => (
    <>
        <Table striped bordered hover style={{marginTop: 50, marginBottom: 50}}>
            <thead className="thead-dark">
                <tr>
                    <th>Text</th>
                    <th>Percentage</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Articles</th>
                </tr>
            </thead>
            <tbody>
                {props.discounts.filter(discount => new Date(discount.endDate) > new Date()).map((discount) =>{
                    return(
                        <tr key={discount.discountId}>
                            <td>{discount.text}</td>
                            <td>{discount.percentage}</td>
                            <td>{discount.startDate}</td>
                            <td>{discount.endDate}</td>
                            <td>
                                <ul>
                                    {discount.articles.map((article) => {
                                        return(
                                            <li key={article.articleId}>{article.name}</li>
                                        );
                                    })}
                                </ul>
                            </td>
                        </tr>
                    );
                })}
            </tbody>
        </Table>
    </>
)

export default ListSellersDicountsComponent;