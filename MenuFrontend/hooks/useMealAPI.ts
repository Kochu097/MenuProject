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

export function useTestMenuConnection() {
    const [post, setPost] = useState([]);
    const { token } = useUser();

    useEffect(() => {
        if(token != null) {
        axios.get('http://localhost:8083/api/test', getAPIConfiguration(token))
        .then( response => {
            setPost(response.data);
        }).catch( error => {
            console.error('There was an error!', error);
        })
        }
    }, [token]);

    return post;
}