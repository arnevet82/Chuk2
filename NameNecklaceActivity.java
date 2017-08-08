package com.chuk.chuk;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import static android.view.View.VISIBLE;
import static com.chuk.chuk.DesignActivity.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class NameNecklaceActivity extends AppCompatActivity {
    TextView generalText, textView;
    EditText editText;

    Typeface openSansBold, latoBlack, pacifico;
    Button done, cancel, next, back, continueForPayment;

    FrameLayout textContainer, openeContainer, oldTextContainer;

    int numberOfLetters;
    TextView letterCount;
    TextWatcher tt = null;

    Button color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11,
            color12, color13, color14, color15, color16, color17, color18, color19, color20, color21 ;
    BottomNavigationView bottomNavigationView;

    ImageView bubble;

    RelativeLayout colorContainer;

    RelativeLayout beforePaymentLayout;

    int heightScreen;
    int widthScreen;
    public static String textColor;
    File imageFile;
    String fileName;

    File galleryImageFile;
    String galleryFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_necklace_layout);

        init();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                switch (id) {

                    case R.id.color:
                        bubble.setVisibility(View.INVISIBLE);

                        hideAllBtns();
                        textContainer.setVisibility(View.VISIBLE);
                        done.setVisibility(View.INVISIBLE);
                        cancel.setVisibility(View.INVISIBLE);
                        editText.setVisibility(View.INVISIBLE);

                        colorContainer.setVisibility(View.VISIBLE);

                        initColorButtons();


                        break;

                    case R.id.text:
                        bubble.setVisibility(View.INVISIBLE);

                        colorContainer.setVisibility(View.INVISIBLE);
                        generalText.setVisibility(View.INVISIBLE);
                        textView.setVisibility(View.INVISIBLE);

                        editText.setVisibility(View.VISIBLE);
                        textContainer.setVisibility(View.VISIBLE);
                        letterCount.setVisibility(View.VISIBLE);
                        done.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

                        countLetters();
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onDoneBtnPressed();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editText.setText("");
                                textView.setText("");
                                letterCount.setVisibility(View.INVISIBLE);
                            }
                        });


                        break;
                }

                return true;

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(beforePaymentLayout.getVisibility() == VISIBLE) {
                    takeScreenshot();
                    takeScreenshotForGallery();
                }else{
                    hideAllBtns();
                }

                bottomNavigationView.setVisibility(View.INVISIBLE);
                generalText.setVisibility(View.INVISIBLE);
                goToBeforePaymentScreen();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


    public void goToBeforePaymentScreen(){


        textContainer.setScaleX(0.9f);
        textContainer.setScaleY(0.9f);
        textContainer.setY(-heightScreen/10);
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
    public void onDoneBtnPressed(){
        bubble.setVisibility(View.VISIBLE);
        String text = editText.getText().toString();
        textView.setText(text);
        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.INVISIBLE);
        done.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        letterCount.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void countLetters(){

        tt = new TextWatcher() {
            public void afterTextChanged(Editable s){
                editText.setSelection(s.length());
            }
            public void beforeTextChanged(CharSequence s,int start,int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textToEdit = editText.getText().toString();
                numberOfLetters = textToEdit.length();
                editText.removeTextChangedListener(tt);
                letterCount.setText(numberOfLetters +"/9");
                editText.addTextChangedListener(tt);
                if(numberOfLetters == 9){
                    onDoneBtnPressed();
                    Toast.makeText(getApplicationContext(), "Sorry, no room left", Toast.LENGTH_SHORT).show();
                }
            }
        };
        editText.addTextChangedListener(tt);

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

                        changeColor(i);

                        colorContainer.setVisibility(View.INVISIBLE);

                    }
                }
            }
        };


        for(int i = 0; i < colorButtons.length; i++){
            colorButtons[i].setOnClickListener(colorListener);

        }
    }




    public void changeColor(int color){

        String colors[] = new String[]{"#044182","#3482C4", "#1C6502", "#B4FC30", "#5F9D2D", "#FFF8EB", "#CC6427",
                "#FB24F2", "#EFEFEF", "#8E8E8E", "#AF0000", "#DD3333", "#29D6E4", "#000000",
                "#EEFF00", "#EFEE22", "#FFCE9E", "#6927C3", "#FFC214", "#C9C9C9", "#1D73BE",
                "#7da4aab3", "#f9f8f8", "#e7f7ff"};

        String colorToChange = colors[color];
        textView.setTextColor(Color.parseColor(colorToChange));

        textColor = colorToChange;
    }


    public void hideAllBtns(){
        colorContainer.setVisibility(View.INVISIBLE);
        bubble.setVisibility(View.INVISIBLE);
        letterCount.setVisibility(View.INVISIBLE);
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

    private void takeScreenshotForGallery() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
/////////////// directory
            File dir = getExternalFilesDir("Chuk");
//////////////path
            String mPath = "ChukGallery"+now+".jpg";

            View v1 = getWindow().getDecorView().findViewById(R.id.text_container_namenecklace);
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

    private void takeScreenshot() {

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
/////////////// directory
            File dir = getExternalFilesDir("Chuk");
//////////////path
            String mPath = "Chuk"+now+".jpg";

            View v1 = getWindow().getDecorView().findViewById(R.id.text_container_namenecklace);
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
        new SendMailTask(NameNecklaceActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, "", fileName, emailBody);

    }


    @Override
    public void onBackPressed() {

        if(beforePaymentLayout.getVisibility() == View.VISIBLE) {
            beforePaymentLayout.setVisibility(View.INVISIBLE);
            textContainer.setScaleX(1);
            textContainer.setScaleY(1);
            textContainer.setY(heightScreen/50);
            bottomNavigationView.setVisibility(View.VISIBLE);
            generalText.setVisibility(View.VISIBLE);
        }else if(colorContainer.getVisibility()==View.VISIBLE){
            colorContainer.setVisibility(View.INVISIBLE);
        }else{
            super.onBackPressed();
        }

    }
    public void init() {
        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
        latoBlack = Typeface.createFromAsset(getAssets(), "Lato-Black.ttf");
        pacifico = Typeface.createFromAsset(getAssets(), "Pacifico-Regular.ttf");


        generalText = (TextView) findViewById(R.id.name_necklace_text);
        generalText.setTypeface(openSansBold);

        textContainer = (FrameLayout) findViewById(R.id.text_container_namenecklace);
        textContainer.setVisibility(View.INVISIBLE);

        editText = (EditText) findViewById(R.id.edttxt);
        editText.setTypeface(pacifico);

        textView = (TextView) findViewById(R.id.txt);
        textView.setTypeface(pacifico);
        textView.setShadowLayer(10.0f, 10.0f, 10.0f, Color.BLACK);

        letterCount = (TextView) findViewById(R.id.letter_count);
        letterCount.setText("0/9");

        done = (Button) findViewById(R.id.done_text);
        done.setTypeface(latoBlack);
        cancel = (Button) findViewById(R.id.cancel_text);
        cancel.setTypeface(latoBlack);

        next = (Button) findViewById(R.id.next);
        back = (Button)findViewById(R.id.back);


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        colorContainer = (RelativeLayout) findViewById(R.id.color_container);
        colorContainer.setVisibility(View.INVISIBLE);
        colorContainer.setY(colorContainer.getY() + 30);

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

        bubble = (ImageView) findViewById(R.id.bubble);
        bubble.setVisibility(View.INVISIBLE);

        generalText.setText("Choose color + text for your\n" + "awesome name necklace");

        beforePaymentLayout = (RelativeLayout) findViewById(R.id.before_payment);
        beforePaymentLayout.setVisibility(View.INVISIBLE);

        continueForPayment = (Button) findViewById(R.id.continue_for_payment);




    }
}