package com.bsbwebsites.deivid.filarapidahospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityShowCasa extends AppCompatActivity {

    ListView list_instituicoes1;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    private List<Casa> listCasa = new ArrayList<Casa>();
    private ArrayAdapter<Casa> arrayAdapterCasa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_casa);



        inicializarFirebase();
       // eventoDatabase();


    }

/*    private void eventoDatabase() {
        databaseReference.child("Casa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCasa.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Casa casa = objSnapshot.getValue(Casa.class);
                    listCasa.add(casa);
                }
                arrayAdapterCasa = new ArrayAdapter<Casa>(ActivityShowCasa.this,
                        android.R.layout.simple_list_item_1,listCasa);
                list_instituicoes1.setAdapter(arrayAdapterCasa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    */

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ActivityShowCasa.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        //writeNewUser("teste","teste","teste","teste");

    }
}
