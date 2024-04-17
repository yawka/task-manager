import http from "../http-common";

class TaskDataService {
  getAll(projectId) {
    return http.get(`tasks/project/${projectId}`);
  }
  getMy() {
    return http.get("tasks/user");
  }

  get(id) {
    return http.get(`/tasks/${id}`);
  }

  create(data) {
    return http.post("/tasks", data);
  }

  update(id, data) {
    return http.put(`/tasks/${id}`, data);
  }

  delete(id) {
    return http.delete(`/tasks/${id}`);
  }

  getUniqueDates(id) {
    return http.get(`/tasks/${id}/dates`)
  }

}

export default new TaskDataService();