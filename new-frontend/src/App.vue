<template>
  <div id="app">
    <component :is="currentView"/>
  </div>
</template>

<script>
import MainPanel from "@/components/SideBar.vue";
import LoginPage from "@/components/LoginPage.vue";
import RegistrationPage from "@/components/RegistrationPage.vue";
import VerifyEmailPage from "@/components/VerifyEmailPage.vue";
import ResetPasswordPage from "@/components/ResetPasswordPage.vue";

export default {
  name: 'App',
  components: {
    MainPanel,
    LoginPage,
    RegistrationPage,
    VerifyEmailPage,
    ResetPasswordPage,
  },
  computed: {
    currentView() {
      switch (this.$route.path) {
        case '/login':
          return 'LoginPage';
        case '/signup':
          return 'RegistrationPage';
        case '/verify-email':
          return 'VerifyEmailPage';
        case '/reset-password':
          return 'ResetPasswordPage';
        default:
          return 'MainPanel';
      }
    }
  }
}
</script>
