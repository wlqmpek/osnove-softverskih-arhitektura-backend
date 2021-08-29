import React from "react";
import {Button, ButtonGroup, Form, FormControl, FormGroup, FormLabel, Table} from 'react-bootstrap';

const CreateArticleComponent = (props) => (
    <>
        <Form>
            <FormGroup>
                <FormLabel>Name</FormLabel>
                <FormControl
                    onChange={props.handleFormInputChange("name")}
                    required
                    value={props.article.name}
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Description</FormLabel>
                <FormControl
                    onChange={props.handleFormInputChange("description")}
                    required
                    value={props.article.description}
                    as="textarea"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Price</FormLabel>
                <FormControl
                    onChange={props.handleFormInputChange("price")}
                    required
                    value={props.article.price}
                    type="number"
                    as="input"
                />
            </FormGroup>
            <FormGroup>
                <FormLabel>Image</FormLabel>
                <FormControl
                    onChange={e => props.setImage(e.target.files[0])}
                    required
                    type="file"
                    accept=".png, .jpg"
                    // as="input"
                />
            </FormGroup>
            <FormGroup>
                <Button
                    variant="primary"
                    onClick={props.submit}
                >
                    Create Article
                </Button>
            </FormGroup>
        </Form>
    </>
);

export default CreateArticleComponent;