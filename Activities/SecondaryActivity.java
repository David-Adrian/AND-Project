package com.example.andproject;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

import com.example.andproject.KalimdorMap;

public class SecondaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView teldrasil = (ImageView) findViewById(R.id.teldrasil);
        final TextView points = (TextView) findViewById(R.id.text4);
        TextView text1 = (TextView) findViewById(R.id.text3);
        final Button Button = (Button) findViewById(R.id.button7);
        final ImageButton Button2 = (ImageButton) findViewById(R.id.imageButton6);


        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            points.setText("unlocked");
            boolean pressed = true;
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(SecondaryActivity.this, KalimdorMap.class);
                startActivity(intent2);

                SecondaryActivity.this.finish();
            }
        });


    }
}
