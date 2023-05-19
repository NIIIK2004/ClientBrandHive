package com.example.brandboostclient.controller;

import com.example.brandboostclient.BrandBoostApplication;
import com.example.brandboostclient.model.User;
import com.example.brandboostclient.response.BaseResponse;
import com.example.brandboostclient.service.UserService;
import com.example.brandboostclient.utils.HTTPUtils;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AuthController {

    private static final String API_URL = "http://localhost:8080/api/auth/login";
    private static final HTTPUtils httpUtils = new HTTPUtils();
    private static final Gson gson = new Gson();

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    private final UserService userService;

    public static String userRole;

    @FXML
    void authAction(ActionEvent event) {
        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        String requestBody = gson.toJson(user);
        try {
            String response = httpUtils.post(API_URL, requestBody, "");
            BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
            if (baseResponse.isSuccess()) {
                String successMessage = baseResponse.getMessage();
                UserService.init();
                userRole = UserService.getUserRole(user.getUsername());
                System.out.println(userRole);
                showAlert(Alert.AlertType.INFORMATION, "Успешная авторизация", successMessage);
                openUserPage(usernameField.getScene());
            } else {
                String errorMessage = baseResponse.getMessage();
                showAlert(Alert.AlertType.ERROR, "Ошибка авторизации", errorMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Ошибка соединения", "Не удалось установить соединение с сервером");
        }
    }

    @FXML
    void closeAction(ActionEvent event) {
            Button cancelButton = (Button) event.getSource();
            Scene scene = cancelButton.getScene();
            if (scene != null && scene.getWindow() != null) {
                Stage stage = (Stage) scene.getWindow();
                stage.close();
            }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openUserPage(Scene scene) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("user-page.fxml"));
        Parent root = loader.load();
        UserController userController = loader.getController();
        Stage currentStage = (Stage) scene.getWindow();
        currentStage.close();
        Stage userPage = new Stage();
        userPage.setResizable(false);
        userPage.setTitle("BrandBoost");
        userPage.setScene(new Scene(root, 950, 670));
        userPage.show();
    }
}
