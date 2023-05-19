package com.example.brandboostclient.controller;

import com.example.brandboostclient.model.Client;
import com.example.brandboostclient.model.Order;
import com.example.brandboostclient.model.Services;
import com.example.brandboostclient.service.OrderService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Getter
@Setter
public class EditOrderController {
    @FXML
    private ComboBox<Client> clientField;

    @FXML
    private DatePicker finalDate;

    @FXML
    private Button saveServices;

    @FXML
    private ComboBox<Services> servicesField;

    @FXML
    void saveServices(ActionEvent event) throws IOException {
        if (order != null) {
            order.setService(servicesField.getValue());
            order.setClient(clientField.getValue());
            order.setFinalDate(String.valueOf(finalDate.getValue()));
            OrderService orderService = new OrderService();
            orderService.save(order);
            Stage stage = (Stage) saveServices.getScene().getWindow();
            stage.close();
            showSuccessMessage();
        }
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешное добавление");
        alert.setHeaderText(null);
        alert.setContentText("Заказ успешно добавлен.");
        alert.showAndWait();
    }

    private Order order;

    public void setOrder(Order order) {
        this.order = order;
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
