import axios from 'axios';
import { API_URLS } from '../api.config';


export default {
  getDelaisRamadan() {
    return axios.get(API_URLS.delaisRamadan);
  },
  ajouterDelaiRamadan(delaiRamadan) {
    return axios.post(API_URLS.delaisRamadan, delaiRamadan);
  },
  mettreAJourDelaiRamadan(id, delaiRamadan) {
    return axios.put(`${API_URLS.delaisRamadan}/${id}`, delaiRamadan);
  },
  supprimerDelaiRamadan(id) {
    return axios.delete(`${API_URLS.delaisRamadan}/${id}`);
  }
};
