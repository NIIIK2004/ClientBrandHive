package com.example.brandboostclient.controller;

import com.example.brandboostclient.BrandBoostApplication;
import com.example.brandboostclient.model.Client;
import com.example.brandboostclient.model.Role;
import com.example.brandboostclient.model.User;
import com.example.brandboostclient.service.ClientService;
import com.example.brandboostclient.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ClientController {
    @FXML
    private Button addClientButton;

    @FXML
    private TableColumn<Client, String> address;

    @FXML
    private Button deleteModalClient;

    @FXML
    private TableColumn<Client, String> email;

    @FXML
    private TableColumn<Client, String> lastname;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private TableColumn<Client, String> name;

    @FXML
    private TableColumn<Client, String> number;

    @FXML
    private Button openEditModalClient;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Client, String> surname;

    @FXML
    private TableView<Client> tClient;

    @FXML
    private MenuItem openServicesPage;

    @FXML
    private MenuItem openUserPage;

    @FXML
    private MenuItem openClientPage;

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
    void addClientButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("add-client.page.fxml"));
        Parent root = loader.load();
        Stage addClientPage = new Stage();
        addClientPage.initModality(Modality.APPLICATION_MODAL);
        addClientPage.setTitle("Добавление клиента");
        addClientPage.setScene(new Scene(root));
        AddClientController addClientController = loader.getController();
        addClientPage.setOnHidden(e -> {
            try {
                loadData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        addClientPage.showAndWait();
    }

    @FXML
    void deleteModalClient(ActionEvent event) throws IOException {
        Client clientSelectedList = tClient.getSelectionModel().getSelectedItem();
        if (clientSelectedList != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление записи");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить запись?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new ClientService().delete(clientSelectedList);
                data.remove(clientSelectedList);
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

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешное удаление");
        alert.setHeaderText(null);
        alert.setContentText("Пользователь успешно удален!");
        alert.showAndWait();
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
    void openEditModalClient(ActionEvent event) throws IOException {
        Client selectedClient = tClient.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("edit-client.page.fxml"));
            Parent root = loader.load();
            EditClientController editClientController = loader.getController();
            editClientController.setClient(selectedClient);
            Stage editUserStage = new Stage();
            editUserStage.initModality(Modality.APPLICATION_MODAL);
            editUserStage.setTitle("Редактирование клиента");
            editUserStage.setScene(new Scene(root));
            editUserStage.setOnHidden(e -> {
                try {
                    loadData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            editUserStage.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Нет выбранной записи");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для редактирования.");
            alert.showAndWait();
        }
    }

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

    public static ObservableList<Client> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        loadData();
        if (AuthController.userRole.equals("Администратор")){
            addClientButton.setVisible(true);
            deleteModalClient.setVisible(true);
            openEditModalClient.setVisible(true);
            openUserPage.setVisible(true);
            ;
        }
        else {
            addClientButton.setVisible(false);
            deleteModalClient.setVisible(false);
            openEditModalClient.setVisible(false);
            openUserPage.setVisible(false);
        }
        searchButton.setOnAction(event -> {
            try {
                search();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void loadData() throws IOException {
        ClientService.init();
        name.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<Client, String>("surname"));
        lastname.setCellValueFactory(new PropertyValueFactory<Client, String>("lastname"));
        number.setCellValueFactory(new PropertyValueFactory<Client, String>("number"));
        address.setCellValueFactory(new PropertyValueFactory<Client, String>("address"));
        email.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        tClient.setItems(data);
    }

    @FXML
    private void search() throws IOException {
        String query = searchField.getText();
        new ClientService().search(query);
        tClient.setItems(data);
    }

}
