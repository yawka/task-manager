<template>
  <div class="profile-container">
    <div class="text"><h1>Profile</h1></div>
    <div class="white-container">
        <div class="profile-left">
            <img :src="user.photo" alt="User Photo" height="200"/>
            <div class="card-1">
                <label for="username">{{ user.username }}</label>
            </div>
        </div>
        <div class="profile-right">
            <div class="card-2">
                <label for="position">Position: </label>
                <input type="text" id="position" v-model="user.position" :disabled="!editing">
            </div>
            <div class="card-2">
                <label for="email">Email: </label>
                <input type="email" id="email" v-model="user.email" :disabled="!editing">
            </div>

            <div class="delete-icon" v-if="editing">
                <button class="save-button" @click="saveChanges">Save</button>
                <button class="cancel-button" @click="cancelEditing">Cancel</button>
            </div>

            <button class="edit" v-else @click="startEditing">Edit</button>

        </div>
    </div>
</div>
</template>

  <script>
  //import UserDataService from "@/services/UserDataService";

  export default {
    data() {
      return {
        user: {
          username: "",
          position: "",
          email: "",
          photo: ""
        },
        editing: false
      };
    },
    mounted() {
      this.getUserData();
    },
    methods: {
      async getUserData() {
        try {
          this.user.username = "Aibek Shamshiev"
          this.user.position = "Java Developer"
          this.user.email = "aibek@nurtelecom.kg"
          this.user.photo = "/diversity_avatar_boy_man_people_smiling_icon_159093.png";
          //const userId = this.$route.query.id;
          //const response = await UserDataService.get(userId);
          //this.user = response.data;
          //this.user.photo = "/diversity_avatar_boy_man_people_smiling_icon_159093.png";
        } catch (error) {
          this.user.username = "Aibek Shamshiev"
          this.user.position = "Java Developer"
          this.user.email = "aibek@nurtelecom.kg"
          this.user.photo = "/diversity_avatar_boy_man_people_smiling_icon_159093.png";
          console.error("Ошибка получения данных пользователя:", error);
        }
      },
      startEditing() {
        this.editing = true;
        },
      // Method to save changes
      async saveChanges() {
        try {
          // Implement saving changes logic here
          this.editing = false; // Set editing back to false after saving changes
        } catch (error) {
          console.error("Error saving changes:", error);
        }
      },
      // Method to cancel editing
      cancelEditing() {
        this.editing = false;
        // Optionally, you may want to revert any changes made by the user
      }
    }
  };
  </script>

  <style scoped>

.profile-container {
    margin-top: 60px;
}

.text h1 {
    margin-bottom: 20px;
    font-size: 2.5rem;
}

.white-container {
    background-color: #fff;
    padding: 30px;
    border-radius: 30px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 1040px;
    margin: 0 auto;
    display: flex; /* Add flexbox display */
}

.profile-left {
    margin-right: 30px; /* Adjust as needed */
}

.profile-right {
    flex-grow: 1; /* Right side takes up remaining space */
}

.card-1, .card-2 {
    margin-bottom: 20px;
}

.card-1 label {
    font-size: 1.3rem;
    font-weight: bold;
}

.card-2 label {
    font-size: 1.3rem;
}

input[type="text"], input[type="email"] {
    font-size: 1.3rem;
    padding: 5px;
    width: 100%; /* Adjust as needed */
    border: 1px solid #ccc;
    border-radius: 5px;
}

.edit,
.delete-icon {
  cursor: pointer;
  font-size: 1.5rem; /* Adjust the size as needed */
  margin-right: 10px; /* Adjust the margin between icons as needed */
}

.save-button,
.cancel-button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
    margin-right: 10px;
}

.cancel-button {
    background-color: red;
}
.edit {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 5px;
    font-size: 15px;
    cursor: pointer;
}
</style>
