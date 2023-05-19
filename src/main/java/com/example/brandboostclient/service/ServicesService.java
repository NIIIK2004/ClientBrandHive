package com.example.brandboostclient.service;

import com.example.brandboostclient.controller.ClientController;
import com.example.brandboostclient.controller.ServicesController;
import com.example.brandboostclient.model.Services;
import com.example.brandboostclient.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.brandboostclient.controller.ServicesController.data;

public class ServicesService {
    public static HTTPUtils http = new HTTPUtils();
    public static Gson gson = new Gson();
    public static String api = "http://localhost:8080/api/service";

    public static void init() throws IOException {
        data.clear();
        String res = http.get(api, "/all");
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Services services = gson.fromJson(dataArr.get(i).toString(), Services.class);
            data.add(services);
    }
    }

    public void search(String query) throws IOException {
        data.clear();
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String res = http.get(api, "/find?param=" + encodedQuery);
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Services services = gson.fromJson(dataArr.get(i).toString(), Services.class);
            data.add(services);
        }
    }

    public void save(Services services) throws IOException {
        String json = gson.toJson(services);
        String args = "/add";
        http.post(api, json, args);
    }

    public void delete(Services services) throws IOException {
        String args = "/delete/" + services.getId();
        http.delete(api, args);
    }

    public List<Services> getServices() throws IOException {
        List<Services> services = new ArrayList<>();
        ServicesController.data.clear();
        String res = http.get(api, "/all");
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Services servicesList = gson.fromJson(dataArr.get(i).toString(), Services.class);
            data.add(servicesList);
        }
        return data;
    }
}
