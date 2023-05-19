package com.example.brandboostclient.controller;

import com.example.brandboostclient.BrandBoostApplication;
import com.example.brandboostclient.model.Client;
import com.example.brandboostclient.model.Order;
import com.example.brandboostclient.model.Services;
import com.example.brandboostclient.service.ClientService;
import com.example.brandboostclient.service.OrderService;
import com.example.brandboostclient.service.ServicesService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderController {
    @FXML
    private Button addUserButton;

    @FXML
    private TableColumn<Order, String> client;

    @FXML
    private Button deleteModalUser;

    @FXML
    private TableColumn<Order, String> finalDate;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private Button openEditModalUser;

    @FXML
    private TableColumn<Order, String> orderDate;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Order, String> services;

    @FXML
    private TableView<Order> tOrder;

    @FXML
    private MenuItem openServicesPage;

    @FXML
    private MenuItem openUserPage;

    @FXML
    private MenuItem openClientPage;

    @FXML
    private MenuItem openOrderPage;

    @FXML
    void openOrderPage(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        openOrderPage(scene);
    }

    private void openOrderPage(Scene scene) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("order-page.fxml"));
        Parent root = loader.load();
        OrderController orderController = loader.getController();
        Stage currentStage = (Stage) scene.getWindow();
        currentStage.close();
        Stage orderStage = new Stage();
        orderStage.setResizable(false);
        orderStage.setTitle("BrandBoost");
        orderStage.setScene(new Scene(root, 950, 670));
        orderStage.show();
    }

    @FXML
    void openUserPage(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        openUserPage(scene);
    }

    private void openUserPage(Scene scene) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("user-page.fxml"));
        Parent root = loader.load();
        UserController userController = loader.getController();
        Stage currentStage = (Stage) scene.getWindow();
        currentStage.close();
        Stage userStage = new Stage();
        userStage.setResizable(false);
        userStage.setTitle("BrandBoost");
        userStage.setScene(new Scene(root, 950, 670));
        userStage.show();
    }

    @FXML
    void openServicesPage(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        openServicesPage(scene);
    }

    private void openServicesPage(Scene scene) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("services-page.fxml"));
        Parent root = loader.load();
        ServicesController servicesController = loader.getController();
        Stage currentStage = (Stage) scene.getWindow();
        currentStage.close();
        Stage servicesStage = new Stage();
        servicesStage.setResizable(false);
        servicesStage.setTitle("BrandBoost");
        servicesStage.setScene(new Scene(root, 950, 670));
        servicesStage.show();
    }

    @FXML
    void openClientPage(ActionEvent event) throws IOException {
        MenuItem menuItem = (MenuItem) event.getSource();
        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
        openClientPage(scene);
    }

    private void openClientPage(Scene scene) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("client-page.fxml"));
        Parent root = loader.load();
        ClientController clientController = loader.getController();
        Stage currentStage = (Stage) scene.getWindow();
        currentStage.close();
        Stage clientStage = new Stage();
        clientStage.setResizable(false);
        clientStage.setTitle("BrandBoost");
        clientStage.setScene(new Scene(root, 950, 670));
        clientStage.show();
    }

    @FXML
    void deleteModalUser(ActionEvent event) throws IOException {
        Order orderSelectedList = tOrder.getSelectionModel().getSelectedItem();
        if (orderSelectedList != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление записи");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить запись?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new OrderService().delete(orderSelectedList);
                data.remove(orderSelectedList);
                showSuccessMessage();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Выберите запись");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для удаления.");
            alert.showAndWait();
        }
    }

    @FXML
    void logoutButton(ActionEvent event) {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Подтверждение выхода");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Вы действительно хотите выйти из системы?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Stage currentStage = (Stage) logoutButton.getParentPopup().getOwnerWindow();
            currentStage.close();
            try {
                FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("auth-page.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage authStage = new Stage();
                authStage.setScene(scene);
                authStage.show();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Выход из системы");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Вы успешно вышли из системы!");
                successAlert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void openAddUserForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("add-order.page.fxml"));
        Parent root = loader.load();
        Stage addUserStage = new Stage();
        addUserStage.initModality(Modality.APPLICATION_MODAL);
        addUserStage.setTitle("Добавление заказа");
        addUserStage.setScene(new Scene(root));
        AddOrderController addOrderController = loader.getController();
        List<Client> clients = new ClientService().getClient();
        addOrderController.getClientField().setItems(FXCollections.observableList(clients));
        System.out.println(clients);
        List<Services> services = new ServicesService().getServices();
        addOrderController.getServicesField().setItems(FXCollections.observableList(services));
        System.out.println(services);
        addUserStage.setOnHidden(e -> {
            try {
                loadData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        addUserStage.showAndWait();
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешное удаление");
        alert.setHeaderText(null);
        alert.setContentText("Заказ успешно удален!");
        alert.showAndWait();
    }

    @FXML
    void openEditModalUser(ActionEvent event) throws IOException {
        Order selectedOrders = tOrder.getSelectionModel().getSelectedItem();
        if (selectedOrders != null) {
            FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("edit-order.page.fxml"));
            Parent root = loader.load();
            EditOrderController editOrderController = loader.getController();
            List<Client> clients = new ClientService().getClient();
            editOrderController.getClientField().setItems(FXCollections.observableList(clients));
            System.out.println(clients);
            List<Services> services = new ServicesService().getServices();
            editOrderController.getServicesField().setItems(FXCollections.observableList(services));
            editOrderController.setOrder(selectedOrders);
            Stage editOrderStage = new Stage();
            editOrderStage.initModality(Modality.APPLICATION_MODAL);
            editOrderStage.setTitle("Редактирование заявки");
            editOrderStage.setScene(new Scene(root));
            editOrderStage.setOnHidden(e -> {
                try {
                    loadData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            editOrderStage.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Нет выбранной записи");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для редактирования.");
            alert.showAndWait();
        }
    }

    public static ObservableList<Order> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        loadData();
        if (AuthController.userRole.equals("Администратор")){
            openEditModalUser.setVisible(true);
            deleteModalUser.setVisible(true);
            addUserButton.setVisible(true);
            openUserPage.setVisible(true);
        }
        else {
            openEditModalUser.setVisible(false);
            deleteModalUser.setVisible(false);
            addUserButton.setVisible(false);
            openUserPage.setVisible(false);
        }
    }

    @FXML
    public void loadData() throws IOException {
        OrderService.init();
        client.setCellValueFactory(data -> {
            Order order = data.getValue();
            String clientName = String.valueOf(order.getClient().getName());
            return new SimpleStringProperty(clientName);
        });
        services.setCellValueFactory(data -> {
            Order order = data.getValue();
            String servicesName = String.valueOf(order.getService().getName());
            return new SimpleStringProperty(servicesName);
        });
        orderDate.setCellValueFactory(new PropertyValueFactory<Order, String>("orderDate"));
        finalDate.setCellValueFactory(new PropertyValueFactory<Order, String>("finalDate"));
        tOrder.setItems(data);
    }

    @FXML
    void updateOpenClientPage(ActionEvent event) throws IOException {
//        MenuItem menuItem = (MenuItem) event.getSource();
//        Scene scene = menuItem.getParentPopup().getOwnerWindow().getScene();
//        openClientPage(scene);

        try {
            File htmlFile = new File("src/main/resources/com/example/brandboostclient/update8_1_1.html");
            Desktop.getDesktop().browse(htmlFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
