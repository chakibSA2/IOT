<template>
  <div>
    <div v-for="(capture, index) in data" :key="index" class="sensor-card">
      <div class="card-header">
        <h3>📡 Capture {{ index + 1 }}</h3>
      </div>
      <div class="card-body">
        <p v-for="(value, label) in capture" :key="label">
          {{ getIcon(label) }} <strong>{{ getLabel(label) }} :</strong> {{ value }}
        </p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ["data"],
  methods: {
    formatTimestamp(timestamp) {
      return new Date(timestamp).toLocaleTimeString();
    },
    getIcon(label) {
      const icons = {
        temperature: "🌡️",
        humidity: "💧",
        co2: "🌿",
        cov: "⚗️",
        luminosity: "💡",
        pressure: "🔵"
      };
      return icons[label] || "📌";
    },
    getLabel(label) {
      const labels = {
        temperature: "Température (°C)",
        humidity: "Humidité (%)",
        co2: "CO₂ (ppm)",
        cov: "COV (Indice)",
        luminosity: "Luminosité (Lux)",
        pressure: "Pression (hPa)"
      };
      return labels[label] || label;
    }
  }
};
</script>
