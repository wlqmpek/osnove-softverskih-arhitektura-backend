import AxiosClient from "./clients/AxiosClient";

export const OrderService = {
    getOrder,
    getUndeliveredOrders,
    getDeliveredOrders,
    getDeliveredAndCommented,
    createOrder,
    setDelivered,
    setArchiveComment,
    buyerFeedback,
    searchOrders
};

async function searchOrders(searchParams) {
    return await AxiosClient.get("orders", {params : searchParams});
}

async function getOrder(id) {
    return await AxiosClient.get(`orders/${id}`);
}

async function getUndeliveredOrders() {
    return await AxiosClient.get(`orders/undelivered`);
}

async function getDeliveredOrders() {
    return await AxiosClient.get(`orders/delivered`);
}

async function getDeliveredAndCommented() {
    return await AxiosClient.get(`orders/delivered/commented/`);
}

async function createOrder(order) {
    return await AxiosClient.post("orders", order);
}

async function setDelivered(id) {
    return await AxiosClient.put(`orders/delivered/${id}`, {});
}

async function setArchiveComment(id, archived) {
    return await AxiosClient.put(`orders/archived_comment/${id}`, archived);
}

async function buyerFeedback(id, feedback) {
    return await AxiosClient.put(`orders/buyer_feedback/${id}`, feedback);
}