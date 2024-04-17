<template>
  <div class="user-container">
    <div class="text"><h1>Access Rights</h1></div>
    <div class="white-container">
      <input v-model="searchQuery" @input="handleSearchInput" placeholder="Search" class="search-input" />
      <div v-for="user in paginatedUsers" :key="user.id" class="list-group-item">
        <div class="user-info">
          <h4 @click="handleUserClick(user.userId)" class="list-group-item-text">
            {{ user.username }}
          </h4>
          <p class="list-group-item-text">{{ user.email }}</p>
        </div>
        <span @click="deleteUser(user.userId)" class="delete-icon">&#128465;</span>
      </div>

      <div class="pagination">
        <button @click="changePage(currentPage - 1)" :disabled="currentPage === 1">Previous</button>
        <span>Page {{ currentPage }} of {{ totalPages }}</span>
        <button @click="changePage(currentPage + 1)" :disabled="currentPage === totalPages">Next</button>
      </div>
    </div>
    <delete-user-dialog :showDialog="showDeleteUserDialog" :userId="selectedUserId" @closeDialog="closeDeleteUserDialog"/>
  </div>
</template>

<script>
import axios from 'axios';
import DeleteUserDialog from '@/components/users/DeleteUserDialog.vue';

export default {
  components: {
    DeleteUserDialog,
  },
  data() {
    return {
      users: [],
      selectedUserId: null,
      showDeleteUserDialog: false,
      searchQuery: '',
      maxSearchLength: 20,
      pageSize: 10,
      currentPage: 1,
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.users.length / this.pageSize);
    },
    paginatedUsers() {
      const filteredUsers = this.users.filter((user) =>
          user.username.toLowerCase().includes(this.searchQuery.toLowerCase())
      );

      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return filteredUsers.slice(start, end);
    },
  },
  mounted() {
    this.fetchUsers();
  },
  methods: {
    deleteUser(userId) {
      console.log('Delete user with id', userId);
      this.selectedUserId = userId;
      this.showDeleteUserDialog = true;
    },
    closeEditUserDialog() {
      this.selectedUserId = null;
      this.fetchUsers();
    },
    closeDeleteUserDialog() {
      this.selectedUserId = null;
      this.showDeleteUserDialog = false;
      this.fetchUsers();
    },
    handleUserClick(id) {
      this.$router.push({ name: 'ProfileUser', query: { id } });
    },
    changePage(page) {
      this.currentPage = page;
    },
    handleSearchInput() {
      this.fetchUsers();
    },
    resetSearch() {
      this.searchQuery = '';
      this.fetchUsers();
    },
    fetchUsers() {
      axios.get('http://localhost:8816/users', {
        params: {
          size: 1000,
        },
      })
          .then(response => {
            this.users = response.data;
          })
          .catch(error => {
            console.error('Ошибка при получении данных:', error);
          });
    },
  },
};
</script>

<style scoped>

.user-container{
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
  padding-bottom: 0px; /* Add padding to create space for the line */
  border-bottom: 1px solid #ccc; /* Add a bottom border between users */
}

.user-info {
  flex: 1;
  margin-right: 20px;
}

.list-group-item-text {
  margin: 0;
  font-size: 1rem;
  color: #555;

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
  margin-left: 400px; /* Adjust the margin as needed */
  padding: 5px;
  width: 150px; /* Adjust the width as needed */
  height: 30px;
  border: 2px solid #ccc; /* Add border styling */
  border-radius: 10px; /* Optional: Add border-radius for rounded corners */
  font-size: 15px;
}
</style>
