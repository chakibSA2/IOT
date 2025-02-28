<template>
  <div class="analysis-container">
    <h2 class="title">📊 Analyse des données</h2>
    
    <button class="back-btn" @click="$router.push('/')">⬅️ Retour</button>

    <div class="chart-container">
      <LineChart v-if="temperatureData.length" :data="temperatureData" title="🌡️ Température (°C)" color="#ff6384"/>
      <LineChart v-if="humidityData.length" :data="humidityData" title="💧 Humidité (%)" color="#36a2eb"/>
      <LineChart v-if="co2Data.length" :data="co2Data" title="🌿 CO₂ (ppm)" color="#ff9f40"/>
      <LineChart v-if="covData.length" :data="covData" title="⚗️ COV (Indice)" color="#4bc0c0"/>
      <LineChart v-if="luminosityData.length" :data="luminosityData" title="💡 Luminosité (Lux)" color="#9966ff"/>
    </div>
  </div>
</template>

<script>
import { computed, onMounted } from 'vue';
import { useSensorStore } from '@/store/sensorStore';
import LineChart from '@/components/LineChart.vue';

export default {
  components: { LineChart },
  setup() {
    const sensorStore = useSensorStore();

    // Récupération des données des capteurs via Pinia
    onMounted(() => {
      if (!sensorStore.sensorData.length) {
        sensorStore.fetchSensorData(); // Appel API si nécessaire
      }
    });

    // Extraction des différentes données sous forme de graphiques
    const temperatureData = computed(() => sensorStore.extractData('temperature'));
    const humidityData = computed(() => sensorStore.extractData('humidity'));
    const co2Data = computed(() => sensorStore.extractData('co2'));
    const covData = computed(() => sensorStore.extractData('cov'));
    const luminosityData = computed(() => sensorStore.extractData('luminosity'));

    return {
      temperatureData,
      humidityData,
      co2Data,
      covData,
      luminosityData,
    };
  }
};
</script>

<style scoped>
.analysis-container {
  text-align: center;
  padding: 20px;
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
  margin-bottom: 20px;
  transition: background 0.3s;
}

.back-btn:hover {
  background: #c0392b;
}
</style>
