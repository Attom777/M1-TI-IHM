package uiApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

// ======= SECTION : CLASSE PRINCIPALE =======
public class CreditApp extends Application {
    // ======= SECTION : INITIALISATION DE L'APPLICATION =======
    @Override
    public void start(Stage stage) throws IOException {
        // Chargement du fichier FXML
        FXMLLoader fxmlLoader = new FXMLLoader(CreditApp.class.getResource("appCreditGUI.fxml"));

        // Création de la scène avec dimensions spécifiées
        Scene scene = new Scene(fxmlLoader.load(), 1000, 750);

        // Configuration de la fenêtre principale
        stage.setTitle("Simulateur de Crédit Bancaire");
        stage.setScene(scene);
        stage.show();
    }

    // ======= SECTION : POINT D'ENTRÉE =======
    public static void main(String[] args) {
        launch();
    }
}
