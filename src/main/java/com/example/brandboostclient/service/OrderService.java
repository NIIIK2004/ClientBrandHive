package com.example.brandboostclient.service;

import com.example.brandboostclient.model.Order;
import com.example.brandboostclient.utils.HTTPUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URLEncoder;

import static com.example.brandboostclient.controller.OrderController.data;

public class OrderService {
    public static HTTPUtils http = new HTTPUtils();
    public static Gson gson = new Gson();
    public static String api = "http://localhost:8080/api/order";

    public static void init() throws IOException {
        data.clear();
        String res = http.get(api, "/all");
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Order order = gson.fromJson(dataArr.get(i).toString(), Order.class);
            data.add(order);
        }
    }

    public void search(String query) throws IOException {
        data.clear();
        String encodedQuery = URLEncoder.encode(query, "UTF-8");
        String res = http.get(api, "/find?param=" + encodedQuery);
        JsonObject base = gson.fromJson(res, JsonObject.class);
        JsonArray dataArr = base.getAsJsonArray("data");
        for (int i = 0; i < dataArr.size(); i++) {
            Order order = gson.fromJson(dataArr.get(i).toString(), Order.class);
            data.add(order);
        }
    }

    public void save(Order order) throws IOException {
        String json = gson.toJson(order);
        String args = "/add";
        http.post(api, json, args);
    }

    public void delete(Order order) throws IOException {
        String args = "/delete/" + order.getId();
        http.delete(api, args);
    }
}
