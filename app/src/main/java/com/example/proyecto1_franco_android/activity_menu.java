package com.example.proyecto1_franco_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnEditProfile = findViewById(R.id.btnEditProfile);
        Button btnTakePhoto = findViewById(R.id.btnTakePhoto);
        Button btnLocationQuery = findViewById(R.id.btnLocationQuery);

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, EditarPerfilActivity.class);
                startActivity(intent);
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SacarFotosActivity.class);
                startActivity(intent);
            }
        });

        btnLocationQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ConsultaUbicacionActivity.class);
                startActivity(intent);
            }
        });
    }
}