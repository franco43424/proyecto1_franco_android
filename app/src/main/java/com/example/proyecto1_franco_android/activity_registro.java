package com.example.proyecto1_franco_android; // Asegúrate de que el nombre de tu paquete sea correcto

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class RegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navega a la pantalla del menú principal
                Intent intent = new Intent(RegistroActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}