import { useEffect, useState } from "react";
import axios from 'axios';
import { useUser } from "@/context/UserContext";

export function useTestMenuConnection() {
    const [post, setPost] = useState([]);
    const token = useUser();
    useEffect(() => {
        axios.get('http://localhost:8083/api/test', {
            headers: {
                Authorization: 'Bearer '+ String(token),
                'Content-Type': 'application/json', 
            },
            httpsAgent: false,
        })
        .then( response => {
            setPost(response.data);
        }).catch( error => {
            console.log(error.api);
        })
    });

    return post;
}