<template>
    <v-dialog v-model="dialog" persistent max-width="500">
      <v-card>
        <v-card-title class="pa-0">
          <v-toolbar flat>
            <v-toolbar-title>Delete Project</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <p>Are you sure you want to delete this project?</p>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="outlined" @click="closeDialog">
            Cancel
          </v-btn>
          <v-btn color="red" dark @click="deleteProject">
            Delete
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-snackbar v-model="snackbar.show" :color="snackbar.color">
      {{ snackbar.message }}
    </v-snackbar>
  </template>
  
  <script>
  import ProjectDataService from "../../services/ProjectDataService";
  
  export default {
    props: {
      showDialog: Boolean,
      projectId: String,
    },
    data: () => ({
      dialog: false,
      snackbar: {
        show: false,
        message: "",
        color: "error",
      },
    }),
    watch: {
      showDialog: function (newValue) {
        if (newValue) {
          this.dialog = true;
        } else {
          this.dialog = false;
        }
      },
    },
    methods: {
      closeDialog() {
        this.$emit("closeDialog");
      },
      deleteProject() {
        ProjectDataService.delete(this.projectId)
          .then((response) => {
            console.log(response.data);
            this.showSnackbarSuccess("Project successfully deleted.");
            this.closeDialog();
          })
          .catch((e) => {
            console.log(e);
            this.showSnackbarError(
              "Service is currently unavailable. Please try again later."
            );
          });
      },
      showSnackbarError(message) {
        this.snackbar.message = message;
        this.snackbar.color = "error";
        this.snackbar.show = true;
      },
      showSnackbarSuccess(message) {
        this.snackbar.message = message;
        this.snackbar.color = "success";
        this.snackbar.show = true;
      },
    },
  };
  </script>