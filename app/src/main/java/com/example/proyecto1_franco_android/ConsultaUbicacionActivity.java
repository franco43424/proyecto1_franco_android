package com.example.proyecto1_franco_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConsultaUbicacionActivity extends AppCompatActivity {

    private EditText etDate, etCountry, etCity, etAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ubi);

        // Conecta las vistas con el código
        etDate = findViewById(R.id.etFecha);
        etCountry = findViewById(R.id.etPais);
        etCity = findViewById(R.id.etCiudad);
        etAddress = findViewById(R.id.etDireccion);

        Button btnSave = findViewById(R.id.btnGuardar);
        Button btnReturn = findViewById(R.id.btnRegresar);
        Button btnOpenMaps = findViewById(R.id.btnOpenMaps);

        // Carga los datos guardados al iniciar la pantalla
        loadLocationData();

        // Botón para guardar los datos
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocationData();
                Toast.makeText(ConsultaUbicacionActivity.this, "Ubicación guardada", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para regresar al menú
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Botón para abrir Google Maps
        btnOpenMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsWithAddress();
            }
        });
    }

    private void saveLocationData() {
        SharedPreferences sharedPref = getSharedPreferences("location_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("date", etDate.getText().toString());
        editor.putString("country", etCountry.getText().toString());
        editor.putString("city", etCity.getText().toString());
        editor.putString("address", etAddress.getText().toString());

        editor.apply();
    }

    private void loadLocationData() {
        SharedPreferences sharedPref = getSharedPreferences("location_data", Context.MODE_PRIVATE);

        String date = sharedPref.getString("date", "");
        String country = sharedPref.getString("country", "");
        String city = sharedPref.getString("city", "");
        String address = sharedPref.getString("address", "");

        etDate.setText(date);
        etCountry.setText(country);
        etCity.setText(city);
        etAddress.setText(address);
    }

    private void openMapsWithAddress() {
        // Combina la dirección de los campos de texto
        String address = etAddress.getText().toString() + ", " +
                etCity.getText().toString() + ", " +
                etCountry.getText().toString();

        // Crea la URL de Google Maps para el navegador
        String mapUrl = "http://maps.google.com/maps?q=?q=" + address;

        try {
            // Crea un Intent para abrir la URL en cualquier navegador
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUrl));
            startActivity(browserIntent);
        } catch (Exception e) {
            // Muestra un mensaje si no se puede abrir el navegador
            Toast.makeText(this, "No se puede abrir el mapa. Verifica tu conexión a Internet.", Toast.LENGTH_SHORT).show();
        }
    }
}


