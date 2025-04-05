package uiApp;

// ======= SECTION : MODÈLE DE CRÉDIT =======
public class CreditModel {
    // ======= SECTION : ATTRIBUTS =======
    private double dureeMax;         // Durée maximale du crédit en mois
    private double montantMax;       // Montant maximum du crédit
    private double mensualiteMax;    // Mensualité maximale
    private double nouveauMontant;   // Nouveau montant calculé
    private double nouvelleDuree;    // Nouvelle durée calculée
    private double nouvelleMensualite; // Nouvelle mensualité calculée

    // ======= SECTION : CALCUL INITIAL DU CRÉDIT =======
    public void calculerCredit(int age, double salaire) {
        dureeMax = (62 - age) * 12;
        montantMax = dureeMax * 0.3 * salaire;
        mensualiteMax = montantMax / dureeMax;
    }

    // ======= SECTION : RECALCUL DU CRÉDIT =======
    public void recalculerCredit(double dureeSouhaitee, double mensualiteSouhaitee) {
        if (dureeSouhaitee < dureeMax) {
            nouveauMontant = dureeSouhaitee * mensualiteMax;
            nouvelleDuree = dureeSouhaitee;
            nouvelleMensualite = mensualiteMax;
        } else {
            nouveauMontant = montantMax;
            nouvelleDuree = dureeMax;
            nouvelleMensualite = mensualiteSouhaitee;
        }
    }

    // ======= SECTION : GETTERS =======
    public double getDureeMax() { return dureeMax; }
    public double getMontantMax() { return montantMax; }
    public double getMensualiteMax() { return mensualiteMax; }
    public double getNouveauMontant() { return nouveauMontant; }
    public double getNouvelleDuree() { return nouvelleDuree; }
    public double getNouvelleMensualite() { return nouvelleMensualite; }
}
