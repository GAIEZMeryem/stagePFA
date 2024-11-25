<template>
  <div class="container mt-5">
    <div class="text-center mb-4">
      <img src="@/assets/logo1.png" alt="Logo" class="img-fluid logo-img">
    </div>
    <div class="card shadow-lg rounded-card">
      <div class="card-body p-5">
        <h1 class="h3 mb-4 primary-color text-center">Se Connecter</h1>
        <form @submit.prevent="login">
          <div class="form-group mb-4">
            <input type="text" v-model="username" class="form-control" id="username" placeholder="Nom d'utilisateur" required>
          </div>
          <div class="form-group mb-4">
            <input type="password" v-model="password" class="form-control" id="password" placeholder="Mot de passe" required>
          </div>
          <button type="submit" class="btn btn-primary w-100">Connexion</button>
        </form>
        <div v-if="error" class="alert alert-danger mt-4">{{ error }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import signInService from '../services/SignInService';

export default {
  name: 'SignIn',
  data() {
    return {
      username: '',
      password: '',
      error: null
    };
  },
  methods: {
    login() {
      this.error = null;
      signInService.login(this.username, this.password)
        .then(response => {
          console.log("Réponse de la connexion:", response.data); 
          const { message, role, nomComplet } = response.data;

          if (message === "Login successful") {
            const normalizedRole = role.trim().toLowerCase(); 

            console.log("Role reçu:", normalizedRole); 
            console.log("Nom complet reçu:", nomComplet); 
           
            localStorage.setItem('isAuthenticated', 'true');
            localStorage.setItem('userRole', normalizedRole);
            localStorage.setItem('nomComplet', nomComplet);

            if (normalizedRole === 'admin') {
              this.$router.push('/ajouterTrimestre');
            } else if (normalizedRole === 'super_admin') {
              this.$router.push('/gestionUtilisateurs');
            } else if (normalizedRole === 'user') {
              this.$router.push('/tableauGarde');
            } else {
              this.error = "Rôle non reconnu.";
            }
          }
        })
        .catch(error => {
          console.error("Erreur lors de la connexion:", error);
          this.error = "Nom d'utilisateur ou mot de passe incorrect.";
        });
    }
  }
};
</script>


<style>

.container {
  max-width: 600px; 
  margin: auto;
  display: flex;           
  justify-content: center; 
  align-items: center;     
  min-height: 100vh;       
  flex-direction: column; 
}

.logo-img {
  max-width: 150px;
  margin-bottom: 20px;
}


.card {
  border: none;
  border-radius: 20px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  background: #ffffff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 30px;
}

.primary-color {
  color: #003366;
}

.form-control {
  border-radius: 8px;
  padding: 15px;
  font-size: 1rem;
  border: 1px solid #ced4da;
  background: #f8f9fa;
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: border-color 0.3s, box-shadow 0.3s;
}

.form-control:focus {
  border-color: #003366;
  box-shadow: 0 0 0 0.2rem rgba(0, 51, 102, 0.25);
}

::placeholder {
  color: #6c757d;
  opacity: 1;
}


.btn-primary {
  background: linear-gradient(45deg, #003366, #001f4d);
  border: none;
  padding: 10px;
  font-size: 1.1rem;
  font-weight: 300;
  border-radius: 8px;
  transition: background 0.3s, transform 0.3s;
}

.btn-primary:hover {
  background: linear-gradient(45deg, #00224b, #000f33);
  transform: translateY(-2px);
}

.alert-danger {
  color: #721c24;
  background-color: #f8d7da;
  border-color: #f5c6cb;
  border-radius: 8px;
  padding: 10px;
  margin-top: 20px;
}

.text-center {
  text-align: center;
}

.mb-3 {
  margin-bottom: 1rem;
}

.mb-4 {
  margin-bottom: 1.5rem;
}

.shadow-lg {
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
}

.rounded-card {
  border-radius: 20px;
}

.p-5 {
  padding: 3rem;
}

.w-100 {
  width: 100%;
}
</style>
