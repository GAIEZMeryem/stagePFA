import axios from 'axios';
import {API_URLS} from '../api.config' ;

export default {
  
  getTrimestres() {
    return axios.get(API_URLS.trimestres);
  },

  
  getTrimestre(id) {
    return axios.get(`${API_URLS.trimestres}/${id}`);
  },


  ajouterTrimestre(trimestre) {
    return axios.post(API_URLS.trimestres, trimestre);
  },

 
  mettreAJourTrimestre(id, trimestre) {
    return axios.put(`${API_URLS.trimestres}/${id}`, trimestre);
  },


  supprimerTrimestre(id) {
    return axios.delete(`${API_URLS.trimestres}/${id}`);
  },

  closeTrimestre(id) {
    return axios.put(`${API_URLS.trimestres}/${id}/close`);
  },
  openTrimestre(id) {
    return axios.put(`${API_URLS.trimestres}/${id}/open`);
  },
  getOpenTrimestres() {
    return axios.get(`${API_URLS.trimestres}/open`);
  },
  getFichesForTrimestre(id) {
    return axios.get(`${API_URLS.trimestres}/${id}/fiches`);
  }

};
