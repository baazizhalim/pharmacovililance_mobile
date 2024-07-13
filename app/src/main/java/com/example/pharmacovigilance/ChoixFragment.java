package com.example.pharmacovigilance;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pharmacovigilance.databinding.FragmentChoixBinding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChoixFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChoixFragment extends Fragment {
    DatabaseManager dbManager;
    private OkHttpClient okHttpClient;

private FragmentChoixBinding binding;

    public static ChoixFragment newInstance() {
        return new ChoixFragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChoixBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbManager=new DatabaseManager(requireActivity().getApplicationContext());
        okHttpClient = new OkHttpClient();
        binding.answer1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.declaration=new Declaration();
                MainActivity.declaration.setMedecin(MainActivity.medecin.getEmail());
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Pharmaco1 fragment = Pharmaco1.newInstance();
                fragmentTransaction.replace(R.id.fragment_container_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        binding.answer2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ItemFragment1 fragment = ItemFragment1.newInstance(1);
                fragmentTransaction.replace(R.id.fragment_container_view, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        binding.answer3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("cliquer","je clique sur synchro");
                Request request = new Request.Builder()
                        .url("http://"+MainActivity.ip+":5000/synchronise")
                        .get()
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(
                            @NotNull Call call,
                            @NotNull IOException e) {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(requireContext(), "server down", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final GsonBuilder builder = new GsonBuilder();
                        final Gson gson = builder.create();
                        String[][] reponse = gson.fromJson(response.body().charStream(), String[][].class);
                        boolean res = dbManager.insererMedicament(reponse);

                        if (res) {
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(requireContext(), "synchronisation des médicaments réussie", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });


//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                WelcomeFragment fragment = WelcomeFragment.newInstance();
//                fragmentTransaction.replace(R.id.fragment_container_view, fragment);
//                //fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

            }
        });

        binding.quitterP.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                WelcomeFragment fragment = WelcomeFragment.newInstance();
//                fragmentTransaction.replace(R.id.fragment_container_view, fragment);
//                //fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();


                    finishAffinity(requireActivity()); // Pour les versions antérieures

                System.exit(0);

            }
        });
    }
}