<template>
  <div class="air-quality-card" :style="{ backgroundColor: getQualityColor() }">
    <h2>🌍 Qualité de l'Air</h2>
    <p class="status">{{ getQualityText() }}</p>
    <p>🌿 CO₂ : {{ co2Value }} ppm</p>
    <p>⚗️ COV : {{ covValue }} Indice</p>
  </div>
</template>

<script>
export default {
  props: {
    co2Value: Number,
    covValue: Number
  },
  methods: {
    getQualityText() {
      if (this.co2Value > 1000 || this.covValue > 100) return "❌ Mauvaise";
      if (this.co2Value > 600 || this.covValue > 50) return "⚠️ Moyenne";
      return "✅ Bonne";
    },
    getQualityColor() {
      if (this.co2Value > 1000 || this.covValue > 100) return "#e74c3c"; // Rouge
      if (this.co2Value > 600 || this.covValue > 50) return "#f39c12"; // Orange
      return "#2ecc71"; 
    }
  }
};
</script>

<style scoped>
.air-quality-card {
  text-align: center;
  color: white;
  padding: 20px;
  border-radius: 10px;
  font-size: 18px;
  margin: 20px auto;
  width: 250px;
}
.status {
  font-size: 24px;
  font-weight: bold;
}
</style>
