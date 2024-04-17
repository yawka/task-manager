<template>
  <div class="userGroup-container">
    <div class="text"><h1>User Group</h1></div>
    <div class="white-container">
      <input v-model="searchQuery" @input="handleSearchInput" placeholder="Search" class="search-input" />
      <button @click="addUserGroup" class="add-button">New user group</button>
      <div v-for="userGroup in paginatedUserGroups" :key="userGroup.id" class="list-group-item">
        <div class="userGroup-info">
<!--          <h4 @click="handleUserGroupClick(userGroup.userGroupId)" class="list-group-item-text">-->
          <h4 class="list-group-item-text" >
            {{ userGroup.groupName }}
          </h4>
        </div>
        <i @click="editUserGroup(userGroup.userGroupId)" class="edit-icon">&#9998;</i> <!-- Иконка ручки -->
        <span @click="deleteUserGroup(userGroup.userGroupId)" class="delete-icon">&#128465;</span>
      </div>

      <div class="pagination">
        <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">Previous</button>
        <span>Page {{ currentPage }} of {{ totalPages }}</span>
        <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">Next</button>
      </div>
    </div>
    <delete-userGroup-dialog :showDialog="showDeleteUserGroupDialog" :userGroupId="selectedUserGroupId" @closeDialog="closeDeleteUserGroupDialog"/>
    <create-userGroup-dialog :showDialog="showCreateUserGroupDialog" @closeDialog="closeCreateUserGroupDialog"/>
    <edit-userGroup-dialog :showDialog="showEditUserGroupDialog" :userGroupId="selectedUserGroupId"
                         @closeDialog="closeEditUserGroupDialog"/>
  </div>
</template>

<script>
// import axios from 'axios';
import DeleteUserGroupDialog from '@/components/usergroups/DeleteUserGroupDialog.vue';
import EditUserGroupDialog from "@/components/usergroups/EditUserGroupDialog.vue";
import CreateUserGroupDialog from "@/components/usergroups/CreateUserGroupDialog.vue";
import http from "../http-common";

export default {
  components: {
    DeleteUserGroupDialog, EditUserGroupDialog, CreateUserGroupDialog
  },
  data() {
    return {
      userGroups: [],
      selectedUserGroupId: null,
      showCreateUserGroupDialog: false,
      showEditUserGroupDialog: false,
      showDeleteUserGroupDialog: false,
      searchQuery: '',
      maxSearchLength: 20,
      pageSize: 10,
      currentPage: 1,
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.userGroups.length / this.pageSize);
    },
    paginatedUserGroups() {
      const filteredUserGroups = this.userGroups.filter((userGroup) =>
          userGroup.groupName.toLowerCase().includes(this.searchQuery.toLowerCase())
      );

      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return filteredUserGroups.slice(start, end);
    },
  },
  mounted() {
    this.fetchUserGroups();
  },
  methods: {
    deleteUserGroup(userGroupId) {
      console.log('Delete userGroup with id', userGroupId);
      this.selectedUserGroupId = userGroupId;
      this.showDeleteUserGroupDialog = true;
    },

    closeDeleteUserGroupDialog() {
      this.selectedUserGroupId = null;
      this.showDeleteUserGroupDialog = false;
      this.fetchUserGroups();
    },

    editUserGroup(userGroupId) {
      console.log('Edit group with id', userGroupId);
      this.selectedUserGroupId = userGroupId;
      this.showEditUserGroupDialog = true;
    },

    addUserGroup() {
      console.log('Add a new group');
      this.showCreateUserGroupDialog = true;
    },

    closeCreateUserGroupDialog() {
      this.showCreateUserGroupDialog = false;
      this.fetchUserGroups();
    },

    closeEditUserGroupDialog() {
      this.showEditUserGroupDialog = false;
      this.selectedUserGroupId = null;
      this.fetchUserGroups();
    },

    // handleUserGroupClick(id) {
    //   this.$router.push({ name: 'ProfileUserGroup', query: { id } });
    // },
    changePage(page) {
      this.currentPage = page;
    },
    handleSearchInput() {
      this.fetchUserGroups();
    },
    resetSearch() {
      this.searchQuery = '';
      this.fetchUserGroups();
    },
    fetchUserGroups() {
      http.get('/users/groups', {
        params: {
          size: 1000,
        },
      })
          .then(response => {
            this.userGroups = response.data;
          })
          .catch(error => {
            console.error('Ошибка при получении данных:', error);
          });
    },
  },
};
</script>

<style scoped>

.userGroup-container{
  margin-top: 60px;
}

.text h1{
  margin-bottom: 20px;
  position: relative;
  bottom: 31px;
  width: 100px;
  font-size: 2.5rem;
  white-space: nowrap;
  left: 2px;
}

.white-container {
  background-color: #fff;
  padding: 30px;
  border-radius: 30px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 1040px;
  height: 108.3vh;
  margin: 0 auto;
  position: relative;
  bottom: 32px;
}


.list-group-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 0; 
  border-bottom: 1px solid #ccc; 
}

.userGroup-info {
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
  font-size: 1.5rem; 
  margin-right: 10px; 
}
.delete-icon {
  cursor: pointer;
  font-size: 1.5rem; 
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
  margin: 0 auto; 
  font-size: 15px;
}
.add-button {
  margin-left: 250px;
  cursor: pointer;
  background-color: #007bff; 
  color: white; 
  border: none; 
  padding: 10px 20px; 
  border-radius: 5px; 
  font-size: 15px; 
}

.search-input {
  margin-left: 400px; 
  padding: 5px;
  width: 150px; 
  height: 30px;
  border: 2px solid #ccc; 
  border-radius: 10px; 
  font-size: 15px;
}
</style>
