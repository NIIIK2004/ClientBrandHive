package com.example.brandboostclient.utils;

import okhttp3.*;
import java.io.IOException;

public class HTTPUtils {
    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json, String args) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url + args).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String get(String url, String args) throws IOException {
        Request req = new Request.Builder().url(url + args).build();
        try (Response response = client.newCall(req).execute()) {
            return response.body().string();
        }
    }

    public String delete(String url, String args) throws IOException {
        Request req = new Request.Builder().url(url + args).delete().build();
        try (Response response = client.newCall(req).execute()) {
            return response.body().string();
        }
    }
}
