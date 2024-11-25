import axios from 'axios';
import { API_URLS } from '../api.config';

export default {
  getAllTableauGardes() {
    return axios.get(API_URLS.tableauGardes);
  },
  getTableauGardeById(id) {
    return axios.get(`${API_URLS.tableauGardes}/${id}`);
  },
  createTableauGarde(tableauGarde) {
    return axios.post(`${API_URLS.tableauGardes}`, tableauGarde);
  },
  updateTableauGarde(id, tableauGarde) {
    return axios.put(`${API_URLS.tableauGardes}/${id}`, tableauGarde);
  },
  deleteTableauGarde(id) {
    return axios.delete(`${API_URLS.tableauGardes}/${id}`);
  },
  getTrimestres() {
    return axios.get(`${API_URLS.tableauGardes}/trimestres`);
  },
  getOpenTrimestres() {
    return axios.get(`${API_URLS.tableauGardes}/trimestres/open`);
  },
  getServices() {
    return axios.get(`${API_URLS.tableauGardes}/services`);
  }
};
