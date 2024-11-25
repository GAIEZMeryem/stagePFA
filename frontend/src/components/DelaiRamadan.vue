<template>
  <div class="content-container">
    
    <profil-header></profil-header>
    

    <div class="menu-container d-flex justify-content-between align-items-center mt-4">
      <h1 class="primary-color">
        <i class="fas fa-calendar-alt me-2"></i> Gestion des délais de Ramadan
      </h1>
      <button @click="showAddModal" class="btn btn-custom btn-add">
        <i class="fas fa-plus-circle me-2"></i> Ajouter
      </button>
    </div>

    <h3 class="mt-4 primary-color">
      <i class="fas fa-list me-2"></i> Liste des délais
    </h3>

    <table class="table table-modern">
      <thead>
        <tr>
          <th class="header-grey">Date Début</th>
          <th class="header-grey">Date Fin</th>
          <th class="header-grey">Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="delaiRamadan in delaisRamadan" :key="delaiRamadan.id">
          <td>{{ delaiRamadan.dateDebut }}</td>
          <td>{{ delaiRamadan.dateFin }}</td>
          <td class="table-action-btns d-flex justify-content-start">
            <button @click="showEditModal(delaiRamadan)" class="btn btn-custom btn-sm me-2">
              <i class="fas fa-edit me-2"></i> Éditer
            </button>
            <button @click="confirmerSuppression(delaiRamadan.id)" class="btn btn-custom btn-sm">
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
            <i :class="delaiRamadanEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
            {{ delaiRamadanEnCours ? 'Modifier le délai de Ramadan' : 'Ajouter un nouveau délai de Ramadan' }}
          </h2>
          <button class="close-btn" @click="hideModal">×</button>
        </div>
        <form @submit.prevent="handleSubmit">
          <div class="form-group mb-3">
            <label for="dateDebut" class="form-label">
              <i class="fas fa-calendar-day me-2"></i> Date Début
            </label>
            <input type="date" id="dateDebut" v-model="form.dateDebut" class="form-control" required>
          </div>
          <div class="form-group mb-3">
            <label for="dateFin" class="form-label">
              <i class="fas fa-calendar-day me-2"></i> Date Fin
            </label>
            <input type="date" id="dateFin" v-model="form.dateFin" class="form-control" required>
          </div>
          <div class="d-flex justify-content-center">
            <button type="submit" class="btn btn-custom me-2">
              <i :class="delaiRamadanEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
              {{ delaiRamadanEnCours ? 'Éditer' : 'Ajouter' }}
            </button>
            <button type="button" class="btn btn-custom" @click="hideModal">
              <i class="fas fa-times me-2"></i>
              {{ delaiRamadanEnCours ? 'Annuler l\'édition' : 'Annuler' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import delaiRamadanService from '../services/DelaiRamadanService';
import ProfilHeader from '../components/ProfilHeader.vue';  

export default {
  name: 'DelaiRamadan',
  components: {
    ProfilHeader,  
  },
  data() {
    return {
      delaisRamadan: [],
      form: {
        dateDebut: '',
        dateFin: ''
      },
      delaiRamadanEnCours: null,
      isModalVisible: false
    };
  },
  methods: {
    getDelaisRamadan() {
      delaiRamadanService.getDelaisRamadan()
        .then((response) => {
          this.delaisRamadan = response.data;
        })
        .catch(error => {
          console.error("Il y avait une erreur lors de la récupération des délais de Ramadan:", error);
        });
    },
    showAddModal() {
      this.resetForm();
      this.delaiRamadanEnCours = null;
      this.isModalVisible = true;
    },
    showEditModal(delaiRamadan) {
      this.delaiRamadanEnCours = { ...delaiRamadan };
      this.form.dateDebut = delaiRamadan.dateDebut;
      this.form.dateFin = delaiRamadan.dateFin;
      this.isModalVisible = true;
    },
    hideModal() {
      this.isModalVisible = false;
      this.resetForm();
    },
    supprimerDelaiRamadan(id) {
      if (confirm("Êtes-vous sûr de vouloir supprimer ce délai de Ramadan ?")) {
        delaiRamadanService.supprimerDelaiRamadan(id)
          .then(() => {
            this.getDelaisRamadan();
            alert("Le délai de Ramadan a été supprimé avec succès !");
          })
          .catch(error => {
            console.error("Erreur lors de la suppression du délai de Ramadan:", error);
          });
      }
    },
    handleSubmit() {
      if (this.delaiRamadanEnCours) {
        this.mettreAJourDelaiRamadan();
      } else {
        this.ajouterDelaiRamadan();
      }
    },
    ajouterDelaiRamadan() {
      delaiRamadanService.ajouterDelaiRamadan(this.form)
        .then(() => {
          this.getDelaisRamadan();
          this.hideModal();
          alert("Le délai de Ramadan a été ajouté avec succès !");
        })
        .catch(error => {
          console.error("Erreur lors de l'ajout du délai de Ramadan:", error);
        });
    },
    mettreAJourDelaiRamadan() {
      delaiRamadanService.mettreAJourDelaiRamadan(this.delaiRamadanEnCours.id, this.form)
        .then(() => {
          this.getDelaisRamadan();
          this.hideModal();
          alert("Le délai de Ramadan a été mis à jour avec succès !");
          this.delaiRamadanEnCours = null;
        })
        .catch(error => {
          console.error("Erreur lors de la mise à jour du délai de Ramadan:", error);
        });
    },
    confirmerSuppression(id) {
      if (confirm("Êtes-vous sûr de vouloir supprimer ce délai de Ramadan ?")) {
        this.supprimerDelaiRamadan(id);
      }
    },
    resetForm() {
      this.form.dateDebut = '';
      this.form.dateFin = '';
      this.delaiRamadanEnCours = null;
    }
  },
  mounted() {
    this.getDelaisRamadan();
  }
};
</script>
