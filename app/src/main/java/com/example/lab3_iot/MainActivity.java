package com.example.lab3_iot;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(v -> {
            if (hayConexion()) {
                startActivity(new Intent(this, AppActivity.class));
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Sin conexión a Internet")
                        .setMessage("No hay conexión disponible. ¿Deseas abrir Configuración?")
                        .setPositiveButton("Configuración", (dialog, which) ->
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
    }

    private boolean hayConexion() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo redActiva = cm.getActiveNetworkInfo();
        return redActiva != null && redActiva.isConnected();
    }
}
