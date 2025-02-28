<template>
  <div class="analysis-container">
    <h2 class="title">📊 Analyse des données</h2>

    <AirQualityCard :co2Value="co2Value" :covValue="covValue" />
    <GaugeChart :value="co2Value" title="CO₂ Niveau" :max="1500" />
    <BarChart :co2="co2Value" :cov="covValue" :temperature="temperatureValue" :humidity="humidityValue" />

    <button class="back-btn" @click="$router.push('/')">⬅️ Retour</button>
  </div>
</template>

<script>
import { computed, onMounted } from 'vue';
import { useSensorStore } from '@/store/sensorStore';
import AirQualityCard from '@/components/AirQualityCard.vue';
import GaugeChart from '@/components/GaugeChart.vue';
import BarChart from '@/components/BarChart.vue';

export default {
  components: {
    AirQualityCard,
    GaugeChart,
    BarChart
  },
  setup() {
    const sensorStore = useSensorStore();

    onMounted(() => {
      if (!sensorStore.sensorData.length) {
        sensorStore.fetchSensorData();
      }
    });

    const co2Value = computed(() =>
      sensorStore.sensorData.length ? sensorStore.sensorData[0].co2 : 0
    );
    const covValue = computed(() =>
      sensorStore.sensorData.length ? sensorStore.sensorData[0].cov : 0
    );
    const temperatureValue = computed(() =>
      sensorStore.sensorData.length ? sensorStore.sensorData[0].temperature : 0
    );
    const humidityValue = computed(() =>
      sensorStore.sensorData.length ? sensorStore.sensorData[0].humidity : 0
    );

    return {
      co2Value,
      covValue,
      temperatureValue,
      humidityValue
    };
  }
};
</script>
