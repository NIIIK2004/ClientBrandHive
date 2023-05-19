package com.example.brandboostclient.controller;

import com.example.brandboostclient.BrandBoostApplication;
import com.example.brandboostclient.model.Role;
import com.example.brandboostclient.model.Services;
import com.example.brandboostclient.model.User;
import com.example.brandboostclient.service.ServicesService;
import com.example.brandboostclient.service.UserService;
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
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServicesController {
    @FXML
    private Button addServiceButton;

    @FXML
    private Button deleteModalService;

    @FXML
    private TableColumn<Services, String> description;

    @FXML
    private MenuItem logoutButton;

    @FXML
    private TableColumn<Services, String> name;

    @FXML
    private MenuItem openClientPage;

    @FXML
    private Button openEditServiceModal;

    @FXML
    private TableColumn<Services, Integer> price;

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Services> tUser;

    @FXML
    private MenuItem openServicesPage;

    @FXML
    private MenuItem openUserPage;

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
    void addServiceButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("add-services.page.fxml"));
        Parent root = loader.load();
        Stage addUserStage = new Stage();
        addUserStage.initModality(Modality.APPLICATION_MODAL);
        addUserStage.setTitle("Добавление услуги");
        addUserStage.setScene(new Scene(root));
        AddServicesController addServicesController = loader.getController();
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
    void deleteModalService(ActionEvent event) throws IOException {
        Services servicesSelectedList = tUser.getSelectionModel().getSelectedItem();
        if (servicesSelectedList != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Удаление записи");
            alert.setHeaderText(null);
            alert.setContentText("Вы уверены, что хотите удалить запись?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new ServicesService().delete(servicesSelectedList);
                data.remove(servicesSelectedList);
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
        alert.setContentText("Услуга успешно удалена!");
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
    void openEditServiceModal(ActionEvent event) throws IOException {
        Services selectedServices = tUser.getSelectionModel().getSelectedItem();
        if (selectedServices != null) {
            FXMLLoader loader = new FXMLLoader(BrandBoostApplication.class.getResource("edit-services.page.fxml"));
            Parent root = loader.load();
            EditServicesController editServicesController = loader.getController();
            editServicesController.setServices(selectedServices);
            Stage editServiceStage = new Stage();
            editServiceStage.initModality(Modality.APPLICATION_MODAL);
            editServiceStage.setTitle("Редактирование услуг");
            editServiceStage.setScene(new Scene(root));
            editServiceStage.setOnHidden(e -> {
                try {
                    loadData();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            editServiceStage.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Нет выбранной записи");
            alert.setHeaderText(null);
            alert.setContentText("Пожалуйста, выберите запись для редактирования.");
            alert.showAndWait();
        }
    }

    public static ObservableList<Services> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws IOException {
        loadData();
        if (AuthController.userRole.equals("Администратор")){
            openEditServiceModal.setVisible(true);
            deleteModalService.setVisible(true);
            addServiceButton.setVisible(true);
            openUserPage.setVisible(true);
        }
        else {
            openEditServiceModal.setVisible(false);
            deleteModalService.setVisible(false);
            addServiceButton.setVisible(false);
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
        ServicesService.init();
        name.setCellValueFactory(new PropertyValueFactory<Services, String>("name"));
        description.setCellValueFactory(new PropertyValueFactory<Services, String>("description"));
        price.setCellValueFactory(new PropertyValueFactory<Services, Integer>("price"));
        tUser.setItems(data);
    }

    @FXML
    private void search() throws IOException {
        String query = searchField.getText();
        new ServicesService().search(query);
        tUser
                .setItems(data);
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
