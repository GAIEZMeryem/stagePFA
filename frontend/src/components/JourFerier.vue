<template>
  <div class="container">
    <profil-header></profil-header>
    <div class="content-container">
  
      <div class="header-container d-flex justify-content-between align-items-center">
        <h1 class="primary-color"><i class="fas fa-calendar-day me-2"></i> Gestion des jours fériés</h1>
        <button @click="showModal" class="btn btn-custom"><i class="fas fa-plus-circle me-2"></i> Ajouter</button>
      </div>

      <h3 class="mt-4 primary-color"><i class="fas fa-list me-2"></i> Liste des jours fériés</h3>
      <table class="table table-modern">
        <thead>
          <tr>
            <th class="header-grey">Titre</th>
            <th class="header-grey">Date</th>
            <th class="header-grey">Action</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="jourFerier in jourFeriers" :key="jourFerier.id">
            <td>{{ jourFerier.titre }}</td>
            <td>{{ jourFerier.date }}</td>
            <td class="d-flex justify-content-start">
              <button @click="modifierJourFerier(jourFerier)" class="btn btn-custom btn-sm me-2">
                <i class="fas fa-edit me-2"></i> Editer
              </button>
              <button @click="confirmerSuppression(jourFerier.id)" class="btn btn-custom btn-sm">
                <i class="fas fa-trash-alt me-2"></i> Supprimer
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

  
    <div v-if="isModalVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="primary-color">
            <i :class="jourFerierEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
            {{ jourFerierEnCours ? 'Modifier le jour férié' : 'Ajouter un nouveau jour férié' }}
          </h2>
          <button class="close-btn" @click="hideModal"><i class="fas fa-times"></i></button>
        </div>
        <form @submit.prevent="handleSubmit">
          <div class="form-group mb-3">
            <label for="titre" class="form-label"><i class="fas fa-font me-2"></i> Titre</label>
            <input type="text" id="titre" v-model="form.titre" class="form-control" required>
          </div>
          <div class="form-group mb-3">
            <label for="date" class="form-label"><i class="fas fa-calendar-alt me-2"></i> Date</label>
            <input type="date" id="date" v-model="form.date" class="form-control" required>
          </div>
          <div class="d-flex justify-content-center">
            <button type="submit" class="btn btn-custom me-2">
              <i :class="jourFerierEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
              {{ jourFerierEnCours ? 'Editer' : 'Ajouter' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import ProfilHeader from './ProfilHeader.vue';
import jourFerierService from '../services/JourFerierService';

export default {
  name: 'JourFerier',
  components: {
    ProfilHeader,
  },
  data() {
    return {
      jourFeriers: [],
      form: {
        titre: '',
        date: ''
      },
      jourFerierEnCours: null,
      isModalVisible: false,
    };
  },
  methods: {
    getJourFeriers() {
      jourFerierService.getJourFeriers().then((response) => {
        this.jourFeriers = response.data;
      }).catch(error => {
        console.error("Il y avait une erreur lors de la récupération des jours fériés:", error);
      });
    },
    showModal() {
      this.isModalVisible = true;
    },
    hideModal() {
      this.isModalVisible = false;
      this.resetForm();
    },
    modifierJourFerier(jourFerier) {
      this.jourFerierEnCours = { ...jourFerier };
      this.form.titre = jourFerier.titre;
      this.form.date = jourFerier.date;
      this.showModal();
    },
    supprimerJourFerier(id) {
      if (confirm("Êtes-vous sûr de vouloir supprimer ce jour férié ?")) {
        jourFerierService.supprimerJourFerier(id).then(() => {
          this.getJourFeriers();
          alert("Le jour férié a été supprimé avec succès !");
        }).catch(error => {
          console.error("Erreur lors de la suppression du jour férié:", error);
        });
      }
    },
    handleSubmit() {
      if (this.jourFerierEnCours) {
        this.mettreAJourJourFerier();
      } else {
        this.ajouterJourFerier();
      }
    },
    ajouterJourFerier() {
      jourFerierService.ajouterJourFerier(this.form).then(() => {
        this.getJourFeriers();
        this.hideModal();
        alert("Le jour férié a été ajouté avec succès !");
      }).catch(error => {
        console.error("Erreur lors de l'ajout du jour férié:", error);
      });
    },
    mettreAJourJourFerier() {
      jourFerierService.mettreAJourJourFerier(this.jourFerierEnCours.id, this.form).then(() => {
        this.getJourFeriers();
        this.hideModal();
        alert("Le jour férié a été mis à jour avec succès !");
        this.jourFerierEnCours = null;
      }).catch(error => {
        console.error("Erreur lors de la mise à jour du jour férié:", error);
      });
    },
    resetForm() {
      this.form.titre = '';
      this.form.date = '';
      this.jourFerierEnCours = null;
    },
    confirmerSuppression(id) {
      this.supprimerJourFerier(id);
    }
  },
  created() {
    this.getJourFeriers();
  }
}
</script>

<style>

.container {
  max-width: 1200px;
  margin: auto;
  padding-top: 20px;
}

.primary-color {
  color: #003366;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.btn-custom {
  background: linear-gradient(45deg, #003366, #001f4d);
  border: none;
  padding: 10px 15px;
  font-size: 1rem;
  font-weight: 600;
  border-radius: 25px;
  color: #fff;
  transition: background 0.3s, transform 0.3s;
  display: flex;
  align-items: center;
}

.btn-custom:hover {
  background: linear-gradient(45deg, #001f4d, #003366);
  transform: scale(1.05);
}

.table-modern {
  width: 100%;
  border-collapse: collapse;
}

.table-modern th,
.table-modern td {
  border: 1px solid #dee2e6;
  padding: 10px;
  text-align: left;
}

.table-modern th {
  background-color: #f8f9fa;
}

.header-grey {
  background-color: #e9ecef;
}


td.d-flex {
  display: flex;
  align-items: center;
  justify-content: start;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1050;
}

.modal-content {
  background: #fff;
  border-radius: 8px;
  padding: 20px;
  max-width: 600px;
  width: 100%;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.modal-header h2 {
  margin: 0;
  color: #003366;
}

.close-btn {
  background: transparent;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

.close-btn i {
  color: #003366;
}

.form-group {
  margin-bottom: 15px;
}

.form-label {
  display: flex;
  align-items: center;
}

.form-label i {
  margin-right: 10px;
}

.form-control {
  border-radius: 4px;
  border: 1px solid #ced4da;
  padding: 10px;
  width: 100%;
}

.d-flex {
  display: flex !important;
  align-items: center;
}

.justify-content-center {
  justify-content: center;
}

.justify-content-between {
  justify-content: space-between;
}

.justify-content-start {
  justify-content: start;
}

.me-2 {
  margin-right: 10px;
}
</style>
