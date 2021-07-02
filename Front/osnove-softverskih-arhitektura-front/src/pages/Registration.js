import React, {useState} from "react";
import { useHistory } from "react-router";
import {Col, Container, FormControl, FormGroup, FormLabel, Row} from "react-bootstrap";
import { SellerService } from "../services/SellerService";
import {BuyerService} from "../services/BuyerService";
import SellerRegistration from "../components/registration/SellerRegistration";
import { AuthenticationService } from "../services/AuthenticationService";
import BuyerRegistration from "../components/registration/BuyerRegistration";

const Registration = () => {

    const [seller, setSeller] = useState({
        firstName: "",
        lastName: "",
        username: "",
        password: "",
        repeatedPassword: "",
        email: "",
        address: "",
        name: ""
    });
    const [buyer, setBuyer] = useState({
        firstName: "",
        lastName: "",
        username: "",
        password: "",
        repeatedPassword: "",
        address: ""
    });

    const [userType, setUserType] = useState("Seller");

    const [showAlert, setShowAlert] = useState({ success: null, message: "" });

    const history = useHistory();

    async function registerSeller() {
        try {
            await SellerService.registerSeller(seller);

            setSeller({
                firstName: "",
                lastName: "",
                username: "",
                password: "",
                repeatedPassword: "",
                email: "",
                address: "",
                name: ""
            });

            //Message for successful registration
            setShowAlert({ success: true, message: "Seller registered!" });
        } catch (error) {
            //Message for the developer.
            console.error(`Error: ${error}`);

            //Message for the user.
            setShowAlert({
                success: false,
                message: "Error during registration!",
            });
        }
    }

    async function registerBuyer() {
        try {
            await BuyerService.registerBuyer(buyer);

            setBuyer({
                firstName: "",
                lastName: "",
                username: "",
                password: "",
                repeatedPassword: "",
                address: ""
            });

            //Message for successful registration
            setShowAlert({ success: true, message: "Buyer registered!" });
        } catch (error) {
            //Message for the developer.
            console.error(`Error: ${error}`);

            //Message for the user.
            setShowAlert({
                success: false,
                message: "Error during registration!",
            });
        }
    }

    const handleSellerFormInputChange = (name) => (event) => {
        const val = event.target.value;
        //TODO: Ovde izmeniti tako da se menja state o Selleru ili Buyeru u zavisnosti od selektovanog.
        setSeller({...seller, [name]: val });
    };

    const handleBuyerFormInputChange = (name) => (event) => {
        const val = event.target.value;
        //TODO: Ovde izmeniti tako da se menja state o Selleru ili Buyeru u zavisnosti od selektovanog.
        setBuyer({...buyer, [name]: val });
    };

    const handleSelectChange = (name) => (event) => {
        const val = event.target.value;
        console.log("Hey " + val);
        setUserType(val);
    }

    function returnComponent() {
        if(userType === "Seller") {
            return <SellerRegistration
                seller={seller}
                addInputChangeHandler={handleSellerFormInputChange}
                showAlert={showAlert}
                setShowAlert={setShowAlert}
                registerSeller={registerSeller}
            />
        } else if(userType === "Buyer") {
            return <BuyerRegistration
                buyer={buyer}
                addInputChangeHandler={handleBuyerFormInputChange}
                showAlert={showAlert}
                setShowAlert={setShowAlert}
                registerBuyer={registerBuyer}
            />
        }
    }

    return(
        <Container>
            <Row>
                <Col md={{ span: 8, offset: 2}} style={{ textAlign: "center" }}>
                    <h1>Registration</h1>
                    <FormGroup>
                        <FormLabel>Select Type</FormLabel>
                        <FormControl
                            onChange={handleSelectChange()}
                            as="select"
                        >
                            <option name="Seller" value="Seller">Seller</option>
                            <option name="Buyer" value="Buyer">Buyer</option>
                        </FormControl>
                    </FormGroup>
                    {returnComponent()}
                </Col>
            </Row>
        </Container>
    );
};

export default Registration;