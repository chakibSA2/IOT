<template>
  <div class="sensor-data">
    <h2>Données du Capteur</h2>

    <div v-if="loading">Chargement des données...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else>
      <table>
        <thead>
          <tr>
            <th>Température (°C)</th>
            <th>Humidité (%)</th>
            <th>CO₂ (ppm)</th>
            <th>Batterie (V)</th>
            <th>Alimentation</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(data, index) in sensorData" :key="index">
            <td>{{ data.temperature }}</td>
            <td>{{ data.humidity }}</td>
            <td>{{ data.co2 }}</td>
            <td>{{ data.batteryVoltage }}</td>
            <td>{{ data.powerMode }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import apiClient from '../service/api'

export default {
  data() {
    return {
      sensorData: [],
      loading: true,
      error: null
    };
  },
  methods: {
    async fetchSensorData() {
      this.loading = true;
      try {
        const response = await apiClient.get('/decoded/urn:lo:nsid:lora:70B3D5E75E029068');
        this.sensorData = response.data;
      } catch (err) {
        this.error = "Erreur lors du chargement des données.";
        console.error(err);
      } finally {
        this.loading = false;
      }
    }
  },
  mounted() {
    this.fetchSensorData();
  }
};
</script>

<style scoped>
.sensor-data {
  padding: 20px;
  font-family: Arial, sans-serif;
}
.error {
  color: red;
}
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}
th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}
th {
  background-color: #f4f4f4;
}
</style>
