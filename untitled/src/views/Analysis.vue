<template>
  <div class="analysis-container">
    <h2>📊 Analyse des Données</h2>

    <!-- 📡 Indicateur de qualité de l'air -->
    <AirQualityCard :co2Value="co2Value" :covValue="covValue" />

    <div class="charts-wrapper">
      <!-- 🌿 Jauge CO₂ -->
      <GaugeChart :value="co2Value" title="Niveau CO₂" :max="1500" />

      <!-- ⚗️ Comparaison des données -->
      <BarChart
        :co2="co2Value"
        :cov="covValue"
        :temperature="temperatureValue"
        :humidity="humidityValue"
      />
    </div>

    <div class="chart-container">
      <!-- 🌡️ Graphique Température -->
      <LineChart v-if="temperatureData.length" :data="temperatureData" title="🌡️ Température" color="#ff6384" />

      <!-- 💧 Graphique Humidité -->
      <LineChart v-if="humidityData.length" :data="humidityData" title="💧 Humidité" color="#36a2eb" />
    </div>

    <button class="back-btn" @click="$router.push('/')">⬅ Retour</button>
  </div>
</template>

<script>
import { computed, onMounted } from 'vue';
import { useSensorStore } from '@/store/sensorStore';
import LineChart from '@/components/LineChart.vue';
import AirQualityCard from '@/components/AirQualityCard.vue';
import GaugeChart from '@/components/GaugeChart.vue';
import BarChart from '@/components/BarChart.vue';

export default {
  components: { LineChart, AirQualityCard, GaugeChart, BarChart },
  setup() {
    const sensorStore = useSensorStore();

    // Charger les données au montage
    onMounted(() => {
      if (!sensorStore.sensorData.length) {
        console.log("📡 Chargement des données capteurs...");
        sensorStore.fetchSensorData();
      }
    });

    // Extraire les données des capteurs
    const co2Value = computed(() => sensorStore.sensorData.length ? sensorStore.sensorData[0].co2 : 0);
    const covValue = computed(() => sensorStore.sensorData.length ? sensorStore.sensorData[0].cov : 0);
    const temperatureValue = computed(() => sensorStore.sensorData.length ? sensorStore.sensorData[0].temperature : 0);
    const humidityValue = computed(() => sensorStore.sensorData.length ? sensorStore.sensorData[0].humidity : 0);

    const temperatureData = computed(() => sensorStore.extractData("temperature"));
    const humidityData = computed(() => sensorStore.extractData("humidity"));

    return { temperatureData, humidityData, co2Value, covValue, temperatureValue, humidityValue };
  }
};
</script>

<style scoped>
.analysis-container {
  text-align: center;
  padding: 20px;
}

.charts-wrapper {
  display: flex;
  justify-content: space-around;
  flex-wrap: wrap;
  margin: 20px 0;
}

.chart-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

.back-btn {
  background: #e74c3c;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  margin-top: 20px;
  transition: background 0.3s;
}

.back-btn:hover {
  background: #c0392b;
}
</style>
