package com.chuk.chuk;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class GadgetsCategoryActivity extends AppCompatActivity {

    TextView textView;
    Typeface openSansBold;

    Button gyro, keyChain, opener, petTag, back, next;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_gadgets);

        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        View.OnClickListener catListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = view.getId();
                switch (id){
                    case R.id.gyro:
                        intent =  new Intent(getApplication(), CustomProductActivity.class);
                        intent.putExtra(CustomProductActivity.TAG, "gyring");
                        startActivity(intent);

                        break;
                    case R.id.keychain:

                        Intent swipeIntent =  new Intent(getApplication(), SwipeActivity.class);
                        swipeIntent.putExtra(SwipeActivity.TAG, "keychain");
                        startActivity(swipeIntent);
                        break;
                    case R.id.opener:
                        intent =  new Intent(getApplication(), CustomProductActivity.class);
                        intent.putExtra(CustomProductActivity.TAG, "opener");
                        startActivity(intent);

                        break;
                    case R.id.pet_tag:
                        intent =  new Intent(getApplication(), CustomProductActivity.class);
                        intent.putExtra(CustomProductActivity.TAG, "petTag");
                        startActivity(intent);


                        break;

                }

            }
        };

        gyro.setOnClickListener(catListener);
        keyChain.setOnClickListener(catListener);
        opener.setOnClickListener(catListener);
        petTag.setOnClickListener(catListener);

    }


    public void init(){
        next = (Button)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        back = (Button)findViewById(R.id.back);


        openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        textView = (TextView)findViewById(R.id.text);
        textView.setTypeface(openSansBold);

        gyro = (Button)findViewById(R.id.gyro);
        keyChain = (Button)findViewById(R.id.keychain);
        opener = (Button)findViewById(R.id.opener);
        petTag = (Button)findViewById(R.id.pet_tag);

    }
}