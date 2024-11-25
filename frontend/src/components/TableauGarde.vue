<template>
  <div class="container">
    <profil-header></profil-header>
    <div class="header-container">
      <h1 class="primary-color">
        <i class="fas fa-calendar-alt me-2"></i> Gestion des Tableaux de Garde
      </h1>
      <button @click="showAddTableauForm" class="btn-custom btn-add">
        <i class="fas fa-plus-circle me-2"></i> Ajouter
      </button>
    </div>

    <div class="table-container">
      <h3 class="mt-4 primary-color">
        <i class="fas fa-list me-2"></i> Liste des Tableaux de Garde
      </h3>
      <table class="table-modern">
        <thead>
          <tr>
            <th class="header-grey">Mois</th>
            <th class="header-grey">Année</th>
            <th class="header-grey">Type de Garde</th>
            <th class="header-grey">Observation</th>
            <th class="header-grey">Nombre de Personnel</th>
            <th class="header-grey">Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="tableau in tableaux" :key="tableau.id" :class="{ 'selected-row': selectedTableau && selectedTableau.id === tableau.id }">
            <td>{{ tableau.mois }}</td>
            <td>{{ tableau.annee }}</td>
            <td>{{ tableau.typeGarde }}</td>
            <td>{{ tableau.observation }}</td>
            <td>{{ tableau.nombrePersonnelGarde }}</td>
            <td class="table-action-btns">
              <button @click="editTableau(tableau)" class="btn-custom btn-edit">
                <i class="fas fa-edit me-2"></i> Modifier
              </button>
              <button @click="deleteTableau(tableau.id)" class="btn-custom btn-delete">
                <i class="fas fa-trash-alt me-2"></i> Supprimer
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

  
    <div v-if="isFormVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h3 class="modal-title">
            <i :class="selectedTableau ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
            {{ selectedTableau ? 'Modifier Tableau de Garde' : 'Ajouter Tableau de Garde' }}
          </h3>
          <button class="close-btn" @click="hideForm"><i class="fas fa-times"></i></button>
        </div>
        <form @submit.prevent="handleSubmit" class="form-container">
          <div class="form-group">
            <label for="trimestre" class="form-label">
              <i class="fas fa-calendar me-2"></i> Trimestre:
            </label>
            <select id="trimestre" v-model="form.trimestre" @change="updateAnneeFromTrimestre" :disabled="!!selectedTableau" required>
              <option v-for="trimestre in trimestres" :key="trimestre.id" :value="trimestre">
                {{ trimestre.numeroTrimestre }} - {{ trimestre.annee }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label for="annee" class="form-label">
              <i class="fas fa-calendar me-2"></i> Année:
            </label>
            <input id="annee" type="number" v-model="form.annee" :readonly="!!selectedTableau" required />
          </div>
          <div class="form-group">
            <label for="mois" class="form-label">
              <i class="fas fa-calendar me-2"></i> Mois:
            </label>
            <input id="mois" type="number" v-model="form.mois" :readonly="!!selectedTableau" required />
          </div>
          <div class="form-group">
            <label for="nombrePersonnelGarde" class="form-label">
              <i class="fas fa-users me-2"></i> Nombre de Personnel par Garde:
            </label>
            <input id="nombrePersonnelGarde" type="number" v-model="form.nombrePersonnelGarde" required />
          </div>
          <div class="form-group">
            <label for="observation" class="form-label">
              <i class="fas fa-comment me-2"></i> Observation:
            </label>
            <input id="observation" type="text" v-model="form.observation" />
          </div>
          <div class="form-group">
            <label for="typeGarde" class="form-label">
              <i class="fas fa-shield-alt me-2"></i> Type de Garde:
            </label>
            <select id="typeGarde" v-model="form.typeGarde" required>
              <option value="Garde">Garde</option>
              <option value="Permanence">Permanence</option>
            </select>
          </div>
          <div class="form-group">
            <label for="service" class="form-label">
              <i class="fas fa-building me-2"></i> Service:
            </label>
            <select id="service" v-model="form.service" :disabled="!!selectedTableau" required>
              <option v-for="service in services" :key="service.id" :value="service">
                {{ service.titre }}
              </option>
            </select>
          </div>
          <div class="d-flex justify-content-center">
            <button type="submit" class="btn-custom">
              <i :class="selectedTableau ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
              {{ selectedTableau ? 'Mettre à jour' : 'Ajouter' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import TableauGardeService from '../services/TableauGardeService';
import ProfilHeader from './ProfilHeader.vue';

export default {
  components:{
    ProfilHeader
  },
  data() {
    return {
      tableaux: [],
      selectedTableau: null,
      isFormVisible: false,
      form: {
        trimestre: null,
        annee: null,
        mois: null,
        nombrePersonnelGarde: null,
        observation: '',
        typeGarde: 'Garde',
        service: null,
      },
      trimestres: [],
      services: [],
    };
  },
  methods: {
    async fetchData() {
      try {
        const tableauResponse = await TableauGardeService.getAllTableauGardes();
        this.tableaux = tableauResponse.data;

        const trimestreResponse = await TableauGardeService.getOpenTrimestres();
        this.trimestres = trimestreResponse.data;

        const serviceResponse = await TableauGardeService.getServices();
        this.services = serviceResponse.data;
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    },
    showAddTableauForm() {
      this.selectedTableau = null;
      this.resetForm();
      this.isFormVisible = true;
    },
    hideForm() {
      this.isFormVisible = false;
    },
    selectTableau(tableau) {
      this.selectedTableau = tableau;
      this.form = { ...tableau };
      this.isFormVisible = false;
    },
    editTableau(tableau) {
      this.selectedTableau = tableau;
      this.form = {
        trimestre: tableau.trimestre,
        annee: tableau.annee,
        mois: tableau.mois,
        nombrePersonnelGarde: tableau.nombrePersonnelGarde,
        observation: tableau.observation,
        typeGarde: tableau.typeGarde,
        service: tableau.service
      };
      this.isFormVisible = true;
    },
    async deleteTableau(id) {
      if (confirm('Êtes-vous sûr de vouloir supprimer ce tableau de garde ?')) {
        try {
          await TableauGardeService.deleteTableauGarde(id);
          this.fetchData();
          alert('Tableau de garde supprimé avec succès');
        } catch (error) {
          console.error('Error deleting tableau:', error);
          alert('Erreur lors de la suppression du tableau de garde');
        }
      }
    },
    async handleSubmit() {
      try {
        if (!this.selectedTableau) {
          await TableauGardeService.createTableauGarde(this.form);
          this.handleSubmitAndNavigateToDetail(); 
        } else {
          await TableauGardeService.updateTableauGarde(this.selectedTableau.id, this.form);
          this.handleSubmitAndNavigateToDetail(); 
        }
      } catch (error) {
        console.error('Error saving tableau:', error);
        alert('Erreur lors de l\'enregistrement du tableau de garde');
      }
    },
    handleSubmitAndNavigateToDetail() {
      const navigateParams = {
        typeGarde: this.form.typeGarde,
        mois: Number(this.form.mois),
        annee: Number(this.form.annee),
        serviceTitre: this.form.service.titre, 
        nombrePersonnelGarde: Number(this.form.nombrePersonnelGarde) 
      };

      if (!this.selectedTableau) {
        TableauGardeService.createTableauGarde(this.form)
          .then(() => {
            this.$router.push({ name: 'AjouterDetailsWithId', params: navigateParams });
          })
          .catch(error => {
            console.error('Erreur lors de la création du tableau :', error);
            alert('Erreur lors de la création du tableau de garde');
          });
      } else {
        TableauGardeService.updateTableauGarde(this.selectedTableau.id, this.form)
          .then(() => {
            navigateParams.tableauId = this.selectedTableau.id;
            this.$router.push({ name: 'AjouterDetailsWithId', params: navigateParams });
          })
          .catch(error => {
            console.error('Erreur lors de la mise à jour du tableau :', error);
            alert('Erreur lors de la mise à jour du tableau de garde');
          });
      }
    },
    resetForm() {
      this.form = {
        trimestre: null,
        annee: null,
        mois: null,
        nombrePersonnelGarde: null,
        observation: '',
        typeGarde: 'Garde',
        service: null,
      };
    },
    updateAnneeFromTrimestre() {
      if (this.form.trimestre) {
        this.form.annee = this.form.trimestre.annee;
      }
    },
  },
  created() {
    this.fetchData();
  },
};
</script>

