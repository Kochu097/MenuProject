import axios from 'axios';
import { useUser } from "@/context/UserContext";
import { MenuItem, Product } from '@/components/Interfaces/ICommon';

function getAPIConfiguration(token: string) {
    return {
        headers: {
            Authorization: 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        httpsAgent: false,
    }
}

function getMultipartAPIConfiguration(token: string) {
    return {
        headers: {
            Authorization: 'Bearer ' + token,
            'Content-Type': 'multipart/form-data',
        },
        httpsAgent: false,
    }
}

export async function fetchRecipes(token: string) {
    const response = await axios.get('http://localhost:8083/api/getRecipes', getAPIConfiguration(token)).catch((error) => {
        console.log('error fetching recipes', error);
    });
    return response?.data;
}

export async function fetchProducts(token: string) {
    const response = await axios.get('http://localhost:8083/api/getProducts', getAPIConfiguration(token)).catch((error) => {
        console.log('error fetching products', error);
    });
    return response?.data;
}

export async function fetchMenu(token: string, startDate: Date, endDate: Date) {
    const formattedStartDate = formatDate(startDate);
    const formattedEndDate = formatDate(endDate);
    const response = await axios.get(`http://localhost:8083/api/getMenuForPeriod?startDate=${formattedStartDate}&endDate=${formattedEndDate}`, getAPIConfiguration(token))
    .catch((error) => {
        console.log('error fetching menu', error);
    });
    return response?.data;
}

export async function addMenuItem(token: string, menuItem: MenuItem, date: Date) {
    const formattedDate = formatDate(date);
    await axios.post(`http://localhost:8083/api/addMenuItem?date=${formattedDate}`, menuItem, getAPIConfiguration(token)).catch((error) => {
        console.log('error adding menu item', error);
    });
}

export async function addProduct(token: string, product: Product, image: File | undefined) {
    const formData = new FormData();
    formData.append('product', new Blob([JSON.stringify(product)], { type: 'application/json' }));
    if (image) {
        formData.append('image', image);
    }
    await axios.post('http://localhost:8083/api/addProduct', formData, getMultipartAPIConfiguration(token)).catch((error) => {
        console.log('error adding product', error);
    });
}

function formatDate(date: Date) {
    return date.toISOString().split('T')[0];
}