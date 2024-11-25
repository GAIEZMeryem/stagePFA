<template>
  <div class="container">
    <profil-header></profil-header>
    <div class="content-container">
    
      <div class="menu-container d-flex justify-content-between align-items-center">
        <h1 class="primary-color"><i class="fas fa-calendar me-2"></i> Gestion des trimestres :</h1>
        <button class="btn btn-custom" @click="showAddTrimestreForm">
          <i class="fas fa-plus-circle me-2"></i> Ajouter
        </button>
      </div>

      <div class="table-container">
        <h3 class="mt-4 primary-color"><i class="fas fa-list me-2"></i> Liste des trimestres</h3>
        <table class="table table-modern">
          <thead>
            <tr>
              <th>Année</th>
              <th>Trimestre</th>
              <th>État</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="trimestre in trimestres"
              :key="trimestre.id"
              @click="selectTrimestre(trimestre)"
              :class="{ 'selected-row': selectedTrimestre && selectedTrimestre.id === trimestre.id }"
            >
              <td>{{ trimestre.annee }}</td>
              <td>{{ trimestre.numeroTrimestre }}</td>
              <td>{{ trimestre.etatTrimestre ? 'Clôturé' : 'Ouvert' }}</td>
              <td class="action-buttons">
                <button class="btn btn-custom btn-edit" @click.stop="editTrimestre(trimestre)">
                  <i class="fas fa-edit"></i> Modifier
                </button>
                <button class="btn btn-custom btn-delete" @click.stop="deleteTrimestre(trimestre.id)">
                  <i class="fas fa-trash"></i> Supprimer
                </button>
                <button class="btn btn-custom btn-toggle" @click.stop="toggleTrimestre(trimestre)">
                  <i class="fas" :class="trimestre.etatTrimestre ? 'fa-lock' : 'fa-unlock'"></i>
                  {{ trimestre.etatTrimestre ? 'Ouvrir' : 'Clôturer' }}
                </button>
              </td>
            </tr>
          </tbody>
        </table>
        <h4 class="mt-4 primary-color"> Pour générer le rapport trimestriel, veuillez sélectionner un trimestre dans le tableau ci-dessus:</h4>
        <button class="btn btn-custom mt-3" @click="generatePDF">
          <i class="fas fa-file-pdf"></i> Générer 
        </button>
      </div>
    </div>


        <div v-if="isFormVisible" class="modal-overlay">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="primary-color text-center">{{ selectedTrimestre ? 'Modifier Trimestre' : 'Ajouter Trimestre' }}</h2>
          <button class="close-btn" @click="hideForm">×</button>
        </div>
        <form @submit.prevent="handleSubmit">
      
          <div class="form-group mb-3">
            <label for="annee" class="form-label">Année</label>
            <input
              type="number"
              id="annee"
              v-model.number="form.annee"
              class="form-control"
              placeholder="Saisir l'année"
              required
            >
          </div>

     
          <div class="form-group mb-3">
            <label for="trimestre" class="form-label">Trimestre</label>
            <select
              id="trimestre"
              v-model.number="form.numeroTrimestre"
              class="form-control"
              required
            >
              <option value="1">1er Trimestre</option>
              <option value="2">2ème Trimestre</option>
              <option value="3">3ème Trimestre</option>
              <option value="4">4ème Trimestre</option>
            </select>
          </div>

          <div v-if="services.length > 0" class="mb-3">
            <h2 class="primary-color text-center">Sélectionner les services</h2>
            <div class="service-list">
              <div
                v-for="service in services"
                :key="service.id"
                class="form-check"
              >
                <input
                  type="checkbox"
                  :id="'service-' + service.id"
                  v-model="selectedServices"
                  :value="service.id"
                  class="form-check-input"
                  :disabled="isEditing"
                >
                <label
                  :for="'service-' + service.id"
                  class="form-check-label"
                >{{ service.titre }}</label>
              </div>
            </div>
          </div>

         
          <div class="text-center">
            <button type="submit" class="btn btn-custom">
              <i class="fas" :class="selectedTrimestre ? 'fa-edit' : 'fa-plus'"></i>
              {{ selectedTrimestre ? 'Modifier' : 'Ajouter' }}
            </button>
          </div>
        </form>

        <div v-if="error" class="alert alert-danger mt-3">
          {{ error }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import jsPDF from 'jspdf';
import 'jspdf-autotable'; 
import TrimestreService from '../services/TrimestreService';
import ServiceService from '../services/ServiceService';
import ProfilHeader from './ProfilHeader.vue';
export default {
  components:{
    ProfilHeader
  },
  data() {
    return {
      trimestres: [],
      isFormVisible: false,
      selectedTrimestre: null,
      form: {
        annee: '',
        numeroTrimestre: '',
        etatTrimestre: 0,
        servicesIds: []
      },
      services: [],
      selectedServices: [],
      error: '',
      trimestreResults: [],  
    };
  },
  computed: {
    isEditing() {
      return !!this.selectedTrimestre;
    }
  },
  methods: {
    fetchTrimestres() {
      TrimestreService.getTrimestres()
        .then(response => {
          this.trimestres = response.data;
        })
        .catch(error => {
          this.error = "Erreur lors de la récupération des trimestres : " + error.message;
        });
    },
    fetchServices() {
      ServiceService.getServices()
        .then(response => {
          this.services = response.data;
        })
        .catch(error => {
          this.error = "Erreur lors de la récupération des services : " + error.message;
        });
    },
    handleSubmit() {
      this.form.servicesIds = this.selectedServices;
      const service = this.selectedTrimestre ? 
        TrimestreService.mettreAJourTrimestre(this.selectedTrimestre.id, this.form) :
        TrimestreService.ajouterTrimestre(this.form);
      
      service
        .then(() => {
          this.fetchTrimestres();
          this.hideForm();
          alert(`Trimestre ${this.selectedTrimestre ? 'modifié' : 'ajouté'} avec succès !`);
        })
        .catch(error => {
          this.error = `Erreur lors de ${this.selectedTrimestre ? 'la modification' : 'l\'ajout'} du trimestre : ${error.message}`;
        });
    },
    deleteTrimestre(id) {
      if (confirm("Êtes-vous sûr de vouloir supprimer ce trimestre ?")) {
        TrimestreService.supprimerTrimestre(id)
          .then(() => {
            this.fetchTrimestres();
            alert("Trimestre supprimé avec succès !");
          })
          .catch(error => {
            this.error = "Erreur lors de la suppression du trimestre : " + error.message;
          });
      }
    },
    openTrimestre(id) {
      if (confirm("Êtes-vous sûr de vouloir ouvrir ce trimestre ?")) {
        TrimestreService.openTrimestre(id)
          .then(() => {
            this.fetchTrimestres();
            alert("Trimestre ouvert avec succès !");
          })
          .catch(error => {
            this.error = "Erreur lors de l'ouverture du trimestre : " + error.message;
          });
      }
    },
    closeTrimestre(id) {
      if (confirm("Êtes-vous sûr de vouloir clôturer ce trimestre ?")) {
        TrimestreService.closeTrimestre(id)
          .then(() => {
            this.fetchTrimestres();
            alert("Trimestre clôturé avec succès !");
          })
          .catch(error => {
            this.error = "Erreur lors de la clôture du trimestre : " + error.message;
          });
      }
    },
    toggleTrimestre(trimestre) {
      trimestre.etatTrimestre ? this.openTrimestre(trimestre.id) : this.closeTrimestre(trimestre.id);
    },
    showAddTrimestreForm() {
      this.isFormVisible = true;
      this.selectedTrimestre = null;
      this.form = {
        annee: '',
        numeroTrimestre: '',
        etatTrimestre: 0,
        servicesIds: []
      };
      this.selectedServices = [];
      this.fetchServices();
    },
    hideForm() {
      this.isFormVisible = false;
      this.error = '';
    },
    editTrimestre(trimestre) {
      this.isFormVisible = true;
      this.selectedTrimestre = trimestre;
      this.form = { ...trimestre };
      this.selectedServices = trimestre.servicesIds || [];
      this.fetchServices();
    },
    selectTrimestre(trimestre) {
      this.selectedTrimestre = trimestre;
    },
    async generatePDF() {
  try {
    if (!this.selectedTrimestre) {
      alert("Veuillez sélectionner un trimestre pour générer le PDF.");
      return;
    }

    // On récupere les fiches pour trimestre selectionné
    const response = await TrimestreService.getFichesForTrimestre(this.selectedTrimestre.id);
    this.trimestreResults = response.data;

    if (this.trimestreResults.length === 0) {
      alert("Aucune fiche trouvée pour le trimestre sélectionné.");
      return;
    }

    const doc = new jsPDF();
    doc.setFontSize(12);

    //On Regroupe les résultats par service
    const groupedByService = this.trimestreResults.reduce((acc, fiche) => {
      const serviceId = fiche.serviceId; // ID du service
      if (!acc[serviceId]) {
        acc[serviceId] = [];
      }
      acc[serviceId].push(fiche);
      return acc;
    }, {});

    // on crée une table pour chaque service
    let startY = 30;
    for (const [serviceId, fiches] of Object.entries(groupedByService)) {
      const service = this.services.find(s => s.id === Number(serviceId));

      if (service) {
        doc.text(`Service: ${service.titre}`, 10, startY);
        startY += 10;

    
        const mois1 = fiches[0].t1; 
        const mois2 = fiches[0].t2; 
        const mois3 = fiches[0].t3; 

        const tableRows = fiches.map(fiche => [
          fiche.nomComplet,
          fiche.t1NbrNormal, fiche.t1NbrFerier, fiche.t1NombreHeure,
          fiche.t2NbrNormal, fiche.t2NbrFerier, fiche.t2NombreHeure,
          fiche.t3NbrNormal, fiche.t3NbrFerier, fiche.t3NombreHeure,
          fiche.totalHG,
          fiche.taux,
          fiche.brut,
          fiche.net
        ]);

        doc.autoTable({
          head: [
            [
              { content: 'Nom Complet', rowSpan: 2 },
              { content: mois1, colSpan: 3 }, 
              { content: mois2, colSpan: 3 }, 
              { content: mois3, colSpan: 3 }, 
              { content: 'Total HG', rowSpan: 2 },
              { content: 'Taux', rowSpan: 2 },
              { content: 'Brut', rowSpan: 2 },
              { content: 'Net', rowSpan: 2 }
            ],
            [
              { content: 'N.Normal' },
              { content: 'N.Ferier' },
              { content: 'Total des heures' },
              { content: 'N.Normal' },
              { content: 'N.Ferier' },
              { content: 'Total des heures' },
              { content: 'N.Normal' },
              { content: 'N.Ferier' },
              { content: 'Total des heures' },
            ]
          ],
          body: tableRows,
          startY: startY,
          theme: 'plain', //tableau simple
          styles: {
            lineColor: [0, 0, 0], // Couleur des bordures en noir
            lineWidth: 0.5, // Épaisseur des bordures
            fillColor: [255, 255, 255], // Fond blanc
            textColor: [0, 0, 0], // Texte en noir
            halign: 'center', // Centrer le texte
            valign: 'middle', // Centrer verticalement le texte
          },
          headStyles: {
            fillColor: [255, 255, 255], // Fond blanc pour l'en-tête
            textColor: [0, 0, 0], // Texte en noir pour l'en-tête
            lineWidth: 0.5, // Épaisseur des bordures de l'en-tête
            lineColor: [0, 0, 0], // Bordures noires pour l'en-tête
          },
          alternateRowStyles: {
            fillColor: [255, 255, 255],
          }
        });

        startY = doc.lastAutoTable.finalY + 10; 
      }
    }

    doc.save(`Fiches_${this.selectedTrimestre.numeroTrimestre}_${this.selectedTrimestre.annee}.pdf`);
  } catch (error) {
    console.error('Erreur lors de la génération du PDF:', error);
    alert('Erreur lors de la génération du PDF.');
  }
}



  },
  mounted() {
    this.fetchTrimestres();
    this.fetchServices();
  }
};
</script>



<style scoped >
.container {
  display: flex;
  padding: 2rem;
}
.content-container {
  width: 100%;
  margin-left: 20px;
}
.table-container {
  margin-top: 2rem;
}
.primary-color {
  color: #003366;
}
.btn-custom {
  background-color: #003366;
  color: #fff;
  border: none;
  padding: 0.5rem 1rem;
  margin-right: 0.5rem;
  display: flex;
  align-items: center;
  gap: 0.25rem;
}
.btn-edit {
  background-color: #28a745;
}
.btn-delete {
  background-color: #dc3545;
}
.btn-toggle {
  background-color: #ffc107;
}
.action-buttons {
  display: flex;
  gap: 0.5rem; 
  justify-content: center; 
  align-items: center; 
}
.table-modern {
  width: 100%;
  border-collapse: collapse;
}
.table-modern th,
.table-modern td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid #ddd;
}
.selected-row {
  background-color: #f0f8ff;
}
.modal-title {
  text-align: center;
  color: #003366; 
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}


.checkbox-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr); 
  gap: 1rem; 
  margin-top: 1rem;
}

.checkbox-item {
  display: flex;
  align-items: center;
}

.checkbox-item label {
  margin-left: 0.5rem;
}


.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  padding-top: 10rem;
  z-index: 1000;
}

.modal-content {
  background: #fff;
  border-radius: 10px;
  padding: 20px;
  width: 500px;
  box-shadow: 0px 4px 20px rgba(0, 0, 0, 0.1);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.close-btn {
  background: transparent;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
}

.form-control {
  border-radius: 5px;
}

.service-list {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.text-center {
  margin-top: 20px;
}

.btn-custom {
  background-color: #003366;
  color: #fff;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-custom:hover {
  background-color: #005399;
}
</style>