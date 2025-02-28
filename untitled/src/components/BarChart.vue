<template>
  <div class="chart-container">
    <h3>📊 Comparaison des Données</h3>
    <canvas ref="chartCanvas"></canvas>
  </div>
</template>

<script>
import { onMounted, ref, watch } from 'vue';
import Chart from 'chart.js/auto';

export default {
  props: {
    co2: Number,
    cov: Number,
    temperature: Number,
    humidity: Number
  },
  setup(props) {
    const chartCanvas = ref(null);
    let chartInstance = null;

    const renderChart = () => {
      if (chartInstance) {
        chartInstance.destroy();
      }

      chartInstance = new Chart(chartCanvas.value, {
        type: 'bar',
        data: {
          labels: ["CO₂ (ppm)", "COV (Indice)", "Température (°C)", "Humidité (%)"],
          datasets: [{
            label: "Valeurs actuelles",
            data: [props.co2, props.cov, props.temperature, props.humidity],
            backgroundColor: ["#ff9f40", "#ff6384", "#36a2eb", "#4bc0c0"]
          }]
        },
        options: {
          responsive: true,
          scales: {
            y: { beginAtZero: true }
          }
        }
      });
    };

    onMounted(renderChart);
    watch(() => [props.co2, props.cov, props.temperature, props.humidity], renderChart, { deep: true });

    return { chartCanvas };
  }
};
</script>

<style scoped>
.chart-container {
  width: 400px;
  margin: auto;
}
</style>
