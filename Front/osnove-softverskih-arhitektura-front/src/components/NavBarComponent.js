import {Navbar, Nav, Button, FormControl, Form, NavDropdown} from "react-bootstrap";

const NavBarComponent = () => {
    return(
        <>
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand href="/">Home</Navbar.Brand>
                <Nav className="mr-auto">
                    <Nav.Link href="/sellers">List</Nav.Link>
                    <Nav.Link href="/undelivered_orders">Undelivered</Nav.Link>
                    <Nav.Link href="/delivered_orders">Delivered</Nav.Link>
                    <NavDropdown title="Seller" id="collasible-nav-dropdown">
                        <NavDropdown.Item href="/my_articles">My Articles</NavDropdown.Item>
                        <NavDropdown.Item href="/my_comments">Comment Managment</NavDropdown.Item>
                        <NavDropdown.Item href="/create_discount">Discounts</NavDropdown.Item>
                    </NavDropdown>
                    <Nav.Link href="/login">Login</Nav.Link>
                    <Nav.Link href="/logout">Logout</Nav.Link>
                </Nav>
            </Navbar>
            <br />

        </>
    );
}

export default NavBarComponent;