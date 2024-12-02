import axios from 'axios';
import { useUser } from "@/context/UserContext";
import { MenuItem } from '@/components/Interfaces/ICommon';

function getAPIConfiguration(token: string) {
    return {
        headers: {
            Authorization: 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        httpsAgent: false,
    }
}

export async function fetchRecipes(token: string) {
    const response = await axios.get('http://localhost:8083/api/getRecipes', getAPIConfiguration(token));
    return response.data;
}

export async function fetchProducts(token: string) {
    const response = await axios.get('http://localhost:8083/api/getProducts', getAPIConfiguration(token));
    return response.data;
}

export async function fetchMenu(token: string, startDate: Date, endDate: Date) {
    const formattedStartDate = formatDate(startDate);
    const formattedEndDate = formatDate(endDate);
    const response = await axios.get(`http://localhost:8083/api/getMenuForPeriod?startDate=${formattedStartDate}&endDate=${formattedEndDate}`, getAPIConfiguration(token));
    return response.data;
}

export async function addMenuItem(token: string, menuItem: MenuItem, date: Date) {
    const formattedDate = formatDate(date);
    await axios.post(`http://localhost:8083/api/addMenuItem?date=${formattedDate}`, menuItem, getAPIConfiguration(token));
}

function formatDate(date: Date) {
    return date.toISOString().split('T')[0];
}