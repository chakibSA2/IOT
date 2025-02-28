import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/liveobjects', // 🔥 Adapte selon ton backend
    headers: {
        'Content-Type': 'application/json'
    }
});

export default apiClient;
