package com.example.pharmacovigilance;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "pharmacovigilance.db";
    private static final int DATABASE_VERSION = 3;
    String sql4 = " create table medic (" +
            " id integer primary key autoincrement," +
            " dci text not null" +
            ")";


    String sql1 = " create table declaration (" +
            " id integer primary key autoincrement," +
            " nomMalade text not null," +
            " prenom text not null," +
            " age integer," +
            " genre text not null," +
            " taille integer," +
            " poids integer," +
            " DescriptionReactionIndesirable text not null," +
            " apparaition Date," +
            " mois integer," +
            " jour integer," +
            " heure integer," +
            " traitementMedicamenteux boolean," +
            " traitementNonMedicamenteux boolean," +
            " DescriptionTraitementReactionIndesirable text not null," +
            " evolution text not null," +
            " reexposition boolean," +
            " effetReexposition boolean," +
            " examenCompl boolean," +
            " effetExamenCompl boolean," +
            " antecedant text not null," +
            " autreExplication text not null," +
            " medecin text not null" +
            ")";

    String sql2 = " create table medicament (" +
            " id integer primary key autoincrement," +
            " dci text not null," +
            " lot text not null," +
            " voixAdministration text not null," +
            " posologie text not null," +
            " dateDebutAdministration Date," +
            " dateFinAdministration Date," +
            " enCours boolean," +
            " indication text not null," +
            " id_declaration integer" +
            ")";

    String sql3 = " create table medecin (" +
            " id integer primary key autoincrement," +
            " nom text not null," +
            " prenom text not null," +
            " email text not null," +
            " motdepasse text not null," +
            " telephone text not null," +
            " exercice text not null," +
            " domaine text not null," +
            " adresse text not null" +
            ")";

    public DatabaseManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            String sql = "alter table medicament add column nom_commercial text";
            db.execSQL(sql);
        }

    }


    public int getCountDeclaration() {

        String countQuery = "SELECT  * FROM declaration";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;


    }


    public ArrayList<Declaration> getAllDeclaration() {

        ArrayList<Declaration> ListDeclaration = new ArrayList<>();

        String strSql = "Select * from declaration";
        try {
            @SuppressLint("Recycle") Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);

            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                    String nomMalade = cursor.getString(cursor.getColumnIndexOrThrow("nomMalade"));
                    String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));
                    int age = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("age")));
                    String genre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
                    int taille = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("taille")));
                    int poids = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("poids")));
                    String DescriptionReactionIndesirable = cursor.getString(cursor.getColumnIndexOrThrow("DescriptionReactionIndesirable"));
                    Date apparaition = Date.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("apparaition")));
                    int mois = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("mois")));
                    int jour = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("jour")));
                    int heure = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("heure")));
                    boolean traitementMedicamenteux = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("traitementMedicamenteux")));
                    boolean traitementNonMedicamenteux = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("traitementNonMedicamenteux")));
                    String DescriptionTraitementReactionIndesirable = cursor.getString(cursor.getColumnIndexOrThrow("DescriptionTraitementReactionIndesirable"));
                    String evolution = cursor.getString(cursor.getColumnIndexOrThrow("evolution"));
                    boolean reexposition = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("reexposition")));
                    boolean effetReexposition = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("effetReexposition")));
                    boolean examenCompl = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("examenCompl")));
                    boolean effetExamenCompl = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("effetExamenCompl")));
                    String antecedant = cursor.getString(cursor.getColumnIndexOrThrow("antecedant"));
                    String autreExplication = cursor.getString(cursor.getColumnIndexOrThrow("autreExplication"));
                    String medecin = cursor.getString(cursor.getColumnIndexOrThrow("medecin"));
                    Medicament[] medicaments = getAllMedicament(id);


                    Declaration declaration = new Declaration(nomMalade, prenom, age, genre, taille, poids, DescriptionReactionIndesirable, apparaition, mois, jour, heure, medicaments, traitementMedicamenteux, traitementNonMedicamenteux, DescriptionTraitementReactionIndesirable, evolution, reexposition, effetReexposition, examenCompl, effetExamenCompl, antecedant, autreExplication, medecin);


                    ListDeclaration.add(declaration);

                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ListDeclaration;

    }

    public boolean insererMedicament(String[][] res) {
        boolean ok = false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from  medic");
        ContentValues values = new ContentValues();
        for (String[] re : res) {
            ok = false;
            if (re != null) {
                values.put("dci", re[0]);
                db.insert("medic", null, values);
                ok = true;
            }
        }
        db.close();

        return ok;
    }

    public List<CharSequence> getAllMedicament() {

        List<CharSequence> listMedicaments = new ArrayList<>();

        String strSql = "Select * from medic;";
        try {
            @SuppressLint("Recycle") Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);

            if (cursor.moveToFirst()) {


                while (!cursor.isAfterLast()) {

                    //String dci = cursor.getString(cursor.getColumnIndexOrThrow("dci"));
                    String nomMedicament = cursor.getString(cursor.getColumnIndexOrThrow("dci"));


                    listMedicaments.add(nomMedicament);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Log.d("liste medic db", listMedicaments.toString());
        return listMedicaments;

    }

    public Medicament[] getAllMedicament(int id_declaration) {

        Medicament[] listMedicaments = new Medicament[3];

        String strSql = "Select * from medicament where id_declaration=" + id_declaration;
        try {
            @SuppressLint("Recycle") Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);

            if (cursor.moveToFirst()) {


                int i = 0;
                while (!cursor.isAfterLast()) {

                    String dci = cursor.getString(cursor.getColumnIndexOrThrow("dci"));
                    String nomCommercial = cursor.getString(cursor.getColumnIndexOrThrow("nom_commercial"));
                    String lot = cursor.getString(cursor.getColumnIndexOrThrow("lot"));

                    String posologie = cursor.getString(cursor.getColumnIndexOrThrow("posologie"));

                    String voixAdministration = cursor.getString(cursor.getColumnIndexOrThrow("voixAdministration"));
                    Date dateDebutAdministration = Date.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("dateDebutAdministration")));
                    Date dateFinAdministration = Date.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("dateFinAdministration")));
                    boolean enCours = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndexOrThrow("enCours")));
                    String indication = cursor.getString(cursor.getColumnIndexOrThrow("indication"));


                    Medicament medicament = new Medicament(dci, nomCommercial, lot, voixAdministration, posologie, dateDebutAdministration, dateFinAdministration, enCours, indication);

                    listMedicaments[i++] = medicament;
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listMedicaments;

    }

    public void insertMedicament(Declaration declaration, long id_declaration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (Medicament medicament : declaration.getMedicaments()) {
            if (medicament != null) {
                values.put("dci", medicament.getDci());
                values.put("nom_commercial", medicament.getNomCommercial());
                values.put("lot", medicament.getLot());
                values.put("voixAdministration", medicament.getVoieAdministration());
                values.put("posologie", medicament.getPosologie());
                values.put("dateDebutAdministration", String.valueOf(medicament.getDateDebutAdministration()));
                values.put("dateFinAdministration", String.valueOf(medicament.getDateFinAdministration()));
                values.put("enCours", medicament.isEncours());
                values.put("indication", medicament.getIndication());
                values.put("id_declaration", id_declaration);
                db.insert("medicament", null, values);
            }
        }
        db.close();

    }


    public long insertDeclaration(Declaration declaration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put("nomMalade", declaration.getNomMalade());
        values.put("prenom", declaration.getPrenom());
        values.put("age", declaration.getAge());
        values.put("genre", declaration.getGenre());
        values.put("taille", String.valueOf(declaration.getTaille()));
        values.put("poids", String.valueOf(declaration.getPoids()));
        values.put("DescriptionReactionIndesirable", declaration.getDescriptionReactionIndesirable());

        values.put("apparaition", String.valueOf(declaration.getApparaition()));
        values.put("mois", declaration.getMois());
        values.put("jour", declaration.getJour());
        values.put("heure", declaration.getHeure());
        values.put("traitementMedicamenteux", declaration.isTraitementMedicamenteux());
        values.put("traitementNonMedicamenteux", declaration.isTraitementNonMedicamenteux());
        values.put("DescriptionTraitementReactionIndesirable", declaration.getDescriptionTraitementReactionIndesirable());

        values.put("evolution", declaration.getEvolution());
        values.put("reexposition", declaration.isReexposition());
        values.put("effetReexposition", declaration.isEffetReexposition());
        values.put("examenCompl", declaration.isExamenCompl());
        values.put("effetExamenCompl", declaration.isEffetExamenCompl());
        values.put("antecedant", String.valueOf(declaration.getAntecedant()));
        values.put("autreExplication", declaration.getAutreExplication());
        values.put("medecin", declaration.getMedecin());

        long id_declaration = db.insert("declaration", null, values);

        insertMedicament(declaration, id_declaration);
        //db.close();
        return id_declaration;
    }

    public long insertMedecin(Medecin medecin) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nom", medecin.getNom());
        values.put("prenom", medecin.getPrenom());
        values.put("email", medecin.getEmail());
        values.put("motdepasse", medecin.getMotdepasse());
        values.put("telephone", medecin.getTelephone());
        values.put("exercice", medecin.getExercice());
        values.put("domaine", medecin.getDomaine());
        values.put("adresse", medecin.getAdresse());
        long id = db.insert("medecin", null, values);
        db.close();
        return id;
    }

    public Medecin getMedecin(String email, String motdepasse) {
        SQLiteDatabase db = this.getReadableDatabase();


        Medecin medecin = null;

        String strSql = "Select * from medecin where email='" + email + "' and motdepasse='" + motdepasse + "'";
        try {
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(strSql, null);

            if (cursor.moveToFirst()) {


                while (!cursor.isAfterLast()) {

                    String nom = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
                    String prenom = cursor.getString(cursor.getColumnIndexOrThrow("prenom"));

                    String email1 = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                    String motdepasse1 = cursor.getString(cursor.getColumnIndexOrThrow("motdepasse"));
                    String telephone = cursor.getString(cursor.getColumnIndexOrThrow("telephone"));
                    String exercice = cursor.getString(cursor.getColumnIndexOrThrow("exercice"));
                    String domaine = cursor.getString(cursor.getColumnIndexOrThrow("domaine"));
                    String adresse = cursor.getString(cursor.getColumnIndexOrThrow("adresse"));
                    medecin = new Medecin(nom, prenom, email1, motdepasse1, telephone, exercice, domaine, adresse);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return medecin;


    }


    public int verifierMedecin(String email) {
        SQLiteDatabase db = this.getReadableDatabase();


        String strSql = "Select * from medecin where email='" + email + "'";
        try {
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(strSql, null);

            if (cursor.moveToFirst()) return 1;
            else return 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();
        return -1;
    }
}