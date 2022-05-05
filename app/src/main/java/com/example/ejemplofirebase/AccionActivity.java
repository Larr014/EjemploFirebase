package com.example.ejemplofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Modelo.Persona;
import Security.Hash;

public class AccionActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    EditText etNombre,etApellido,etCorreo,etPass;
    String id;
    Button btnActualizar,btnEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accion);

        iniciarFirebase();


        //Recupero la intencion
        Intent i = getIntent();
        //Recupero los extras de la intencion
        Bundle extras = i.getExtras();
        //Recupero cada valor enviado
        String nombre = extras.getString("nombre");
        String apellido = extras.getString("apellido");
        String correo = extras.getString("correo");
        String pass = extras.getString("pass");
        id = extras.getString("id");

        //Recupero los campos de la vista y asigno
        etNombre = (EditText) findViewById(R.id.etNombre);
        etNombre.setText(nombre);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etApellido.setText(apellido);
        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etCorreo.setText(correo);
        etPass = (EditText) findViewById(R.id.etContrasenia);
        pass = Hash.getHash(pass,"MD5");
        pass = Hash.getHash(pass,"MD5");
        etPass.setText(pass);
        Toast.makeText(this, "Tu pass es: "+pass, Toast.LENGTH_SHORT).show();

        btnActualizar = (Button) findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar(view);
                finish(); //Cierras la activity actual
            }
        });

        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar(view);
            }
        });


    }

    public void eliminar(View v){
        databaseReference.child("Persona").child(id).removeValue();

    }

    public void actualizar(View v){
        Persona p = new Persona();
        p.setId(id);
        p.setNombre(etNombre.getText().toString());
        p.setApellido(etApellido.getText().toString());
        p.setCorreo(etCorreo.getText().toString());
        p.setContrasenia(Hash.md5("123"));
        databaseReference.child("Persona").child(id).setValue(p);
    }
    public void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

}