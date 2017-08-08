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

public class HomeStyleCategoryActivity extends AppCompatActivity {

    TextView textView;
    Typeface openSansBold;
    Button coasters, magnets, opener, doorSign, back, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_home_style);

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
                switch (id) {

                    case R.id.magnets:
                        Intent magnetIntent = new Intent(getApplication(), SwipeActivity.class);
                        magnetIntent.putExtra(SwipeActivity.TAG, "magnet");
                        startActivity(magnetIntent);
                        break;

                    case R.id.coasters:

                        Intent coasterIntent= new Intent(getApplication(), SwipeActivity.class);
                        coasterIntent.putExtra(SwipeActivity.TAG, "coaster");
                        startActivity(coasterIntent);
                        break;

                    case R.id.opener:
                        Intent intent = new Intent(getApplication(), CustomProductActivity.class);
                        intent.putExtra(CustomProductActivity.TAG, "opener");
                        startActivity(intent);

                        break;

                    case R.id.door_sign:
                        Intent doorIntent = new Intent(getApplication(), DesignActivity.class);
                        doorIntent.putExtra(DesignActivity.DESIGN_KEY, "doorSign");
                        startActivity(doorIntent);

                        break;

                }

            }
        };

        coasters.setOnClickListener(catListener);
        magnets.setOnClickListener(catListener);
        opener.setOnClickListener(catListener);
        doorSign.setOnClickListener(catListener);


    }


    public void init() {
        next = (Button)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        back = (Button)findViewById(R.id.back);

        openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        textView = (TextView) findViewById(R.id.text);
        textView.setTypeface(openSansBold);

        coasters = (Button) findViewById(R.id.coasters);
        magnets = (Button) findViewById(R.id.magnets);
        opener = (Button) findViewById(R.id.opener);
        doorSign = (Button) findViewById(R.id.door_sign);


    }
}