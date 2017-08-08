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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class CustomProductActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    public static String item = null;

    Typeface openSansBold, latoBlack, montserrat, vampiroOne;
    private Button colorButton, textButton;
    private RelativeLayout colorContainer, textContainer, beforePaymentScreen;
    private LinearLayout buttonsBar;
    private Button done, cancel, next, back, continueForPayment;
    public static EditText editText;
    public static CustomProductTouchView touchView;
    private ImageView ruler;
    public static RelativeLayout designContainer;
    public static ImageView frameShape, colorFrameShape;
    public static int mainImageDrawable;
    float heightScreen, widthScreen;
    public static String textColor;
    public static int currentColor;
    public static Context context;
    public static String textString;
    Button color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15, color16, color17, color18, color19, color20, color21 ;

    File imageFile;
    String fileName;

    File galleryImageFile;
    String galleryFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_product_layout);

        item = getIntent().getStringExtra(TAG);

        init();
        instantiateFrame(item);
        designContainer.addView(touchView);

        initColorButton();
        initTextButton();
        setRotationRuler();
        onNextButtonClicked();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void onNextButtonClicked(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(beforePaymentScreen.getVisibility() == VISIBLE) {
                    takeScreenshot();
                    takeScreenshotForGallery();
                }else {
                    hideAllUIElements();
                }

                goToBeforePaymentScreen();
                buttonsBar.setVisibility(View.INVISIBLE);


            }
        });
    }

    public void goToBeforePaymentScreen(){

        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);
        hideAllUIElements();

        designContainer.setScaleX(0.6f);
        designContainer.setScaleY(0.6f);

        designContainer.setY(pixels/2f);

        beforePaymentScreen.setVisibility(View.VISIBLE);

        continueForPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getApplicationContext())) {
                    takeScreenshot();
                    takeScreenshotForGallery();                }

            }
        });

    }

    public void initColorButton(){

        final Button[]colorButtons = {color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15, color16, color17, color18, color19, color20, color21 };
        final int[] colorList = new int[]{R.id.color1, R.id.color2, R.id.color3,R.id.color4, R.id.color5,R.id.color6, R.id.color7, R.id.color8,R.id.color9, R.id.color10,
                R.id.color11, R.id.color12, R.id.color13, R.id.color14, R.id.color15 , R.id.color16, R.id.color17, R.id.color18, R.id.color19 , R.id.color20, R.id.color21};

        final int colors[] = new int[]{R.color.color1,R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7,
                R.color.color8, R.color.color9, R.color.color10, R.color.color11, R.color.color12, R.color.color13, R.color.color14,
                R.color.color15, R.color.color16, R.color.color17, R.color.color18, R.color.color21, R.color.color22, R.color.color24,
                R.color.editGrayBigShape, R.color.background, R.color.colorFifthScreen};

        final String colorStings[] = new String[]{"#044182","#3482C4", "#1C6502", "#B4FC30", "#5F9D2D", "#FFF8EB", "#CC6427",
                "#FB24F2", "#EFEFEF", "#8E8E8E", "#AF0000", "#DD3333", "#29D6E4", "#000000",
                "#EEFF00", "#EFEE22", "#FFCE9E", "#6927C3", "#FFC214", "#C9C9C9", "#1D73BE",
                "#7da4aab3", "#f9f8f8", "#e7f7ff", "#ffffff"};

        View.OnClickListener colorListener = new View.OnClickListener() {
            public void onClick(View v) {

                hideAllUIElements();
                colorContainer.setVisibility(View.VISIBLE);

                for(int i = 0; i < colorButtons.length; i++) {
                    if (v.getId() == colorList[i]) {

                        textColor = colorStings[i];
                        if(item.equals("gyring")){
                            changeMainImageColor(colors[i]);
                            colorFrameShape.setAlpha(220);
                            currentColor = colors[i];

                        }else {
                            changeMainImageColor(colors[i]);
                            currentColor = colors[i];
                        }
                        colorContainer.setVisibility(View.INVISIBLE);
                        ruler.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        colorButton.setOnClickListener(colorListener);
        for(int i = 0; i < colorButtons.length; i++){
            colorButtons[i].setOnClickListener(colorListener);

        }

    }


    public static void changeMainImageColor(int color){

        colorFrameShape.setColorFilter(ContextCompat.getColor(App.getContext(), color));
        colorFrameShape.setVisibility(View.VISIBLE);

        try {
            touchView.textPaint.setColor(Color.parseColor(textColor));

        }catch (RuntimeException e){

        }

        touchView.invalidate();
    }


    public void initTextButton(){

        final View.OnClickListener textListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int widthScreen = getResources().getDisplayMetrics().widthPixels;
                switch (view.getId()){
                    case R.id.text:
                        hideAllUIElements();
                        textContainer.setVisibility(View.VISIBLE);
                        showTextElements();
                        textString = "";
                        touchView.sl = new StaticLayout(textString,touchView.textPaint,widthScreen/2,
                                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
                        touchView.invalidate();

                        break;
                    case R.id.done_text:
                        textString = editText.getText().toString();
                        touchView.textView.setText(textString);

                        touchView.sl = new StaticLayout(textString,touchView.textPaint,widthScreen/2,
                                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);


                        touchView.invalidate();
                        hideTextElements();
                        ruler.setVisibility(View.VISIBLE);
                        break;
                    case R.id.cancel_text:
                        hideTextElements();
                        textString = "";
                        touchView.sl = new StaticLayout(textString,touchView.textPaint,widthScreen/2,
                                Layout.Alignment.ALIGN_CENTER, 1f,0f,false);
                        touchView.invalidate();
                        break;
                }
            }
        };
        textButton.setOnClickListener(textListener);
        done.setOnClickListener(textListener);
        cancel.setOnClickListener(textListener);
    }

    public void hideTextElements(){
        editText.setVisibility(View.INVISIBLE);
        done.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    public void showTextElements(){
        editText.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void hideAllUIElements(){
        colorContainer.setVisibility(View.INVISIBLE);
        textContainer.setVisibility(View.INVISIBLE);
        ruler.setVisibility(View.INVISIBLE);
    }


    public void setRotationRuler(){
        ruler.setOnTouchListener(new View.OnTouchListener() {
            float x;
            float delta;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:

                        x = motionEvent.getX();

                        delta = (x - touchView.angle);


                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = motionEvent.getX();
                        touchView.angle = (x - delta) / 3;

                        break;
                    case MotionEvent.ACTION_UP:
                        touchView.angle = (x - delta) / 3;

                        break;
                }

                touchView.invalidate();


                return true;
            }
        });
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

            View v1 = getWindow().getDecorView().findViewById(R.id.design_container);
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
        values.put(MediaStore.Images.Media.TITLE, "Title");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Description");
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

        changeMainImageColor(R.color.black);
        designContainer.setBackgroundColor(Color.WHITE);
        touchView.textPaint.setColor(Color.parseColor("#FFFFFF"));
        touchView.invalidate();


        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
/////////////// directory
            File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//////////////path
            String mPath = "Chuk"+now+".jpg";

            View v1 = getWindow().getDecorView().findViewById(R.id.design_container);
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
        String emailSubject = item;
        String emailBody = "Color: " + textColor;
        new SendMailTask(CustomProductActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, "", fileName, emailBody);


        changeMainImageColor(currentColor);
        designContainer.setBackgroundColor(Color.TRANSPARENT);
        touchView.textPaint.setColor(Color.parseColor(textColor));
        touchView.invalidate();

    }


    @Override
    public void onBackPressed() {

        if(beforePaymentScreen.getVisibility() == View.VISIBLE) {
            beforePaymentScreen.setVisibility(View.INVISIBLE);

            float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (100 * scale + 0.5f);

            designContainer.setScaleX(1);
            designContainer.setScaleY(1);
            designContainer.setY(pixels/0.8f);

            if(item.equals("petTag")){
                frameShape.setScaleX(1.02f);
                frameShape.setScaleY(1.02f);
                colorFrameShape.setScaleX(1.02f);
                colorFrameShape.setScaleY(1.02f);
                designContainer.setScaleX(1.2f);
                designContainer.setScaleY(1.2f);
            }

            buttonsBar.setVisibility(View.VISIBLE);
        }else if(colorContainer.getVisibility()==View.VISIBLE){
            colorContainer.setVisibility(View.INVISIBLE);
        }else{
            super.onBackPressed();
        }

    }





    public void init(){
        designContainer = (RelativeLayout) findViewById(R.id.design_container);
        frameShape = (ImageView)findViewById(R.id.frame_shape);
        colorFrameShape = (ImageView)findViewById(R.id.color_frame_shape);
        colorFrameShape.setColorFilter(ContextCompat.getColor(this,R.color.transparent));
        colorFrameShape.setVisibility(View.INVISIBLE);

        openSansBold = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");
        latoBlack = Typeface.createFromAsset(getAssets(), "Lato-Black.ttf");
        montserrat = Typeface.createFromAsset(getAssets(), "Montserrat-ExtraBold.ttf");
        vampiroOne = Typeface.createFromAsset(getAssets(), "VampiroOne-Regular.ttf");
        editText = (EditText)findViewById(R.id.editText);
        editText.setTypeface(montserrat);
        textString = editText.getText().toString();

        if(item.equals("petTag")){
            frameShape.setScaleX(1.02f);
            frameShape.setScaleY(1.02f);
            colorFrameShape.setScaleX(1.02f);
            colorFrameShape.setScaleY(1.02f);
            designContainer.setScaleX(1.2f);
            designContainer.setScaleY(1.2f);
            editText.setY(editText.getY()+20);
        }else if(item.equals("gyring")){
            editText.setAllCaps(true);
        }

        touchView = new CustomProductTouchView(this);
        touchView.angle = 0;

        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        colorButton = (Button)findViewById(R.id.color);
        textButton = (Button)findViewById(R.id.text);

        ruler = (ImageView)findViewById(R.id.ruler);
        ruler.setVisibility(View.INVISIBLE);

        colorContainer = (RelativeLayout) findViewById(R.id.color_container);
        colorContainer.setVisibility(View.INVISIBLE);

        beforePaymentScreen = (RelativeLayout)findViewById(R.id.before_payment);
        beforePaymentScreen.setVisibility(View.INVISIBLE);



        textContainer = (RelativeLayout)findViewById(R.id.text_container);
        textContainer.setVisibility(View.INVISIBLE);



        done = (Button)findViewById(R.id.done_text);
        cancel = (Button)findViewById(R.id.cancel_text);
        done.setTypeface(latoBlack);
        cancel.setTypeface(latoBlack);

        if(item.equals("gyring")){

            editText.setTypeface(vampiroOne);
            touchView.textPaint.setTypeface(vampiroOne);
        }



        buttonsBar = (LinearLayout)findViewById(R.id.buttons_nav_bar);
        continueForPayment = (Button)findViewById(R.id.continue_for_payment);

        initButtons();

    }

    public static void instantiateFrame(String item){

        switch (item){
            case "opener":
                mainImageDrawable = R.drawable.bottle_opener;
                frameShape.setImageResource(mainImageDrawable);
                colorFrameShape.setImageResource(mainImageDrawable);

                break;
            case "petTag":
                mainImageDrawable = R.drawable.pet_tag;
                frameShape.setImageResource(mainImageDrawable);
                colorFrameShape.setImageResource(mainImageDrawable);
                break;
            case "gyring":
                mainImageDrawable = R.drawable.gyring_new;
                frameShape.setImageResource(mainImageDrawable);
                colorFrameShape.setImageResource(mainImageDrawable);
                break;

        }

    }




    public void initButtons(){
        next = (Button)findViewById(R.id.next);
        back = (Button)findViewById(R.id.back);

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



    }
}