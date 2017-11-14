package com.example.andproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    Toolbar myToolbar;
    ImageButton backBttn;
    Button registeBttn;
    EditText email;
    EditText password;

    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener authState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        backBttn = (ImageButton) findViewById(R.id.backButton);
        registeBttn = (Button) findViewById(R.id.registerButton);

        auth = FirebaseAuth.getInstance();

        backBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, LogIn.class);
                finish();
                startActivity(intent);
            }
        });


        registeBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();

                callSignUp(getEmail, getPassword);

            }
        });

//
//        final Button logout = (Button) findViewById(R.id.button2);
//
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view){
//                FirebaseAuth.getInstance().signOut();
//            }
//        });

    }

    private void callSignUp(String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Testing", "Sign up successfull" + task.isSuccessful());

                    if (!task.isSuccessful()) {
                        getUser();
                        Toast.makeText(Register.this, "Register unsuccesfull", Toast.LENGTH_SHORT).show();

                    } else{

                        Intent intent = new Intent(Register.this, MenuMain.class);
                        finish();
                        startActivity(intent);
                    }
            }
        });
    }

    private void getUser(){
        FirebaseUser user = auth.getCurrentUser();
    }


//    @Override
//    protected void onStart(){
//        super.onStart();
//
//        auth.addAuthStateListener(authState);
//
//    }


}
