package com.example.andproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class something extends AppCompatActivity {

    private FirebaseAuth auth;
    TextView text;
    Button logout;


    private FirebaseAuth.AuthStateListener authState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.something);

        auth = FirebaseAuth.getInstance();
        text = (TextView) findViewById(R.id.textView2);
        logout  = (Button) findViewById(R.id.registerButton);

        //Check if user logged in
        if(auth.getCurrentUser() == null){
            finish();
            Intent intent = new Intent(getApplicationContext(), Register.class);
            startActivity(intent);
        }

        //get user's email
        FirebaseUser user = auth.getCurrentUser();

        text.setText("WELCOME" + user.getEmail().toString());

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                auth.signOut();

                if(auth.getCurrentUser() == null) {
                    Toast.makeText(something.this, "Signed out succesfull", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(something.this, "Signing out unsuccesfull", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}