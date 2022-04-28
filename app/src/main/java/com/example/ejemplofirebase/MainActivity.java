package com.example.ejemplofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import Modelo.Persona;
import Security.Hash;

public class MainActivity extends AppCompatActivity {

    EditText etNombre,etApellido,etCorreo,etContrasenia;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciarFirebase();
    }

    public void registrarPersona(View v){
       etNombre = (EditText) findViewById(R.id.etNombre);
       etApellido = (EditText) findViewById(R.id.etApellido);
       etCorreo = (EditText) findViewById(R.id.etCorreo);
       etContrasenia = (EditText) findViewById(R.id.etContrasenia);

       String nombre = etNombre.getText().toString();
       String apellido = etApellido.getText().toString();
       String correo = etCorreo.getText().toString();
       String contrasenia = etContrasenia.getText().toString();
        contrasenia = Hash.md5(contrasenia);
        contrasenia = Hash.md5(contrasenia);
       //Generar un identificador automatico
       String id = UUID.randomUUID().toString();
       //Crear objeto
        Persona p = new Persona();
        p.setId(id);
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setCorreo(correo);
        p.setContrasenia(contrasenia);
        //Persona p = new Persona(id,nombre,apellido,correo,contrasenia);
        //Insertar en firebase
        databaseReference.child("Persona").child(id).setValue(p);


    }
    public void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
    public void goListado(View v){
        Intent i = new Intent(getApplicationContext(),ListadoActivity.class);
        startActivity(i);
    }

}