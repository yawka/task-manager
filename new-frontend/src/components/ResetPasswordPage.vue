<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6" style="margin-top: 64px;">
        <v-card>
          <v-card-title class="text-h5 text-center">
            Сброс пароля с помошью почты
          </v-card-title>

          <v-card-text>
            <v-form @submit.prevent="verify_verification_code"  v-if="verified === 'false'">
              <v-text-field
                  v-model="email"
                  label="Введите почту"
                  outlined
                  required
              ><v-btn type="button" @click="send_verification_code">send</v-btn></v-text-field>
              <v-text-field
                  v-model="code"
                  label="Введите код отправленный в почту"
                  outlined
                  required
              ></v-text-field>
              <v-btn type="submit" color="primary" block>
                Сабмит
              </v-btn>
            </v-form>

            <v-form @submit.prevent="submit_new_password"  v-if="verified === 'true'">
              <v-text-field
                  v-model="password"
                  label="Введите новый пароль"
                  outlined
                  required
              ></v-text-field>
              <v-btn type="submit" color="primary" block>
                Сабмит для пароля
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
      verified: 'false',
      email: '',
      code: '',
      password: ''
    };
  },
  methods: {
    async submit_new_password() {
      try {
        const response = await axios.post('http://localhost:8816/api/auth/reset-password', {
          email: this.email,
          password: this.password,
        });
        console.log('Registration successful:', response.data);
        toast.info(response.data);
        this.$router.push('/login');
      } catch (error) {
        if (error.response && error.response.data && error.response.data.error) {
          toast.error(error.response.data.error);
        } else {
          console.error('Code error:', error.response.data);
          // console.log(error);
        }
      }
    },

    async send_verification_code() {
      try {
        const response = await axios.post('http://localhost:8816/api/auth/forgot-password', {
          email: this.email,
        });
        console.log('Email exists', response.data);
        toast.info(response.data);
        //this.$router.push('/login');
      } catch (error) {
        if (error.response && error.response.data && error.response.data.error) {
          toast.error(error.response.data.error);
        } else {
          console.error('Code error:', error.response.data);
          // console.log(error);
        }
      }
    },

    async verify_verification_code() {
      try {
        const response = await axios.post('http://localhost:8816/api/auth/verify', {
          code: this.code,
        });
        console.log('Успешно верифаед', response.data);
        toast.done(response.data);
        console.log(response.status);
        if (response.status === 200) {
          this.verified = 'true';
        }
        //this.$router.push('/login');
      } catch (error) {
        if (error.response && error.response.data && error.response.data) {
          toast.error(error.response.data);
        } else {
          console.error('Code error:', error.response.data);
          // console.log(error);
        }
      }
    },
  },
};
</script>
