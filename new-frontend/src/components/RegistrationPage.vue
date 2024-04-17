<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6" style="margin-top: 64px;">
        <v-card>
          <v-card-title class="text-h5 text-center">
            Регистрация
          </v-card-title>

          <v-card-text>
            <v-form @submit.prevent="registration">
              <v-text-field
                  v-model="username"
                  label="Имя пользователя"
                  outlined
                  required
              ></v-text-field>

              <v-text-field
                  v-model="email"
                  label="Почта"
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
              <v-btn type="submit" color="primary" block>
                Зарегистрироваться
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
import { toast } from 'vue3-toastify';
import 'vue3-toastify/dist/index.css';

export default {
  data() {
    return {
      username: '',
      email: '',
      password: '',
    };
  },
  methods: {
    async registration() {
      try {
        const response = await axios.post('http://localhost:8816/api/auth/signup', {
          username: this.username,
          email: this.email,
          password: this.password,
        });
        console.log('Registration successful:', response.data);
        this.$router.push('/verify-email');
      } catch (error) {
        if (error.response && error.response.data && error.response.data.error) {
          toast.error(error.response.data.error);
        } else {
          console.error('Registration failed:', error.response.data);
          // console.log(error);
        }
      }
    },
  },
};
</script>
