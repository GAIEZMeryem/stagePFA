import axios from 'axios';
import { API_URLS } from '../api.config';



class PersonnelTableauGardeService {
  getAllPersonnelTableauGardes() {
    return axios.get(API_URLS.personnelTableauGardes);
  }
  
  getPersonnelTableauGardeById(id) {
    return axios.get(`${API_URLS.personnelTableauGardes}/${id}`);
  }
  
 
  createPersonnelTableauGarde(personnelTableauGarde) {
    return axios.post(`${API_URLS.personnelTableauGardes}`, personnelTableauGarde);
  }
  
  
  updatePersonnelTableauGarde(id, personnelTableauGarde) {
    return axios.put(`${API_URLS.personnelTableauGardes}/${id}`, personnelTableauGarde);
  }
  
  deletePersonnelTableauGarde(id) {
    return axios.delete(`${API_URLS.personnelTableauGardes}/${id}`);
  }

  getPersonnels() {
    return axios.get(`${API_URLS.personnelTableauGardes}/personnel`);
  }

  getFonctions() {
    return axios.get(`${API_URLS.personnelTableauGardes}/fonction`);
  }

  

  getPersonnelTableauGardesByTableauGardeId(tableauId) {
    return axios.get(`${API_URLS.personnelTableauGardes}/tableauGarde/${tableauId}`);
  }
  existsTableauId(tableauId) {
    return axios.get(`${API_URLS.personnelTableauGardes}/exists/${tableauId}`);
  }
}

export default new PersonnelTableauGardeService();
