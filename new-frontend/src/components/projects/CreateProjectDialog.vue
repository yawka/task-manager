<template>
  <v-dialog v-model="dialog" scrollable persistent width="1024">
    <v-card>
      <v-card-title class="pa-0">
        <v-toolbar flat>
          <v-toolbar-title>Create Project</v-toolbar-title>
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
            <v-col cols="12">
              <v-row>
                <v-col cols="2" class="field-label">
                  Description
                </v-col>
                <v-col cols="10">
                  <v-textarea
                    v-model="description"
                    outlined
                    dense
                    solo
                  ></v-textarea>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12">
              <v-row>
                <v-col cols="2" class="field-label">
                  Parent Project
                </v-col>
                <v-col cols="10">
                  <v-select
                      v-model="selectedParentProject"
                      :items="itemExistingProjects"
                      item-text="projectName"
                      item-value="projectId"
                      outlined
                      dense
                      solo
                      label="Select Parent Project"
                  ></v-select>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12">
              <v-row>
                <v-col cols="2" class="field-label">
                  Constraints
                </v-col>
                <v-col cols="10">
                  <v-select
                    v-model="valueConstraints"
                    :items="itemConstraints"
                    chips
                    multiple
                  ></v-select>
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
import ProjectDataService from "../../services/ProjectDataService";

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
    
    description: "",
    snackbar: {
      show: false,
      message: "",
      color: "error",
    },
    itemConstraints: ['NO_OVERLAP'],
    valueConstraints: [],
    itemExistingProjects: [], 
    selectedParentProject: null, 

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
      this.description = "";
    },
    create() {
      if (!this.title) {
        this.showSnackbarError("Please enter a Title.");
        return;
      }
      console.log("Creating project:", this.title);
      console.log("Description:", this.description);
      var data = {
        projectName: this.title,
        description: this.description,
        parentProjectId: this.selectedParentProject ? this.selectedParentProject.projectId : null,
      };
      ProjectDataService.create(data)
        .then((response) => {
          console.log(response.data);
          this.showSnackbarSuccess("Project successfully created.");
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
    getExistingProjects() {
    ProjectDataService.getAll()
      .then((response) => {
        this.itemExistingProjects = response.data; // Замените на фактический путь к данным проектов
      })
      .catch((error) => {
        console.error("Error getting existing projects:", error);
      });
    },

  },
};
</script>
