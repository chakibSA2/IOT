<template>
  <div class="sensor-data-container">
    <h2>📡 Données des Capteurs</h2>

    <div v-if="sensorStore.loading" class="loading">
      <p>Chargement des données...</p>
    </div>

    <div v-else-if="sensorStore.error" class="error">
      <p>⚠️ {{ sensorStore.error }}</p>
    </div>

    <div v-else>
      <button class="analyze-btn" @click="goToAnalysis">📊 Voir l'analyse</button>

      <div class="sensor-grid">
        <SensorList :data="sensorStore.sensorData" />
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted } from 'vue';
import { useSensorStore } from '@/store/sensorStore';
import SensorList from '@/components/SensorList.vue';
import { useRouter } from 'vue-router';

export default {
  components: { SensorList },
  setup() {
    const router = useRouter();
    const sensorStore = useSensorStore();

    const goToAnalysis = () => {
      router.push('/analysis');
    };

    onMounted(() => {
      sensorStore.fetchSensorData("70B3D5E75E029068"); // Appareil à adapter
    });

    return { sensorStore, goToAnalysis };
  }
};
</script>

<style scoped>
.analyze-btn {
  background: #1e88e5;
  color: white;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
  margin-bottom: 20px;
}

.analyze-btn:hover {
  background: #1565c0;
}
</style>
