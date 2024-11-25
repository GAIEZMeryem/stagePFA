import axios from 'axios';
import { API_URLS } from '../api.config';

export default {
  getServices() {
    return axios.get(API_URLS.services);
  },
  getService(id) {
    return axios.get(`${API_URLS.services}/${id}`);
  },
  ajouterService(service) {
    return axios.post(API_URLS.services, service);
  },
  mettreAJourService(id, service) {
    return axios.put(`${API_URLS.services}/${id}`, service);
  },
  supprimerService(id) {
    return axios.delete(`${API_URLS.services}/${id}`);
  }
};
