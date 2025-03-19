# 🌍 IoT Data Monitoring - LiveObjects + Vue.js + Spring Boot

## 📌 Description

Cette application **Full Stack** permet la récupération, le traitement et l'affichage des données issues d'un **capteur Watteco VaQao Plus** via **Orange Live Objects**.  
Elle utilise un backend en **Spring Boot**, un frontend en **Vue.js**, un **décodage des trames en Python**, et une **persistance en JSON** pour stocker les données des capteurs.

## 🚀 Technologies Utilisées

### 🖥️ Backend - Spring Boot
- **Spring Boot** : Développement des API REST.
- **Spring Scheduler** : Récupération automatique des données.
- **RestTemplate** : Communication avec l'API LiveObjects.
- **Jackson (ObjectMapper)** : Gestion du format JSON.
- **Fichier JSON** : Stockage persistant des données capteurs.

### 🎨 Frontend - Vue.js
- **Vue.js** : Interface utilisateur dynamique et interactive.
- **Vue Router** : Navigation entre les différentes pages.
- **Pinia** : Gestion des états côté frontend.
- **Axios** : Communication avec les API REST.
- **Chart.js** : Visualisation des données via un dashboard.

### 🔍 Traitement des Données - Python
- **Décodage des trames hexadécimales** reçues de LiveObjects.
- **Conversion des données en valeurs exploitables** (température, humidité, pression, etc.).
- **Envoi des données décodées** au backend pour stockage et affichage.

---

## ⚙️ Architecture

📌 **Backend (Spring Boot)**
- 📁 `LiveObjectsController` : Expose les endpoints API pour récupérer les données des capteurs.
- 📁 `LiveObjectsService` : Récupération des données via l'API LiveObjects + gestion de la persistance.
- 📁 `SensorData` : Modèle de données pour stocker les mesures des capteurs.

📌 **Frontend (Vue.js)**
- 📁 `views/` : Pages principales (Tableau de bord, Capteurs, Historique).
- 📁 `components/` : Composants réutilisables pour l'affichage des données.
- 📁 `store/` : Gestion de l’état des données capteurs avec **Pinia**.
- 📁 `router/` : Gestion des routes.

📌 **Python**
- 📁 `decoder.py` : Décodeur des trames reçues depuis **LiveObjects**.

---

## 📡 API REST

| Méthode | Endpoint                          | Description                                     |
|---------|-----------------------------------|-------------------------------------------------|
| `GET`   | `/api/liveobjects/decoded/{id}`  | Récupère et décode les données d’un capteur    |

📌 **Données stockées (SensorData)** :
- Température 🌡️
- Humidité 💧
- Pression 🏔️
- CO2 🍃
- COV (Composés Organiques Volatils) 🏭
- Luminosité 💡
- Tension Batterie 🔋
- Mode d’alimentation ⚡
- Détection de mouvement 🚶‍♂️
- Timestamp ⏳

---

## 🛠 Installation et Exécution

### 🔧 Prérequis
- **Java 17+**, **Maven**, **Node.js 16+**, **Python 3+**
- **Dépendances backend** : Spring Boot, Jackson, RestTemplate
- **Dépendances frontend** : Vue.js, Axios, Pinia, Chart.js

### 🚀 Lancer l’Application

#### 📌 Backend (Spring Boot)
```sh
git clone https://github.com/ton-repo/iot-liveobjects.git
cd backend
mvn clean install
mvn spring-boot:run
```
API disponible sur http://localhost:8080/api/liveobjects/

#### 📌 Frontend (Vue.js)
```sh
cd frontend
npm install
npm run dev
```
Interface disponible sur http://localhost:5173/

#### 📌 Décodage Python
```sh
python decoder.py
```
#### 🔮 Améliorations Futures
✅ Migration vers une base de données PostgreSQL
✅ Ajout d’une historisation avancée des mesures
✅ Intégration de WebSockets pour un affichage en temps réel


> 📌 **Note :** Projet développé dans un cadre d’apprentissage et d’expérimentation avec Orange Live Objects.
