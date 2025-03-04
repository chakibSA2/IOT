<template>
  <div id="app">
    <header>
      <h1 class="title">WattEco - Vaqa'O +</h1>
      <nav>
        <router-link to="/">🏠 Accueil</router-link>
        <router-link to="/analysis">📊 Analyse</router-link>
      </nav>
    </header>

    <div class="content">
      <router-view /> 
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
#app {
  text-align: center;
  font-family: Arial, sans-serif;
}

.title {
  font-size: 50px;
  font-weight: bold;
  margin-bottom: 20px;
}

nav {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-bottom: 20px;
}

nav a {
  text-decoration: none;
  color: #1e88e5;
  font-size: 18px;
  font-weight: bold;
  padding: 10px;
  border-radius: 5px;
  transition: background 0.3s;
}

nav a:hover {
  background: #1565c0;
  color: white;
}
</style>
