package com.chuk.chuk;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.chuk.chuk.DesignActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class AnimalRingActivity extends AppCompatActivity {

    TextView textView;
    Typeface openSansBold;
    Button teddyBearBtn, sadDogBtn, funnyBunnyBtn, miaoBtn, next, back, continueForPayment;
    ImageView star, triangle, oval;
    ImageView mainImage, bubble;
    View.OnClickListener ringListener;
    Drawable teddyBear, sadDog, funnyBunny, miao;
    Bitmap teddyBearBitmap, sadDogBitmap, funnyBunnyBitmap, miaoBitmap, mainBitmap;
    LinearLayout topLayout, bottomLayout;
    RelativeLayout ringLayout, colorContainer, beforePaymentLayout;
    Button color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15, color16, color17, color18, color19, color20, color21 ;
    public static String textColor;
    public static float imageLocation;
    File imageFile;
    String fileName;

    File galleryImageFile;
    String galleryFileName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_ring_layout);

        init();
        onNextButtonClicked();


        final Button[] ringButtons = {teddyBearBtn, sadDogBtn, funnyBunnyBtn, miaoBtn};
        final int[] buttons = {R.id.teddy_bear_ring, R.id.sad_dog_ring, R.id.funny_bunny_ring, R.id.miao_ring};

        ringListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.teddy_bear_ring:
                        changeLayout();
                        mainImage.setImageBitmap(teddyBearBitmap);
                        mainBitmap = teddyBearBitmap;

                        break;
                    case R.id.sad_dog_ring:
                        changeLayout();
                        mainImage.setImageBitmap(sadDogBitmap);
                        mainBitmap = sadDogBitmap;

                        break;
                    case R.id.funny_bunny_ring:
                        changeLayout();
                        mainImage.setImageBitmap(funnyBunnyBitmap);
                        mainBitmap = funnyBunnyBitmap;

                        break;
                    case R.id.miao_ring:
                        changeLayout();
                        mainImage.setImageBitmap(miaoBitmap);
                        mainBitmap = miaoBitmap;

                        break;
                }

            }
        };

        for(Button button: ringButtons){
            button.setOnClickListener(ringListener);
        }


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorContainer.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.INVISIBLE);
                goToBeforePaymentScreen();
            }
        });


    }

    public void onNextButtonClicked(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(beforePaymentLayout.getVisibility()==View.VISIBLE){
                    takeScreenshot();
                    takeScreenshotForGallery();
                }else{
                    colorContainer.setVisibility(View.INVISIBLE);
                    textView.setVisibility(View.INVISIBLE);
                    goToBeforePaymentScreen();
                }
            }
        });
    }

    public void goToBeforePaymentScreen(){

        mainImage.setScaleX(0.6f);
        mainImage.setScaleY(0.6f);
        mainImage.setY(230);
        beforePaymentLayout.setVisibility(View.VISIBLE);



        continueForPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getApplicationContext())) {
                    takeScreenshot();
                    takeScreenshotForGallery();                }
            }
        });

    }


    public void initColorButtons(){


        View.OnClickListener colorListener;

        final Button[]colorButtons = {color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15, color16, color17, color18, color19, color20, color21 };
        final int[] colorList = new int[]{R.id.color1, R.id.color2, R.id.color3,R.id.color4, R.id.color5,R.id.color6, R.id.color7, R.id.color8,R.id.color9, R.id.color10,
                R.id.color11, R.id.color12, R.id.color13, R.id.color14, R.id.color15 , R.id.color16, R.id.color17, R.id.color18, R.id.color19 , R.id.color20, R.id.color21};
        colorListener = new View.OnClickListener() {
            public void onClick(View v) {

                for(int i = 0; i < colorList.length; i++){
                    if(v.getId() == colorList[i]){
                        changeColor(i, mainBitmap);
                    }

                }
            }
        };


        for(int i = 0; i < colorButtons.length; i++){
            colorButtons[i].setOnClickListener(colorListener);

        }
    }

    public void changeColor(int color, Bitmap bitmap){

        String colors[] = new String[]{"#044182","#3482C4", "#1C6502", "#B4FC30", "#5F9D2D", "#FFF8EB", "#CC6427",
                "#FB24F2", "#EFEFEF", "#8E8E8E", "#AF0000", "#DD3333", "#29D6E4", "#000000",
                "#EEFF00", "#EFEE22", "#FFCE9E", "#6927C3", "#FFC214", "#C9C9C9", "#1D73BE",
                "#7da4aab3", "#f9f8f8", "#e7f7ff"};


        String colorToChange = null;

        Bitmap myBitmap = bitmap;


        int pixel = myBitmap.getPixel(myBitmap.getWidth()/6, (myBitmap.getHeight()/8));

        colorToChange = colors[color];

        int [] allpixels = new int [myBitmap.getHeight()*myBitmap.getWidth()];

        myBitmap.getPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());

        for(int i = 0; i < allpixels.length; i++){
            if(allpixels[i] == pixel){
                allpixels[i] = Color.parseColor(colorToChange);
            }

        }

        myBitmap.setPixels(allpixels, 0, myBitmap.getWidth(), 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
        bubble.setVisibility(View.VISIBLE);
        textColor = colorToChange;
    }



    public Bitmap getBitmap(Drawable drawable, float width, float height, float angle){
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        Bitmap shrinkBitmap = Bitmap.createScaledBitmap(bitmap, (int)width, (int)height, false);
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(shrinkBitmap, 0, 0, shrinkBitmap.getWidth(), shrinkBitmap.getHeight(), matrix, true);
    }

    public void changeLayout(){
        mainImage.setVisibility(View.VISIBLE);
        topLayout.setVisibility(View.INVISIBLE);
        bottomLayout.setVisibility(View.INVISIBLE);
        star.setVisibility(View.INVISIBLE);
        oval.setVisibility(View.INVISIBLE);
        triangle.setVisibility(View.INVISIBLE);
        ringLayout.setBackgroundColor(Color.parseColor("#e7f7ff"));
        colorContainer.setVisibility(View.VISIBLE);
        textView.setText("Choose your animal ring color");
        initColorButtons();

    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    this,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    private void takeScreenshot() {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
/////////////// directory
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//////////////path
            String mPath = "Chuk"+now+".jpg";

            View v1 = getWindow().getDecorView().findViewById(R.id.main_image);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

//////////// file with directory and path
            imageFile = new File(dir, mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            Log.e("couldnt send email", "");
            e.printStackTrace();
        }


        Uri uri= Uri.fromFile(imageFile);
        try {
            fileName = PathUtil.getPath(getApplicationContext(), uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Log.i("SendMailActivity", "Send Button Clicked.");

        String fromEmail = "nataliestarr82@gmail.com";

        String fromPassword = "doobieshamenbeteruf";

        String toEmails = "nataliestarr82@gmail.com";

        List toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + toEmailList);
        String emailSubject = "Name Necklace";
        String emailBody = "Color: " + textColor;
        new SendMailTask(AnimalRingActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, "", fileName, emailBody);

    }

    private void takeScreenshotForGallery() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
/////////////// directory
            File dir = getExternalFilesDir("Chuk");
//////////////path
            String mPath = "ChukGallery"+now+".jpg";

            View v1 = getWindow().getDecorView().findViewById(R.id.main_image);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

//////////// file with directory and path
            galleryImageFile = new File(dir, mPath);

            FileOutputStream outputStream = new FileOutputStream(galleryImageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

        } catch (Throwable e) {
            Log.e("couldnt send email", "");
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Name Necklace");
        values.put(MediaStore.Images.Media.DESCRIPTION, "No Description");
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis ());
        values.put(MediaStore.Images.ImageColumns.BUCKET_ID, galleryImageFile.toString().toLowerCase(Locale.US).hashCode());
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, galleryImageFile.getName().toLowerCase(Locale.US));
        values.put("_data", galleryImageFile.getAbsolutePath());

        ContentResolver cr = getContentResolver();
        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        Uri uri= Uri.fromFile(galleryImageFile);
        try {
            galleryFileName = PathUtil.getPath(getApplicationContext(), uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {


        if(beforePaymentLayout.getVisibility() == View.VISIBLE) {
            beforePaymentLayout.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            mainImage.setScaleX(1);
            mainImage.setScaleY(1);
            mainImage.setY(imageLocation);
            colorContainer.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            bubble.setVisibility(View.INVISIBLE);
        }else if(colorContainer.getVisibility()==View.VISIBLE){

            topLayout.setVisibility(View.VISIBLE);
            bottomLayout.setVisibility(View.VISIBLE);
            star.setVisibility(View.VISIBLE);
            oval.setVisibility(View.VISIBLE);
            triangle.setVisibility(View.VISIBLE);
            ringLayout.setBackgroundColor(Color.parseColor("#f9f8f8"));
            colorContainer.setVisibility(View.INVISIBLE);
            textView.setText("Which Animal are you?");
            mainImage.setVisibility(View.INVISIBLE);
            bubble.setVisibility(View.INVISIBLE);

        }else{
            super.onBackPressed();
        }

    }




    public void init(){

        next = (Button)findViewById(R.id.next);
        back = (Button)findViewById(R.id.back);
        bubble = (ImageView)findViewById(R.id.bubble);
        bubble.setVisibility(View.INVISIBLE);

        topLayout = (LinearLayout)findViewById(R.id.toplayout);
        bottomLayout = (LinearLayout)findViewById(R.id.bottomlayout);
        ringLayout = (RelativeLayout)findViewById(R.id.ring_container);
        colorContainer = (RelativeLayout)findViewById(R.id.color_container);
        colorContainer.setVisibility(View.INVISIBLE);
        colorContainer.setY(colorContainer.getY() + 130);
        beforePaymentLayout = (RelativeLayout)findViewById(R.id.before_payment);
        beforePaymentLayout.setVisibility(View.INVISIBLE);

        star = (ImageView)findViewById(R.id.star);
        triangle = (ImageView)findViewById(R.id.triangle);
        oval = (ImageView)findViewById(R.id.oval);



        openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
        textView = (TextView)findViewById(R.id.ring_text);
        textView.setTypeface(openSansBold);

        teddyBearBtn = (Button)findViewById(R.id.teddy_bear_ring);
        sadDogBtn = (Button)findViewById(R.id.sad_dog_ring);
        funnyBunnyBtn = (Button)findViewById(R.id.funny_bunny_ring);
        miaoBtn = (Button)findViewById(R.id.miao_ring);

        teddyBear = ContextCompat.getDrawable(this, R.drawable.teddybear);
        sadDog = ContextCompat.getDrawable(this, R.drawable.saddog);
        funnyBunny = ContextCompat.getDrawable(this, R.drawable.bunny);
        miao = ContextCompat.getDrawable(this, R.drawable.cat_white_shape);


        teddyBearBitmap = getBitmap(teddyBear, teddyBear.getIntrinsicWidth(), teddyBear.getIntrinsicHeight(), 0);
        sadDogBitmap = getBitmap(sadDog, sadDog.getIntrinsicWidth(), sadDog.getIntrinsicHeight(), 0);
        funnyBunnyBitmap = getBitmap(funnyBunny, funnyBunny.getIntrinsicWidth(), funnyBunny.getIntrinsicHeight(), 0);
        miaoBitmap = getBitmap(miao, miao.getIntrinsicWidth(), miao.getIntrinsicHeight(), 0);


        int heightScreen = getResources().getDisplayMetrics().heightPixels;
        mainImage = (ImageView)findViewById(R.id.main_image);
        imageLocation = heightScreen/4;


        color1 = (Button) findViewById(R.id.color1);
        color2 = (Button) findViewById(R.id.color2);
        color3 = (Button) findViewById(R.id.color3);
        color4 = (Button) findViewById(R.id.color4);
        color5 = (Button) findViewById(R.id.color5);
        color6 = (Button) findViewById(R.id.color6);
        color7 = (Button) findViewById(R.id.color7);
        color8 = (Button) findViewById(R.id.color8);
        color9 = (Button) findViewById(R.id.color9);
        color10 = (Button) findViewById(R.id.color10);
        color11 = (Button) findViewById(R.id.color11);
        color12 = (Button) findViewById(R.id.color12);
        color13 = (Button) findViewById(R.id.color13);
        color14 = (Button) findViewById(R.id.color14);
        color15 = (Button) findViewById(R.id.color15);
        color16 = (Button) findViewById(R.id.color16);
        color17 = (Button) findViewById(R.id.color17);
        color18 = (Button) findViewById(R.id.color18);
        color19 = (Button) findViewById(R.id.color19);
        color20 = (Button) findViewById(R.id.color20);
        color21 = (Button) findViewById(R.id.color21);

        beforePaymentLayout = (RelativeLayout)findViewById(R.id.before_payment);
        beforePaymentLayout.setVisibility(View.INVISIBLE);

        continueForPayment = (Button)findViewById(R.id.continue_for_payment);


    }

}
