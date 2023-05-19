package com.example.brandboostclient.controller;

import com.example.brandboostclient.model.Role;
import com.example.brandboostclient.model.User;
import com.example.brandboostclient.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Collections;

@Getter
@Setter
public class AddUserController {
    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<Role> roleField;

    @FXML
    private Button saveUserButton;

    @FXML
    private TextField usernameField;

    @FXML
    void saveUser(ActionEvent event) throws IOException {
        Role role = roleField.getValue();
        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(passwordField.getText());
        user.setRoles(Collections.singleton(role));
        UserService userService = new UserService();
        userService.save(user);
        Stage stage = (Stage) saveUserButton.getScene().getWindow();
        stage.close();
        showSuccessMessage();
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешное добавление");
        alert.setHeaderText(null);
        alert.setContentText("Клиент успешно добавлен.");
        alert.showAndWait();
    }

    @FXML
    private void cancelButton(ActionEvent event) {
        Button cancelButton = (Button) event.getSource();
        Scene scene = cancelButton.getScene();
        if (scene != null && scene.getWindow() != null) {
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        }
    }
}
