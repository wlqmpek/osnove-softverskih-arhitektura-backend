import React from "react";
import {Button, ButtonGroup, Form, FormControl, FormGroup, FormLabel, Table} from 'react-bootstrap';
import DatePicker from "react-datepicker";

const CreateDiscountComponent = (props) => (
    <>
        <Form>
            <FormGroup>
                <FormLabel>Percentage</FormLabel>
                <FormControl
                    onChange={props.handleFormInputChange("percentage")}
                    required
                    name="percentage"
                    value={props.discount.percentage}
                    type="number"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Duration</FormLabel>
                <DatePicker placeholderText="Select date range!"
                            startDate={props.discount.startDate}
                            endDate={props.discount.endDate}
                            selected={props.discount.startDate}
                            onChange={(dates) => props.setDate(dates)}
                            selectsRange
                            inline
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Comment</FormLabel>
                <FormControl
                    onChange={props.handleFormInputChange("text")}
                    required
                    name="text"
                    value={props.discount.text}
                />
            </FormGroup>
        </Form>
        <Table striped bordered hover style={{marginTop: 50, marginBottom: 50}}>
            <thead className="thead-dark">
                <tr>
                    <th>Article ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Alredy On Sale</th>
                    <th>Description</th>
                    <th>Add to Sale</th>
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
                                <Form.Group id="formGridCheckbox">
                                    <Form.Check name="anonymusComment" type="checkbox" label="Add To Sale" onChange={props.handleCheckInputChange(article.articleId)} checked={props.discount.articles.includes(article.articleId)} />
                                </Form.Group>
                            </td>
                        </tr>
                    );
                })}
            </tbody>
        </Table>
        <Button
            variant="primary"
            onClick={props.createDiscount}
        >
            Send Feedback
        </Button>
    </>
);

export default CreateDiscountComponent;