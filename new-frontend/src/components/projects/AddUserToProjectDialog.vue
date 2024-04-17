<template>
  <div v-if="showDialog" class="dialog-overlay">
    <div class="dialog-container">
      <div class="dialog-content">
        <h2>Add User</h2>
        <div class="form-group">
          <label class="label" for="projectSelect">Project:</label>
          <select v-model="selectedProject" id="projectSelect" class="select-box">
            <option v-for="project in existingProjects" :key="project.projectId" :value="project.projectId">{{ project.projectName }}</option>
          </select>
        </div>
        <div class="form-group">
          <label class="label" for="userSelect">User:</label>
          <select v-model="selectedUser" id="userSelect" class="select-box">
            <option v-for="user in existingUsers" :key="user.userId" :value="user.userId">{{ user.username }}</option>
          </select>
        </div>
        <div class="button-group">
          <button @click="closeDialog" class="btn-cancel">Close</button>
          <button @click="add" class="btn-add">Add</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ProjectDataService from "../../services/ProjectDataService";
import UserDataService from "../../services/UserDataService";

export default {
  props: {
    showDialog: Boolean,
  },
  data() {
    return {
      selectedProject: null,
      selectedUser: null,
      existingProjects: [],
      existingUsers: []
    };
  },
  watch: {
    showDialog: function (newValue) {
      if (newValue) {
        this.getExistingProjects();
        this.getExistingUsers();
      } else {
        this.resetForm();
      }
    },
  },
  methods: {
    closeDialog() {
      this.$emit("closeDialog");
    },
    resetForm() {
      this.selectedProject = null;
      this.selectedUser = null;
    },
    add() {
      if (!this.selectedProject || !this.selectedUser) {
        this.showSnackbarError("Please select a project and a user.");
        return;
      }

      ProjectDataService.addUser(this.selectedProject, this.selectedUser)
          .then((response) => {
            console.log(response.data);
            this.showSnackbarSuccess("User successfully added to the project.");
            this.closeDialog();
          })
          .catch((error) => {
            console.error(error);
            this.showSnackbarError("Service is currently unavailable. Please try again later.");
          });
    },

    showSnackbarError(message) {
      alert(message);
    },
    showSnackbarSuccess(message) {
      alert(message);
    },
    getExistingProjects() {
      ProjectDataService.getAll()
          .then((response) => {
            console.log(response.data)
            this.existingProjects = response.data;
          })
          .catch((error) => {
            console.error("Error getting existing projects:", error);
          });
    },
    getExistingUsers() {
      UserDataService.getAll()
          .then((response) => {
            console.log(response.data);
            this.existingUsers = response.data;
          })
          .catch((error) => {
            console.error("Error getting existing users:", error);
          });
    },
  },
};
</script>

<style scoped>
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.dialog-container {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
  width: 60%;
  max-height: 80%;
  overflow-y: auto;
}

.dialog-content {
  max-width: 100%;
}

.form-group {
  margin-bottom: 20px;
}

.label {
  font-size: 18px;
  margin-bottom: 8px;
}

.select-box,
.input-box {
  width: 100%;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.button-group {
  text-align: right;
}

.btn-cancel {
  padding: 8px 16px;
  margin-right: 10px;
  background-color: #ccc;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.btn-add {
  padding: 8px 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
