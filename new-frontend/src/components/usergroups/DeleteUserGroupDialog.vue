<template>
    <v-dialog v-model="dialog" persistent max-width="500">
      <v-card>
        <v-card-title class="pa-0">
          <v-toolbar flat>
            <v-toolbar-title>Delete User Group</v-toolbar-title>
            <v-spacer></v-spacer>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <p>Are you sure you want to delete this group?</p>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue-darken-1" variant="outlined" @click="closeDialog">
            Cancel
          </v-btn>
          <v-btn color="red" dark @click="deleteGroup">
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
  import UserGroupDataService from "@/services/UserGroupDataService";

  export default {
    props: {
      showDialog: Boolean,
      userGroupId: String,
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
      deleteGroup() {
        UserGroupDataService.delete(this.userGroupId)
          .then((response) => {
            console.log(response.data);
            this.showSnackbarSuccess("Group successfully deleted.");
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
