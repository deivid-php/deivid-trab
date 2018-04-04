package com.bsbwebsites.deivid.filarapidahospital;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityLogin extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private EditText emailText;
    private EditText passText;

    private LoginButton loginButton;
    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailText = (EditText) findViewById(R.id.userEmail);
        passText = (EditText) findViewById(R.id.userSenha);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()!= null){
                    Intent intent = new Intent(ActivityLogin.this, ActivityCadastrarItem.class);
                    startActivity(intent);
                }
            }
        };

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(LoginResult loginResult) {

                RadioButton radioButton =(RadioButton)findViewById(R.id.radioButSoudoador);
                RadioButton radioButton1 =(RadioButton)findViewById(R.id.radioButton2);

                    if(radioButton.isChecked()){
                        goMainScreen();
                    }else {
                        Toast.makeText(getApplicationContext(),"Confirme seu cadastro", Toast.LENGTH_LONG).show();
                        goCasaScreen();
                    }

            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.com_facebook_smart_login_confirmation_cancel, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(), R.string.erro_login, Toast.LENGTH_SHORT).show();
            }
        });



    }



    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        Toast.makeText(getApplicationContext(),mAuth.toString(), Toast.LENGTH_LONG).show();
        // FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, ActivityCadastrarItem.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goCasaScreen() {
        Intent intent = new Intent(this, ActivityCadCasa.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




    public void Login(View view){
        String email = emailText.getText().toString();
        String senha = passText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)){
            Toast.makeText(this, "entre com ambos os valores",Toast.LENGTH_LONG).show();
        }else {
            mAuth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(ActivityLogin.this,"senha ou usuário incorretos",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }




}
