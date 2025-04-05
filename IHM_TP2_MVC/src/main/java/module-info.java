module perimetre{
    requires javafx.controls;
    requires javafx.fxml;


    opens perimetre to javafx.fxml;
    exports perimetre;
}