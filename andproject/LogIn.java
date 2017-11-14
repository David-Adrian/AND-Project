package com.example.andproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogIn extends AppCompatActivity {

    EditText email;
    EditText password;

    private FirebaseAuth auth;

    private FirebaseAuth.AuthStateListener authState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        Log.d("whit e", "here is you debug message");

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);


        final Button loginBttn = (Button) findViewById(R.id.login);
        final Button signUp = (Button) findViewById(R.id.noacc);
        final String user = "user";
        final String pass = "blurp";

        auth = FirebaseAuth.getInstance();


        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();

                callSignIn(getEmail, getPassword);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(LogIn.this, Register.class);
                    startActivity(intent);
            }
        });

    }

    private void callSignIn(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                Log.d("Testing", "Log in successfull" + task.isSuccessful());

                if (!task.isSuccessful()) {
                    Toast.makeText(LogIn.this, "Log in unsuccesfull", Toast.LENGTH_SHORT).show();

                } else{

                    Intent intent = new Intent(LogIn.this, MenuMain.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
