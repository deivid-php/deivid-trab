package com.bsbwebsites.deivid.filarapidahospital;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ActivityShowCasa extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private ListView listView;
    private ArrayList<String> mUserName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_item2);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ItemDoado");

       /* 14/03/2018 // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               // 14/03/18 - String value = dataSnapshot.getValue(String.class);
               // 14/03/18 - TextView textView = (TextView) findViewById(R.id.textViewEx);
               // 14/03/18 - textView.setText(value);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
               // Log.w(TAG, "Failed to read value.", error.toException());
            }
        }); */

       /* 14/03/18 - myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
               Map<String, String> map = (Map<String,String>) dataSnapshot.getValue();
               String name = map.get("Name");
               String contact = map.get("Contact");

               Log.v("Name", name);
               Log.v("Contact", contact);
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       }); */
        listView = (ListView) findViewById(R.id.newListView);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mUserName);
        listView.setAdapter(arrayAdapter);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                /* String value = dataSnapshot.getValue(String.class);
                mUserName.add(value);
                arrayAdapter.notifyDataSetChanged();
                */

                //if(dataSnapshot.hasChild("nome")) {
                        dataSnapshot.child("nome").getChildren();
                        String value = String.valueOf(dataSnapshot.child("nome").getValue());
                        mUserName.add(value);
                        arrayAdapter.notifyDataSetChanged();


               //   }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    /* public void buttonClicked(View view){


        EditText editTextName = (EditText) findViewById(R.id.name);
        EditText editTextContact = (EditText) findViewById(R.id.contact);
        // 14/03/18 - String message = editText.getText().toString();
        // 14/03/18 - myRef.setValue(message);
        // myRef = database.getReference("Users");
        //myRef.child("Names").push().setValue(editTextName.getText().toString());

        String child = editTextName.getText().toString();
        myRef = database.getReference("Users").child(child);

        myRef.child("Name").setValue(editTextName.getText().toString());
        myRef.child("Contact").setValue(editTextContact.getText().toString());

    } */

}
