package com.example.brandboostclient.service;

import com.example.brandboostclient.controller.ClientController;
import com.example.brandboostclient.model.Client;
import com.example.brandboostclient.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.example.brandboostclient.controller.ClientController.data;


public class ClientService {
    public static HTTPUtils http = new HTTPUtils();
    public static Gson gson = new Gson();
    public static String api = "http://localhost:8080/api/client";

    public static void init() throws IOException {
        data.clear();
        String res = http.get(api, "/all");
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Client client = gson.fromJson(dataArr.get(i).toString(), Client.class);
            data.add(client);
            System.out.println("Массив данных клиентов для таблицы " + client);
        }
    }

    public void search(String query) throws IOException {
        data.clear();
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String res = http.get(api, "/find?param=" + encodedQuery);
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Client client = gson.fromJson(dataArr.get(i).toString(), Client.class);
            data.add(client);
        }
    }

    public void save(Client client) throws IOException {
        String json = gson.toJson(client);
        String args = "/add";
        http.post(api, json, args);
    }

    public void delete(Client client) throws IOException {
        String args = "/delete/" + client.getId();
        http.delete(api, args);
    }

    public List<Client> getClient() throws IOException {
        List<Client> clients = new ArrayList<>();
        ClientController.data.clear();
        String res = http.get(api, "/all");
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Client client = gson.fromJson(dataArr.get(i).toString(), Client.class);
            data.add(client);
        }
        System.out.println("Массив данных клиентов для комбо бокса " + data);
        return data;
    }
}
