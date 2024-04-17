import http from "../http-common";

class ProjectDataService {
  getAll() {
    return http.get("/projects", {
      params: {
        size: 1000
      }
    });
  }
  getMy() {
    return http.get("/projects/user", {
      params: {
        size: 1000
      }
    });
  }

  get(id) {
    return http.get(`/projects/${id}`);
  }

  create(data) {
    return http.post("/projects", data);
  }

  update(id, data) {
    return http.put(`/projects/${id}`, data);
  }

  delete(id) {
    return http.delete(`/projects/${id}`);
  }

  getUsers(id) {
    return http.get(`/projects/${id}/users`);
  }

  addUser(projectId, userId) {
    return http.post(`/projects/users`, null, {
      params: {
        projectId: projectId,
        userId: userId
      }
    });
  }
}

export default new ProjectDataService();