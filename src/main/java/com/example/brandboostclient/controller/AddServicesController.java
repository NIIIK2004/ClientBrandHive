package com.example.brandboostclient.controller;

import com.example.brandboostclient.model.Client;
import com.example.brandboostclient.model.Services;
import com.example.brandboostclient.service.ClientService;
import com.example.brandboostclient.service.ServicesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddServicesController {
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private Button saveServices;

    @FXML
    void saveServices(ActionEvent event) throws IOException {
        Services services = new Services();
        String priceText = priceField.getText();
        if (!nameField.getText().matches("^[А-яA-Za-z]{2,100}$") || nameField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода данных");
            alert.setHeaderText(null);
            alert.setContentText("Название услуги должна быть от 2 до 100 символов и содержать только латинские буквы, кириллицу! В названии услуги должно быть от 2 до 100 символов! Название услуги не может быть пустым!");
            alert.showAndWait();
        }
        else if(!descriptionField.getText().matches("^[А-яA-Za-z]{2,2000}$") || descriptionField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода данных");
            alert.setHeaderText(null);
            alert.setContentText("Описание услуги должна быть от 2 до 2000 символов и содержать только латинские буквы, кириллицу! В описании услуги должно быть от 2 до 2000 символов! Описание услуги не может быть пустым!");
            alert.showAndWait();
        }
        else if (priceText.isEmpty() || !priceText.matches("\\d+") || !(Integer.parseInt(priceText) >= 1 && Integer.parseInt(priceText) <= 100000000)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка ввода данных");
            alert.setHeaderText(null);
            alert.setContentText("Цена не может быть больше 100000000! Цена не может быть меньше 1 рубля! Цена не может быть пустой! Цена должна содержать только цифры!");
            alert.showAndWait();
        }
        else {
            services.setName(nameField.getText());
            services.setDescription(descriptionField.getText());
            services.setPrice(Integer.valueOf(priceField.getText()));
            ServicesService servicesService = new ServicesService();
            servicesService.save(services);
            Stage stage = (Stage) saveServices.getScene().getWindow();
            stage.close();
            showSuccessMessage();
        }
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
