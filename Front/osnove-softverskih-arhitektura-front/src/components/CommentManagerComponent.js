import React from "react";
import {Button, ButtonGroup, Form, Table} from 'react-bootstrap';

const CommentManagerComponent = (props) => (
    <>
        <Table striped bordered hover style={{marginTop: 50, marginBottom: 50}}>
            <thead className="thead-dark">
                <tr>
                    <th>Order ID</th>
                    <th>Comment</th>
                    <th>Buyer Info</th>
                    <th>Visibility</th>
                </tr>
            </thead>
            <tbody>
                {props.orders.map((order) => {
                    return(
                        <tr key={order.orderId}>
                            <td>{order.orderId}</td>
                            <td>{order.comment}</td>
                            {/*<td>{props.buyers.length}</td>*/}
                            {typeof props.buyers.find(buyer => buyer.userId == order.buyer) !== 'undefined' && <td>{props.buyers.find(buyer => buyer.userId == order.buyer).username}</td>}
                            <td>
                                <Form.Group id="formGridCheckbox">
                                    <Form.Check name="anonymusComment" type="checkbox" label="Show Comment" onChange={props.handleCheckedInputState({orderId:order.orderId, name:"nan"})} checked={order.archivedComment} />
                                </Form.Group>
                            </td>
                        </tr>
                    );
                })}
            </tbody>

        </Table>
    </>
);

export default CommentManagerComponent;