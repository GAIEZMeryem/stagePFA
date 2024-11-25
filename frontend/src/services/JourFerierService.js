import axios from 'axios';
import { API_URLS } from '../api.config'; // on va importe les urls a partir de api.config.js



class JourFerierService {
  getJourFeriers() {
    return axios.get(API_URLS.joursFeries);
  }

  supprimerJourFerier(id) {
    return axios.delete(`${API_URLS.joursFeries}/${id}`);
  }

  ajouterJourFerier(jourFerier) {
    return axios.post(API_URLS.joursFeries, jourFerier);
  }

  mettreAJourJourFerier(id, jourFerier) {
    return axios.put(`${API_URLS.joursFeries}/${id}`, jourFerier);
  }
}

export default new JourFerierService();
