package com.example.pharmacovigilance;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pharmacovigilance.databinding.Pharmaco3Binding;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pharmaco3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pharmaco3 extends Fragment {
    static Declaration declaration;
    private DatabaseManager dbManager;
    private Pharmaco3Binding binding;
    private LayoutInflater inflater;

    public static Pharmaco3 newInstance() {
        return new Pharmaco3();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = Pharmaco3Binding.inflate(inflater, container, false);
        this.inflater = inflater;
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        declaration = MainActivity.declaration;
        dbManager = new DatabaseManager(requireActivity().getApplicationContext());
        if (declaration.getMedicaments()[1] == null)
            declaration.getMedicaments()[1] = new Medicament();
        binding.nomCommercial.setText(declaration.getMedicaments()[1].getNomCommercial());
        binding.lot.setText(declaration.getMedicaments()[1].getLot());
        binding.posologie.setText(declaration.getMedicaments()[1].getPosologie());
        binding.dda.setText(declaration.getMedicaments()[1].getDateDebutAdministration().toString());
        binding.dfa.setText(declaration.getMedicaments()[1].getDateFinAdministration().toString());
        binding.encours.setChecked(declaration.getMedicaments()[1].isEncours());
        binding.indication.setText(String.valueOf(declaration.getMedicaments()[1].getIndication()));

        List<CharSequence> medicaments = dbManager.getAllMedicament();

        Log.d("liste medic 2", medicaments.toString());
        Spinner spinnerMed = (Spinner) binding.dci;
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapterMed = new ArrayAdapter<>(
                this.requireContext(),
                android.R.layout.simple_spinner_item,
                medicaments
        );
// Specify the layout to use when the list of choices appears.
        adapterMed.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinnerMed.setAdapter(adapterMed);
        for (int i = 0; i < adapterMed.getCount(); i++) {
            if (adapterMed.getItem(i).equals(declaration.getMedicaments()[1].getDci()))
                spinnerMed.setSelection(i);
        }


        Spinner spinner = (Spinner) binding.voie;
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.liste_voie,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(declaration.getMedicaments()[1].getVoieAdministration()))
                spinner.setSelection(i);
        }
        binding.next1P2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declaration.getMedicaments()[1].setDci(binding.dci.getSelectedItem().toString());
                declaration.getMedicaments()[1].setNomCommercial(binding.nomCommercial.getText().toString());
                declaration.getMedicaments()[1].setLot(binding.lot.getText().toString());
                declaration.getMedicaments()[1].setPosologie(binding.posologie.getText().toString());
                declaration.getMedicaments()[1].setDateDebutAdministration(java.sql.Date.valueOf(binding.dda.getText().toString()));
                declaration.getMedicaments()[1].setDateFinAdministration(Date.valueOf(binding.dfa.getText().toString()));
                declaration.getMedicaments()[1].setEncours(binding.encours.isChecked());
                declaration.getMedicaments()[1].setIndication(binding.indication.getText().toString());
                declaration.getMedicaments()[1].setVoieAdministration(binding.voie.getSelectedItem().toString());
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Pharmaco5 fragment = Pharmaco5.newInstance();
                fragmentTransaction.replace(R.id.fragment_container_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        binding.ajouter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               declaration.getMedicaments()[1].setDci(binding.dci.getSelectedItem().toString());
               declaration.getMedicaments()[1].setNomCommercial(binding.nomCommercial.getText().toString());
               declaration.getMedicaments()[1].setLot(binding.lot.getText().toString());
               declaration.getMedicaments()[1].setPosologie(binding.posologie.getText().toString());
               declaration.getMedicaments()[1].setDateDebutAdministration(Date.valueOf(binding.dda.getText().toString()));
               declaration.getMedicaments()[1].setDateFinAdministration(Date.valueOf(binding.dfa.getText().toString()));
               declaration.getMedicaments()[1].setEncours(binding.encours.isChecked());
               declaration.getMedicaments()[1].setIndication(binding.indication.getText().toString());
               declaration.getMedicaments()[1].setVoieAdministration(binding.voie.getSelectedItem().toString());

               FragmentManager fragmentManager = getParentFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               Pharmaco4 fragment = Pharmaco4.newInstance();
               fragmentTransaction.replace(R.id.fragment_container_view, fragment);
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();

           }
       }
        );

        binding.dda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(binding.dda);

            }
        });

        binding.dfa.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               openDialog(binding.dfa);

                                           }
                                       }

        );

        binding.encours.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.dfa.setEnabled(false);
                   // binding.dfa.setText("");
                } else {
                    binding.dfa.setEnabled(true);
                    //binding.dfa.setText(declaration.getMedicaments()[1].getDateFinAdministration().toString());
                }
            }
        });
    }


    private void openDialog(TextView t) {
        Calendar rightNow = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this.requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mois = "";
                if (month < 9) mois = "0" + (month + 1);
                else mois = String.valueOf(month + 1);
                String jour = "";
                if (dayOfMonth < 10) jour = "0" + String.valueOf(dayOfMonth);
                else jour = String.valueOf(dayOfMonth);
                String date = String.valueOf(year) + "/" + mois + "/" + jour;
                date = date.replace('/', '-');
                t.setText(date);
            }
        }, rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DATE));
        dialog.show();
    }

}