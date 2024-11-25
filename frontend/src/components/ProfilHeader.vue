<template>
    <header class="navbar">
      <div class="navbar-main">
        
        <div class="navbar-center">
          <input type="text" class="search-bar" placeholder="Rechercher " />
        </div>
        <div class="navbar-right">
          <i class="fas fa-sync-alt"></i> 
          <i class="fas fa-moon"></i> 
          <i class="fas fa-cog"></i> 
          <i class="fas fa-bell"></i> 
          <div class="profile">
            <img src="@/assets/utilisateur.png" alt="Photo de profil" class="profile-pic">
            <div class="profile-info">
              <p>{{ nomComplet }}</p>
              <span>{{ role }}</span>
            </div>
          </div>
        </div>
      </div>
    </header>
  </template>
  
  <script>
  export default {
    name: 'NavBar',
    data() {
      return {
        nomComplet: localStorage.getItem('nomComplet') || 'Nom complet',
        role: localStorage.getItem('userRole') || 'Rôle',
        isSidebarVisible: false 
      };
    },
    methods: {
      toggleSidebar() {
        
        this.isSidebarVisible = !this.isSidebarVisible;
      }
    },
    watch: {
      
      '$route'() {
        this.nomComplet = localStorage.getItem('nomComplet') || 'Nom complet';
        this.role = localStorage.getItem('userRole') || 'Rôle';
      }
    }
  };
  </script>
  
  <style scoped>
.navbar {
    display: flex;
    flex-direction: column;
    padding: 0;
    background-color: white;
    border-bottom: 2px solid #003366;
    position: fixed; 
    top: 0;
    left: 0;
    height: 11vh; 
    width: 100%;
    z-index: 1000; 
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}


.table-container,
.title-container {
  padding-top: 13vh; 
}


.navbar-main {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px 30px;
}


.horizontal-menu {
    display: flex;
    align-items: center;
    margin-left: 40px; 
}

.horizontal-menu ul {
    list-style: none;
    display: flex;
    margin: 0;
    padding: 0;
}

.horizontal-menu li {
    margin-right: 30px; 
}

.horizontal-menu a {
    text-decoration: none;
    color: #333;
    font-weight: bold;
    padding: 10px 16px; 
    border-radius: 6px;
    transition: background-color 0.3s, color 0.3s;
}

.horizontal-menu a:hover {
    background-color: #003366; 
    color: white; 
}

.navbar-center {
    flex-grow: 1;
    display: flex;
    justify-content: center;
}


.search-bar {
    padding: 8px 12px;
    border-radius: 8px;
    border: 1px solid #ccc;
    width: 350px;
    transition: border-color 0.3s, box-shadow 0.3s;
}

.search-bar:focus {
    border-color: #003366; 
    box-shadow: 0 0 5px rgba(22, 160, 133, 0.5); 
    outline: none;
}


.navbar-right {
    display: flex;
    align-items: center;
    gap: 30px; 
}

.navbar-right i {
    cursor: pointer;
    font-size: 20px;
    transition: color 0.3s;
}

.navbar-right i:hover {
    color: #003366; 
}


.profile {
    display: flex;
    align-items: center;
    cursor: pointer;
}

.profile-pic {
    width: 45px;
    height: 45px;
    border-radius: 50%;
    margin-right: 15px; 
    border: 2px solid #003366; 
}

.profile-info p {
    margin: 0;
    font-size: 15px;
    font-weight: bold;
    color: #003366; 
}

.profile-info span {
    font-size: 13px;
    color: gray;
}


.table-container,
.title-container {
    padding-top: 12vh; 
}

</style>
