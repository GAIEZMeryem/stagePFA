import axios from 'axios';
import { API_URLS } from '../api.config';
class UserManagementService {
    
    fetchUsers() {
        return axios.get(`${API_URLS.authentification}/users`)
            .then(response => response.data)
            .catch(error => {
                console.error("Erreur lors de la récupération des utilisateurs:", error);
                throw error;
            });
    }

    
    registerUser(userData) {
        return axios.post(`${API_URLS.authentification}/register`, userData)
            .then(response => response.data)
            .catch(error => {
                console.error("Erreur lors de l'enregistrement:", error);
                throw error;
            });
    }

  
    loginUser(userData) {
        return axios.post(`${API_URLS.authentification}/login`, userData)
            .then(response => response.data)
            .catch(error => {
                console.error("Erreur lors de la connexion:", error);
                throw error;
            });
    }

   
    updateUser(userData) {
        return axios.put(`${API_URLS.authentification}/update`, userData)
            .then(response => response.data)
            .catch(error => {
                console.error("Erreur lors de la mise à jour de l'utilisateur:", error);
                throw error;
            });
    }

    assignRoleToUser(userId, role) {
        return axios.put(`${API_URLS.authentification}/assignRole/${userId}`, role, {
            headers: {
                'Content-Type': 'text/plain'
            }
        })
            .then(response => response.data)
            .catch(error => {
                console.error("Erreur lors de l'attribution du rôle:", error);
                throw error;
            });
    }

    
    deleteUser(login) {
        return axios.delete(`${API_URLS.authentification}/delete/${login}`)
            .then(response => response.data)
            .catch(error => {
                console.error("Erreur lors de la suppression de l'utilisateur:", error);
                throw error;
            });
    }
}

export default new UserManagementService();
