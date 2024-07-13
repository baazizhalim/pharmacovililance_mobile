package com.example.pharmacovigilance;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pharmacovigilance.databinding.Pharmaco1Binding;

import java.sql.Date;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pharmaco1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pharmaco1 extends Fragment {
private Pharmaco1Binding binding;
static Declaration declaration;
public static Pharmaco1 newInstance() {
        return new Pharmaco1();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = Pharmaco1Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }





    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        declaration=MainActivity.declaration;
        binding.editTextPatientName.setText(declaration.getNomMalade());
        binding.editTextPatientFirstName.setText(declaration.getPrenom());
        binding.editTextPatientAge.setText(String.valueOf(declaration.getAge()));
        binding.editTextPatientTail.setText(String.valueOf(declaration.getTaille()));
        binding.editTextPatientPoids.setText(String.valueOf(declaration.getPoids()));
        binding.editTextEffetDescription.setText(declaration.getDescriptionReactionIndesirable());
        binding.editTextEffetDateParution.setText(declaration.getApparaition().toString());
        binding.mois.setText(String.valueOf(declaration.getMois()));
        binding.jour.setText(String.valueOf(declaration.getJour()));
        binding.heure.setText(String.valueOf(declaration.getHeure()));

        Spinner spinner = (Spinner) binding.spinnerPatientGenre;
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.liste_genre,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner.setAdapter(adapter);
        spinner.setSelection(declaration.getGenre().equals("Masculin")?0:1);


        binding.next1P1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declaration.setNomMalade(binding.editTextPatientName.getText().toString());

                declaration.setPrenom(binding.editTextPatientFirstName.getText().toString());
                declaration.setAge(Integer.parseInt(binding.editTextPatientAge.getText().toString()));
                declaration.setTaille(Integer.parseInt(binding.editTextPatientTail.getText().toString()));
                declaration.setPoids(Integer.parseInt(binding.editTextPatientPoids.getText().toString()));
                declaration.setDescriptionReactionIndesirable(binding.editTextEffetDescription.getText().toString());
                declaration.setApparaition(Date.valueOf(binding.editTextEffetDateParution.getText().toString()));
                declaration.setGenre(binding.spinnerPatientGenre.getSelectedItem().toString());
                declaration.setMois(Integer.parseInt(binding.mois.getText().toString()));
                declaration.setJour(Integer.parseInt(binding.jour.getText().toString()));
                declaration.setHeure(Integer.parseInt(binding.heure.getText().toString()));


                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Pharmaco2 fragment = Pharmaco2.newInstance();
                fragmentTransaction.replace(R.id.fragment_container_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });


        binding.editTextEffetDateParution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(binding.editTextEffetDateParution);

            }
        });




    }
    private void openDialog (TextView t) {
        Calendar rightNow = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this.requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String mois="";
                if(month<9) mois="0"+(month+1);else mois=String.valueOf(month+1);
                String jour="";
                if(dayOfMonth<10) jour="0"+String.valueOf(dayOfMonth);else jour=String.valueOf(dayOfMonth);
                String date=String.valueOf(year) + "/" + mois+ "/" + jour;
                date=date.replace('/','-');
                t.setText(date);

            }
        }, rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DATE));
        dialog.show();
    }
}