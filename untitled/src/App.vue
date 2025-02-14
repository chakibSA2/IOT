<template>
  <div id="app">
    <h1>LiveObjects - Données du Capteur</h1>
    <SensorData />
  </div>
</template>

<script>
import SensorData from '@/components/SensorData.vue';

export default {
  components: {
    SensorData
  }
};
</script>

<style>
#app {
  text-align: center;
  font-family: Arial, sans-serif;
}
</style>