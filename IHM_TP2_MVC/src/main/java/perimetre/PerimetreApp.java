package perimetre;

// ===== BASIC =====
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PerimetreApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        Parent root = FXMLLoader.load(getClass().getResource("PerimetreGUI.fxml"));

        // Créer une nouvelle scène avec les nœuds root
        Scene scene = new Scene(root, 350, 150);

        // Mettre le titre de la fenêtre principale
        primaryStage.setTitle("Calcul Périmètre d'un rectangle");

        // Ajouter la scène au Stage
        primaryStage.setScene(scene);

        // Afficher l'application JavaFX
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

