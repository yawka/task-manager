<template>
  <div class="project-container">
    <div class="white-container">
      <h2 class="headline font-weight-bold mb-5">
        Projects
        <input v-model="searchQuery" @input="handleSearchInput" placeholder="Поиск" class="search-input" />
        <button @click="addUserToProject" class="addUser-button">+Add user</button>
        <button @click="addProject" class="add-button">New project</button>

      </h2>

      <div v-for="project in paginatedProjects" :key="project.id" class="list-group-item">
        <div class="project-info">
          <h4 @click="handleProjectClick(project.projectId, project)" class="list-group-item-text">
            {{ project.projectName }}
          </h4>
        </div>
        <i @click="editProject(project.projectId)" class="edit-icon">&#9998;</i> <!-- Иконка ручки -->
        <span @click="deleteProject(project.projectId)" class="delete-icon">&#128465;</span>
      </div>

      <div class="pagination">
        <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">Previous</button>
        <span>Page {{ currentPage }} of {{ totalPages }}</span>
        <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">Next</button>
      </div>
    </div>
    <create-project-dialog :showDialog="showCreateProjectDialog" @closeDialog="closeCreateProjectDialog"/>
    <edit-project-dialog :showDialog="showEditProjectDialog" :projectId="selectedProjectId"
                         @closeDialog="closeEditProjectDialog"/>
    <delete-project-dialog :showDialog="showDeleteProjectDialog" :projectId="selectedProjectId"
                           @closeDialog="closeDeleteProjectDialog"/>
    <add-user-to-project-dialog :showDialog="showAddUserDialog" @closeDialog="closeAddUserDialog"/>
  </div>
</template>

<script>
import EditProjectDialog from "@/components/projects/EditProjectDialog.vue";
import DeleteProjectDialog from "@/components/projects/DeleteProjectDialog.vue";
import CreateProjectDialog from "@/components/projects/CreateProjectDialog.vue";
import ProjectDataService from "@/services/ProjectDataService";
import AddUserToProjectDialog from "@/components/projects/AddUserToProjectDialog.vue";

export default {
  components: {
    AddUserToProjectDialog,
    CreateProjectDialog, DeleteProjectDialog, EditProjectDialog
  },
  data() {
    return {
      projects: [],
      selectedProjectId: null,
      showCreateProjectDialog: false,
      showEditProjectDialog: false,
      showDeleteProjectDialog: false,
      showAddUserDialog: false,
      searchQuery: '',
      maxSearchLength: 20,
      pageSize: 10,
      currentPage: 1,
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.projects.length / this.pageSize);
    },
    paginatedProjects() {
      const filteredProjects = this.projects.filter((project) =>
          project.projectName.toLowerCase().includes(this.searchQuery.toLowerCase())
      );

      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return filteredProjects.slice(start, end);
    },
  },
  mounted() {
    this.fetchProjects();
  },
  methods: {
    editProject(projectId) {
      console.log('Edit project with id', projectId);
      this.selectedProjectId = projectId;
      this.showEditProjectDialog = true;
    },

    deleteProject(projectId) {
      console.log('Delete project with id', projectId);
      this.selectedProjectId = projectId;
      this.showDeleteProjectDialog = true;
    },

    addProject() {
      console.log('Add a new project');
      this.showCreateProjectDialog = true;
    },

    closeCreateProjectDialog() {
      this.showCreateProjectDialog = false;
      this.fetchProjects();
    },

    closeEditProjectDialog() {
      this.showEditProjectDialog = false;
      this.selectedProjectId = null;
      this.fetchProjects();
    },

    closeDeleteProjectDialog() {
      this.showDeleteProjectDialog = false;
      this.selectedProjectId = null;
      this.fetchProjects();
    },
    addUserToProject() {
      this.showAddUserDialog = true;
    },
    closeAddUserDialog() {
      this.showAddUserDialog = false;
      this.fetchProjects();
    },

    handleProjectClick(projectId, project) {
      sessionStorage.setItem('currentProject', JSON.stringify(project));

      localStorage.setItem('projectId', projectId);

      this.$router.push({name: 'GanttChart', query: {projectId}});
    },
    changePage(page) {
      this.currentPage = page;
    },
    handleSearchInput() {
      this.fetchProjects();
    },
    resetSearch() {
      this.searchQuery = '';
      this.fetchProjects();
    },
    fetchProjects() {
      ProjectDataService.getAll()
          .then(response => {
            console.log(response.data)
            this.projects = response.data;
          })
          .catch(error => {
            console.error('Ошибка при получении данных:', error);
          });
    },
  },
};
</script>

<style>

.project-container{
  margin-left: 280px;
  margin-top: 43px;
}

.white-container {
  background-color: #fff;
  padding: 30px;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 900px;
  height: 121vh;
  margin: 0 auto;
}


.list-group-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 0px; /* Add padding to create space for the line */
  border-bottom: 1px solid #ccc; /* Add a bottom border between projects */
}

.project-info {
  flex: 1;
  margin-right: 20px;
}

.list-group-item-text {
  margin: 0;
  font-size: 1rem;
  color: #555;

}
.edit-icon,
.delete-icon {
  cursor: pointer;
  font-size: 1.5rem; /* Adjust the size as needed */
  margin-right: 10px; /* Adjust the margin between icons as needed */
}
.delete-icon {
  cursor: pointer;
  font-size: 1.5rem; /* Adjust the size as needed */
  color: red;
}

.pagination {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
}

.pagination button {
  padding: 5px 10px;
  cursor: pointer;
  margin: 0 auto; /* Adjust the margin as needed */
  font-size: 15px;
}

.search-input {
  margin-left: 200px; /* Adjust the margin as needed */
  padding: 5px;
  width: 150px; /* Adjust the width as needed */
  height: 30px;
  border: 2px solid #ccc; /* Add border styling */
  border-radius: 10px; /* Optional: Add border-radius for rounded corners */
  font-size: 15px;
}

.add-button {
  margin-left: 30px;
  cursor: pointer;
  background-color: #007bff; /* Green background */
  color: white; /* White text */
  border: none; /* Remove borders */
  padding: 10px 20px; /* Some padding */
  border-radius: 5px; /* Rounded corners */
  font-size: 15px; /* Set font size */
}
.addUser-button {
  margin-left: 160px
}

</style>
