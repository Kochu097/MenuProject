import { useEffect, useState } from "react";
import axios from 'axios';
import { useUser } from "@/context/UserContext";

function getAPIConfiguration(token: string) {
    return {
        headers: {
            Authorization: 'Bearer '+ token,
            'Content-Type': 'application/json',
        },
        httpsAgent: false,
    }
}

function useAPI(url: string, params: Record<string, string> = {}) {
    const [data, setData] = useState([]);
    const { token } = useUser();

    useEffect(() => {
        if (token != null) {
            const queryString = new URLSearchParams(params).toString();
            const fullUrl = queryString ? `${url}?${queryString}` : url;
            axios.get(fullUrl, getAPIConfiguration(token))
                .then(response => {
                    setData(response.data);
                }).catch(error => {
                    console.error('There was an error!', error);
                });
        }
    }, [token, url, ...Object.values(params)]);

    return data;
}

export function useTestMenuConnection() {
    return useAPI('http://localhost:8083/api/test');
}

export function useFetchMenu(startDate: Date, endDate: Date) {
    const formattedStartDate = startDate.toISOString().split('T')[0];
    const formattedEndDate = endDate.toISOString().split('T')[0];
    return useAPI('http://localhost:8083/api/getMenuForPeriod', { startDate: formattedStartDate, endDate: formattedEndDate });
}