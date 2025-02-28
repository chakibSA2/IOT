import { createApp } from 'vue';
import { createPinia } from 'pinia'; // 📌 Import Pinia
import App from './App.vue';
import router from './router';

import './assets/main.css';

const app = createApp(App);
const pinia = createPinia(); // 📌 Créer une instance Pinia

app.use(pinia); // 📌 Ajouter Pinia à Vue
app.use(router);

app.mount('#app');
