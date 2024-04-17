<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="6" style="margin-top: 64px;">
        <v-card>
          <v-card-title class="text-h5 text-center">
            Подтверждение почты
          </v-card-title>

          <v-card-text>
            <v-form @submit.prevent="verify_email">
              <v-text-field
                  v-model="code"
                  label="Введите код отправленный вам на почту"
                  outlined
                  required
              ></v-text-field>
              <v-btn type="submit" color="primary" block>
                Сабмит
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
      code: '',
    };
  },
  methods: {
    async verify_email() {
      try {
        const response = await axios.post('http://localhost:8816/api/auth/verify-email', {
          code: this.code
        });
        console.log('Registration successful:', response.data);
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
  },
};
</script>
