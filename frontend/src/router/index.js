import { createRouter, createWebHistory } from 'vue-router';
import SignIn from '../components/SignIn.vue';
import JourFerier from '../components/JourFerier.vue';
import FonctionComponent from '../components/FonctionComponent.vue';
import PersonnelComponent from '../components/PersonnelComponent.vue';
import ServiceComponent from '../components/ServiceComponent.vue';
import DelaiRamadan from '../components/DelaiRamadan.vue';
import AjouterTrimestre from '../components/AjouterTrimestre.vue';
import TableauGarde from '../components/TableauGarde.vue';
import AjouterDetails from '../components/AjouterDetails.vue';
import UserManagement from '../components/UserManagement.vue';



const routes = [
  {
    path: '/',
    name: 'SignIn',
    component: SignIn
  },
 
  {
    path: '/jourFerie',
    name: 'JourFerie',
    component: JourFerier,
    meta: { requiresAuth: true, allowedRoles: ['ADMIN','SUPER_ADMIN'] }
  },
  {
    path: '/fonction',
    name: 'FonctionComponent',
    component: FonctionComponent,
    meta: { requiresAuth: true, allowedRoles: ['ADMIN','SUPER_ADMIN'] }
  },
  {
    path: '/personnel',
    name: 'PersonnelComponent',
    component: PersonnelComponent,
    meta: { requiresAuth: true, allowedRoles: ['ADMIN','SUPER_ADMIN'] }
  },
  {
    path: '/service',
    name: 'ServiceComponent',
    component: ServiceComponent,
    meta: { requiresAuth: true, allowedRoles: ['ADMIN','SUPER_ADMIN'] }
  },
  {
    path: '/delaiRamadan',
    name: 'DelaiRamadan',
    component: DelaiRamadan,
    meta: { requiresAuth: true, allowedRoles: ['ADMIN' ,'SUPER_ADMIN'] }
  },
  {
    path: '/ajouterTrimestre',
    name: 'AjouterTrimestre',
    component: AjouterTrimestre,
    meta: { requiresAuth: true, allowedRoles: ['SUPER_ADMIN','ADMIN'] }
  },
  {
    path: '/tableauGarde',
    name: 'TableauGarde',
    component: TableauGarde,
    meta: { requiresAuth: true, allowedRoles: ['USER', 'ADMIN','SUPER_ADMIN'] }
  },
  {
    path: '/details/:tableauId',
    name: 'AjouterDetailsWithId',
    component: AjouterDetails,
    props: true,
    meta: { requiresAuth: true, allowedRoles: ['USER', 'ADMIN','SUPER_ADMIN'] }
  },
  {
    path: '/gestionUtilisateurs',
    name: 'UserManagement',
    component: UserManagement,
    props: true,
    meta: { requiresAuth: true, allowedRoles: ['SUPER_ADMIN'] }
  }

];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('isAuthenticated') === 'true';
  const userRole = localStorage.getItem('userRole');

  if (to.meta.requiresAuth) {
    if (!isAuthenticated) {
      
      next({ name: 'SignIn' });
    } else if (to.meta.allowedRoles && !to.meta.allowedRoles.includes(userRole.toUpperCase())) {
      next({ name: 'SignIn' }); 
    } else {
      
      next();
    }
  } else {
    next();
  }
});

export default router;
