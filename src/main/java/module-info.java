module com.example.brandboostclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires okhttp3;
    requires com.google.gson;
            
                            
    opens com.example.brandboostclient.controller to javafx.fxml;
    exports com.example.brandboostclient;
    exports com.example.brandboostclient.model;
    exports com.example.brandboostclient.controller;
    exports com.example.brandboostclient.response;
    exports com.example.brandboostclient.service;
    exports com.example.brandboostclient.utils;
}