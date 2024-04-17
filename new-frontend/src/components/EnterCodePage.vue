<template>
  <div class="confirmation-container">
    <div class="confirmation-card">
      <h2 class="confirmation-title">Введите код подтверждения, отправленный на почту</h2>

      <form @submit.prevent="submitCode">
        <label for="confirmationCode" class="confirmation-label">Код подтверждения</label>
        <input v-model="confirmationCode" id="confirmationCode" type="text" class="confirmation-input" required>

        <button type="submit" class="confirmation-button">Подтвердить</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      confirmationCode: '',
    };
  },
  methods: {
    submitCode() {
      // Add logic to handle the submitted confirmation code
      console.log('Submitted confirmation code:', this.confirmationCode);
      // You can implement further actions, such as API calls or navigation
    },
  },
};
</script>

<style scoped>
.confirmation-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60vh;
  font-family: 'Poppins', sans-serif;
}

.confirmation-card {
  border: 1px solid #ccc;
  padding: 20px;
  max-width: 400px;
  width: 100%;
  border-radius: 2rem;
  box-shadow: 0 2rem 3rem var(--clr-light);
}

.confirmation-title {
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 20px;
}

.confirmation-label {
  display: block;
  margin-bottom: 5px;
  font-size: 20px;
}

.confirmation-input {
  width: 100%;
  padding: 8px;
  border: 2px solid #ccc;
  margin-bottom: 15px;
  border-radius: 10px;
  box-sizing: border-box;
  font-size: 15px;
  font-family: 'Poppins', sans-serif;
}

.confirmation-button {
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
