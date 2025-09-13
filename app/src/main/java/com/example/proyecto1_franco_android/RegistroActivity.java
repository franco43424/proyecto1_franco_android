package com.example.proyecto1_franco_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistroActivity extends AppCompatActivity {

    private EditText etNombre, etFechaNacimiento, etPokemon;
    private Spinner spRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Conecta las vistas con el código
        etNombre = findViewById(R.id.etNombre);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etPokemon = findViewById(R.id.etPokemon);
        spRegion = findViewById(R.id.spRegion);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        // Configura el Spinner con la lista de regiones
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.pokemon_regions,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRegion.setAdapter(adapter);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guarda la información antes de cambiar de pantalla
                saveUserData();

                // Navega a la pantalla del menú principal
                Intent intent = new Intent(RegistroActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveUserData() {
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("fecha_nacimiento", etFechaNacimiento.getText().toString());
        editor.putString("pokemon", etPokemon.getText().toString());

        // Aquí guarda el valor seleccionado del Spinner
        editor.putString("region", spRegion.getSelectedItem().toString());

        editor.apply();

        Toast.makeText(this, "Datos guardados en SharedPreferences", Toast.LENGTH_SHORT).show();
    }
}