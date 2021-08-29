import AxiosClient from "./clients/AxiosClient";

export const DiscountService = {
    createDiscount,
    getSellersDiscounts
}

async function createDiscount(discount) {
    return await AxiosClient.post("discounts", discount);
}

async function getSellersDiscounts(id) {
    return await AxiosClient.get(`discounts/sellers/${id}`);
}