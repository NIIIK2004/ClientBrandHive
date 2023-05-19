package com.example.brandboostclient.controller;

import com.example.brandboostclient.BrandBoostApplication;
import com.example.brandboostclient.model.Role;
import com.example.brandboostclient.model.User;
import com.example.brandboostclient.service.UserService;
import javafx.beans.property.SimpleStringProperty;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserController {
    @FXML
    private Button addUserButton;

    @FXML
    private TableColumn<User, String> password;

    @FXML
    private TableColumn<User, String> role;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<User> tUser;

    @FXML
    private MenuItem tableCompany;

    @FXML
    private TableColumn<User, String> username;

    @FXML
    private MenuItem logoutButton;
    
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
    void deleteModalUser(ActionEvent event) throws IOException {
        User userSelectedList = tUser.getSelectionModel().getSelectedItem();
        if (userSelectedList != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление записи");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить запись?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new UserService().delete(userSelectedList);
                data.remove(userSelectedList);
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
    void openAddUserForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("add-user.page.fxml"));
        Parent root = loader.load();
        Stage addUserStage = new Stage();
        addUserStage.initModality(Modality.APPLICATION_MODAL);
        addUserStage.setTitle("Добавление пользователя");
        addUserStage.setScene(new Scene(root));
        AddUserController addUserController = loader.getController();
        addUserController.getRoleField().setItems(FXCollections.observableArrayList(Role.values()));
        addUserStage.setOnHidden(e -> {
            try {
                loadData();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        addUserStage.showAndWait();
    }

    @FXML
    void openEditModalUser(ActionEvent event) throws IOException {
        User selectedUser = tUser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("edit-user.page.fxml"));
            Parent root = loader.load();
            EditUserController editUserController = loader.getController();
            editUserController.getRoleField().setItems(FXCollections.observableArrayList(Role.values()));
            editUserController.setUser(selectedUser);
            Stage editUserStage = new Stage();
            editUserStage.initModality(Modality.APPLICATION_MODAL);
            editUserStage.setTitle("Редактирование пользователя");
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
    private Button deleteModalUser;

    @FXML
    private Button openEditModalUser;

    public static ObservableList<User> data = FXCollections.observableArrayList();

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
        UserService.init();
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        role.setCellValueFactory(data -> {
            User user = data.getValue();
            String role = user.getRoles()
                    .stream()
                    .map(Role::getName)
                    .findFirst()
                    .orElse("");
            return new SimpleStringProperty(role);
        });
        tUser.setItems(data);
    }

    @FXML
    private void search() throws IOException {
        String query = searchField.getText();
        new UserService().search(query);
        tUser.setItems(data);
    }
}
