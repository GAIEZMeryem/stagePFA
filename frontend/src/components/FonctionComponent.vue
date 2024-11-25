<template>
  <div class="content-container">
    <profil-header></profil-header>
    <div class="menu-container d-flex justify-content-between align-items-center">
      <h1 class="primary-color">
        <i class="fas fa-user-md me-2"></i> Gestion des fonctions
      </h1>
      <button @click="showModal" class="btn btn-custom btn-add">
        <i class="fas fa-plus-circle me-2"></i> Ajouter
      </button>
    </div>

    <h3 class="mt-4 primary-color"><i class="fas fa-list me-2"></i> Liste des fonctions</h3>
    <table class="table table-modern">
      <thead>
        <tr>
          <th class="header-grey">Titre</th>
          <th class="header-grey">Taux</th>
          <th class="header-grey">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="fonction in fonctions" :key="fonction.id">
          <td>{{ fonction.titre }}</td>
          <td>{{ fonction.taux }}</td>
          <td class="table-action-btns d-flex justify-content-start">
            <button @click="modifierFonction(fonction)" class="btn btn-custom btn-edit me-2">
              <i class="fas fa-edit me-2"></i> Éditer
            </button>
            <button @click="supprimerFonction(fonction.id)" class="btn btn-custom btn-delete">
              <i class="fas fa-trash-alt me-2"></i> Supprimer
            </button>
          </td>
        </tr>
      </tbody>
    </table>

  
    <div v-if="isModalVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="primary-color">
            <i :class="fonctionEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
            {{ fonctionEnCours ? 'Modifier la fonction' : 'Ajouter une nouvelle fonction' }}
          </h3>
          <button class="close-btn" @click="hideModal"><i class="fas fa-times"></i></button>
        </div>
        <form @submit.prevent="handleSubmit" class="form-container">
          <div class="form-group">
            <label for="titre"><i class="fas fa-tag me-2"></i> Titre:</label>
            <input type="text" id="titre" v-model="form.titre" class="form-control" required>
          </div>
          <div class="form-group">
            <label for="taux"><i class="fas fa-percentage me-2"></i> Taux:</label>
            <input type="number" step="0.01" id="taux" v-model="form.taux" class="form-control" required>
          </div>
          <div class="d-flex justify-content-center">
            <button type="submit" class="btn btn-custom">
              <i :class="fonctionEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
              {{ fonctionEnCours ? 'Éditer' : 'Ajouter' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import fonctionService from '../services/FonctionService';
import ProfilHeader from './ProfilHeader.vue';

export default {
  components:{
    ProfilHeader
  },
  name: 'FonctionComponent',
  data() {
    return {
      fonctions: [],
      form: {
        titre: '',
        taux: 0
      },
      fonctionEnCours: null,
      isModalVisible: false 
    };
  },
  methods: {
    getFonctions() {
      fonctionService.getFonctions().then((response) => {
        this.fonctions = response.data;
      }).catch(error => {
        console.error("Il y avait une erreur lors de la récupération des fonctions:", error);
      });
    },
    showModal() {
      this.isModalVisible = true;
    },
    hideModal() {
      this.isModalVisible = false;
      this.resetForm();
    },
    modifierFonction(fonction) {
      this.fonctionEnCours = { ...fonction }; 
      this.form.titre = fonction.titre;
      this.form.taux = fonction.taux;
      this.showModal();
    },
    supprimerFonction(id) {
      if (confirm("Êtes-vous sûr de vouloir supprimer cette fonction ?")) {
        fonctionService.supprimerFonction(id).then(() => {
          this.getFonctions();
          alert("La fonction a été supprimée avec succès !");
        }).catch(error => {
          console.error("Erreur lors de la suppression de la fonction:", error);
        });
      }
    },
    handleSubmit() {
      if (this.fonctionEnCours) {
        this.mettreAJourFonction();
      } else {
        this.ajouterFonction();
      }
    },
    ajouterFonction() {
      fonctionService.ajouterFonction(this.form).then(() => {
        this.getFonctions();
        this.hideModal();
        alert("La fonction a été ajoutée avec succès !");
      }).catch(error => {
        console.error("Erreur lors de l'ajout de la fonction:", error);
      });
    },
    mettreAJourFonction() {
      fonctionService.mettreAJourFonction(this.fonctionEnCours.id, this.form).then(() => {
        this.getFonctions();
        this.hideModal();
        alert("La fonction a été mise à jour avec succès !");
        this.fonctionEnCours = null;
      }).catch(error => {
        console.error("Erreur lors de la mise à jour de la fonction:", error);
      });
    },
    resetForm() {
      this.form.titre = '';
      this.form.taux = 0;
      this.fonctionEnCours = null;
    }
  },
  created() {
    this.getFonctions();
  }
};
</script>
<style>
.btn-add {
  margin-left: 20px; 
}
</style>
