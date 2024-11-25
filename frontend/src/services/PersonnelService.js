import axios from 'axios';
import { API_URLS } from '../api.config';

class PersonnelService {
  getPersonnels() {
    return axios.get(API_URLS.personnel);
  }

  ajouterPersonnel(personnel) {
    return axios.post(API_URLS.personnel, personnel);
  }

  mettreAJourPersonnel(id, personnel) {
    return axios.put(`${API_URLS.personnel}/${id}`, personnel);
  }

  supprimerPersonnel(id) {
    return axios.delete(`${API_URLS.personnel}/${id}`);
  }
}

export default new PersonnelService();
