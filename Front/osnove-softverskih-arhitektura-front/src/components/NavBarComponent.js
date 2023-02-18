import {Navbar, Nav, Button, FormControl, Form, NavDropdown} from "react-bootstrap";
import { AuthenticationService } from "../services/AuthenticationService";
import React, { useState, useEffect } from "react";
import * as Constants from "../helper/Constants";


const NavBarComponent = () => {

    const [userId, setUserId] = useState();
    const [userRole, setUserRole] = useState();

    const loggedIn = false;

    useEffect(() => {
        setUserId(AuthenticationService.getUserId());
        setUserRole(AuthenticationService.getRole());
    })

    return(
        <>
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="/">Home</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/sellers">List</Nav.Link>
                    <Nav.Link href="/undelivered_orders">Undelivered</Nav.Link>
                    <Nav.Link href="/delivered_orders">Delivered</Nav.Link>
                    {
                        userRole === Constants.UserRoles.SELLER ? (
                            <NavDropdown title="Seller" id="collasible-nav-dropdown">
                                <NavDropdown.Item href="/my_articles">My Articles</NavDropdown.Item>
                                <NavDropdown.Item href="/my_comments">Comment Managment</NavDropdown.Item>
                                <NavDropdown.Item href="/create_discount">Create Discount</NavDropdown.Item>
                                <NavDropdown.Item href="/create_article">Create Article</NavDropdown.Item>
                            </NavDropdown>
                        ) : null
                    }
                    {
                        userId ? (
                            <Nav.Link href="/logout">Logout</Nav.Link>
                        ) : (
                            <Nav.Link href="/login">Login</Nav.Link>
                        )
                    }

                </Nav>
            </Navbar>
            <br />
            <button onClick={() => {console.log(userRole === Constants.UserRoles.SELLER)}}>User Role</button>

        </>
    );
}

export default NavBarComponent;