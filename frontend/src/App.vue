<template>
  <div id="app">
    <AppMenu v-if="showMenu" />
    <router-view />
  </div>
</template>

<script>
import AppMenu from './components/AppMenu.vue';

export default {
  components: {
    AppMenu
  },
  computed: {
    showMenu() {
      return this.$route.name !== 'SignIn';
    }
  }
};
</script>

<style>
#app {
  display: flex;
  justify-content: center; 
  align-items: center; 
  height: 100vh; 
}
</style>
