
const API_BASE_URL = process.env.VUE_APP_API_URL || 'http://localhost:8080/api';


export const API_URLS = {
    services: `${API_BASE_URL}/services`,
    users: `${API_BASE_URL}/utilisateurs`,
    authentification: `${API_BASE_URL}/auth`,
    delaisRamadan: `${API_BASE_URL}/delais-ramadan`, 
    fonctions: `${API_BASE_URL}/fonctions`, 
    joursFeries: `${API_BASE_URL}/joursFeries`, 
    personnel: `${API_BASE_URL}/personnels`, 
    personnelTableauGardes: `${API_BASE_URL}/personnelTableauGardes`,
    tableauGardes: `${API_BASE_URL}/tableauGardes`, 
    trimestres: `${API_BASE_URL}/trimestres`, 
  };
  