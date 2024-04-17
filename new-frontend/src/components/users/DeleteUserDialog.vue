<template>
    <v-dialog v-model="dialog" persistent max-width="500">
      <v-card>
        <v-card-title class="pa-0">
          <v-toolbar flat>
            <v-toolbar-title>Delete User</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <p>Are you sure you want to delete this user?</p>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="outlined" @click="closeDialog">
            Cancel
          </v-btn>
          <v-btn color="red" dark @click="deleteUser">
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
  import UserDataService from "../../services/UserDataService";
  
  export default {
    props: {
      showDialog: Boolean,
      userId: String,
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
      deleteUser() {
        UserDataService.delete(this.userId)
          .then((response) => {
            console.log(response.data);
            this.showSnackbarSuccess("User successfully deleted.");
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