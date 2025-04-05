package uiApp;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;

// ======= SECTION : CONTRÔLEUR DU SIMULATEUR DE CRÉDIT =======
public class CreditSimulatorController {
    // ======= SECTION : DÉCLARATION DES ÉLÉMENTS DE L'INTERFACE =======
    @FXML private TextField prenomField, nomField, villeField, ageField, salaireField;
    @FXML private TextField dureeMaxField, montantMaxField, mensualiteMaxField;
    @FXML private Slider dureeSouhaiteeSlider, mensualiteSouhaiteeSlider;
    @FXML private Label dureeSouhaiteeLabel, mensualiteSouhaiteeLabel;
    @FXML private TextField nouveauMontantField, nouvelleDureeField, nouvelleMensualiteField;
    @FXML private Button calculerButton, recalculerButton;

    private CreditModel model = new CreditModel();
    private boolean isUpdatingSliders = false;

    // ======= SECTION : INITIALISATION =======
    @FXML
    public void initialize() {
        configureNumericField(ageField, "Ex: 30");
        configureNumericField(salaireField, "Ex: 2500");

        setReadOnlyFields(dureeMaxField, montantMaxField, mensualiteMaxField,
                nouveauMontantField, nouvelleDureeField, nouvelleMensualiteField);

        addTextChangeListeners(prenomField, nomField, villeField, ageField, salaireField);

        setupSliders();

        calculerButton.setDisable(false);
        recalculerButton.setDisable(true);
        dureeSouhaiteeSlider.setDisable(true);
        mensualiteSouhaiteeSlider.setDisable(true);

        setupEnterKeyHandler();
    }

    // ======= SECTION : CONFIGURATION DES CHAMPS ET ÉVÉNEMENTS =======
    private void setupEnterKeyHandler() {
        TextField[] fields = {prenomField, nomField, villeField, ageField, salaireField};
        for (TextField field : fields) {
            field.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    calculerCredit();
                }
            });
        }
    }

    private void configureNumericField(TextField field, String placeholder) {
        field.setPromptText(placeholder);
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    private void setReadOnlyFields(TextField... fields) {
        for (TextField field : fields) {
            field.setEditable(false);
        }
    }

    private void addTextChangeListeners(TextField... fields) {
        for (TextField field : fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        }
    }

    // ======= SECTION : CONFIGURATION DES SLIDERS =======
    private void setupSliders() {
        dureeSouhaiteeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdatingSliders) {
                isUpdatingSliders = true;
                dureeSouhaiteeLabel.setText(String.format("%.0f mois", newValue));

                double nouveauMontantMensuel = model.getMontantMax() / newValue.doubleValue();
                mensualiteSouhaiteeSlider.setValue(nouveauMontantMensuel);
                mensualiteSouhaiteeLabel.setText(String.format("%.2f €", nouveauMontantMensuel));

                recalculerButton.setDisable(false);
                isUpdatingSliders = false;
            }
        });

        mensualiteSouhaiteeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!isUpdatingSliders) {
                isUpdatingSliders = true;
                mensualiteSouhaiteeLabel.setText(String.format("%.2f €", newValue));

                double nouvelleDuree = model.getMontantMax() / newValue.doubleValue();
                dureeSouhaiteeSlider.setValue(Math.min(nouvelleDuree, model.getDureeMax()));
                dureeSouhaiteeLabel.setText(String.format("%.0f mois", Math.min(nouvelleDuree, model.getDureeMax())));

                recalculerButton.setDisable(false);
                isUpdatingSliders = false;
            }
        });
    }

    // ======= SECTION : VALIDATION DES CHAMPS =======
    private void validateFields() {
        boolean isValid = !prenomField.getText().isEmpty() &&
                !nomField.getText().isEmpty() &&
                !ageField.getText().isEmpty() &&
                !salaireField.getText().isEmpty();

        calculerButton.setDisable(!isValid);
    }

    // ======= SECTION : CALCUL DU CRÉDIT =======
    @FXML
    private void calculerCredit() {
        StringBuilder errors = new StringBuilder("Veuillez corriger les erreurs suivantes :\n");
        boolean hasErrors = false;

        // Vérification des champs vides
        if (prenomField.getText().isEmpty()) {
            errors.append("- Champ 'Prénom' obligatoire non rempli\n");
            hasErrors = true;
        }
        if (nomField.getText().isEmpty()) {
            errors.append("- Champ 'Nom' obligatoire non rempli\n");
            hasErrors = true;
        }
        if (ageField.getText().isEmpty()) {
            errors.append("- Champ 'Âge' obligatoire non rempli\n");
            hasErrors = true;
        }
        if (salaireField.getText().isEmpty()) {
            errors.append("- Champ 'Salaire' obligatoire non rempli\n");
            hasErrors = true;
        }

        if (hasErrors) {
            showAlert("Erreur", errors.toString());
            return;
        }

        try {
            int age = Integer.parseInt(ageField.getText());
            double salaire = Double.parseDouble(salaireField.getText());

            // Vérification des conditions d'éligibilité
            if (age < 18) {
                errors.append("- Âge minimum requis : 18 ans\n");
                hasErrors = true;
            }
            if (age >= 62) {
                errors.append("- Âge maximum autorisé : 61 ans\n");
                hasErrors = true;
            }
            if (salaire < 2000) {
                errors.append("- Salaire minimum requis : 2000 euros\n");
                hasErrors = true;
            }

            if (hasErrors) {
                showAlert("Erreur", errors.toString());
                return;
            }

            // Calcul et affichage des résultats
            model.calculerCredit(age, salaire);

            dureeMaxField.setText(String.format("%.0f", model.getDureeMax()));
            montantMaxField.setText(String.format("%.2f", model.getMontantMax()));
            mensualiteMaxField.setText(String.format("%.2f", model.getMensualiteMax()));

            // Configuration des sliders
            dureeSouhaiteeSlider.setDisable(false);
            dureeSouhaiteeSlider.setMin(1);
            dureeSouhaiteeSlider.setMax(model.getDureeMax());
            dureeSouhaiteeSlider.setValue(model.getDureeMax());

            mensualiteSouhaiteeSlider.setDisable(false);
            mensualiteSouhaiteeSlider.setMin(model.getMensualiteMax() / 2);
            mensualiteSouhaiteeSlider.setMax(model.getMensualiteMax() * 2);
            mensualiteSouhaiteeSlider.setValue(model.getMensualiteMax());

            recalculerCredit();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer des valeurs numériques valides pour l'âge et le salaire.");
        }
    }

    // ======= SECTION : RECALCUL DU CRÉDIT =======
    @FXML
    private void recalculerCredit() {
        double dureeSouhaitee = dureeSouhaiteeSlider.getValue();
        double mensualiteSouhaitee = mensualiteSouhaiteeSlider.getValue();

        model.recalculerCredit(dureeSouhaitee, mensualiteSouhaitee);

        nouveauMontantField.setText(String.format("%.2f", model.getNouveauMontant()));
        nouvelleDureeField.setText(String.format("%.0f", model.getNouvelleDuree()));
        nouvelleMensualiteField.setText(String.format("%.2f", model.getNouvelleMensualite()));

        recalculerButton.setDisable(true);
    }

    // ======= SECTION : AFFICHAGE DES ALERTES =======
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
