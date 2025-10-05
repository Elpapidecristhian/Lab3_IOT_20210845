package com.example.lab3_iot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AppActivity extends AppCompatActivity {

    Button btnLocations, btnPronostico, btnFuturo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        // Referencias a los botones de la parte de pie de la vistas
        btnLocations = findViewById(R.id.btnLocations);
        btnPronostico = findViewById(R.id.btnPronostico);
        btnFuturo = findViewById(R.id.btnFuturo);

        replaceFragment(new LocationFragment());

        btnLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LocationFragment());
            }
        });

        btnPronostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ForecasterFragment());
            }
        });

        btnFuturo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new DateFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
