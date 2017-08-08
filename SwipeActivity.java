package com.chuk.chuk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class SwipeActivity extends AppCompatActivity {
    ViewPager viewPager;
    CustomSwipeAdapter adapter;
    TextView text;
    public static final String TAG = "TAG";
    public static String item;

    Button rightArrow, leftArrow, next, back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_frame_shape);

        next = (Button)findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        item = getIntent().getStringExtra(TAG);

        text = (TextView)findViewById(R.id.text);

        text.setSingleLine(false);
        text.setText("Choose the frame shape\n"+"for your " + item);

        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new CustomSwipeAdapter(getApplicationContext());
        viewPager.setCurrentItem( 1 );
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                Log.v( "onPageSelected", String.valueOf( index ) );
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // Log.v("onPageScrolled", "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.v("StateChanged", String.valueOf(state));

                if (state ==ViewPager.SCROLL_STATE_IDLE) {
                    int index = viewPager.getCurrentItem();
                    if ( index == 0 )
                        viewPager.setCurrentItem( adapter.getCount() - 2, false );
                    else if ( index == adapter.getCount() - 1 )
                        viewPager.setCurrentItem( 1 , false);
                }
            }
        });



//        rightArrow = (Button)findViewById(R.id.right_arrow);
//        leftArrow = (Button)findViewById(R.id.left_arrow);
//
//
//        rightArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
//
//
//            }
//        });
//
//        leftArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
//
//            }
//        });




        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(adapter);

    }
}