package com.example.ejemplofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Modelo.Persona;

public class ListadoActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public List<Persona> personas = new ArrayList<Persona>();
    ArrayAdapter<Persona> adPersonas;
    ListView lvPersonas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);
        iniciarFirebase();
        listarPersonas();
    }


    public void iniciarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public void listarPersonas(){
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //for(DataSnapshot obj : dataSnapshot.getChildren()){
                System.out.println("Imprimiendo ...");
                for (DataSnapshot objeto: snapshot.getChildren()) {
                    Persona p = objeto.getValue(Persona.class);
                    System.out.println(p);
                    personas.add(p);
                }
                //Creo adaptador
                adPersonas = new ArrayAdapter<Persona>(getApplicationContext(), android.R.layout.simple_list_item_1, personas);
                //Recupero el view del layout
                lvPersonas = (ListView) findViewById(R.id.lvPersonas);
                //Asigno adaptador al listView
                lvPersonas.setAdapter(adPersonas);
                lvPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Persona p = (Persona) adapterView.getItemAtPosition(i);
                        Toast.makeText(ListadoActivity.this, "Persona: "+p, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(),AccionActivity.class);
                        startActivity(in);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}