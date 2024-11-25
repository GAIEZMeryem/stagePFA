<template>
  <div class="container">
    <profil-header></profil-header>
    <div class="content-container">
    
      <div class="menu-container d-flex justify-content-between align-items-center">
        <h1 class="primary-color"><i class="fas fa-user-plus me-2"></i> Gestion du personnel :</h1>
        <button class="btn btn-custom" @click="showAddPersonnelForm">
          <i class="fas fa-plus-circle me-2"></i> Ajouter
        </button>
      </div>

     
      <h3 class="mt-4 primary-color"><i class="fas fa-users me-2"></i> Liste des Personnels</h3>
      <table class="table-modern">
        <thead>
          <tr class="header-grey">
            <th>Nom Complet</th>
            <th>Service</th>
            <th>Fonction</th>
            <th>Taux</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="personnel in personnels" :key="personnel.id">
            <td>{{ personnel.nomComplet }}</td>
            <td>{{ getServiceTitle(personnel.service.id) }}</td>
            <td>{{ getFonctionTitle(personnel.fonction.id) }}</td>
            <td>{{ personnel.taux }}</td>
            <td class="table-action-btns d-flex justify-content-start">
              <button class="btn-custom btn-edit me-2" @click="modifierPersonnel(personnel)">
                <i class="fas fa-edit me-2"></i> Modifier
              </button>
              <button class="btn-custom btn-delete" @click="confirmSupprimerPersonnel(personnel.id)">
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
          <h3><i :class="personnelEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
            {{ personnelEnCours ? 'Modifier le Personnel' : 'Ajouter un Nouveau Personnel' }}
          </h3>
          <button class="close-btn" @click="hideForm"><i class="fas fa-times"></i></button>
        </div>
        <form class="form-container" @submit.prevent="submitForm">
          <div class="form-group">
            <label for="nomComplet"><i class="fas fa-user me-2"></i> Nom Complet:</label>
            <input type="text" v-model="personnel.nomComplet" required />
          </div>
          <div class="form-group">
            <label for="cin"><i class="fas fa-id-card me-2"></i> CIN:</label>
            <input type="text" v-model="personnel.cin" required />
          </div>
          <div class="form-group">
            <label for="imt"><i class="fas fa-hashtag me-2"></i> IMT:</label>
            <input type="text" v-model="personnel.imt" required />
          </div>
          <div class="form-group">
            <label for="taux"><i class="fas fa-percentage me-2"></i> Taux:</label>
            <input type="number" v-model="personnel.taux" step="0.01" required />
          </div>
          <div class="form-group">
            <label for="fonction"><i class="fas fa-briefcase me-2"></i> Fonction:</label>
            <select v-model="personnel.fonction.id" required>
              <option v-for="fonction in fonctions" :key="fonction.id" :value="fonction.id">
                {{ fonction.titre }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label for="service"><i class="fas fa-building me-2"></i> Service:</label>
            <select v-model="personnel.service.id" required>
              <option v-for="service in services" :key="service.id" :value="service.id">
                {{ service.titre }}
              </option>
            </select>
          </div>
          <button class="btn btn-custom" type="submit">
            <i :class="personnelEnCours ? 'fas fa-edit me-2' : 'fas fa-plus-circle me-2'"></i>
            {{ personnelEnCours ? 'Modifier' : 'Ajouter' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
import ProfilHeader from './ProfilHeader.vue';

export default {
  components: { ProfilHeader },
  data() {
    return {
      personnels: [],
      fonctions: [],
      services: [],
      personnel: {
        nomComplet: '',
        cin: '',
        imt: '',
        taux: null,
        fonction: { id: null },
        service: { id: null }
      },
      personnelEnCours: null,
      isFormVisible: false
    };
  },
  methods: {
    async fetchFonctionsAndServices() {
      try {
        const [fonctionsResponse, servicesResponse] = await Promise.all([
          axios.get('http://localhost:8080/api/fonctions'),
          axios.get('http://localhost:8080/api/services')
        ]);
        this.fonctions = fonctionsResponse.data;
        this.services = servicesResponse.data;
      } catch (error) {
        console.error(error);
      }
    },
    async fetchPersonnels() {
      try {
        const response = await axios.get('http://localhost:8080/api/personnels');
        this.personnels = response.data;
      } catch (error) {
        console.error(error);
      }
    },
    async submitForm() {
      try {
        if (this.personnelEnCours) {
          await axios.put(`http://localhost:8080/api/personnels/${this.personnelEnCours.id}`, this.personnel);
          alert('Personnel modifié avec succès');
        } else {
          await axios.post('http://localhost:8080/api/personnels', this.personnel);
          alert('Personnel ajouté avec succès');
        }
        this.resetForm();
        this.fetchPersonnels();
        this.hideForm();
      } catch (error) {
        console.error(error);
      }
    },
    modifierPersonnel(personnel) {
      this.personnelEnCours = { ...personnel };
      this.personnel = { ...personnel };
      this.isFormVisible = true;
    },
    confirmSupprimerPersonnel(id) {
      if (confirm("Êtes-vous sûr de vouloir supprimer ce personnel ?")) {
        this.supprimerPersonnel(id);
      }
    },
    async supprimerPersonnel(id) {
      try {
        await axios.delete(`http://localhost:8080/api/personnels/${id}`);
        alert('Personnel supprimé avec succès');
        this.fetchPersonnels();
      } catch (error) {
        console.error(error);
      }
    },
    resetForm() {
      this.personnel = {
        nomComplet: '',
        cin: '',
        imt: '',
        taux: null,
        fonction: { id: null },
        service: { id: null }
      };
      this.personnelEnCours = null;
    },
    showAddPersonnelForm() {
      this.resetForm();
      this.isFormVisible = true;
    },
    hideForm() {
      this.isFormVisible = false;
    },
    getFonctionTitle(id) {
      const fonction = this.fonctions.find(f => f.id === id);
      return fonction ? fonction.titre : 'N/A';
    },
    getServiceTitle(id) {
      const service = this.services.find(s => s.id === id);
      return service ? service.titre : 'N/A';
    }
  },
  created() {
    this.fetchFonctionsAndServices();
    this.fetchPersonnels();
  }
};
</script>
<style>

.container {
  display: flex;
  padding: 10rem;
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
  transform: translateY(-2px);
}


.btn-edit {
  background-color: #28a745;
}

.btn-delete {
  background-color: #dc3545;
}


.table-modern {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.table-modern th, .table-modern td {
  padding: 12px 15px;
  border: 1px solid #ddd;
}

.table-modern th {
  background-color: #f8f9fa;
  color: #333;
  text-align: left;
}

.table-modern tbody tr:nth-child(even) {
  background-color: #f2f2f2;
}

.table-modern tbody tr:hover {
  background-color: #e9ecef;
}


.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow-y: auto;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  width: 100%;
  max-width: 500px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #333;
}

.form-container {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 5px;
  display: block;
}

.form-group input, .form-group select {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.form-group input:focus, .form-group select:focus {
  outline: none;
  border-color: #003366;
}

.table-action-btns {
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.table-action-btns .btn-custom {
  margin-right: 10px;
}

.table-action-btns .btn-custom:last-child {
  margin-right: 0;
}
</style>