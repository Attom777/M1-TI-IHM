module uiApp {
    requires javafx.controls;
    requires javafx.fxml;


    opens uiApp to javafx.fxml;
    exports uiApp;
}