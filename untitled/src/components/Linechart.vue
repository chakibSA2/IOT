<template>
  <div class="chart-wrapper">
    <h3>{{ title }}</h3>
    <canvas ref="chartCanvas"></canvas>
  </div>
</template>

<script>
import { onMounted, ref, watch } from 'vue';
import Chart from 'chart.js/auto';

export default {
  props: {
    data: Array,
    title: String,
    color: String
  },

  setup(props) {
    const chartCanvas = ref(null);
    let chartInstance = null;

    const renderChart = () => {
      if (chartInstance) {
      }

      chartInstance = new Chart(chartCanvas.value, {
        type: 'line',
        data: {
          labels: props.data.map(d => d.timestamp),
          datasets: [{
            label: props.title,
            data: props.data.map(d => d.value),
            borderColor: props.color,
            borderWidth: 2,
            fill: false
          }]
        }
      });
    };

    onMounted(renderChart);
    watch(() => props.data, renderChart, { deep: true });

    return { chartCanvas };
  }
};
</script>

<style scoped>
.chart-wrapper {
  width: 400px;
  height: 300px;
}
</style>
