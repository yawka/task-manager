<template>
  <v-dialog v-model="dialog" scrollable persistent width="1024">
    <v-card>
      <v-card-title class="pa-0">
        <v-toolbar flat>
          <v-toolbar-title>Create Group</v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-row>
                <v-col cols="2" class="field-label">
                  Title*
                </v-col>
                <v-col cols="10">
                  <v-text-field
                    v-model="title"
                    required
                    outlined
                    dense
                    solo
                    :rules="titleRules"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row>
            <v-col>
              <small>*indicates required field</small>
            </v-col>
          </v-row>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue-darken-1" variant="outlined" @click="closeDialog">
          Close
        </v-btn>
        <v-btn color="blue-darken-1" variant="elevated" @click="create">
          Create
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
  },
  data: () => ({
    dialog: false,
    title: null,
    titleRules: [
      (v) => !!v || "Title is required",
      (v) => (v && v.length <= 255) || "Title must be less than 255 characters",
    ],

    snackbar: {
      show: false,
      message: "",
      color: "error",
    },
    itemConstraints: ['NO OVERLAP'],
    valueConstraints: [],
  }),
  watch: {
    showDialog: function (newValue) {
      if (newValue) {
        this.dialog = true;
      } else {
        this.dialog = false;
        this.resetForm();
      }
    },
  },
  methods: {
    closeDialog() {
      this.$emit("closeDialog");
    },
    resetForm() {
      this.title = null;
    },
    create() {
      if (!this.title) {
        this.showSnackbarError("Please enter a Title.");
        return;
      }
      console.log("Creating group:", this.title);
      var data = {
        groupName: this.title,
      };
      console.log("Description:", this.data);

      UserGroupDataService.create(data)
        .then((response) => {
          console.log(response.data);
          this.showSnackbarSuccess("Group successfully created.");
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
