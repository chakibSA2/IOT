<template>
  <div class="gauge-container">
    <h3>{{ title }}</h3>
    <canvas ref="chartCanvas"></canvas>
  </div>
</template>

<script>
import { onMounted, ref, watch } from 'vue';
import Chart from 'chart.js/auto';

export default {
  props: {
    value: Number,
    title: String,
    max: Number
  },
  setup(props) {
    const chartCanvas = ref(null);
    let chartInstance = null;

    const renderChart = () => {
      if (chartInstance) {
        chartInstance.destroy();
      }

      chartInstance = new Chart(chartCanvas.value, {
        type: 'doughnut',
        data: {
          labels: ['Niveau actuel', 'Reste'],
          datasets: [{
            data: [props.value, props.max - props.value],
            backgroundColor: ["#ff5733", "#eeeeee"],
            borderWidth: 1
          }]
        },
        options: {
          responsive: true,
          cutout: "80%",
          plugins: {
            legend: { display: false }
          }
        }
      });
    };

    onMounted(renderChart);
    watch(() => props.value, renderChart, { deep: true });

    return { chartCanvas };
  }
};
</script>

<style scoped>
.gauge-container {
  text-align: center;
  width: 200px;
  margin: auto;
}
</style>
