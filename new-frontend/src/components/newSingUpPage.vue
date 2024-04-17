<template>
  <div class="login-container">
    <div class="login-card">
      <div class="login-title">
        Регистрация
      </div>

      <form @submit.prevent="registration">
        <label for="username" class="login-label">Логин</label>
        <input v-model="username" type="text" class="login-input" id="username" required>

        <label for="email" class="login-label">Почта</label>
        <input v-model="email" type="email" class="login-input" id="email" required>

        <label for="password" class="login-label">Пароль</label>
        <input v-model="password" type="password" class="login-input" id="password" required>

        <button type="submit" class="login-button">
          Зарегистрироваться
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const username = ref('');
    const email = ref('');
    const password = ref('');

    const registration = async () => {
      try {
        const response = await axios.post('/aaa/register', {
          username: username.value,
          email: email.value,
          password: password.value,
        });
        console.log('Registration successful:', response.data);
      } catch (error) {
        console.error('Registration failed:', error.response.data);
      }
    };

    return {
      username,
      email,
      password,
      registration,
    };
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
</style>
