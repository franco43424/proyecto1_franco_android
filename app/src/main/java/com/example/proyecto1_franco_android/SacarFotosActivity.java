package com.example.proyecto1_franco_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SacarFotosActivity extends AppCompatActivity {

    // Se usan para identificar las solicitudes
    static final int PERMISSION_REQUEST_CODE = 100;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageView ivCapturedPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sacar_fotos);

        Button btnCapturePhoto = findViewById(R.id.btnCapturePhoto);
        Button btnMenu = findViewById(R.id.btnMenu);
        ivCapturedPhoto = findViewById(R.id.ivCapturedPhoto);

        btnCapturePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Primero, comprueba y pide el permiso de la cámara
                checkAndRequestCameraPermission();
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkAndRequestCameraPermission() {
        // Comprueba si la app ya tiene permiso para usar la cámara
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Si no lo tiene, lo pide al usuario
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Si ya lo tiene, abre la cámara directamente
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Comprueba si el permiso fue concedido
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si el usuario aceptó, abre la cámara
                dispatchTakePictureIntent();
            } else {
                // Si el usuario lo negó, muestra un mensaje
                Toast.makeText(this, "Permiso de cámara denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivCapturedPhoto.setImageBitmap(imageBitmap);
        }
    }
}