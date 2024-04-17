<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6" style="margin-top: 64px;">
        <v-card>
          <v-card-title class="text-h5 text-center">
            Вход в систему
          </v-card-title>

          <v-card-text>
            <v-form @submit.prevent="login">
              <v-text-field
                  v-model="username"
                  label="Логин"
                  outlined
                  required
              ></v-text-field>

              <v-text-field
                  v-model="password"
                  label="Пароль"
                  type="password"
                  outlined
                  required
              ></v-text-field>

              <div style="display: flex; justify-content: left; gap: 10px;">
                <p class="text-left">
                  Забыли пароль? <a href="/reset-password">Сбросьте его</a>
                </p>
                </div>
              <br>
              <v-btn type="submit" color="primary" block>
                Войти
              </v-btn>

              <!-- Sign Up text link -->
              <br>



                <p class="text-center">
                  Нет аккаунта? <a href="/signup">Регистрация</a>
                </p>



            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
  data() {
    return {
      username: '',
      password: '',
    };
  },
  methods: {
    login() {
      console.log(this.username)
      console.log(this.password)

      // Create a new FormData instance
      let userData = {
        username: this.username,
        password: this.password
      };

      axios.post('http://localhost:8816/api/auth/signin', userData)
          .then(response => {
            // The JWT token is returned in the 'accessToken' field of the response body
            const token = response.data.token;
            const username = response.data.username;
            const refreshToken = response.data.refreshToken;
            const userId = response.data.id;
            //console.log(response.data);

            // Store the token in local storage
            localStorage.setItem('jwt-token', token);
            localStorage.setItem('refresh-token', refreshToken);
            localStorage.setItem('username', username);
            localStorage.setItem('isUserLoggedIn', 'true');
            localStorage.setItem('userId', userId);

            // Redirect to the main page
            this.$router.push('/dashboard');
          })
          .catch(error => {
            console.log(error);
            // Handle login error
            if (error.response && error.response.data && error.response.data.message) {
              toast.error(error.response.data.message);
            } else {
              console.log(error);
            }
          });
    }
  }
};
</script>

<style>
</style>