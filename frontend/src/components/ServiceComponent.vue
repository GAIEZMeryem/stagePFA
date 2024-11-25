<template>
  <div class="container">
    <profil-header></profil-header>
    <div class="content-container">
      <div class="menu-container d-flex justify-content-between align-items-center">
        <h1 class="primary-color">
          <i class="fas fa-hospital-alt me-2"></i> Gestion des services
        </h1>
        <button @click="showModal" class="btn btn-custom btn-add">
          <i class="fas fa-plus-circle me-2"></i> Ajouter
        </button>
      </div>

      <h3 class="mt-4 primary-color">
        <i class="fas fa-list me-2"></i> Liste des services
      </h3>
      <table class="table-modern">
        <thead>
          <tr>
            <th class="header-grey">Titre</th>
            <th class="header-grey">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="service in services" :key="service.id">
            <td>{{ service.titre }}</td>
            <td class="table-action-btns d-flex justify-content-start">
              <button @click="modifierService(service)" class="btn btn-custom btn-sm me-2">
                <i class="fas fa-edit me-2"></i> Éditer
              </button>
              <button @click="confirmerSuppression(service.id)" class="btn btn-custom btn-sm">
                <i class="fas fa-trash-alt me-2"></i> Supprimer
              </button>
            </td>
          </tr>
        </tbody>
      </table>

      
      <div v-if="isModalVisible" class="modal-overlay">
        <div class="modal-content">
          <div class="modal-header">
            <h2 class="primary-color">
              <i :class="serviceEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
              {{ serviceEnCours ? 'Modifier le service' : 'Ajouter un nouveau service' }}
            </h2>
            <button class="close-btn" @click="hideModal">×</button>
          </div>
          <form @submit.prevent="handleSubmit">
            <div class="form-group mb-3">
              <label for="titre" class="form-label">
                <i class="fas fa-tag me-2"></i> Titre
              </label>
              <input type="text" id="titre" v-model="form.titre" class="form-control" required>
            </div>
            <div class="d-flex justify-content-center">
              <button type="submit" class="btn btn-custom">
                <i :class="serviceEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
                {{ serviceEnCours ? 'Éditer' : 'Ajouter' }}
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import serviceService from '../services/ServiceService';
import ProfilHeader from './ProfilHeader.vue';


export default {
  components:{
    ProfilHeader
  },
  name: 'ServiceComponent',
  data() {
    return {
      services: [],
      form: {
        titre: ''
      },
      serviceEnCours: null,
      isModalVisible: false
    };
  },
  methods: {
    getServices() {
      serviceService.getServices()
        .then((response) => {
          this.services = response.data;
        })
        .catch((error) => {
          console.error("Il y avait une erreur lors de la récupération des services:", error);
        });
    },
    showModal() {
      this.isModalVisible = true;
    },
    hideModal() {
      this.isModalVisible = false;
      this.resetForm();
    },
    modifierService(service) {
      this.serviceEnCours = { ...service };
      this.form.titre = service.titre;
      this.showModal();
    },
    supprimerService(id) {
      if (confirm("Êtes-vous sûr de vouloir supprimer ce service ?")) {
        serviceService.supprimerService(id)
          .then(() => {
            this.getServices();
            alert("Le service a été supprimé avec succès !");
          })
          .catch((error) => {
            console.error("Erreur lors de la suppression du service:", error);
          });
      }
    },
    handleSubmit() {
      if (this.serviceEnCours) {
        this.mettreAJourService();
      } else {
        this.ajouterService();
      }
    },
    ajouterService() {
      serviceService.ajouterService(this.form)
        .then(() => {
          this.getServices();
          this.hideModal();
          alert("Le service a été ajouté avec succès !");
        })
        .catch((error) => {
          console.error("Erreur lors de l'ajout du service:", error);
        });
    },
    mettreAJourService() {
      serviceService.mettreAJourService(this.serviceEnCours.id, this.form)
        .then(() => {
          this.getServices();
          this.hideModal();
          alert("Le service a été mis à jour avec succès !");
          this.serviceEnCours = null; 
        })
        .catch((error) => {
          console.error("Erreur lors de la mise à jour du service:", error);
        });
    },
    resetForm() {
      this.form.titre = '';
      this.serviceEnCours = null;
    },
    confirmerSuppression(id) {
      this.supprimerService(id);
    }
  },
  created() {
    this.getServices();
  }
}
</script>
