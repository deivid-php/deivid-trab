package com.bsbwebsites.deivid.filarapidahospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bsbwebsites.deivid.filarapidahospital.MainActivity.localizar;

public class ActivityCadCasa extends AppCompatActivity {
    EditText edittextinstit, edittextemailinst, edittexttelinst;
    ListView list_instituicoes;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Casa> listCasa = new ArrayList<Casa>();
    private ArrayAdapter<Casa> arrayAdapterCasa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_casa);
        edittextinstit = (EditText) findViewById(R.id.edittextinstit);
        edittextemailinst = (EditText) findViewById(R.id.edittextemailinst);
        edittexttelinst = (EditText) findViewById(R.id.edittexttelinst);
        list_instituicoes = (ListView) findViewById(R.id.list_instituicoes);

        inicializarFirebase();
        //eventoDatabase();

    }

    /*
    private void eventoDatabase() {
        databaseReference.child("Casa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCasa.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Casa casa = objSnapshot.getValue(Casa.class);
                    listCasa.add(casa);
                }
                arrayAdapterCasa = new ArrayAdapter<Casa>(ActivityCadCasa.this,
                        android.R.layout.simple_list_item_1,listCasa);
                list_instituicoes.setAdapter(arrayAdapterCasa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    } */
    private void eventoDatabase() {
        databaseReference.child("Casa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listCasa.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Casa casa = objSnapshot.getValue(Casa.class);
                    listCasa.add(casa);
                }
                arrayAdapterCasa = new ArrayAdapter<Casa>(ActivityCadCasa.this,
                        android.R.layout.simple_list_item_1,listCasa);
                //list_instituicoes.setAdapter(arrayAdapterCasa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ActivityCadCasa.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        //writeNewUser("teste","teste","teste","teste");

    }

    //construtor não vazio em que é possivel passar os valores diretos por meio de parametros
    private void writeNewUser(String uid, String name, String email, String tel) {
        Casa user = new Casa(uid, name, email, tel);
        databaseReference.child("Casa").child(email).setValue(user);
    }

    public void enviarfirebase(View view) {
        Casa casa = new Casa();
        casa.setUid(UUID.randomUUID().toString());
        casa.setName(edittextinstit.getText().toString());
        casa.setEmail(edittextemailinst.getText().toString());
        casa.setTelefone(edittexttelinst.getText().toString());
        //Lê a localização do dispositivo e transforma a LAt Long em endereço por meio da classe getAddress
        casa.setLocalizacao(localizar);
        databaseReference.child("Casa").child(casa.getName()).setValue(casa);
        limparCampos();

    }

    private void limparCampos() {
        edittextemailinst.setText("");
        edittextinstit.setText("");
        edittexttelinst.setText("");
    }


}