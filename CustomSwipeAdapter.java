package com.chuk.chuk;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Owner on 24/07/2017.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    ImageView imageView;



    public int[] image_resources_magnets = {R.drawable.horse_inner_shadow_blue_copy, R.drawable.oval_for_slider, R.drawable.triangle_for_slider, R.drawable.rectangle_eound_for_slider,
            R.drawable.diamond_for_slider, R.drawable.star_for_slider, R.drawable.cloud_for_slider, R.drawable.drop_for_slider, R.drawable.bird_shape_frame_purple,
            R.drawable.flower_for_slider, R.drawable.heart_for_slider, R.drawable.bubble_shape_frame_purple, R.drawable.horse_inner_shadow_blue_copy, R.drawable.oval_for_slider};

    public int[] image_resources_coaster = {R.drawable.heart_for_slider, R.drawable.oval_for_slider, R.drawable.rectangle_eound_for_slider, R.drawable.star_for_slider,
            R.drawable.flower_for_slider, R.drawable.bubble_shape_frame_purple, R.drawable.heart_for_slider, R.drawable.oval_for_slider};


    public int[] image_resources_earring = {R.drawable.diamond_for_slider, R.drawable.flower_for_slider, R.drawable.star_for_slider, R.drawable.bird_shape_frame_purple, R.drawable.heart_for_slider,
            R.drawable.horse_inner_shadow_blue_copy, R.drawable.drop_for_slider, R.drawable.oval_for_slider, R.drawable.flash_shape_frame_purple, R.drawable.triangle_for_slider, R.drawable.diamond_for_slider, R.drawable.flower_for_slider};


    public int[] image_resources_pendant = {R.drawable.bird_shape_frame_purple, R.drawable.diamond_for_slider, R.drawable.drop_for_slider, R.drawable.oval_for_slider,R.drawable.horse_inner_shadow_blue_copy,
            R.drawable.rectangle_eound_for_slider, R.drawable.heart_for_slider, R.drawable.star_for_slider, R.drawable.flower_for_slider, R.drawable.bird_shape_frame_purple, R.drawable.diamond_for_slider};



    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter (Context context){
        this.context = context;
    }

    @Override
    public int getCount() {

        int imagesLength = 0;
        switch (SwipeActivity.item) {
            case "earrings":
                imagesLength = image_resources_earring.length;

                break;
            case "pendant":
                imagesLength = image_resources_pendant.length;

                break;
            case "keychain":
                imagesLength = image_resources_magnets.length;

                break;

            case "magnet":
                imagesLength = image_resources_magnets.length;
                break;

            case "coaster":
                imagesLength = image_resources_coaster.length;
                break;
        }

        return imagesLength;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {


        return view == ((RelativeLayout)object);
    }


    @Override
    public Object instantiateItem(final ViewGroup container, int position) {


        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        imageView = (ImageView)v.findViewById(R.id.image_view);

        try {

            switch (SwipeActivity.item){
                case "earrings":
                    imageView.setImageResource(image_resources_earring[position+1]);
                    container.addView(v);

                    break;
                case "pendant":
                    imageView.setImageResource(image_resources_pendant[position+1]);
                    container.addView(v);

                    break;
                case "keychain":
                    imageView.setImageResource(image_resources_magnets[position+1]);
                    container.addView(v);

                    break;

                case "coaster":
                    imageView.setImageResource(image_resources_coaster[position+1]);
                    container.addView(v);

                    break;

                case "magnet":
                    imageView.setImageResource(image_resources_magnets[position+1]);
                    container.addView(v);

                    break;

            }

            final int finalPosition = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent earIntent = new Intent(view.getContext(), DesignActivity.class);
                    earIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    earIntent.putExtra(DesignActivity.POSITION_KEY, finalPosition);
                    earIntent.putExtra(DesignActivity.DESIGN_KEY, SwipeActivity.item);

                    context.startActivity(earIntent);
                }
            });
        }catch (IndexOutOfBoundsException e){

        }

        return v;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout)object);
    }
}