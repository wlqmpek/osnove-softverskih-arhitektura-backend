import './App.css';
import Registrtion from "./pages/Registration";
import Login from "./pages/Login";
import Logout from "./components/LogoutComponent";
import ListSellers from "./pages/ListSellers";
import ListSellerArticles from "./pages/ListSellerArticles";
import ListDeliveredOrders from "./pages/ListDeliveredOrders";
import ListUndeliveredOrders from "./pages/ListUndeliveredOrders";
import CommentManager from "./pages/CommentManager";
import CreateDiscount from "./pages/CreateDiscount";
import LeaveFeedback from "./pages/LeaveFeedback";
import MyArticles from "./pages/MyArticles";
import Order from "./pages/Order";
import ListSellersDiscounts from "./pages/ListSellersDiscounts";
import NavBarComponent from "./components/NavBarComponent";
import ChangePassword from "./pages/ChangePassword";
import CreateArticle from "./pages/CreateArticle";
import ArticleDetails from "./pages/ArticleDetails";
import SearchArticles from "./pages/SearchArticle";
// import FrontPage from './components/FrontPage'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
// import { PrivateRoute } from "./components/PrivateRoute";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-datepicker/dist/react-datepicker.css';
import SearchOrders from "./pages/SearchOrders";

function App() {
  return (
      <div>

          <Router>
              <NavBarComponent/>
              <Switch>
                  <Route exact path="/register" component={Registrtion}></Route>
              </Switch>
              <Switch>
                  <Route exact path="/login" component={Login}></Route>
                  <Route exact path="/logout" component={Logout}></Route>
                  <Route exact path="/change_password" component={ChangePassword} ></Route>
              </Switch>
              <Switch>
                  <Route exact path="/sellers" component={ListSellers}></Route>
                  <Route exact path="/sellers/:id" component={ListSellerArticles}></Route>
                  <Route exact path="/create_article" component={CreateArticle}></Route>
                  <Route exact path="/my_articles" component={MyArticles}></Route>
                  <Route exact path="/my_comments" component={CommentManager}></Route>
                  <Route exact path="/create_discount" component={CreateDiscount}></Route>
                  <Route exact path="/search_articles" component={SearchArticles}></Route>
                  <Route exact path="/search_orders" component={SearchOrders}></Route>
              </Switch>
              <Switch>
                  <Route exact path="/undelivered_orders" component={ListUndeliveredOrders}></Route>
                  <Route exact path="/delivered_orders" component={ListDeliveredOrders}></Route>
                  <Route exact path="/orders/:id" component={Order}></Route>
                  <Route exact path="/order_feedback/:id" component={LeaveFeedback}></Route>
                  <Route exact path="/sellers/discounts/:id" component={ListSellersDiscounts}></Route>
                  <Route exact path="/articles/seller/:id" component={ArticleDetails}></Route>
              </Switch>
          </Router>
      </div>
  );
}

export default App;
