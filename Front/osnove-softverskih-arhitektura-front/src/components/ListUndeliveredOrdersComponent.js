import React from "react";
import { Button, ButtonGroup, Table } from 'react-bootstrap';
// import ListSellerArticlesComponent from "./ListSellerArticlesComponent";

const ListUndeliveredOrdersComponent = (props) => (
    <>
        <Table striped bordered hover style={{marginTop: 50, marginBottom: 50}}>
            <thead className="thead-dark">
            <tr>
                <th>Order Id</th>
                <th>Time</th>
                <th>More Info</th>
                <th>Mark Delivered</th>
            </tr>
            </thead>
            <tbody>
            {props.orders.map((order) =>{
                return(
                    <tr key={order.orderId}>
                        <td>{order.orderId}</td>
                        <td>{order.time}</td>
                        <td>
                            <Button
                                className="btn btn-success"
                                block
                                onClick={() => props.goToOrderPage(order.orderId)}
                            >
                                More Details
                            </Button>
                        </td>
                        <td>
                            <Button
                                className="btn btn-danger"
                                block
                                onClick={() => props.confirmOrderDelivered(order.orderId)}
                            >
                                Mark Delivered
                            </Button>
                        </td>
                    </tr>
                );
            })}
            </tbody>
        </Table>
    </>
);
export default ListUndeliveredOrdersComponent;