<template>
  <div class="sensor-data-container">
    <h2 class="title">📡 Données du Capteur</h2>

    <!-- 📌 Affichage du chargement -->
    <div v-if="loading" class="loading">
      <div class="loader"></div>
      <p>Chargement des données...</p>
    </div>

    <!-- 📌 Affichage des erreurs -->
    <div v-else-if="error" class="error">
      <p>⚠️ {{ error }}</p>
    </div>

    <!-- 📌 Affichage des données -->
    <div v-else>
      <button class="analyze-btn" @click="goToAnalysis">📊 Voir l'analyse</button>

      <div class="sensor-grid">
        <div v-for="(capture, index) in sensorData" :key="index" class="sensor-card">
          <div class="card-header">
            <h3>📡 Capture {{ index + 1 }}</h3>
            <small class="timestamp">🕒 {{ formatTimestamp(capture.timestamp) }}</small>
          </div>
          <div class="card-body">
            <p v-for="(value, label) in getValidValues(capture)" :key="label">
              {{ getIcon(label) }} <strong>{{ getLabel(label) }} :</strong> {{ value }}
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import apiClient from '@/service/api';
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useSensorStore } from '@/store/sensorStore';

export default {
  setup() {
    const router = useRouter();
    const sensorStore = useSensorStore();

    // 📌 Définition des variables réactives
    const sensorData = ref([]);
    const loading = ref(true);
    const error = ref(null);

    // 📌 Fonction pour récupérer les données du capteur
    const fetchSensorData = async () => {
      loading.value = true;
      error.value = null; // Réinitialisation de l'erreur
      try {
        const response = await apiClient.get('/decoded/urn:lo:nsid:lora:70B3D5E75E029068');
        if (response.data && response.data.length > 0) {
          sensorData.value = response.data; // ✅ Mise à jour correcte de `sensorData`
        } else {
          error.value = "Aucune donnée disponible.";
        }
      } catch (err) {
        error.value = "Impossible de récupérer les données.";
        console.error(err);
      } finally {
        loading.value = false;
      }
    };

    // 📌 Fonction pour aller à la page d'analyse
    const goToAnalysis = () => {
      sensorStore.setSensorData(sensorData.value); // ✅ Stockage des données dans Pinia
      router.push('/analysis');
    };

    // 📌 Fonction pour formater le timestamp en heure lisible
    const formatTimestamp = (timestamp) => {
      return new Date(timestamp).toLocaleTimeString();
    };

    // 📌 Fonction pour filtrer les valeurs nulles ou non valides
    const getValidValues = (capture) => {
      return Object.fromEntries(
        Object.entries(capture).filter(([_, value]) => value !== null && value !== undefined)
      );
    };

    // 📌 Fonction pour associer un label aux valeurs
    const getLabel = (key) => {
      const labels = {
        temperature: "Température (°C)",
        humidity: "Humidité (%)",
        co2: "CO₂ (ppm)",
        cov: "COV (Indice)",
        luminosity: "Luminosité (Lux)"
      };
      return labels[key] || key;
    };

    // 📌 Fonction pour associer une icône aux valeurs
    const getIcon = (key) => {
      const icons = {
        temperature: "🌡️",
        humidity: "💧",
        co2: "🌿",
        cov: "⚗️",
        luminosity: "💡"
      };
      return icons[key] || "🔹";
    };

    // 📌 Exécuter la récupération des données au montage
    onMounted(fetchSensorData);

    // 📌 Retourner toutes les valeurs pour qu'elles soient accessibles dans le template
    return {
      sensorData,
      loading,
      error,
      goToAnalysis,
      formatTimestamp,
      getValidValues,
      getLabel,
      getIcon
    };
  }
};
</script>

<style scoped>
.analyze-btn {
  background: #1e88e5;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  margin-bottom: 20px;
  transition: background 0.3s;
}

.analyze-btn:hover {
  background: #1565c0;
}
</style>
