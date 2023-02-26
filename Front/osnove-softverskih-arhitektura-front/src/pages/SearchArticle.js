import React, { useState } from 'react';
import { ArticleService } from '../services/ArticleService';

const SearchArticles = () => {
  const [articles, setArticles] = useState([]);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [minPrice, setMinPrice] = useState("");
  const [maxPrice, setMaxPrice] = useState("");
  const [nameDescription, setNameDescription] = useState("AND");
  const [descriptionPrice, setDescriptionPrice] = useState("AND");

  const searchArticles = () => {
    const searchData = {
      name: name,
      nameDescription: nameDescription,
      description: description,
      descriptionPrice: descriptionPrice,
      minPrice: minPrice,
      maxPrice: maxPrice,
    };

    ArticleService.searchArticles(searchData).then((response) => {
      console.log(response.data);
      setArticles(response.data);
    });
  };

  return (
    <div style={{ margin: 'auto', width: '80%' }}>
      <div>
        <input type="text" placeholder='Name' value={name} onChange={(e) => setName(e.target.value)} />
        &nbsp;
        <select name="nameDescription" onChange={(e) => setNameDescription(e.target.value)}>
          <option value="AND">AND</option>
          <option value="OR">OR</option>
        </select>
        &nbsp;
        <input type="text" placeholder='Description' value={description} onChange={(e) => setDescription(e.target.value)} />
        &nbsp;
        <select name="descriptionPrice" onChange={(e) => setDescriptionPrice(e.target.value)}>
          <option value="AND">AND</option>
          <option value="OR">OR</option>
        </select>
        &nbsp;
        <input style={{ width: '100px' }} type="number" placeholder='Min price' value={minPrice} onChange={(e) => setMinPrice(e.target.value)} />
        &nbsp;
        <input style={{ width: '100px' }} type="number" placeholder='Max price' value={maxPrice} onChange={(e) => setMaxPrice(e.target.value)} />
        &nbsp;
        <input type="button" value="Search" onClick={searchArticles} />
      </div>
      <br />
      <h1 style={{ textAlign: 'center' }}>Articles</h1>
      <div>
        <div className="row">
          <table className="table table-striped table bordered">
            <thead>
              <tr>
                <th> Image </th>
                <th> Name </th>
                <th style={{ textAlign: "left" }}> Description </th>

                <th> Seller </th>
                <th style={{ textAlign: "right" }}> Price </th>
              </tr>
            </thead>

            <tbody>
              {articles.map(article =>
                <tr style={{ verticalAlign: 'middle' }} id="lista" key={article.articleId}>
                  <td>
                    <div style={{ float: 'left', marginRight: "10px" }}><img style={{ height: '250px' }} src={"files/images/" + article.sellerUsername + "/" + article.imageName} /></div>
                  </td>
                  <td style={{ textAlign: "right" }}>{article.name}</td>
                  <td>
                    <div style={{ float: 'left', wordBreak: "break-word", maxWidth: "500px", maxHeight: "fit-content", overflow: 'hidden' }}>
                      <p style={{ lineHeight: '13px', fontSize: '14px' }}>{article.description.substring(0, 1300)}</p>
                    </div>
                  </td>
                  <td style={{ wordBreak: "break-word", maxWidth:"300px" }}>{article.sellerUsername}</td>
                  <td style={{ textAlign: "right" }}>{article.price}</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  );
};

export default SearchArticles;




