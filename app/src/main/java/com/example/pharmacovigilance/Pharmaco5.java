package com.example.pharmacovigilance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pharmacovigilance.databinding.Pharmaco5Binding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pharmaco5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pharmaco5 extends Fragment {
private Pharmaco5Binding binding;
static Declaration declaration;
private OkHttpClient okHttpClient;
DatabaseManager dbManager;
public static Pharmaco5 newInstance() {
        return new Pharmaco5();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = Pharmaco5Binding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        okHttpClient = new OkHttpClient();
        dbManager = new DatabaseManager(requireActivity().getApplicationContext());
        declaration = MainActivity.declaration;
        binding.radioButtonMedicamenteux.setChecked(declaration.isTraitementMedicamenteux());
        binding.radioButtonNonMedicamenteux.setChecked(declaration.isTraitementNonMedicamenteux());
        binding.descriptionTraitementReactionIndesirable.setText(declaration.getDescriptionTraitementReactionIndesirable());
        binding.radioButtonDisparaition.setChecked(false);
        for (RadioButton radioButton : Arrays.asList(binding.radioButtonInconnue, binding.radioButtonEncours, binding.radioButtonDeces)) {
            radioButton.setChecked(false);
        }
        switch (declaration.getEvolution()) {
            case "Disparaition":
                binding.radioButtonDisparaition.setChecked(true);
                break;
            case "Inconnue":
                binding.radioButtonInconnue.setChecked(true);
                break;
            case "En cours":
                binding.radioButtonEncours.setChecked(true);
                break;
            case "Déces":
                binding.radioButtonDeces.setChecked(true);
                break;
        }

        binding.checkBoxRexpo.setChecked(declaration.isReexposition());
        binding.checkBoxEffetReexpo.setChecked(declaration.isEffetReexposition());
        binding.checkBoxExamenComp.setChecked(declaration.isExamenCompl());
        binding.checkBoxEffetExamenComp.setChecked(declaration.isEffetExamenCompl());
        binding.antecedant.setText(declaration.getAntecedant());
        binding.autreExplication.setText(declaration.getAutreExplication());


        binding.checkBoxRexpo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) binding.checkBoxEffetReexpo.setEnabled(true);
            else {
                binding.checkBoxEffetReexpo.setEnabled(false);
                binding.checkBoxEffetReexpo.setChecked(false);
            }
        });

        binding.radioButtonMedicamenteux.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) binding.radioButtonNonMedicamenteux.setChecked(false);

        });

        binding.radioButtonNonMedicamenteux.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) binding.radioButtonMedicamenteux.setChecked(false);

        });

        binding.checkBoxExamenComp.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) binding.checkBoxEffetExamenComp.setEnabled(true);
            else {
                binding.checkBoxEffetExamenComp.setEnabled(false);
                binding.checkBoxEffetExamenComp.setChecked(false);
            }
        });


        binding.send.setOnClickListener(v -> {

            declaration.setTraitementMedicamenteux(binding.radioButtonMedicamenteux.isChecked());
            declaration.setTraitementNonMedicamenteux(binding.radioButtonNonMedicamenteux.isChecked());
            declaration.setDescriptionTraitementReactionIndesirable(binding.descriptionTraitementReactionIndesirable.getText().toString());
            if (binding.radioButtonDisparaition.isChecked())
                declaration.setEvolution("Disparaition");
            else if (binding.radioButtonInconnue.isChecked())
                declaration.setEvolution("Inconnue");
            else if (binding.radioButtonEncours.isChecked())
                declaration.setEvolution("En cours");
            else if (binding.radioButtonDeces.isChecked()) declaration.setEvolution("Déces");
            declaration.setReexposition(binding.checkBoxRexpo.isChecked());
            declaration.setEffetReexposition(binding.checkBoxEffetReexpo.isChecked());
            declaration.setExamenCompl(binding.checkBoxExamenComp.isChecked());
            declaration.setEffetExamenCompl(binding.checkBoxEffetExamenComp.isChecked());
            declaration.setAntecedant(binding.antecedant.getText().toString());
            declaration.setAutreExplication(binding.autreExplication.getText().toString());
            declaration.setMedecin(MainActivity.medecin.getEmail());
            save(declaration);
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            final String json = gson.toJson(declaration);
            RequestBody formbody
                    = new FormBody.Builder()
                    .add("declaration", json)
                    .build();

            // while building request
            // we give our form
            // as a parameter to post()
            Request request = new Request.Builder().url("http://"+MainActivity.ip+":5000/declaration")
                    .post(formbody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(
                        @NotNull Call call,
                        @NotNull IOException e) {
                    requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "server down", Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseBody = response.body().string();
                        requireActivity().runOnUiThread(() -> {
                            AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
                            alert.setTitle("info");
                            alert.setMessage(responseBody);
                            alert.setPositiveButton("Oui", null);
                            alert.show();
                        });
                    }

            });


            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            ChoixFragment fragment = ChoixFragment.newInstance();
            fragmentTransaction.replace(R.id.fragment_container_view, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });

    }

    private void save(Declaration declaration) {
    dbManager.insertDeclaration(declaration);

    }


}