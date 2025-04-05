package perimetre;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class PerimetreController {

    @FXML
    private TextField textLongueur;

    @FXML
    private TextField textLargeur;

    @FXML
    private Label valResultat;

    private PerimetreModele modele;

    public PerimetreController() {
        modele = new PerimetreModele();
    }

    @FXML
    private void buttonCalculer(ActionEvent event) {
        try {
            double longueur = Double.parseDouble(textLongueur.getText());
            double largeur = Double.parseDouble(textLargeur.getText());
            double perimetre = modele.calculPerimetre(longueur, largeur);
            valResultat.setText(String.format("%.2f", perimetre));
        } catch (NumberFormatException e) {
            valResultat.setText("Erreur");
        }
    }

    @FXML
    private void buttonNettoyer(ActionEvent event) {
        textLongueur.clear();
        textLargeur.clear();
        valResultat.setText("0.0");
    }
}
