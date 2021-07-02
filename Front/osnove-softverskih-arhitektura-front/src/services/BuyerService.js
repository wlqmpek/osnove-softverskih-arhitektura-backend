import AxiosClient from "./clients/AxiosClient";

export const BuyerService = {
    getBuyers,
    getBuyer,
    registerBuyer
};

async function getBuyers() {
    return await AxiosClient.get("buyers");
}

async function getBuyer(id) {
    return await AxiosClient.get(`buyers/${id}`);
}

async function registerBuyer(buyer) {
    return await AxiosClient.post("buyers/registration", buyer);
}