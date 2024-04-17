<template>
  <div class="login-container">
    <div class="login-card">
      <h2 class="login-title">Вход в систему</h2>

      <form @submit.prevent="login">
        <label for="username" class="login-label">Логин</label>
        <input v-model="username" id="username" type="text" class="login-input" required>

        <label for="password" class="login-label">Пароль</label>
        <input v-model="password" id="password" type="password" class="login-input" required>

        <button type="submit" class="login-button">Войти</button>

        <p class="forgot-password" @click="redirectToForgotPassword">Забыли пароль?</p>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    login() {
      console.log(this.username);
      console.log(this.password);

      let userData = {
        username: this.username,
        password: this.password,
      };

      axios.post('http://localhost:8816/api/auth/signin', userData)
          .then(response => {
            const token = response.data.token;

            localStorage.setItem('jwt-token', token);

            this.$router.push('/dashboard');
          })
          .catch(error => {
            console.log(error);
          });
    },
    redirectToForgotPassword() {
      console.log('Redirect to Forgot Password page');
    },
  },
};
</script>


<style scoped>

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh;
  font-family: 'Poppins', sans-serif;
}

.login-card {
  border: 1px solid #ccc;
  padding: 20px;
  max-width: 400px;
  width: 100%;
  border-radius: 2rem;
  box-shadow: 0 2rem 3rem var(--clr-light);
}

.login-title {
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 20px;
}

.login-label {
  display: block;
  margin-bottom: 5px;
  font-size: 20px;
}

.login-input {
  width: 100%;
  padding: 8px;
  border: 2px solid #ccc;
  margin-bottom: 15px;
  border-radius: 10px;
  box-sizing: border-box;
  font-size: 15px;
  font-family: 'Poppins', sans-serif;
}

.login-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px;
  font-size: 1.0rem;
  width: 100%;
  cursor: pointer;
  border-radius: 10px;
}
.forgot-password {
  text-align: center;
  font-size: 16px;
  color: #007bff;
  cursor: pointer;
  margin-top: 10px;
}
</style>
