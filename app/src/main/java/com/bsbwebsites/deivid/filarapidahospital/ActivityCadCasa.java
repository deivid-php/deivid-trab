package com.bsbwebsites.deivid.filarapidahospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.bsbwebsites.deivid.filarapidahospital.MainActivity.localizar;

public class ActivityCadCasa extends AppCompatActivity {
    private static final String TAG = "";
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
/*
    private void writeUserData( String uid, String name, String email, String tel) {
        Casa casa = new Casa();

        databaseReference('Casa' + uid).set({
                username: name,
                email: email,
                profile_picture : imageUrl
        databaseReference.child("Casa").child(casa.getName()).setValue(casa);
        });
    } */

    private void writeNewPost(String uid, String name, String email, String tel) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = databaseReference.child("Casa").push().getKey();
        Casa post = new Casa(uid, name, email, tel);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/Casa/" + key, postValues);
        childUpdates.put("/Casa-post/" + uid + "/" + key, postValues);

        databaseReference.updateChildren(childUpdates);
    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

            // A comment has changed, use the key to determine if we are displaying this
            // comment and if so remove it.
            String commentKey = dataSnapshot.getKey();

            // ...
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };
//databaseReference.addChildEventListener(childEventListener);


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

        if (edittextinstit.getText().toString() =="") {
            Toast.makeText(ActivityCadCasa.this, "Entre com o nome da instituicão" , Toast.LENGTH_LONG).show();

        } else if (edittextemailinst.getText().toString() == "") {
            Toast.makeText(ActivityCadCasa.this, "E-mail obrigatório", Toast.LENGTH_LONG).show();
        } else if (edittexttelinst.getText().toString() =="") {
            Toast.makeText(ActivityCadCasa.this, "Telefone obrigatório", Toast.LENGTH_LONG).show();
        } else {
            Casa casa = new Casa();
            casa.setUid(UUID.randomUUID().toString());
            casa.setName(edittextinstit.getText().toString());
            casa.setEmail(edittextemailinst.getText().toString());
            casa.setTelefone(edittexttelinst.getText().toString());
            //Lê a localização do dispositivo e transforma a LAt Long em endereço por meio da classe getAddress
            casa.setLocalizacao(localizar);
            databaseReference.child("Casa").child(casa.getName()).setValue(casa);
            limparCampos();
            //writeNewPost();

        }
    }

    private void limparCampos() {
        edittextemailinst.setText("");
        edittextinstit.setText("");
        edittexttelinst.setText("");
    }


}