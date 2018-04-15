package com.bsbwebsites.deivid.filarapidahospital;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bsbwebsites.deivid.filarapidahospital.MainActivity.localizar;

public class ActivityCadastrarItem extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private StorageReference mStorageRef;
    private static final int GALLERY_INTENT=1;
    RadioGroup rg;
    Button btnDisplay;
    Uri uri;
    EditText qtd;
    EditText desc;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<ItemDoado> listItemDoado = new ArrayList<ItemDoado>();
    private ArrayAdapter<ItemDoado> arrayAdapterItemDoado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_item);

        rg = (RadioGroup) findViewById(R.id.radiogroup1);
        int selectedId = rg.getCheckedRadioButtonId();
        qtd = (EditText) findViewById(R.id.qtd_itens);
        desc = (EditText) findViewById(R.id.etDesc);


        Toast.makeText(ActivityCadastrarItem.this, "seleção" + rg.getCheckedRadioButtonId(), Toast.LENGTH_LONG).show();



        mStorageRef = FirebaseStorage.getInstance().getReference();

        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }

        inicializarFirebase();
        eventoDatabase();

    }


    private void goLoginScreen() {
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void startThirdActivity(View view) {

        Intent ActivityLogin = new Intent(this, ActivityLogin.class);
        startActivity(ActivityLogin);
    }

    public void signOut(View view){
        LoginManager.getInstance().logOut();
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        startActivity(new Intent(this,ActivityLogin.class));

    }
    public void Inicio(View view) {
        Intent ActPrincipal = new Intent(this, ActPrincipal.class);
        startActivity(ActPrincipal);
    }

    public void confirma(View view) {
        Intent ActPrincipal = new Intent(this, ConfimaDoacaoActivity.class);
        startActivity(ActPrincipal);
    }

    public void fotografar (View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_INTENT);
        //String valorRadio = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK){


            uri = data.getData();
            StorageReference filepath = mStorageRef.child("Phots").child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ActivityCadastrarItem.this, "imagem caregada" + uri, Toast.LENGTH_LONG).show();
                }
            });





            Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
            StorageReference riversRef = mStorageRef.child("images/rivers.jpg");

            riversRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            Toast.makeText(ActivityCadastrarItem.this, "imagem Carregada", Toast.LENGTH_LONG).show();
                        }
                    });


        }
    }

    private void eventoDatabase() {
        databaseReference.child("ItemDoado").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listItemDoado.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    ItemDoado itemdoado = objSnapshot.getValue(ItemDoado.class);
                    listItemDoado.add(itemdoado);
                }
                arrayAdapterItemDoado = new ArrayAdapter<ItemDoado>(ActivityCadastrarItem.this,
                        android.R.layout.simple_list_item_1,listItemDoado);
                //list_instituicoes.setAdapter(arrayAdapterItemDoado);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(ActivityCadastrarItem.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    protected void enviarparafirebase(View view) {
        ItemDoado itemdoado = new ItemDoado();
        itemdoado.setUid(UUID.randomUUID().toString());
        itemdoado.setClasse(rg.getCheckedRadioButtonId());
        itemdoado.setQtd(1);
        itemdoado.setLocalizacao(localizar);
        itemdoado.setimgem(uri.getPath());
        databaseReference.child("ItemDoado").child(itemdoado.getUid()).setValue(itemdoado);
        limparCampos();
        Toast.makeText(ActivityCadastrarItem.this, "i" + uri, Toast.LENGTH_LONG).show();
        Intent ActPrincipal = new Intent(this, ActivityCadastrarItem3.class);
        startActivity(ActPrincipal);
    }


    private void limparCampos() {


    }


}
