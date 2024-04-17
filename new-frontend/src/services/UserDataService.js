import http from "../http-common";

class UserDataService {
  getAll() {
    return http.get("/users", {
      params: {
        size: 1000
      }
    });
  }

  get(id) {
    return http.get(`/users/${id}`);
  }

  getUsersByProject(id) {
    return http.get(`/users/projects/${id}`);
  }

  create(data) {
    return http.post("/users", data);
  }

  update(id, data) {
    return http.put(`/users/${id}`, data);
  }

  delete(id) {
    return http.delete(`/users/${id}`);
  }

}

export default new UserDataService();