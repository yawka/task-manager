<template>
  <h2 class="headline font-weight-bold mb-5" style="white-space: nowrap; margin-top: 25px; margin-left: 9px;">
    Профиль пользователя
  </h2>

  <div class="headline-container">
    <div class="user-profile">
      <div class="user-photo">
        <img :src="user.photo" alt="User Photo" height="200"/>
        <h3 class="text-h6 font-weight-bold text-center">{{ user.username }}</h3>
      </div>
      <div class="user-info">
        <div class="info-card">
          <h4 class="font-weight-bold mb-8">Информация о пользователе</h4>
          <ul>
            <li><span class="info-label">Должность:</span> {{ user.position }}</li>
            <li><span class="info-label">Email:</span> {{ user.email }}</li>
          </ul>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import UserDataService from "@/services/UserDataService";

export default {
  data() {
    return {
      user: {
        username: "",
        position: "",
        email: "",
        photo: ""
      }
    };
  },
  mounted() {
    this.getUserData();
  },
  methods: {
    async getUserData() {
      try {
        const userId = this.$route.query.id;
        const response = await UserDataService.get(userId);
        this.user = response.data;
        this.user.photo = "./user.jpg";
      } catch (error) {
        console.error("Ошибка получения данных пользователя:", error);
      }
    }
  }
};
</script>

<style>

.headline-container {
  display: flex;
  align-items: baseline;

  background-color: #fff;
  padding: 30px;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 800px;
  height: 110vh;
  margin: 0 auto;
}


.user-profile {
  display: flex;
  margin-top: 16px;
}

.user-photo {
  flex: 1;
  margin-right: 16px;
}

.user-info {
  flex: 2;
}

.info-card {
  border: 1px solid #ccc;
  padding: 30px;
  border-radius: 8px;
  font-size: 18px;
  width: 400px;
}

.info-label {
  font-weight: bold;
  margin-right: 8px;
}
</style>
