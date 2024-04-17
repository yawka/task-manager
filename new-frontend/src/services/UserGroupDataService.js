import http from "../http-common";

class UserGroupDataService {
    getAll() {
        return http.get("/users/groups", {
            params: {
                size: 1000
            }
        });
    }

    get(id) {
        return http.get(`/users/groups/${id}`);
    }

    create(data) {
        return http.post("/users/groups", data);
    }

    update(id, data) {
        return http.put(`/users/groups/${id}`, data);
    }

    delete(id) {
        return http.delete(`/users/groups/${id}`);
    }

}

export default new UserGroupDataService();
