import AxiosClient from "./clients/AxiosClient";

export const ArticleService = {
    getArticles,
    getArticlesFromSeller,
    getArticlesFromOrder,
    getArticle,
    createArticle,
    updateArticle,
    saveImage,
    deleteArticle
};

async function getArticles() {
    return await AxiosClient.get("articles");
}

async function getArticlesFromSeller(id) {
    return await AxiosClient.get(`articles/seller/${id}`);
}

async function getArticlesFromOrder(id) {
    return await AxiosClient.get(`articles/order/${id}`);
}

async function getArticle(id) {
    return await AxiosClient.get(`articles/${id}`);
}

async function createArticle(article) {
    const formData = new FormData();
    formData.append("name", article.name);
    formData.append("description", article.description);
    formData.append("price", article.price);
    formData.append("image", article.image);
    formData.append("pdf", article.pdf);

    const config = {
        headers: {
            "content-type": "multipart/form-data"
        }
    };

    return await AxiosClient.post("articles", formData, config);
}

//proveri ovo
async function updateArticle(id, article) {
    return await AxiosClient.put(`articles/${id}`, article);
}

async function saveImage(image) {
    return await AxiosClient.post("articles/picture", image);
}

async function deleteArticle(id) {
    return await AxiosClient.delete(`articles/${id}`);
}