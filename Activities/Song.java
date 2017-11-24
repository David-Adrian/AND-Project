package com.example.andproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class Song extends AppCompatActivity{

    Button profile;
    Button map;
    Button playlist;
    FrameLayout profileLayout;
    Toolbar myToolbar;
    private FirebaseAuth auth;
    private FirebaseStorage firebaseStorage;

    private MediaPlayer player;
    private ImageButton recordPlay;
    private SeekBar mprogressBar;

    ImageView songPhoto;
    StorageReference storageRef ;

    private Handler handler;
    Runnable runnable;

    int realtime;

    String url = "https://firebasestorage.googleapis.com/v0/b/and-project-6a12f.appspot.com/o/wc3-night-elf-full.jpg?alt=media&token=787385cd-4d17-4015-b59c-ed860acf694e";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        auth = FirebaseAuth.getInstance();

        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        songPhoto = (ImageView) findViewById(R.id.songPhoto);

        try{

            player.setDataSource("https://firebasestorage.googleapis.com/v0/b/and-project-6a12f.appspot.com/o/Teldrassil%20-%20Original%20Wow%20Music.mp3?alt=media&token=53e504a8-1400-4344-ba44-195a2a3bfa57");
            player.prepare();

        }catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(Song.this, "Operation failed", Toast.LENGTH_SHORT).show();
        }

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mprogressBar.setMax(player.getDuration());
                playCycle();
            }
        });

        Glide.with(getApplicationContext()).load(url).into(songPhoto);

        mprogressBar = (SeekBar) findViewById(R.id.progressBar2);
        mprogressBar.setMax(99);

        mprogressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    player.seekTo(mprogressBar.getProgress());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        final ImageButton pauseButton = (ImageButton) findViewById(R.id.pauseButton);
        final ImageButton stopButton = (ImageButton) findViewById(R.id.stopButton);
        final ImageButton backButton = (ImageButton) findViewById(R.id.backButton2);

        pauseButton.setVisibility(View.INVISIBLE);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setVisibility(View.INVISIBLE);
                pauseButton.setVisibility(View.VISIBLE);

                player.start();

            }
        });


        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.VISIBLE);

                player.pause();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseButton.setVisibility(View.INVISIBLE);
                playButton.setVisibility(View.VISIBLE);

                player.seekTo(0);
                player.pause();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Song.this, SongList.class);
                startActivity(intent);
                Song.this.finish();
            }
        });

        updateSeekBar();

    }


    private void playCycle(){
        mprogressBar.setProgress(player.getCurrentPosition());

        if(player.isPlaying()){
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case R.id.sortName:
                Toast.makeText(Song.this, "This sorts songs by their title", Toast.LENGTH_SHORT).show();

            case R.id.sortZone:
                Toast.makeText(Song.this, "This sorts songs by zone", Toast.LENGTH_SHORT).show();

            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(Song.this, LogIn.class);
                startActivity(intent);
                Song.this.finish();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        handler.removeCallbacks(runnable);
    }


    private void updateSeekBar(){
        realtime = player.getDuration();
        mprogressBar.setProgress((int) ((float) player.getCurrentPosition()/realtime)*100);

        if(player.isPlaying()){
            Runnable updater = new Runnable() {
                @Override
                public void run(){
                    updateSeekBar();
                    realtime -= 1000;
                }
            };
            handler.postDelayed(updater, 1000);
        }
    }
}