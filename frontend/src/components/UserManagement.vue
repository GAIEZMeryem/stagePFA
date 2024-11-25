<template>
  <div>
    <profil-header></profil-header>
    <div class="header">
      
      <h1 class="primary-color"><i class="fas fa-user-cog me-2"></i> Gestion des utilisateurs :</h1>
      <button class="add-user-button" @click="openUserModal()">
        <i class="fas fa-user-plus"></i> Ajouter un utilisateur
      </button>
    </div>
  
    <div class="user-table">
      <div class="filters">
        <span>Tous ({{ users.length }})</span>
        <span>Administrateurs ({{ countRole('ADMIN') }})</span>
        <span>SuperAdministrateurs ({{ countRole('SUPER_ADMIN') }})</span>
        <span>Utilisateurs ({{ countRole('USER') }})</span>
      </div>

      <table>
        <thead>
          <tr>
            <th>Nom</th>
            <th>Rôle</th>
            <th>Vu</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.nomComplet }}</td>
            <td>{{ user.role }}</td>
            <td><input type="checkbox" v-model="user.vu" disabled></td>
            <td>
              <button class="view-button" @click="viewUser(user)">
                <i class="fas fa-eye"></i> Voir
              </button>
              <button class="edit-button" @click="editUser(user)">
                <i class="fas fa-edit"></i> Modifier
              </button>
              <button class="delete-button" @click="deleteUser(user.login)">
                <i class="fas fa-trash-alt"></i> Supprimer
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>


    <div v-if="showModal" class="modal">
      <div class="modal-content">
        <h2>{{ editingUser ? 'Modifier Utilisateur' : 'Ajouter Utilisateur' }}</h2>
        <form @submit.prevent="saveUser">
          <div>
            <label>Nom Complet:</label>
            <input v-model="form.nomComplet" required />
          </div>
          <div>
            <label>Login:</label>
            <input v-model="form.login" required />
          </div>
          <div>
            <label>Service:</label>
            <select v-model="form.service" required>
              <option v-for="service in services" :key="service.id" :value="service">
                {{ service.titre }}
              </option>
            </select>
          </div>
          <div v-if="!editingUser">
            <label>Mot de Passe:</label>
            <input v-model="form.password" type="password" required />
          </div>
          <div>
            <label>Rôle:</label>
            <select v-model="form.role" required>
              <option value="USER">Utilisateur</option>
              <option value="ADMIN">Administrateur</option>
              <option value="SUPER_ADMIN">Super Administrateur</option>
             
            </select>
          </div>
          <button type="submit">
            <i class="fas fa-save"></i> {{ editingUser ? 'Mettre à jour' : 'Ajouter' }}
          </button>
          <button type="button" @click="closeModal()">
            <i class="fas fa-times"></i> Annuler
          </button>
        </form>
      </div>
    </div>

    
    <div v-if="showUserDetailsModal" class="modal">
      <div class="modal-content">
        <h2>Détails de l'utilisateur</h2>
        <p><strong>Nom Complet:</strong> {{ selectedUser.nomComplet }}</p>
        <p><strong>Login:</strong> {{ selectedUser.login }}</p>
        <p><strong>Service:</strong> {{ selectedUser.service ? selectedUser.service.titre : 'Aucun' }}</p>
        <p><strong>Rôle:</strong> {{ selectedUser.role }}</p>
        <button type="button" @click="closeUserDetailsModal()">
          <i class="fas fa-times"></i> Fermer
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import '@fortawesome/fontawesome-free/css/all.css';
import UserManagementService from '@/services/UserManagementService';
import ServiceService from '@/services/ServiceService';
import ProfilHeader from './ProfilHeader.vue';

export default {
  components:{
    ProfilHeader
  },
  data() {
    return {
      users: [],
      services: [],
      showModal: false,
      showUserDetailsModal: false,
      form: {
        nomComplet: '',
        login: '',
        service: null,
        password: '',
        role: 'USER',
      },
      selectedUser: null,
      editingUser: null,
      currentPage: 1,
      totalPages: 2, 
    };
  },
  mounted() {
    this.fetchUsers();
    this.fetchServices();
  },
  methods: {
    async fetchUsers() {
      try {
        this.users = await UserManagementService.fetchUsers();
      } catch (error) {
        console.error('Erreur lors de la récupération des utilisateurs:', error);
      }
    },
    async fetchServices() {
      try {
        const response = await ServiceService.getServices();
        this.services = response.data;
      } catch (error) {
        console.error('Erreur lors de la récupération des services:', error);
      }
    },
    openUserModal() {
      this.editingUser = null;
      this.resetForm();
      this.showModal = true;
    },
    editUser(user) {
      this.editingUser = user;
      this.form = {
        nomComplet: user.nomComplet,
        login: user.login,
        service: user.service,
        password: '',
        role: user.role,
      };
      this.showModal = true;
    },
    async saveUser() {
      try {
        const formData = { ...this.form };
        if (this.editingUser && !formData.password) {
          formData.password = this.editingUser.password;
        }
        formData.id = this.editingUser ? this.editingUser.id : null;

        if (this.editingUser) {
          await UserManagementService.updateUser(formData);
        } else {
          await UserManagementService.registerUser(formData);
        }
        this.fetchUsers();
        this.closeModal();
      } catch (error) {
        console.error('Erreur lors de l\'enregistrement de l\'utilisateur:', error);
      }
    },
    async deleteUser(login) {
      if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
        try {
          await UserManagementService.deleteUser(login);
          this.fetchUsers();
        } catch (error) {
          console.error('Erreur lors de la suppression de l\'utilisateur:', error);
        }
      }
    },
    viewUser(user) {
      this.selectedUser = user;
      this.showUserDetailsModal = true;
    },
    closeUserDetailsModal() {
      this.showUserDetailsModal = false;
      this.selectedUser = null;
    },
    resetForm() {
      this.form = {
        nomComplet: '',
        login: '',
        service: null,
        password: '',
        role: 'USER',
      };
    },
    closeModal() {
      this.showModal = false;
      this.resetForm();
    },
    countRole(role) {
      return this.users.filter(user => user.role === role).length;
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++;
        this.fetchUsers();
      }
    },
    prevPage() {
      if (this.currentPage > 1) {
        this.currentPage--;
        this.fetchUsers();
      }
    },
  },
};
</script>

<style >
.user-management {
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.add-user-button {
  background-color: #003366;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-user-button:hover {
  background-color: #003366;
}

.user-table {
  margin-top: 20px;
}

.filters {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.filters span {
  font-weight: bold;
  margin-right: 15px;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

th, td {
  border: 1px solid #dee2e6;
  padding: 12px;
  text-align: left;
}

th {
  background-color: #e9ecef;
}

.view-button, .edit-button, .delete-button {
  padding: 6px 12px;
  border: none;
  border-radius: 4px;
  color: white;
  margin-right: 5px;
}

.view-button {
  background-color: #003366;
}

.edit-button {
  background-color: #003366;
}

.delete-button {
  background-color: #dc3545;
}

button[type="submit"] {
  background-color: #003366;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button[type="submit"]:hover {
  background-color: #0056b3;
}

button[type="button"] {
  background-color: #6c757d;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 10px;
}

button[type="button"]:hover {
  background-color: #5a6268;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1050;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 500px;
}

.modal-content h2 {
  margin-top: 0;
}

.modal-content form div {
  margin-bottom: 15px;
}

.modal-content form label {
  display: block;
  font-weight: bold;
}

.modal-content form input, .modal-content form select {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border-radius: 4px;
  border: 1px solid #ced4da;
}

.modal-content form button {
  margin-top: 15px;
}
</style>
