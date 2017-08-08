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

public class ChoiceActivity extends AppCompatActivity {
    TextView textView;
    Typeface openSansBold;

    Button personalStyle, homeStyle, gadgets, back, next;

    Intent categoryIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        init();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = view.getId();
                switch (id){
                    case R.id.personal_style:

                        categoryIntent = new Intent(getApplication(), PersonalStyleCategoryActivity.class);
                        startActivity(categoryIntent);

                        break;
                    case R.id.home_style:
                        categoryIntent = new Intent(getApplication(), HomeStyleCategoryActivity.class);
                        startActivity(categoryIntent);

                        break;
                    case R.id.gadgets:
                        categoryIntent = new Intent(getApplication(), GadgetsCategoryActivity.class);
                        startActivity(categoryIntent);

                        break;


                }
            }
        };

        personalStyle.setOnClickListener(listener);
        homeStyle.setOnClickListener(listener);
        gadgets.setOnClickListener(listener);

    }


    public void init(){
        next = (Button)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        back = (Button)findViewById(R.id.back);

        openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");

        textView = (TextView)findViewById(R.id.text);
        textView.setTypeface(openSansBold);

        personalStyle = (Button)findViewById(R.id.personal_style);
        homeStyle = (Button)findViewById(R.id.home_style);
        gadgets = (Button)findViewById(R.id.gadgets);


    }
}