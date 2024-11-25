import axios from 'axios';
import { API_URLS } from '../api.config';


class FonctionService {
  getFonctions() {
    return axios.get(API_URLS.fonctions);
  }

  supprimerFonction(id) {
    return axios.delete(`${API_URLS.fonctions}/${id}`);
  }

  ajouterFonction(fonction) {
    return axios.post(API_URLS.fonctions, fonction);
  }

  mettreAJourFonction(id, fonction) {
    return axios.put(`${API_URLS.fonctions}/${id}`, fonction);
  }
}

export default new FonctionService();
