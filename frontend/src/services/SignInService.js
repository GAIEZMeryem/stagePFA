import axios from 'axios';
import { API_URLS } from '../api.config';

class SignInService {
 

  login(login, password) {
    return axios.post(`${API_URLS.authentification}/login`, { login, password })
      .then(response => {
        // Stocker le token dans localStorage après une connexion réussie
        localStorage.setItem('token', response.data.token); // Assumes the token is in response.data.token
        return response;
      });
  }
  

  
  assignRole(id, role) {
    return axios.put(`${API_URLS.authentification}/assignRole/${id}`, role, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}` 
      }
    });
  }

  getAllUsers() {
    return axios.get(`${API_URLS.authentification}/users`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}` 
      }
    });
  }

  getUserDetails() {
    return axios.get(`${API_URLS.authentification}/profile`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}` 
      }
    });
  }

}

export default new SignInService();
