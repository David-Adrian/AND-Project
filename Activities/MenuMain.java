package com.example.andproject;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MenuMain extends AppCompatActivity {

    Button profile;
    Button map;
    Button playlist;
    FrameLayout profileLayout;
    Toolbar myToolbar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        auth = FirebaseAuth.getInstance();
        profile = (Button) findViewById(R.id.profile_button);
        profileLayout = (FrameLayout) findViewById(R.id.frameLayoutProfile);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(MenuMain.this, LogIn.class);
                finish();
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
