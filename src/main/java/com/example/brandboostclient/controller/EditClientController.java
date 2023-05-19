package com.example.brandboostclient.controller;

import com.example.brandboostclient.model.Client;
import com.example.brandboostclient.service.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
@Getter
public class EditClientController {
    @FXML
    private TextField addressField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField numberField;

    @FXML
    private Button saveClient;

    @FXML
    private TextField surnameField;

    @FXML
    void saveClient(ActionEvent event) throws IOException {
        if (client != null) {
            if (!nameField.getText().matches("^[А-яA-Za-z]{2,40}$") || nameField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка ввода данных");
                alert.setHeaderText(null);
                alert.setContentText("Имя клиента должна быть от 2 до 40 символов и содержать только латинские буквы, кириллицу! В имени клиента должно быть от 2 до 40 символов! Имя клиента не может быть пустым!");
                alert.showAndWait();
            } else if (!surnameField.getText().matches("^[А-яA-Za-z]{2,40}$") || surnameField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка ввода данных");
                alert.setHeaderText(null);
                alert.setContentText("Фамилия клиента должна быть от 2 до 40 символов и содержать только латинские буквы, кириллицу! В фамилии клиента должно быть от 2 до 40 символов! Фамилия клиента не может быть пустой!");
                alert.showAndWait();
            } else if (!lastnameField.getText().matches("^[А-яA-Za-z]{2,40}$") || lastnameField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка ввода данных");
                alert.setHeaderText(null);
                alert.setContentText("Отчество пользователя должно быть от 2 до 40 символов и содержать только латинские буквы, кириллицу! В отчестве клиента должно быть от 2 до 40 символов!");
                alert.showAndWait();
            } else if (!numberField.getText().matches("^\\+?[0-9]{10,12}$") || numberField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка ввода данных");
                alert.setHeaderText(null);
                alert.setContentText("Номер не должен быть пустым! Номер телефона должен быть в формате +7**********, содержащий только цифры от 0-9, без пробелом и символов!");
                alert.showAndWait();
            } else {
                client.setName(nameField.getText());
                client.setSurname(surnameField.getText());
                client.setLastname(lastnameField.getText());
                client.setNumber(numberField.getText());
                client.setAddress(addressField.getText());
                client.setEmail(mailField.getText());
                ClientService clientService = new ClientService();
                clientService.save(client);
                Stage stage = (Stage) saveClient.getScene().getWindow();
                stage.close();
                showSuccessMessage();
            }
        }
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешное добавление");
        alert.setHeaderText(null);
        alert.setContentText("Клиент успешно обновлен.");
        alert.showAndWait();
    }

    private Client client;

    public void setClient(Client client) {
        this.client = client;
        populateFields();
    }

    private void populateFields() {
        if (client != null) {
            nameField.setText(client.getName());
            surnameField.setText(client.getSurname());
            lastnameField.setText(client.getLastname());
            numberField.setText(client.getNumber());
            addressField.setText(client.getAddress());
            mailField.setText(client.getEmail());
        }
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
