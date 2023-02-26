import React, { useState } from 'react';
import { OrderService } from "../services/OrderService";
import {Button} from "react-bootstrap";
import { useHistory } from "react-router";


const SearchOrders = () => {
  const [orders, setOrders] = useState([]);
  const [minTotalPrice, setMinTotalPrice] = useState("");
  const [maxTotalPrice, setMaxTotalPrice] = useState("");
  const [comment, setComment] = useState("");
  const [priceComment, setPriceComment] = useState("AND");

  const history = useHistory();

  const searchOrders = () => {
    const searchData = {
      minTotalPrice: minTotalPrice,
      maxTotalPrice: maxTotalPrice,
      priceComment: priceComment,
      comment: comment,
    };

    OrderService.searchOrders(searchData).then((response) => {
      console.log(response.data);
      setOrders(response.data);
    });
  };

  return (
    <div style={{ margin: 'auto', width: '80%' }}>
      <button onClick={console.log(priceComment)}>Test</button>
      <div>
        &nbsp;
        <input style={{ width: '100px' }} type="number" placeholder='Min total price' value={minTotalPrice} onChange={(e) => setMinTotalPrice(e.target.value)} />
        &nbsp;
        <input style={{ width: '100px' }} type="number" placeholder='Max total price' value={maxTotalPrice} onChange={(e) => setMaxTotalPrice(e.target.value)} />
        &nbsp;
        <select name="priceComment" onChange={(e) => setPriceComment(e.target.value)}>
          <option value="AND">AND</option>
          <option value="OR">OR</option>
        </select>
        &nbsp;
        <input type="text" placeholder='Comment' value={comment} onChange={(e) => setComment(e.target.value)} />
        &nbsp;
        <input type="button" value="Search" onClick={searchOrders} />
      </div>

      <br />
      <h1 style={{ textAlign: 'center' }}>Orders</h1>
      <div>
        <div className="row">
          <table className="table table-striped table bordered">
            <thead>
              <tr>
                <th style={{ textAlign: "center" }}> Id </th>
                <th style={{ textAlign: "center" }}> Time </th>
                <th style={{ textAlign: "center" }}> Comment </th>

                <th style={{ textAlign: "center" }}> Anonymous </th>
                {/*<th style={{ textAlign: "right" }}> Total Price </th>*/}
              </tr>
            </thead>

            <tbody>
              {orders.map(order =>
                <tr style={{ verticalAlign: 'middle' }} id="lista" key={order.orderId}>
                  <td style={{ textAlign: "center" }}>{order.orderId}</td>
                  <td style={{ textAlign: "center" }}>{order.time}</td>

                  <td>
                    <div style={{ float: 'left', wordBreak: "break-word", maxWidth: "500px", maxHeight: "fit-content", overflow: 'hidden' }}>
                      <p style={{ lineHeight: '13px', fontSize: '14px' }}>{order.comment.substring(0, 1300)}</p>
                    </div>
                  </td>
                  <td style={{ textAlign: "center" }}>{order.anonymusComment ? "true":"false"}</td>
                  <td>
                    <Button
                        className="btn btn-success"
                        block
                        onClick={() => history.push("/orders/" + order.orderId)}
                    >
                      More Details
                    </Button>
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default SearchOrders;




