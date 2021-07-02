import AxiosClient from "./clients/AxiosClient";

export const ArticleService = {
    getArticles,
    getArticlesFromSeller,
    getArticlesFromOrder,
    getArticle,
    createArticle,
    updateArticle,
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
    return await AxiosClient.post("articles", article);
}

//proveri ovo
async function updateArticle(id, article) {
    return await AxiosClient.put(`articles/${id}`, article);
}

async function deleteArticle(id) {
    return await AxiosClient.delete(`articles/${id}`);
}