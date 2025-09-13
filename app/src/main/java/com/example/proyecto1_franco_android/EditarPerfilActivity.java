package com.example.proyecto1_franco_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditarPerfilActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 2;

    private EditText etNombre, etFechaNacimiento, etPokemon;
    private ImageView ivProfileImage;
    private Uri selectedImageUri;

    // Nuevo launcher para la galería
    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    ivProfileImage.setImageURI(selectedImageUri);

                    // Pide permiso persistente para la URI
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getContentResolver().takePersistableUriPermission(selectedImageUri, takeFlags);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        etNombre = findViewById(R.id.etNombre);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etPokemon = findViewById(R.id.etPokemon);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        Button btnSaveChanges = findViewById(R.id.btnSaveChanges);
        Button btnMenu = findViewById(R.id.btnMenu);
        Button btnSelectImage = findViewById(R.id.btnSelectImage);

        loadUserData();

        btnSelectImage.setOnClickListener(v -> checkPermissionAndOpenGallery());

        btnSaveChanges.setOnClickListener(v -> {
            saveUserData();
            Toast.makeText(EditarPerfilActivity.this, "Cambios guardados", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnMenu.setOnClickListener(v -> finish());
    }

    private void checkPermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            } else {
                openGallery();
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                openGallery();
            }
        }
    }

    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permiso denegado. No se puede acceder a la galería.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadUserData() {
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String nombre = sharedPref.getString("nombre", "");
        String fecha = sharedPref.getString("fecha_nacimiento", "");
        String pokemon = sharedPref.getString("pokemon", "");
        String imageUriString = sharedPref.getString("profile_image_uri", null);

        etNombre.setText(nombre);
        etFechaNacimiento.setText(fecha);
        etPokemon.setText(pokemon);

        if (imageUriString != null) {
            ivProfileImage.setImageURI(Uri.parse(imageUriString));
        }
    }

    private void saveUserData() {
        SharedPreferences sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("nombre", etNombre.getText().toString());
        editor.putString("fecha_nacimiento", etFechaNacimiento.getText().toString());
        editor.putString("pokemon", etPokemon.getText().toString());

        if (selectedImageUri != null) {
            editor.putString("profile_image_uri", selectedImageUri.toString());
        } else {
            editor.remove("profile_image_uri");
        }

        editor.apply();
    }
}