import AxiosClient from "./clients/AxiosClient";

export const DiscountService = {
    createDiscount,
    getSellersDiscount
}

async function createDiscount(discount) {
    return await AxiosClient.post("discounts", discount);
}

async function getSellersDiscount(id) {
    return await AxiosClient.get(`discounts/sellers/${id}`);
}