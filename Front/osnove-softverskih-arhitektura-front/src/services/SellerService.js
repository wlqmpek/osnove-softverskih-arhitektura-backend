import AxiosClient from "./clients/AxiosClient";

export const SellerService = {
    getSellers,
    getSeller,
    registerSeller
};

async function getSellers() {
    return await AxiosClient.get("sellers");
}

async function getSeller(id) {
    return await AxiosClient.get(`sellers/${id}`);
}

async function registerSeller(seller) {
    return await AxiosClient.post("sellers/registration", seller);
}