import React, {useState} from "react";
import {ArticleService} from "../services/ArticleService";
import {useHistory} from "react-router";
import {Col, Container, Row} from "react-bootstrap";
import CreateArticleComponent from "../components/CreateArticleComponent";

const CreateArticle = () => {
    const [article, setArticle] = useState({
        name: "",
        description: "",
        price: ""
    });

    const [image, setImage] = useState(null);

    const history = useHistory();

    const handleFormInputChange = (name) => (event) => {
        const val = event.target.value
        setArticle({...article, [name]: val});
    }

    async function edit(id) {
        try {
            await ArticleService.updateArticle(id, article);
        } catch (error){
            console.error(error)
        }
    }

    async function saveImage() {
        try {
            const fd = new FormData()
            fd.append("image", image, image.name)
            await ArticleService.saveImage(fd).then((response) => {
                let id = response.data
                edit(id)
            })
        } catch (error) {
            console.error(error)
        }
    }

    const submit = () => {
        console.log(article);
        saveImage();
    }

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2}} style={{ textAlign: "center" }}>
                    <h1>Create Article</h1>
                    <CreateArticleComponent
                        handleFormInputChange={handleFormInputChange}
                        saveImage={saveImage}
                        article={article}
                        setImage={setImage}
                        submit={submit}
                    />
                </Col>
            </Row>
        </Container>
    );
}

export default CreateArticle;