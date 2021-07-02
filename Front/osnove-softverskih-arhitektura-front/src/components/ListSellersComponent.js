import React from "react";
import { Button, ButtonGroup, Table } from "react-bootstrap";
import { AuthenticationService } from "../services/AuthenticationService";

const ListSellersComponent = (props) => (
    <>
        <Table bordered striped style={{marginTop: 5}}>
            <thead className="thead-dark">
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Name</th>
                    <th>Rating</th>
                    <th>Seller's Article</th>
                    <th>Seller's Discounts</th>
                </tr>
            </thead>
            <tbody>
                {props.sellers.map((seller) => {
                    return(
                        <tr key={seller.id}>
                            <td>{seller.firstName}</td>
                            <td>{seller.lastName}</td>
                            <td>{seller.name}</td>
                            <td>{seller.rating}</td>
                            <td>
                                <Button
                                    variant="info"
                                    block
                                    onClick={() => props.goToSellerPage(seller.id)}
                                >
                                    Seller's Articles
                                </Button>
                            </td>
                            <td>
                                <Button
                                    variant="info"
                                    block
                                    onClick={() => props.goToSellerDiscounts(seller.id)}
                                >
                                    Seller's Discounts
                                </Button>
                            </td>
                        </tr>
                    );
                })}
            </tbody>
        </Table>
    </>
);

export default ListSellersComponent;