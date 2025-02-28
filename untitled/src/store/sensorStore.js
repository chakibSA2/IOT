import { defineStore } from 'pinia';
import apiClient from '@/apiClient';

export const useSensorStore = defineStore('sensor', {
    state: () => ({
        sensorData: [],
        loading: false,
        error: null
    }),

    actions: {
        async fetchSensorData(deviceId) {
            this.loading = true;
            this.error = null;
            try {
                const response = await apiClient.get(`/decoded/urn:lo:nsid:lora:70B3D5E75E029068`);
                if (response.data.length > 0) {
                    this.sensorData = response.data;
                } else {
                    this.error = "Aucune donnée disponible.";
                }
            } catch (err) {
                this.error = "Erreur de récupération des données.";
                console.error(err);
            } finally {
                this.loading = false;
            }
        }
    },

    getters: {
        extractData: (state) => (key) => {
            return state.sensorData
                .filter(item => item[key] !== null)
                .map(item => ({
                    timestamp: new Date(item.timestamp).toLocaleTimeString(),
                    value: item[key]
                }));
        }
    }
});
