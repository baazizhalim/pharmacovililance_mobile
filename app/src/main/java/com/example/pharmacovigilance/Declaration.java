package com.example.pharmacovigilance;

import static org.json.JSONObject.NULL;

import java.sql.Date;

public class Declaration {
    private String nomMalade;
    private String prenom;
    private int age;
    private String genre;
    private int taille;
    private int poids;
    private String DescriptionReactionIndesirable;
    private Date apparaition;
    private int mois;
    private int jour;
    private int heure;
    private Medicament [] medicaments;
    private boolean traitementMedicamenteux;
    private boolean traitementNonMedicamenteux;
    private String DescriptionTraitementReactionIndesirable;
    private String evolution;
    private boolean reexposition;
    private boolean effetReexposition;
    private boolean examenCompl;
    private boolean effetExamenCompl;
    private String antecedant;
    private String autreExplication;
    private String medecin;

    public Declaration(String nomMalade, String prenom, int age, String genre, int taille, int poids, String descriptionReactionIndesirable, Date apparaition, int mois, int jour, int heure, Medicament[] medicaments, boolean traitementMedicamenteux, boolean traitementNonMedicamenteux, String descriptionTraitementReactionIndesirable, String evolution, boolean reexposition, boolean effetReexposition, boolean examenCompl, boolean effetExamenCompl, String antecedant, String autreExplication, String medecin) {
        this.nomMalade = nomMalade;
        this.prenom = prenom;
        this.age = age;
        this.genre = genre;
        this.taille = taille;
        this.poids = poids;
        this.DescriptionReactionIndesirable = descriptionReactionIndesirable;
        this.apparaition = apparaition;
        this.mois = mois;
        this.jour = jour;
        this.heure = heure;

        this.medicaments = medicaments;
        this.traitementMedicamenteux = traitementMedicamenteux;
        this.traitementNonMedicamenteux = traitementNonMedicamenteux;
        this.DescriptionTraitementReactionIndesirable = descriptionTraitementReactionIndesirable;
        this.evolution = evolution;
        this.reexposition = reexposition;
        this.effetReexposition = effetReexposition;
        this.examenCompl = examenCompl;
        this.effetExamenCompl = effetExamenCompl;
        this.antecedant = antecedant;
        this.autreExplication = autreExplication;
        this.medecin = medecin;
    }

    public Declaration() {
        this.genre="Masculin";
        long millis=System.currentTimeMillis();
        this.apparaition= new java.sql.Date(millis);
        this.mois=0;
        this.jour=0;
        this.heure=0;
        this.traitementMedicamenteux = false;
        this.traitementNonMedicamenteux = false;
        this.evolution = "";
        this.reexposition = false;
        this.effetReexposition = false;
        this.examenCompl = false;
        this.effetExamenCompl = false;
        this.medicaments = new Medicament[]{null, null, null};
    }

    public String getNomMalade() {
        return nomMalade;
    }

    public void setNomMalade(String nomMalade) {
        this.nomMalade = nomMalade;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getDescriptionReactionIndesirable() {
        return DescriptionReactionIndesirable;
    }

    public void setDescriptionReactionIndesirable(String descriptionReactionIndesirable) {
        DescriptionReactionIndesirable = descriptionReactionIndesirable;
    }

    public String getDescriptionTraitementReactionIndesirable() {
        return DescriptionTraitementReactionIndesirable;
    }

    public void setDescriptionTraitementReactionIndesirable(String descriptionTraitementReactionIndesirable) {
        DescriptionTraitementReactionIndesirable = descriptionTraitementReactionIndesirable;
    }

    public Date getApparaition() {
        return apparaition;
    }

    public void setApparaition(Date apparaition) {
        this.apparaition = apparaition;
    }

    public Medicament[] getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Medicament[] medicaments) {
        this.medicaments = medicaments;
    }

    public boolean isTraitementMedicamenteux() {
        return traitementMedicamenteux;
    }

    public void setTraitementMedicamenteux(boolean traitementMedicamenteux) {
        this.traitementMedicamenteux = traitementMedicamenteux;
    }

    public boolean isTraitementNonMedicamenteux() {
        return traitementNonMedicamenteux;
    }

    public void setTraitementNonMedicamenteux(boolean traitementNonMedicamenteux) {
        this.traitementNonMedicamenteux = traitementNonMedicamenteux;
    }
    public String getEvolution() {
        return evolution;
    }

    public void setEvolution(String evolution) {
        this.evolution = evolution;
    }

    public boolean isReexposition() {
        return reexposition;
    }

    public void setReexposition(boolean reexposition) {
        this.reexposition = reexposition;
    }

    public boolean isEffetReexposition() {
        return effetReexposition;
    }

    public void setEffetReexposition(boolean effetReexposition) {
        this.effetReexposition = effetReexposition;
    }

    public boolean isExamenCompl() {
        return examenCompl;
    }

    public void setExamenCompl(boolean examenCompl) {
        this.examenCompl = examenCompl;
    }

    public boolean isEffetExamenCompl() {
        return effetExamenCompl;
    }

    public void setEffetExamenCompl(boolean effetExamenCompl) {
        this.effetExamenCompl = effetExamenCompl;
    }

    public String getAntecedant() {
        return antecedant;
    }

    public void setAntecedant(String antecedant) {
        this.antecedant = antecedant;
    }

    public String getAutreExplication() {
        return autreExplication;
    }

    public void setAutreExplication(String autreExplication) {
        this.autreExplication = autreExplication;
    }

    public String getMedecin() {
        return medecin;
    }

    public void setMedecin(String medecin) {
        this.medecin = medecin;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }
}

