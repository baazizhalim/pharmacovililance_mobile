package com.example.pharmacovigilance;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pharmacovigilance.databinding.FragmentWelcomeBinding;


public class WelcomeFragment extends Fragment {
    private FragmentWelcomeBinding binding;
    DatabaseManager dbManager;
    Medecin medecin=null;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.demarrer.setEnabled(false);
        dbManager=new DatabaseManager(requireActivity().getApplicationContext());
        binding.emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.demarrer.setEnabled(!s.toString().isEmpty() && !binding.passEditText.getText().toString().isEmpty());

            }
        });

        binding.passEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.demarrer.setEnabled(!s.toString().isEmpty() && !binding.emailEditText.getText().toString().isEmpty());

            }
        });

        binding.demarrer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                medecin = dbManager.getMedecin(binding.emailEditText.getText().toString(), binding.passEditText.getText().toString());
                if (medecin != null) {
                    MainActivity.medecin = medecin;

                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ChoixFragment fragment = ChoixFragment.newInstance();
                    fragmentTransaction.replace(R.id.fragment_container_view, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else {
                    String text = "Medecin inconnu, Veuillez vous inscrire";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(getContext(), text, duration); // in Activity
                    toast.show();
                }
            }

        });

        binding.inscription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Inscription fragment = Inscription.newInstance();
                fragmentTransaction.replace(R.id.fragment_container_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }
}