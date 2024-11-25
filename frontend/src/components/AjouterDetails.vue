<template>
 <div class="container page-details-tableau">
  <profil-header></profil-header>
    
  <div class="container">
    
    <h1>Les affectations du personel pour le Mois {{ mois }} de l'année {{ annee }}</h1>
    <h2>Service: {{ serviceTitre }}</h2>
    <h2> {{ typeGarde }}</h2>

    <table v-if="typeGarde === 'Permanence'" class="table">
      <thead>
        <tr>
          <th>Date</th>
          <th>Nom du Personnel</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="day in daysInMonth" :key="day.date">
          <td>{{ day.date }}</td>
          <td>
            <select v-model="selectedPersonnels[day.date]" @change="assignPersonnelPermanence(day.date)">
              <option v-for="personnel in personnels" :key="personnel.id" :value="personnel.id">
                {{ personnel.nomComplet }}
              </option>
            </select>
          </td>
        </tr>
      </tbody>
    </table>

    <table v-else-if="typeGarde === 'Garde' && nombrePersonnelGardeNumber === 1" class="table">
      <thead>
        <tr>
          <th>Date</th>
          <th>Nom du Personnel de Jour</th>
          <th>Nom du Personnel de Nuit</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="day in daysInMonth" :key="day.date">
          <td>{{ day.date }}</td>
          <td>
            <select v-model="selectedPersonnels[day.date + '-jour-1']" @change="assignPersonnel(day.date, 0, 1)">
              <option v-for="personnel in personnels" :key="personnel.id" :value="personnel.id">
                {{ personnel.nomComplet }}
              </option>
            </select>
          </td>
          <td>
            <select v-model="selectedPersonnels[day.date + '-nuit-1']" @change="assignPersonnel(day.date, 1, 1)">
              <option v-for="personnel in personnels" :key="personnel.id" :value="personnel.id">
                {{ personnel.nomComplet }}
              </option>
            </select>
          </td>
        </tr>
      </tbody>
    </table>

    <table v-else-if="typeGarde === 'Garde' && nombrePersonnelGardeNumber === 2" class="table">
      <thead>
        <tr>
          <th>Date</th>
          <th>Nom du Personnel de Jour 1</th>
          <th>Nom du Personnel de Jour 2</th>
          <th>Nom du Personnel de Nuit 1</th>
          <th>Nom du Personnel de Nuit 2</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="day in daysInMonth" :key="day.date">
          <td>{{ day.date }}</td>
          <td>
            <select v-model="selectedPersonnels[day.date + '-jour-1']" @change="assignPersonnel(day.date, 0, 1)">
              <option v-for="personnel in personnels" :key="personnel.id" :value="personnel.id">
                {{ personnel.nomComplet }}
              </option>
            </select>
          </td>
          <td>
            <select v-model="selectedPersonnels[day.date + '-jour-2']" @change="assignPersonnel(day.date, 0, 2)">
              <option v-for="personnel in personnels" :key="personnel.id" :value="personnel.id">
                {{ personnel.nomComplet }}
              </option>
            </select>
          </td>
          <td>
            <select v-model="selectedPersonnels[day.date + '-nuit-1']" @change="assignPersonnel(day.date, 1, 1)">
              <option v-for="personnel in personnels" :key="personnel.id" :value="personnel.id">
                {{ personnel.nomComplet }}
              </option>
            </select>
          </td>
          <td>
            <select v-model="selectedPersonnels[day.date + '-nuit-2']" @change="assignPersonnel(day.date, 1, 2)">
              <option v-for="personnel in personnels" :key="personnel.id" :value="personnel.id">
                {{ personnel.nomComplet }}
              </option>
            </select>
          </td>
        </tr>
      </tbody>
    </table>

    
    
  </div>
</div>
</template>

<script>
import PersonnelTableauGardeService from '@/services/PersonnelTableauGardeService';
import ProfilHeader from './ProfilHeader.vue';

export default {
  components:{
    ProfilHeader
  },
  props: {
    tableauId: {
      type: String,
      required: true
    },
    typeGarde: {
      type: String,
      required: true
    },
    mois: {
      type: Number,
      required: true
    },
    annee: {
      type: Number,
      required: true
    },
    serviceTitre: {
      type: String,
      required: true
    },
    nombrePersonnelGarde: {
      type: [Number, String],
      required: true,
      validator(value) {
        return !isNaN(Number(value));
      }
    }
  },
  data() {
    return {
      personnels: [],
      selectedPersonnels: {},
      dataFetched: true
    };
  },
  computed: {
    daysInMonth() {
      const days = [];
      const date = new Date(this.annee, this.mois, 0);
      const numDays = date.getDate();
      for (let i = 1; i <= numDays; i++) {
        days.push({ date: `${i}-${this.mois}-${this.annee}` });
      }
      return days;
    },

    nombrePersonnelGardeNumber() {
      return Number(this.nombrePersonnelGarde);
    }
  },
  methods: {
    async fetchPersonnels() {
      try {
        const response = await PersonnelTableauGardeService.getPersonnels();
        this.personnels = response.data;
      } catch (error) {
        console.error('Erreur lors de la récupération des personnels:', error);
      }
    },
    async fetchAssignedPersonnelsPermanence() {
      try {
        const response = await PersonnelTableauGardeService.getPersonnelTableauGardesByTableauGardeId(this.tableauId);
        if (response.data.length > 0) {
          console.log('Données récupérées pour Permanence:', response.data);
          this.selectedPersonnels = response.data.reduce((acc, personnel) => {
            const key = `${personnel.dateGarde}`;
            acc[key] = personnel.personnel.id;
            return acc;
          }, {});
          this.dataFetched = true;
        } else {
          this.dataFetched = false;
        }
      } catch (error) {
        console.error('Erreur lors de la récupération des personnels assignés:', error);
        this.dataFetched = false;
      }
    },
    async fetchAssignedPersonnelsGarde1() {
      try {
        const response = await PersonnelTableauGardeService.getPersonnelTableauGardesByTableauGardeId(this.tableauId);
        if (response.data.length > 0) {
          console.log('Données récupérées pour Garde 1:', response.data);
          this.selectedPersonnels = response.data.reduce((acc, personnel) => {
            const key = `${personnel.dateGarde}-${personnel.etatGarde === 0 ? 'jour' : 'nuit'}-1`;
            acc[key] = personnel.personnel.id;
            return acc;
          }, {});
          this.dataFetched = true;
        } else {
          this.dataFetched = false;
        }
      } catch (error) {
        console.error('Erreur lors de la récupération des personnels assignés:', error);
        this.dataFetched = false;
      }
    },
    async fetchAssignedPersonnelsGarde2() {
      try {
        const response = await PersonnelTableauGardeService.getPersonnelTableauGardesByTableauGardeId(this.tableauId);
        if (response.data.length > 0) {
          console.log('Données récupérées pour Garde 2:', response.data);
          this.selectedPersonnels = response.data.reduce((acc, personnel) => {
            const key = `${personnel.dateGarde}-${personnel.etatGarde === 0 ? 'jour' : 'nuit'}-${personnel.numero}`;
            acc[key] = personnel.personnel.id;
            return acc;
          }, {});
          this.dataFetched = true;
        } else {
          this.dataFetched = false;
        }
      } catch (error) {
        console.error('Erreur lors de la récupération des personnels assignés:', error);
        this.dataFetched = false;
      }
    },
    async assignPersonnelPermanence(date) {
      const key = `${date}`;
      const personnelId = this.selectedPersonnels[key];
      if (personnelId) {
        const personnel = this.personnels.find(p => p.id === personnelId);
        const personnelData = {
          dateGarde: date,
          taux: personnel.fonction.taux,
          igr: personnel.taux,
          etatGarde: 0,  // Par défaut pour permanence
          personnel: { id: personnel.id },
          tableau: { id: this.tableauId },
          numero: 0  // Toujours 0 pour Permanence
        };

        try {
          await PersonnelTableauGardeService.createPersonnelTableauGarde(personnelData);
        } catch (error) {
          console.error(`Erreur lors de l'assignation du personnel pour la date ${date}:`, error);
        }
      }
    },
    async assignPersonnel(date, etatGarde, numero) {
      const key = `${date}-${etatGarde === 0 ? 'jour' : 'nuit'}-${numero}`;
      const personnelId = this.selectedPersonnels[key];
      if (personnelId) {
        const personnel = this.personnels.find(p => p.id === personnelId);
        const personnelData = {
          dateGarde: date,
          taux: personnel.fonction.taux,
          igr: personnel.taux,
          etatGarde: etatGarde,
          personnel: { id: personnel.id },
          tableau: { id: this.tableauId },
          numero: numero
        };

        try {
          await PersonnelTableauGardeService.createPersonnelTableauGarde(personnelData);
        } catch (error) {
          console.error(`Erreur lors de l'assignation du personnel pour la date ${date}:`, error);
        }
      }
    },
    async enregistrerTousLesPersonnels() {
      if (this.typeGarde === 'Permanence') {
        for (const key in this.selectedPersonnels) {
          await this.assignPersonnelPermanence(key);
        }
      } else {
        for (const key in this.selectedPersonnels) {
          const [date, periode, numero] = key.split('-');
          const etatGarde = periode === 'nuit' ? 1 : 0;
          await this.assignPersonnel(date, etatGarde, numero);
        }
      }
      alert('Tous les personnels ont été enregistrés avec succès.');
      this.checkAndFetchData(); 
    },
    async checkAndFetchData() {
      try {
        const existsResponse = await PersonnelTableauGardeService.existsTableauId(this.tableauId);
        if (existsResponse.data) {
          if (this.typeGarde === 'Permanence') {
            await this.fetchAssignedPersonnelsPermanence();
          } else if (this.typeGarde === 'Garde' && this.nombrePersonnelGardeNumber === 1) {
            await this.fetchAssignedPersonnelsGarde1();
          } else if (this.typeGarde === 'Garde' && this.nombrePersonnelGardeNumber === 2) {
            await this.fetchAssignedPersonnelsGarde2();
          }
        } else {
          this.dataFetched = false;
        }
      } catch (error) {
        console.error("Erreur lors de la vérification de l'existence du tableau de garde:", error);
        this.dataFetched = false;
      }
    }
  },
  async mounted() {
    await this.fetchPersonnels();
    await this.checkAndFetchData();
  }
};
</script>

<style scoped>
.page-details-tableau .table {
  width: 90%; 
  margin-left: auto;
  margin-right: auto;
}
.table {
  width: 200%;
  border-collapse: collapse;
  margin-top: 20px;
}
.table th,
.table td {
  border: 1px solid #ccc;
  padding: 10px;
  text-align: left;
}
</style>