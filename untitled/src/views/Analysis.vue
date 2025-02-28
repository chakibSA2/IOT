<template>
  <div>
    <h2>📊 Analyse des Données</h2>

    <div class="chart-container">
      <LineChart v-if="temperatureData.length" :data="temperatureData" title="🌡️ Température" color="#ff6384" />
      <LineChart v-if="humidityData.length" :data="humidityData" title="💧 Humidité" color="#36a2eb" />
    </div>

    <button @click="$router.push('/')">⬅ Retour</button>
  </div>
</template>

<script>
import { computed } from 'vue';
import { useSensorStore } from '@/store/sensorStore';
import LineChart from '@/components/LineChart.vue';

export default {
  components: { LineChart },
  setup() {
    const sensorStore = useSensorStore();

    const temperatureData = computed(() => sensorStore.extractData("temperature"));
    const humidityData = computed(() => sensorStore.extractData("humidity"));

    return { temperatureData, humidityData };
  }
};
</script>
