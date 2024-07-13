package com.example.pharmacovigilance;

import java.sql.Date;

public class Medicament {
    private String dci;
    private String nomCommercial;
    private String lot;
    private String voieAdministration;
    private String posologie;
    private Date dateDebutAdministration;
    private Date dateFinAdministration;
    private boolean enCours;
    private String indication;


    public Medicament() {
        this.voieAdministration="orale";
        long millis=System.currentTimeMillis();
        this.dateDebutAdministration= new java.sql.Date(millis);
        this.dateFinAdministration= new java.sql.Date(millis);
    }
    public Medicament(String dci, String nomCommercial,String lot, String voieAdministration, String posologie, Date dateDebutAdministration, Date dateFinAdministration, boolean enCours,String indication) {
        this.dci = dci;
        this.nomCommercial=nomCommercial;
        this.lot = lot;
        this.voieAdministration = voieAdministration;
        this.posologie = posologie;
        this.dateDebutAdministration = dateDebutAdministration;
        this.dateFinAdministration = dateFinAdministration;
        this.enCours=enCours;
        this.indication = indication;
    }

    public String getNomCommercial() {
        return nomCommercial;
    }

    public void setNomCommercial(String nomCommercial) {
        this.nomCommercial = nomCommercial;
    }

    public String getDci() {
        return dci;
    }

    public void setDci(String dci) {
        this.dci = dci;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getVoieAdministration() {
        return voieAdministration;
    }

    public void setVoieAdministration(String voieAdministration) {
        this.voieAdministration = voieAdministration;
    }

    public String getPosologie() {
        return posologie;
    }

    public void setPosologie(String posologie) {
        this.posologie = posologie;
    }

    public Date getDateDebutAdministration() {
        return dateDebutAdministration;
    }

    public void setDateDebutAdministration(Date dateDebutAdministration) {
        this.dateDebutAdministration = dateDebutAdministration;
    }

    public Date getDateFinAdministration() {
        return dateFinAdministration;
    }

    public void setDateFinAdministration(Date dateFinAdministration) {
        this.dateFinAdministration = dateFinAdministration;
    }

    public boolean isEncours() {
        return enCours;
    }

    public void setEncours(boolean encours) {
        this.enCours = encours;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }
}
