package com.chuk.chuk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class StartActivity extends AppCompatActivity {
    ImageView chuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        chuk = (ImageView)findViewById(R.id.chuk);

        moveToNextScreen();


    }

    @Override
    public void onResume(){
        super.onResume();
        moveToNextScreen();

    }


    public void moveToNextScreen(){

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplication(), ChoiceActivity.class);
                startActivity(intent);
            }
        }, 500);

    }
}