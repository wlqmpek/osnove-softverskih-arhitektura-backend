import {Alert, Form, ListGroup} from "react-bootstrap";

const OrderComponent = (props) => (
    <>
        <ListGroup>
            {<ListGroup.Item>OrderId = {props.order.orderId}</ListGroup.Item>}
            {<ListGroup.Item>Time = {props.order.time}</ListGroup.Item>}
            {<ListGroup.Item>Rating = {props.order.rating}</ListGroup.Item>}
            {<ListGroup.Item>Comment = {props.order.comment}</ListGroup.Item>}
            <ListGroup.Item>
                <ListGroup.Item>Articles</ListGroup.Item>
                {console.log(props.order.articleQuantity)}
                {typeof props.order.articleQuantity !== 'undefined' &&
                    props.order.articleQuantity.map((aq) => {
                        return(
                            <ListGroup.Item key={aq.articleId}>
                                <ListGroup.Item>ArticleId = {aq.articleId}</ListGroup.Item>
                                {props.articles.length > 0 && <ListGroup.Item>Article Name = {props.articles.find(art => art.articleId === aq.articleId).name}</ListGroup.Item>}
                                <ListGroup.Item>Quantity = {aq.quantity}</ListGroup.Item>

                            </ListGroup.Item>
                        );
                    })
                }
            </ListGroup.Item>
        </ListGroup>
    </>
);

export default OrderComponent;