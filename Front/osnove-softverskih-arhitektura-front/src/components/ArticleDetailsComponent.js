import React from "react";
import {Button, ButtonGroup, Form, FormGroup, FormLabel, Table, Col, Row, Container} from 'react-bootstrap';
import Image from 'react-bootstrap/Image';

const ArticleDetailsComponent = (props) => (
    <>
        <Table striped bordered hover style={{marginTop: 50, marginBottom: 50}}>
            <thead className="thead-dark">
            <tr>
                <th>Article ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>On Sale</th>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td>{props.article.articleId}</td>
                    <td>{props.article.name}</td>
                    <td>{props.article.description}</td>
                    <td>{props.article.price}</td>
                    <td>{props.article.onSale ? 'true' : 'false'}</td>
                </tr>
            </tbody>
        </Table>
        <Container>
            <Row>
                <Col>
                    <Image
                        height="500"
                        width="500"
                        src={props.article.imagePath}
                    />
                </Col>
            </Row>
        </Container>


    </>
);

export default ArticleDetailsComponent;