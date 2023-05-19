package com.example.brandboostclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class BrandBoostApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Region root = new Region();
        root.setStyle("-fx-background-radius: 20;");

//      Поменять путь когда запустишь

        Image icon = new Image("C:\\Users\\admin\\Desktop\\BrandBoostClient\\src\\main\\resources\\com\\example\\brandboostclient\\images\\icon.png");
        stage.getIcons().add(icon);


        FXMLLoader fxmlLoader = new FXMLLoader(BrandBoostApplication.class.getResource("auth-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}