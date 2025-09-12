package com.example.proyecto1_franco_android;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText etNombre, etFechaNacimiento, etPokemon;
    private Spinner spRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        // Conecta las vistas con el código
        etNombre = findViewById(R.id.etNombre);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etPokemon = findViewById(R.id.etPokemon);
        spRegion = findViewById(R.id.spRegion);
        Button btnSaveChanges = findViewById(R.id.btnSaveChanges);
        Button btnMenu = findViewById(R.id.btnMenu);

        // Carga los datos guardados automáticamente al iniciar la pantalla
        loadUserData();

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guarda los datos actualizados
                saveUserData();
                Toast.makeText(EditarPerfilActivity.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadUserData() {
        // Obtiene una referencia a SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);

        // Lee los datos guardados usando las mismas "llaves"
        String nombre = sharedPref.getString("nombre", "");
        String fecha = sharedPref.getString("fecha_nacimiento", "");
        String pokemon = sharedPref.getString("pokemon", "");
        String region = sharedPref.getString("region", "");

        // Pone los datos en los campos de texto
        etNombre.setText(nombre);
        etFechaNacimiento.setText(fecha);
        etPokemon.setText(pokemon);

        // Para el Spinner, es un poco más complejo, si lo deseas podemos revisarlo más adelante
    }

    private void saveUserData() {
        // Obtiene una referencia a SharedPreferences para guardar los datos
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Guarda la información actualizada
        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("fecha_nacimiento", etFechaNacimiento.getText().toString());
        editor.putString("pokemon", etPokemon.getText().toString());
        // Puedes agregar la lógica para guardar el Spinner aquí si lo necesitas

        editor.apply();
    }
}