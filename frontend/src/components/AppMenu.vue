<template>
  <div :class="['app-menu', { collapsed }]">
    <button @click="toggleMenu" class="toggle-btn">
      <span v-if="collapsed">☰</span>
      <span v-else>✖</span>
    </button>
    <div class="logo" v-if="!collapsed">
      <img src="@/assets/logo1.png" alt="Logo" />
    </div>
    <nav v-if="!collapsed" class="menu">
      <router-link to="/ajouterTrimestre" class="menu-item">
    <i class="fas fa-calendar"></i><span>Gestion des Trimestres</span>
  </router-link>
      <router-link to="/tableauGarde" class="menu-item">
    <i class="fas fa-calendar-alt"></i><span>Planification des Gardes</span>
  </router-link>
  <router-link to="/personnel" class="menu-item">
        <i class="fas fa-users"></i><span>Personnel Médical</span>
      </router-link>
      <router-link to="/service" class="menu-item">
        <i class="fas fa-building"></i><span>Services Hospitaliers</span>
      </router-link>
      <router-link to="/fonction" class="menu-item">
        <i class="fas fa-user-tag"></i><span>Fonctions du Personnel</span>
      </router-link>

      <router-link to="/jourFerie" class="menu-item">
        <i class="fas fa-calendar-day"></i><span>Jours Fériés</span>
      </router-link>     
      <router-link to="/delaiRamadan" class="menu-item">
        <i class="fas fa-moon"></i><span>Période du Ramadan</span>
      </router-link>
      <router-link to="/gestionUtilisateurs" class="menu-item">
        <i class="fas fa-user-cog"></i><span>Gestion des Utilisateurs</span>
      </router-link>
       
       <button @click="logout" class="menu-item">
        <i class="fas fa-sign-out-alt"></i><span>Déconnecter</span>
      </button>
    </nav>
  </div>
</template>

<script>
import axios from 'axios';
import { API_URLS } from '../api.config'; 

export default {
  data() {
    return {
      collapsed: false,
    };
  },
  methods: {
    toggleMenu() {
      this.collapsed = !this.collapsed;
    },
   
    logout() {
      axios.post(`${API_URLS.authentification}/logout`, {}, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}` 
        }
      }).then(() => {
        localStorage.removeItem('token'); 
        this.$router.push('/'); 
      }).catch((error) => {
        console.error("Erreur lors de la déconnexion:", error);
      });
    }
  }
};
</script>


<style>
.app-menu {
  background-color: #003366;
  color: #fff;
  padding: 1.5rem;
  position: fixed;
  top: 0;
  left: 0;
  transition: width 0.3s;
  width: 250px; 
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.5);
  z-index: 2000; 
  overflow-y: auto;
  height: 100vh;
  -ms-overflow-style: none;  /* For IE and Edge */
  scrollbar-width: none;  /* For Firefox */
}

.app-menu::-webkit-scrollbar {
  display: none; /* Hide the scrollbar */
}

.collapsed {
  width: 60px; 
}

.logo {
  text-align: center;
  margin-bottom: 15px;
}

.logo img {
  width: 80%;
}

.menu {
  display: flex;
  flex-direction: column;
  margin-bottom: 40px;
}

.menu-item {
  padding: 1rem;
  margin: 0.5rem 0;
  background-color: #003366;
  border-radius: 8px; 
  text-decoration: none;
  color: #fff;
  transition: background 0.3s, transform 0.3s;
  text-align: left;
  display: flex;
  align-items: center;
  border: 2px solid #041629; 
}

.menu-item:hover {
  background-color: #003366;
  transform: scale(1.03);
  border-color: #003366;
}
.menu-item i {
  margin-right: 10px; 
  font-size: 1.2rem; 
}

.menu-item span {
  flex-grow: 1;
  text-align: center;
}


.toggle-btn {
  background: none;
  border: none;
  color: #fff;
  font-size: 1.5rem;
  cursor: pointer;
  margin-bottom: 1rem;
}
</style>
