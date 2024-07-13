package com.example.pharmacovigilance;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pharmacovigilance.databinding.InscriptionBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Inscription extends Fragment {

    DatabaseManager dbManager;
    private OkHttpClient okHttpClient;
    private InscriptionBinding binding;
    private Medecin medecin;
    public static Inscription newInstance() {
        return new Inscription();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = InscriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private boolean formulairePret() {
        return (!binding.nomInscription.getText().toString().isEmpty() && !binding.prenomInscription.getText().toString().isEmpty() && !binding.emailInscription.getText().toString().isEmpty() && !binding.passeInscription.getText().toString().isEmpty() && !binding.telInscription.getText().toString().isEmpty() && !binding.adresseInscription.getText().toString().isEmpty());
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.inscription.setEnabled(false);
        okHttpClient = new OkHttpClient();
        dbManager = new DatabaseManager(requireActivity().getApplicationContext());
        Spinner spinner = (Spinner) binding.ExerciceInscription;
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.liste_exercice,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner.setAdapter(adapter);


        Spinner spinner2 = (Spinner) binding.domaineInscription;
// Create an ArrayAdapter using the string array and a default spinner layout.
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.liste_domaine,
                android.R.layout.simple_spinner_item
        );
// Specify the layout to use when the list of choices appears.
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner.
        spinner2.setAdapter(adapter2);


        binding.nomInscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.inscription.setEnabled(formulairePret());

            }
        });


        binding.prenomInscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.inscription.setEnabled(formulairePret());

            }
        });

        binding.emailInscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.inscription.setEnabled(formulairePret());
            }
        });

        binding.passeInscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.inscription.setEnabled(formulairePret());
            }
        });

        binding.telInscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.inscription.setEnabled(formulairePret());
            }
        });


        binding.adresseInscription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.inscription.setEnabled(formulairePret());
            }
        });

        binding.inscription.setOnClickListener(v -> {
            medecin = new Medecin(binding.nomInscription.getText().toString(), binding.prenomInscription.getText().toString(), binding.emailInscription.getText().toString(), binding.passeInscription.getText().toString(), binding.telInscription.getText().toString(), binding.ExerciceInscription.getSelectedItem().toString(), binding.domaineInscription.getSelectedItem().toString(), binding.adresseInscription.getText().toString());
            int res=dbManager.verifierMedecin(medecin.getEmail());
            if(res==-1) {
                String message="Problème avec la base de données";
                AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
                alert.setTitle("info");
                alert.setMessage(message);
                alert.setPositiveButton("Oui", (dialog, which) -> {
                    try {

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
                alert.show();

            }
            else if(res==1){
                String message="le medecin "+medecin.getNom()+" existe dans la base de données";
                AlertDialog.Builder alert=new AlertDialog.Builder(requireContext());
                alert.setTitle("info");
                alert.setMessage(message);
                alert.setPositiveButton("Oui", (dialog, which) -> {
                    try {

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
                alert.show();
            }
            else {
                final GsonBuilder builder = new GsonBuilder();
                final Gson gson = builder.create();
                final String json = gson.toJson(medecin);
                RequestBody formbody
                        = new FormBody.Builder()
                        .add("medecin", json)
                        .build();

                // while building request
                // we give our form
                // as a parameter to post()
                Request request = new Request.Builder().url("http://" + MainActivity.ip + ":5000/inserermedecin")
                        .post(formbody)
                        .build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(
                            @NotNull Call call,
                            @NotNull IOException e) {
                        requireActivity().runOnUiThread(() -> Toast.makeText(requireContext(), "Problème avec le serveur", Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseBody = response.body().string();
                        if (responseBody.equals("1")) {
                            requireActivity().runOnUiThread(() -> {
                                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                                alert.setTitle("info");
                                alert.setMessage("Medecin enregistré dans le serveur");
                                alert.setPositiveButton("Oui", null);
                                alert.show();


                            dbManager.insertMedecin(medecin);
                            medecin = dbManager.getMedecin(medecin.getEmail(), medecin.getMotdepasse());
                            String message = "le medecin " + medecin.getNom() + " est crée localement";
                            AlertDialog.Builder alert1 = new AlertDialog.Builder(requireContext());
                            alert1.setTitle("info");
                            alert1.setMessage(message);
                            alert1.setPositiveButton("Oui", null);
                            alert1.show();
                            });
                        }
                        else if(responseBody.equals("0")) {
                            requireActivity().runOnUiThread(() -> {
                                String message = "le medecin n'a pas pu être crée";
                                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                                alert.setTitle("info");
                                alert.setMessage(message);
                                alert.setPositiveButton("Oui", null);
                                alert.show();
                            });
                        }
                        else  {
                            requireActivity().runOnUiThread(() -> {
                                String message = "l'émail utilisé est déja enregistré, essayer un autre email";
                                AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                                alert.setTitle("info");
                                alert.setMessage(message);
                                alert.setPositiveButton("Oui", null);
                                alert.show();
                            });
                        }
                    }
                });
            }

            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            WelcomeFragment fragment = WelcomeFragment.newInstance();
            fragmentTransaction.replace(R.id.fragment_container_view, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        });


    }
}
