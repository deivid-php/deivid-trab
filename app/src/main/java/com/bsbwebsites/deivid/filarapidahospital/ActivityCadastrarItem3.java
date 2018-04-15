package com.bsbwebsites.deivid.filarapidahospital;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityCadastrarItem3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_item3);


    }

    public void Inicio1(View view) {
        Intent ActPrincipal = new Intent(this, ActPrincipal.class);
        startActivity(ActPrincipal);
    }
}
