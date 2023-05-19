package com.example.brandboostclient.service;

import com.example.brandboostclient.model.Role;
import com.example.brandboostclient.model.User;
import com.example.brandboostclient.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URLEncoder;

import static com.example.brandboostclient.controller.UserController.data;

public class UserService {
    public static HTTPUtils http = new HTTPUtils();
    public static Gson gson = new Gson();
    public static String api = "http://localhost:8080/api/user";

    public static void init() throws IOException {
        data.clear();
        String res = http.get(api, "/all");
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
           User user = gson.fromJson(dataArr.get(i).toString(), User.class);
            data.add(user);
        }
    }

    public void search(String query) throws IOException {
        data.clear();
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String res = http.get(api, "/find?param=" + encodedQuery);
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            User user = gson.fromJson(dataArr.get(i).toString(), User.class);
            data.add(user);
        }
    }

    public void save(User user) throws IOException {
        String json = gson.toJson(user);
        String args = "/add";
        http.post(api, json, args);
    }

    public void delete(User user) throws IOException {
        String args = "/delete/" + user.getId();
        http.delete(api, args);
    }

    public static String getUserRole(String username) {
        for (User user : data) {
            if (user.getUsername().equals(username)) {
                Role role = user.getRoles().stream().findFirst().orElse(null);
                if (role != null) {
                    return role.getName();
                }
            }
        }
        return "";
    }
}
