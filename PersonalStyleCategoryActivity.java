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

public class PersonalStyleCategoryActivity extends AppCompatActivity {

    TextView textView;
    Typeface openSansBold;
    Intent swipeIntent;

    Button earrings, necklace, keychain, animal, nameFrame, back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_personal_style);

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
                    case R.id.earrings:
                        swipeIntent = new Intent(getApplication(), SwipeActivity.class);
                        swipeIntent.putExtra(SwipeActivity.TAG, "earrings");
                        startActivity(swipeIntent);

                        break;
                    case R.id.necklace:
                        swipeIntent = new Intent(getApplication(), SwipeActivity.class);
                        swipeIntent.putExtra(SwipeActivity.TAG, "pendant");
                        startActivity(swipeIntent);
                        break;
                    case R.id.keychain:
                        swipeIntent = new Intent(getApplication(), SwipeActivity.class);
                        swipeIntent.putExtra(SwipeActivity.TAG, "keychain");
                        startActivity(swipeIntent);
                        break;
                    case R.id.animal:
                        Intent intent = new Intent(getApplication(), AnimalRingActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.name_frame:
                        intent = new Intent(getApplication(), NameNecklaceActivity.class);
                        startActivity(intent);
                        break;



                }

            }
        };

        earrings.setOnClickListener(catListener);
        necklace.setOnClickListener(catListener);
        keychain.setOnClickListener(catListener);
        animal.setOnClickListener(catListener);
        nameFrame.setOnClickListener(catListener);

    }


    public void init(){

        next = (Button)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        back = (Button)findViewById(R.id.back);

        openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        textView = (TextView)findViewById(R.id.text);
        textView.setTypeface(openSansBold);

        earrings = (Button)findViewById(R.id.earrings);
        necklace = (Button)findViewById(R.id.necklace);
        keychain = (Button)findViewById(R.id.keychain);
        animal = (Button)findViewById(R.id.animal);
        nameFrame = (Button)findViewById(R.id.name_frame);

    }
}