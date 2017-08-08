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
import android.graphics.PorterDuff;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import static android.view.View.VISIBLE;

/**
 * Created by Natalie Starr on 24/07/2017.
 */

public class DesignActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 99;

    private Typeface gunplay, montserrat, latoBlack, pacifico;
    private Button punchButton, colorButton, textButton, toppingButton;
    public static Button vButton, grid;
    public static Button delete, done, cancel, undo, print;
    private RelativeLayout colorContainer, resizeContainer, beforePaymentScreen;
    public static RelativeLayout designContainer, textContainer, screenContainer;
    private LinearLayout buttonsBar;
    private Button next, back, continueForPayment, share;
    public static EditText editText;
    public static TouchView touchView;
    public static ImageView frameShape, colorFrameShape, ruler;
    private FrameLayout punchContainer, toppingContainer;
    public static TextView currentNumText;
    public static int mainImageDrawable;
    public static float heightScreen, widthScreen;
    String formattedSize;
    public static String textColor;
    public static int currentColor;
    public static Context context;
    public static String textString;

    public static final String POSITION_KEY = "POSITION";
    public static int position;
    public static final String DESIGN_KEY = "DESIGN_KEY";
    public static String design;
    File imageFile;
    String fileName;

    File galleryImageFile;
    String galleryFileName;

    Button punch1, punch2, punch3, punch4, punch5, punch6, punch7, punch8, punch9, punch10, punch11, punch12, punch13, punch14, punch15, punch16, punch17, punch18, punch19, punch20, punch21, punch22, punch23, punch24, punch25, punch26, punch27, punch28, punch29, punch30, punch31, punch32, punch33, punch34, punch35, punch36;
    Button topping1, topping2, topping3, topping4, topping5, topping6, topping7, topping8, topping9, topping10, topping11, topping12, topping13, topping14, topping15, topping16, topping17, topping18;
    Button color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15, color16, color17, color18, color19, color20, color21;

    TextView shapeSize, recommndedSize;
    TextView txtNo1, txtNo2, txtNo3, txtNo4, txtNo5, txtNo6, txtNo7, txt1, txt2, txt3, txt4, txt5, txt6, txt7;
    Button resizeRuler;

    public static LinkedList<String> stack = new LinkedList<>();
    private LinkedList<Integer> colorStack = new LinkedList<>();
    public static LinkedList<String> textColorStack = new LinkedList<>();
    public static LinkedList<Float> angles = new LinkedList<>();
    public static LinkedList<Integer> rotateRecords = new LinkedList<>();
    public static LinkedList<Float> textAngles = new LinkedList<>();
    public static LinkedList<Shape> deletedShapes = new LinkedList<>();

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        context = this.getApplicationContext();

        position = getIntent().getIntExtra(POSITION_KEY, 1);
        design = getIntent().getStringExtra(DESIGN_KEY);


        init();
        instantiateFrame(design, position);
        recordImageView();
        designContainer.addView(touchView);

        initToppingButton();
        initColorButton();
        initTextButton();
        initGridButton();
        onNextButtonClicked();
        initPunchButton();
        setRotationRuler();
        initUndo();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


//        print.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                for(String stackItem: stack){
//                    Log.e("Stack Item", stackItem);
//                }
//                Log.e("************","************");
//                for(int i = 0; i < deletedShapes.size(); i++){
//                    Log.e("deleted shape", "at location: " + i);
//                }
//                Log.e("************","************");
//                for(int i = 0; i < TouchView.shapes.size(); i++){
//                    Log.e("shape pos", "" + i);
//                }
//                Log.e("************","************");
//            }
//        });
    }


    public void initUndo(){
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String actionType;
                if(stack.isEmpty()){
                    actionType = "";
                }else{
                    actionType = stack.getLast();
                }

                switch (actionType){
                    case "punch":
                        TouchView.shapes.removeLast();
                        TouchView.shapesForColor.removeLast();
                        break;
                    case "touch":
                        int shapeToUndo = TouchView.shapeRecord.getLast();
                        TouchView.shapeRecord.removeLast();
                        TouchView.xMoveRecord.removeLast();
                        TouchView.yMoveRecord.removeLast();
                        TouchView.scaleRecord.removeLast();

                        LinkedList<Integer>locations = new LinkedList<>();
                        for(int i=0;i<TouchView.shapeRecord.size();i++){
                            if(TouchView.shapeRecord.get(i) == shapeToUndo){
                                locations.add(i);
                            }
                        }
                        if(locations.size() > 0){
                            int lastLocation = locations.size()-1;
                            float previousX = TouchView.xMoveRecord.get(lastLocation);
                            float previousY = TouchView.yMoveRecord.get(lastLocation);
                            float previousScale = TouchView.scaleRecord.get(lastLocation);
                            TouchView.shapes.get(shapeToUndo).setPosX(previousX);
                            TouchView.shapes.get(shapeToUndo).setPosY(previousY);
                            TouchView.shapes.get(shapeToUndo).setScaleFactor(previousScale);
                        }else{

                        }

                        break;
                    case "color":
                        colorStack.removeLast();
                        textColorStack.removeLast();
                        if(!colorStack.isEmpty()){
                            currentColor = colorStack.getLast();
                            textColor = textColorStack.getLast();
                        }else {
                            currentColor = R.color.transparent;
                            textColor = "#FFFFFF";
                        }
                        changeMainImageColor(currentColor, currentColor, textColor);
                        break;
                    case "text":
                        editText.setText("");
                        textString = "";
                        touchView.sl = new StaticLayout(textString, touchView.textPaint, (int)widthScreen / 2,
                                Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                        currentNumText.setText(textString);
                        delete.setVisibility(View.INVISIBLE);
                        vButton.setVisibility(View.INVISIBLE);
                        break;
                    case "textTouch":
                        TouchView.textXMoveRecord.removeLast();
                        TouchView.textYMoveRecord.removeLast();
                        TouchView.textScaleRecord.removeLast();
                        float previousX = TouchView.textXMoveRecord.getLast();
                        float previousY = TouchView.textYMoveRecord.getLast();
                        float previousScale = TouchView.textScaleRecord.getLast();
                        TouchView.textView.setX(previousX);
                        TouchView.textView.setY(previousY);
                        TouchView.textScaleFactor = previousScale;
                        break;
                    case "rotate":
                        int shapeToReRotate = rotateRecords.getLast();
                        rotateRecords.removeLast();
                        angles.removeLast();
                        LinkedList<Integer>rLocations = new LinkedList<>();
                        for(int i=0;i<rotateRecords.size();i++){
                            if(rotateRecords.get(i) == shapeToReRotate){
                                rLocations.add(i);
                            }
                        }
                        if(rLocations.size() >= 0 ) {
                            int lastRLocation = rLocations.size();
                            float previousAngle = 0;
                            if(angles.size()>0){
                                previousAngle = angles.get(lastRLocation);
                            }

                            TouchView.shapes.get(shapeToReRotate).setAngle(previousAngle);
                        }else{

                        }
                    case "textRotate":
                            float previousAngle = 0;
                            if(textAngles.size() > 0) {
                                textAngles.removeLast();
                                try {
                                    previousAngle = textAngles.getLast();
                                } catch (IndexOutOfBoundsException e) {
                                }catch (NoSuchElementException e) {
                                }
                                TouchView.angle = previousAngle;
                            }
                        break;
                    case "delete":
                        Shape shape = new Shape(deletedShapes.getLast().getDrawable(), deletedShapes.getLast().getPosX(), deletedShapes.getLast().getPosY());
                        TouchView.shapes.add(shape);
                        TouchView.shapesForColor.add(shape);
                        TouchView.shapes.getLast().setScaleFactor(shape.getScaleFactor());
                        deletedShapes.removeLast();
                        break;
                    default:
                        break;
                }
                if(!stack.isEmpty()){
                    stack.removeLast();
                }
                delete.setVisibility(View.INVISIBLE);
                vButton.setVisibility(View.INVISIBLE);
                try{
                    touchView.clearGrayColor();
                }catch (Exception e){

                }

                touchView.invalidate();
            }
        });
    }

    public static void clearStack(int stackSize){
        while(stack.size() > stackSize){
            stack.removeFirst();
        }
    }


    public void goToBeforePaymentScreen() {

        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);
        hideAllUIElements();
        TouchView.CURRENT_SHAPE = -1;

        setOriginalShapeSize();
        designContainer.setScaleX(0.5f);
        designContainer.setScaleY(0.5f);

        designContainer.setY(-pixels / 2f);
        // position frame in center on all screen densities
        if (pixels == 300) {
            designContainer.setX(pixels / 10.5f);
        } else {
            designContainer.setX(pixels / 10.5f);
        }

        touchView.fillColorShapes(R.color.halfTransparentWhite, currentColor, textColor);
        TouchView.textPaint.setColor(Color.parseColor(textColor));
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

    public void onNextButtonClicked() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAllUIElements();
                Button[] buttons = {punchButton, toppingButton, colorButton, textButton};
                for (Button button : buttons) {
                    button.setOnClickListener(null);
                }

                if(beforePaymentScreen.getVisibility() == VISIBLE) {
                    takeScreenshot();
                    takeScreenshotForGallery();

                } else if (resizeContainer.getVisibility() == View.INVISIBLE) {
                    grid.setVisibility(View.INVISIBLE);
                    screenContainer.setVisibility(View.INVISIBLE);
                    resizeContainer.setVisibility(View.VISIBLE);
                    resizeShape();
                    TouchView.CURRENT_SHAPE = -1;
                }else{
                    goToBeforePaymentScreen();
                    resizeContainer.setVisibility(View.INVISIBLE);
                    buttonsBar.setVisibility(View.INVISIBLE);
                    TouchView.CURRENT_SHAPE = -1;

                }

            }
        });
    }


    public void initPunchButton() {
        final Button[] punchButtons = {punch1, punch2, punch3, punch4, punch5, punch6, punch7, punch8, punch9, punch10, punch11, punch12, punch13, punch14, punch15, punch16, punch17, punch18, punch19, punch20, punch21, punch22, punch23, punch24, punch25, punch26, punch27, punch28, punch29, punch30, punch31, punch32, punch33, punch34, punch35, punch36};
        final int[] punchButtonId = {R.id.punch1, R.id.punch2, R.id.punch3, R.id.punch4, R.id.punch5, R.id.punch6, R.id.punch7, R.id.punch8, R.id.punch9, R.id.punch10, R.id.punch11, R.id.punch12, R.id.punch13, R.id.punch14, R.id.punch15, R.id.punch16, R.id.punch17, R.id.punch18, R.id.punch19, R.id.punch20, R.id.punch21, R.id.punch22, R.id.punch23, R.id.punch24, R.id.punch25, R.id.punch26, R.id.punch27, R.id.punch28, R.id.punch29, R.id.punch30, R.id.punch31, R.id.punch32, R.id.punch33, R.id.punch34, R.id.punch35, R.id.punch36};


        View.OnClickListener punchListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                hideAllUIElements();
                vButton.setVisibility(View.INVISIBLE);
                try {
                    touchView.clearGrayColor();
                }catch (NullPointerException e){

                }

                punchContainer.setVisibility(View.VISIBLE);

                for (int i = 0; i < punchButtons.length; i++) {
                    if (view.getId() == punchButtonId[i]) {
                        touchView.punch(i, "punch");
                        stack.add("punch");
                        clearStack(3);
                        angles.add(0f);
                        vButton.setVisibility(View.VISIBLE);
                        punchContainer.setVisibility(View.INVISIBLE);
                        undo.setVisibility(VISIBLE);
                        initVButton();
                        String currentText = String.valueOf(TouchView.CURRENT_SHAPE + 1);
                        currentNumText.setText(currentText);
                        touchView.invalidate();
                        ruler.setVisibility(View.VISIBLE);
                    }
                }
            }
        };

        punchButton.setOnClickListener(punchListener);
        for (int i = 0; i < punchButtons.length; i++) {
            punchButtons[i].setOnClickListener(punchListener);
        }

    }

    public void initToppingButton() {

        final Button[] toppingButtons = {topping1, topping2, topping3, topping4, topping5, topping6, topping7, topping8, topping9, topping10, topping11, topping12, topping13, topping14, topping15, topping16, topping17, topping18};
        final int[] toppingButtonId = {R.id.topping1, R.id.topping2, R.id.topping3, R.id.topping4, R.id.topping5, R.id.topping6, R.id.topping7, R.id.topping8, R.id.topping9, R.id.topping10, R.id.topping11, R.id.topping12, R.id.topping13, R.id.topping14, R.id.topping15, R.id.topping16, R.id.topping17, R.id.topping18,};

        View.OnClickListener punchListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                hideAllUIElements();
                vButton.setVisibility(View.INVISIBLE);
                touchView.clearGrayColor();
                toppingContainer.setVisibility(View.VISIBLE);

                for (int i = 0; i < toppingButtons.length; i++) {
                    if (view.getId() == toppingButtonId[i]) {
                        touchView.punch(i, "topping");
                        stack.add("punch");
                        clearStack(3);
                        angles.add(0f);
                        vButton.setVisibility(View.VISIBLE);
                        toppingContainer.setVisibility(View.INVISIBLE);
                        initVButton();
                        undo.setVisibility(VISIBLE);
                        String currentText = String.valueOf(TouchView.CURRENT_SHAPE + 1);
                        currentNumText.setText(currentText);
                        ruler.setVisibility(View.VISIBLE);
                    }
                }

                if (currentColor != 0) {
                    changeMainImageColor(currentColor, currentColor, textColor);
                }
                touchView.invalidate();

            }
        };

        toppingButton.setOnClickListener(punchListener);
        for (int i = 0; i < toppingButtons.length; i++) {
            toppingButtons[i].setOnClickListener(punchListener);
        }

    }


    public void initColorButton() {

        final Button[] colorButtons = {color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13, color14, color15, color16, color17, color18, color19, color20, color21};
        final int[] colorList = new int[]{R.id.color1, R.id.color2, R.id.color3, R.id.color4, R.id.color5, R.id.color6, R.id.color7, R.id.color8, R.id.color9, R.id.color10,
                R.id.color11, R.id.color12, R.id.color13, R.id.color14, R.id.color15, R.id.color16, R.id.color17, R.id.color18, R.id.color19, R.id.color20, R.id.color21};

        final int colors[] = new int[]{R.color.color1, R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6, R.color.color7,
                R.color.color8, R.color.color9, R.color.color10, R.color.color11, R.color.color12, R.color.color13, R.color.color14,
                R.color.color15, R.color.color16, R.color.color17, R.color.color18, R.color.color21, R.color.color22, R.color.color24,
                R.color.editGrayBigShape, R.color.background, R.color.colorFifthScreen};

        final String colorStings[] = new String[]{"#044182", "#3482C4", "#1C6502", "#B4FC30", "#5F9D2D", "#FFF8EB", "#CC6427",
                "#FB24F2", "#EFEFEF", "#8E8E8E", "#AF0000", "#DD3333", "#29D6E4", "#000000",
                "#EEFF00", "#EFEE22", "#FFCE9E", "#6927C3", "#FFC214", "#C9C9C9", "#1D73BE",
                "#7da4aab3", "#f9f8f8", "#e7f7ff", "#ffffff"};

        View.OnClickListener colorListener = new View.OnClickListener() {
            public void onClick(View v) {

                hideAllUIElements();
                vButton.setVisibility(View.INVISIBLE);
                touchView.clearGrayColor();
                colorContainer.setVisibility(View.VISIBLE);

                for (int i = 0; i < colorButtons.length; i++) {
                    if (v.getId() == colorList[i]) {

                        textColor = colorStings[i];
                        changeMainImageColor(colors[i], colors[i], colorStings[i]);
                        stack.add("color");
                        clearStack(3);
                        colorStack.add(colors[i]);
                        textColorStack.add(colorStings[i]);
                        currentColor = colors[i];
                        colorContainer.setVisibility(View.INVISIBLE);
                        ruler.setVisibility(View.VISIBLE);
                        undo.setVisibility(VISIBLE);
                    }
                }
            }
        };

        colorButton.setOnClickListener(colorListener);
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i].setOnClickListener(colorListener);

        }

    }


    public static void changeMainImageColor(int frameColor, int toppingColor, String textColor) {

        colorFrameShape.setColorFilter(ContextCompat.getColor(context, frameColor));
        colorFrameShape.setVisibility(View.VISIBLE);
        for (Shape shape : TouchView.shapesForColor) {
            try{
                if (shape.getTag().equals("punch")) {

                } else {
                    shape.getDrawable().mutate().setColorFilter(App.getContext().getResources().getColor(toppingColor), PorterDuff.Mode.SRC_IN);
                }
            }catch (NullPointerException e){

            }
        }
        try {
            TouchView.textPaint.setColor(Color.parseColor(textColor));
        } catch (RuntimeException e) {
        }

        touchView.invalidate();
    }


    public void initTextButton() {

        final View.OnClickListener textListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int widthScreen = getResources().getDisplayMetrics().widthPixels;
                switch (view.getId()) {
                    case R.id.text:
                        hideAllUIElements();
                        vButton.setVisibility(View.INVISIBLE);
                        touchView.clearGrayColor();
                        textContainer.setVisibility(View.VISIBLE);
                        showTextElements();
                        textString = "";
                        TouchView.sl = new StaticLayout(textString, TouchView.textPaint, widthScreen / 2,
                                Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                        touchView.invalidate();

                        break;
                    case R.id.done_text:
                        textString = editText.getText().toString();
                        TouchView.textView.setText(textString);

                        if(design.equals("doorSign")){
                            TouchView.sl = new StaticLayout(textString, TouchView.textPaint, widthScreen / 2,
                                    Layout.Alignment.ALIGN_CENTER, 0.5f, 0f, false);
                        }else {
                            TouchView.sl = new StaticLayout(textString, TouchView.textPaint, widthScreen / 2,
                                    Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                        }
                        currentNumText.setText("T");
                        touchView.invalidate();
                        hideTextElements();
                        initVButton();
                        undo.setVisibility(VISIBLE);
                        vButton.setVisibility(View.VISIBLE);
                        ruler.setVisibility(View.VISIBLE);
                        stack.add("text");
                        clearStack(3);
                        textAngles.add(TouchView.textView.getRotation());
                        TouchView.textXMoveRecord.add(TouchView.textView.getX());
                        TouchView.textYMoveRecord.add(TouchView.textView.getY());
                        TouchView.textScaleRecord.add(TouchView.textScaleFactor);

                        break;
                    case R.id.cancel_text:
                        hideTextElements();
                        editText.setText("");
                        textString = "";
                        TouchView.sl = new StaticLayout(textString, TouchView.textPaint, widthScreen / 2,
                                Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                        touchView.invalidate();
                        currentNumText.setText(textString);
                        break;
                }
            }
        };
        textButton.setOnClickListener(textListener);
        done.setOnClickListener(textListener);
        cancel.setOnClickListener(textListener);
    }


    public static void hideTextElements() {
        editText.setVisibility(View.INVISIBLE);
        done.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void showTextElements() {
        editText.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) App.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    public void setRotationRuler() {
        ruler.setOnTouchListener(new View.OnTouchListener() {
            float x;
            float delta;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int action = motionEvent.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();

                        if (currentNumText.getText().equals("T")) {
                            delta = (x - TouchView.angle);
                        } else if (!TouchView.shapes.isEmpty()) {
                            delta = (x - TouchView.shapes.get(TouchView.CURRENT_SHAPE).getAngle());
                        } else {

                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x = motionEvent.getX();

                        if (currentNumText.getText().equals("T")) {
                            TouchView.angle = (x - delta) / 3;
                        } else if (!TouchView.shapes.isEmpty()) {
                            TouchView.shapes.get(TouchView.CURRENT_SHAPE).setAngle((x - delta) / 3);
                        } else {

                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (currentNumText.getText().equals("T")) {
                            TouchView.angle = (x - delta) / 3;
                            stack.add("textRotate");
                            clearStack(3);
                            textAngles.add(TouchView.angle);
                        } else if (!TouchView.shapes.isEmpty()) {
                            TouchView.shapes.get(TouchView.CURRENT_SHAPE).setAngle((x - delta) / 3);
                            stack.add("rotate");
                            clearStack(3);
                            angles.add(TouchView.shapes.get(TouchView.CURRENT_SHAPE).getAngle());
                            rotateRecords.add(TouchView.CURRENT_SHAPE);
                        } else {

                        }
                        break;
                }
                touchView.invalidate();

                return true;
            }
        });
    }


    public static void initVButton() {
        vButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vButton.setVisibility(View.INVISIBLE);
                delete.setVisibility(View.INVISIBLE);
                touchView.clearGrayColor();
            }
        });
    }

    public void initGridButton() {
        grid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(screenContainer.getVisibility()==VISIBLE){
                    screenContainer.setVisibility(View.INVISIBLE);
                }else {
                    screenContainer.setVisibility(VISIBLE);
                }
            }
        });
    }

    public static void initDeleteButton() {

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentNumText.getText().equals("T")) {

                    textString = "";
                    editText.setText("");
                    TouchView.sl = new StaticLayout(textString, TouchView.textPaint, 1000 / 2,
                            Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
                    vButton.setVisibility(View.INVISIBLE);
                    touchView.clearGrayColor();
                    touchView.invalidate();
                    currentNumText.setText(textString);
                } else {
                    if (!TouchView.shapes.isEmpty()) {

                        Shape shape = new Shape(TouchView.shapes.get(TouchView.CURRENT_SHAPE).getDrawable(), TouchView.shapes.get(TouchView.CURRENT_SHAPE).getPosX(), TouchView.shapes.get(TouchView.CURRENT_SHAPE).getPosY());
                        deletedShapes.add(shape);
                        deletedShapes.getLast().setScaleFactor(shape.getScaleFactor());

                        TouchView.shapes.remove(TouchView.CURRENT_SHAPE);
                        TouchView.shapesForColor.remove(TouchView.CURRENT_SHAPE);
                        if (TouchView.CURRENT_SHAPE == 0 && !TouchView.shapes.isEmpty()) {
                            TouchView.CURRENT_SHAPE++;
                        } else {
                            TouchView.CURRENT_SHAPE--;
                            currentNumText.setText("T");
                        }
                        stack.add("delete");
                        clearStack(1);
                        vButton.setVisibility(View.INVISIBLE);
                        touchView.clearGrayColor();
                        touchView.invalidate();

                    }
                }
            }
        });
    }


    public void resizeShape() {

        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (200 * scale + 0.5f);

        designContainer.setPivotX(0);
        designContainer.setPivotY(0);
        designContainer.setX(-pixels / 50f);
        designContainer.setY(pixels / 6f);

        resizeContainer.setVisibility(View.VISIBLE);

        TextView[] cms1 = {txtNo1, txtNo2, txtNo3, txtNo4, txtNo5, txtNo6, txtNo7};
        TextView[] cms2 = {txt1, txt2, txt3, txt4, txt5, txt6, txt7};

        if (design.equals("coaster") || design.equals("keychain") || design.equals("magnet")) {
            for (int i = 0; i < cms1.length; i++) {
                String text = String.valueOf(i + 3);
                cms1[i].setText(text);
                cms2[i].setText(text);
            }
        }else if(design.equals("doorSign")){
            for (int i = 0; i < cms1.length; i++) {
                String text = String.valueOf(i + 5);
                cms1[i].setText(text);
                cms2[i].setText(text);
            }
        }

        resizeRuler.setOnTouchListener(new View.OnTouchListener() {

            float x;
            float y;
            float newSize;
            float maxSize;


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                float sizeInSm = designContainer.getScaleX() * 5.73f;
                if (design.equals("coaster") || design.equals("keychain") || design.equals("magnet")) {
                    formattedSize = String.format("%.0f", sizeInSm + 2);
                    newSize = sizeInSm + 2;
                    maxSize = 9;
                } else if(design.equals("doorSign")){
                    formattedSize = String.format("%.0f", sizeInSm + 4);
                    newSize = sizeInSm + 4;
                    maxSize = 11;
                } else {
                    formattedSize = String.format("%.0f", sizeInSm + 0.5);
                    newSize = sizeInSm + 0.5f;
                    maxSize = 7;
                }


                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    x = motionEvent.getX();
                    y = motionEvent.getY();

                    float scaleFactor = x / 490;
                    scaleFactor = Math.max(0.25f, Math.min(scaleFactor, 1.20f));

                    designContainer.setScaleX(scaleFactor);
                    designContainer.setScaleY(scaleFactor);

                    if (newSize > maxSize) {
                        shapeSize.setText("");
                    } else {
                        shapeSize.setText("Your " + design + " size: " + formattedSize + " cm");
                    }

                    if (design.equals("earrings") || design.equals("pendant")) {
                        recommndedSize.setText("Recommended size for " + design + ": 3 - 5 cm");
                    } else if (design.equals("coaster")) {
                        recommndedSize.setText("Recommended size for " + design + ": 9 cm");
                    }
                }

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
        designContainer.setBackgroundColor(Color.parseColor("#e7f7ff"));
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
        designContainer.setBackgroundColor(Color.TRANSPARENT);
    }

    private void takeScreenshot() {

        designContainer.setBackgroundColor(Color.WHITE);
        changeMainImageColor(R.color.darkBlack, R.color.realGray, "#616161");
//        touchView.textPaint.setColor(Color.parseColor("#616161"));
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

        changeMainImageColor(currentColor, currentColor, textColor);
        designContainer.setBackgroundColor(Color.TRANSPARENT);
        touchView.fillColorShapes(R.color.halfTransparentWhite, currentColor, null);
//        touchView.textPaint.setColor(Color.parseColor(textColor));
        touchView.invalidate();


        try {
            Uri uri= Uri.fromFile(imageFile);
            fileName = PathUtil.getPath(getApplicationContext(), uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

        Log.i("SendMailActivity", "Send Button Clicked.");

        String fromEmail = "chuk3d@gmail.com";

        String fromPassword = "chukchukchuk";

        String toEmails = "nataliestarr82@gmail.com";

        List toEmailList = Arrays.asList(toEmails
                .split("\\s*,\\s*"));
        Log.i("SendMailActivity", "To List: " + toEmailList);
        String emailSubject = design;
        String emailBody = "design size: " + formattedSize + ", color: " + textColor;
        new SendMailTask(DesignActivity.this).execute(fromEmail,
                fromPassword, toEmailList, emailSubject, "", fileName, emailBody);

    }



    public void setOriginalShapeSize(){

        float scale = getResources().getDisplayMetrics().density;
        int pixels = (int) (100 * scale + 0.5f);

        if(pixels == 300){
            touchView.setLayoutParams();
        }else{
            touchView.setLayoutParams();
        }

        designContainer.setPivotX(widthScreen/2);
        designContainer.setPivotY(heightScreen/2);

        designContainer.setScaleX(1);
        designContainer.setScaleY(1);

        designContainer.setY(pixels/1.8f);
        touchView.invalidate();

    }


    public void hideAllUIElements(){
        colorContainer.setVisibility(View.INVISIBLE);
        textContainer.setVisibility(View.INVISIBLE);
        punchContainer.setVisibility(View.INVISIBLE);
        toppingContainer.setVisibility(View.INVISIBLE);
        ruler.setVisibility(View.INVISIBLE);
        vButton.setVisibility(View.INVISIBLE);
        currentNumText.setVisibility(View.INVISIBLE);
        undo.setVisibility(View.INVISIBLE);
    }

    public void showAllUIElements(){
        grid.setVisibility(View.VISIBLE);
        ruler.setVisibility(View.VISIBLE);
        currentNumText.setVisibility(View.INVISIBLE);
        buttonsBar.setVisibility(View.VISIBLE);
        TouchView.CURRENT_SHAPE = TouchView.shapes.size()-1;
        undo.setVisibility(View.VISIBLE);
    }



    @Override
    public void onBackPressed() {

        if(colorContainer.getVisibility()==View.VISIBLE){
            colorContainer.setVisibility(View.INVISIBLE);
        }else if(punchContainer.getVisibility()==View.VISIBLE){
            punchContainer.setVisibility(View.INVISIBLE);
        }else if(toppingContainer.getVisibility()==View.VISIBLE){
            toppingContainer.setVisibility(View.INVISIBLE);
        }else if(textContainer.getVisibility()==View.VISIBLE){
            textContainer.setVisibility(View.INVISIBLE);
        }else if(resizeContainer.getVisibility()==View.VISIBLE){
            resizeContainer.setVisibility(View.INVISIBLE);
            setOriginalShapeSize();
            showAllUIElements();
            initPunchButton();
            initToppingButton();
            initColorButton();
            initTextButton();
        }else if(beforePaymentScreen.getVisibility()==View.VISIBLE){

            beforePaymentScreen.setVisibility(View.INVISIBLE);
            touchView.fillColorShapes(R.color.transparent, currentColor, null);
            TouchView.textPaint.setColor(Color.parseColor(textColor));
            resizeContainer.setVisibility(View.VISIBLE);
            resizeShape();
        }else{
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            while (!TouchView.shapes.isEmpty()) {
                                TouchView.shapes.remove(0);
                                TouchView.shapesForColor.remove(0);
                            }
                            TouchView.CURRENT_SHAPE = -1;
                            currentColor = 0;
                            clearStack();
                            DesignActivity.super.onBackPressed();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            final AlertDialog.Builder builder = new AlertDialog.Builder(DesignActivity.this);
            builder.setMessage("Are you sure? your design will be lost forever!").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

    }

    public void clearStack(){
        while (!stack.isEmpty()) {
            stack.remove(0);
        }
        while (!TouchView.shapeRecord.isEmpty()) {
            TouchView.shapeRecord.remove(0);
        }
        while (!TouchView.xMoveRecord.isEmpty()) {
            TouchView.xMoveRecord.remove(0);
        }
        while (!TouchView.yMoveRecord.isEmpty()) {
            TouchView.yMoveRecord.remove(0);
        }
        while (!TouchView.scaleRecord.isEmpty()) {
            TouchView.scaleRecord.remove(0);
        }
        while (!angles.isEmpty()) {
            angles.remove(0);
        }
        while (!colorStack.isEmpty()) {
            colorStack.remove(0);
        }
        while (!textColorStack.isEmpty()) {
            textColorStack.remove(0);
        }
    }

    public void init(){
        undo = (Button)findViewById(R.id.undo);
//        print = (Button)findViewById(R.id.print);

        screenContainer = (RelativeLayout) findViewById(R.id.screen_container);
        screenContainer.setBackgroundResource(R.drawable.grid);
        screenContainer.setVisibility(View.INVISIBLE);
        designContainer = (RelativeLayout) findViewById(R.id.design_container);
        frameShape = (ImageView)findViewById(R.id.frame_shape);
        colorFrameShape = (ImageView)findViewById(R.id.color_frame_shape);
        colorFrameShape.setColorFilter(ContextCompat.getColor(this,R.color.transparent));
        colorFrameShape.setVisibility(View.INVISIBLE);

        punchButton = (Button)findViewById(R.id.punch);
        colorButton = (Button)findViewById(R.id.color);
        textButton = (Button)findViewById(R.id.text);
        toppingButton = (Button)findViewById(R.id.topping);

        gunplay = Typeface.createFromAsset(getAssets(), "GUNPLAY_.ttf");
        montserrat = Typeface.createFromAsset(getAssets(), "Montserrat-ExtraBold.ttf");
        latoBlack = Typeface.createFromAsset(getAssets(), "Lato-Black.ttf");
        pacifico = Typeface.createFromAsset(getAssets(), "Pacifico-Regular.ttf");

        editText = (EditText)findViewById(R.id.editText);

        textString = editText.getText().toString();
        if(design.equals("doorSign")){
            editText.setTypeface(pacifico);
        }else{
            editText.setTypeface(montserrat);

        }

        touchView = new TouchView(this);

        heightScreen = getResources().getDisplayMetrics().heightPixels;
        widthScreen = getResources().getDisplayMetrics().widthPixels;

        ruler = (ImageView)findViewById(R.id.ruler);
        ruler.setVisibility(View.INVISIBLE);

        vButton = (Button)findViewById(R.id.v);
        vButton.setVisibility(View.INVISIBLE);

        grid = (Button)findViewById(R.id.grid);

        punchContainer = (FrameLayout)findViewById(R.id.punch_container);
        punchContainer.setVisibility(View.INVISIBLE);
        toppingContainer = (FrameLayout)findViewById(R.id.topping_container);
        toppingContainer.setVisibility(View.INVISIBLE);


        colorContainer = (RelativeLayout) findViewById(R.id.color_container);
        colorContainer.setVisibility(View.INVISIBLE);

        resizeContainer = (RelativeLayout)findViewById(R.id.resize_container);
        resizeContainer.setVisibility(View.INVISIBLE);

        beforePaymentScreen = (RelativeLayout)findViewById(R.id.before_payment);
        beforePaymentScreen.setVisibility(View.INVISIBLE);

        currentNumText = (TextView)findViewById(R.id.current_number);
        currentNumText.setText("");
        currentNumText.setVisibility(View.INVISIBLE);

        textContainer = (RelativeLayout)findViewById(R.id.text_container);
        textContainer.setVisibility(View.INVISIBLE);

        done = (Button)findViewById(R.id.done_text);
        cancel = (Button)findViewById(R.id.cancel_text);
        done.setTypeface(latoBlack);
        cancel.setTypeface(latoBlack);

        delete = (Button)findViewById(R.id.delete);
        delete.setTypeface(latoBlack);
        delete.setVisibility(View.INVISIBLE);
        delete.bringToFront();

        resizeRuler = (Button)findViewById(R.id.ruler_move);
        shapeSize = (TextView)findViewById(R.id.shape_size);
        shapeSize.setTypeface(latoBlack);
        recommndedSize = (TextView)findViewById(R.id.shape_recommendation);
        recommndedSize.setTypeface(latoBlack);

        buttonsBar = (LinearLayout)findViewById(R.id.buttons_nav_bar);
        continueForPayment = (Button)findViewById(R.id.continue_for_payment);

        initButtons();

    }

    public static void instantiateFrame(String design, int position){


        final int[] image_resources_magnets = {R.drawable.oval_frame_shape, R.drawable.triangle_frame_shape, R.drawable.rectangle_frame_shape,
                R.drawable.diamond_frame_shape, R.drawable.star_frame_shape, R.drawable.cloud_frame_shape, R.drawable.drop_frame_shape, R.drawable.bird_shape_frame_purple_copy,
                R.drawable.flower_frame_shape, R.drawable.heart_frame_shape, R.drawable.bubble_shape_frame_purple_copy, R.drawable.horse_inner_shadow_blue_copy_2};

        final int[] image_resources_coaster = {R.drawable.oval_frame_shape, R.drawable.rectangle_frame_shape, R.drawable.star_frame_shape,
                R.drawable.flower_frame_shape, R.drawable.bubble_shape_frame_purple_copy, R.drawable.heart_frame_shape};


        final int[] image_resources_earring = {R.drawable.flower_frame_shape, R.drawable.star_frame_shape, R.drawable.bird_shape_frame_purple_copy, R.drawable.heart_frame_shape,
                R.drawable.horse_inner_shadow_blue_copy, R.drawable.drop_frame_shape, R.drawable.oval_frame_shape, R.drawable.flash_shape_frame_purple_copy, R.drawable.triangle_frame_shape, R.drawable.diamond_frame_shape};


        final int[] image_resources_pendant = {R.drawable.diamond_frame_shape, R.drawable.drop_frame_shape, R.drawable.oval_frame_shape,R.drawable.horse_inner_shadow_blue_copy_2,
                R.drawable.rectangle_frame_shape, R.drawable.heart_frame_shape, R.drawable.star_frame_shape, R.drawable.flower_frame_shape, R.drawable.bird_shape_frame_purple_copy};

        switch (design){
            case "magnet":
                if(position > image_resources_magnets.length){
                    Toast.makeText(App.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
                }else {
                    mainImageDrawable = image_resources_magnets[position];
                    frameShape.setImageResource(mainImageDrawable);
                    colorFrameShape.setImageResource(mainImageDrawable);
                }
                break;
            case "coaster":
                if(position > image_resources_coaster.length){
                    Toast.makeText(App.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
                }else {
                    mainImageDrawable = image_resources_coaster[position];
                    frameShape.setImageResource(mainImageDrawable);
                    colorFrameShape.setImageResource(mainImageDrawable);
                }
                break;
            case "earrings":
                if(position > image_resources_earring.length){
                    Toast.makeText(App.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
                }else {
                    mainImageDrawable = image_resources_earring[position];
                    frameShape.setImageResource(mainImageDrawable);
                    colorFrameShape.setImageResource(mainImageDrawable);
                }
                break;
            case "pendant":
                if(position > image_resources_pendant.length){
                    Toast.makeText(App.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
                }else {
                    mainImageDrawable = image_resources_pendant[position];
                    frameShape.setImageResource(mainImageDrawable);
                    colorFrameShape.setImageResource(mainImageDrawable);
                }
                break;
            case "keychain":
                if(position > image_resources_magnets.length){
                    Toast.makeText(App.getContext(), "Position: " + position, Toast.LENGTH_SHORT).show();
                }else {
                    mainImageDrawable = image_resources_magnets[position];
                    frameShape.setImageResource(mainImageDrawable);
                    colorFrameShape.setImageResource(mainImageDrawable);
                }
                break;
            case "doorSign":

                mainImageDrawable = 0;
                frameShape.setVisibility(View.INVISIBLE);
                colorFrameShape.setVisibility(View.INVISIBLE);

                break;
        }

    }

    /**

     * Record a screen view for the visible displayed


     */

    private void recordImageView() {
        final String[] magnet_names = {"oval", "triangle", "rectangle",
               "diamond", "star", "cloud", "drop", "bird",
                "flower", "heart", "bubble", "horse"};

        String id =  design;

        String name;
        if(design.equals("magnet")){
            name = magnet_names[position];
        }else{
            name = "";
        }


        // [START image_view_event]

        Bundle bundle = new Bundle();

        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);

        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);

        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");

        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        // [END image_view_event]

    }



    public void initButtons(){
        next = (Button)findViewById(R.id.next);
        back = (Button)findViewById(R.id.back);

        punch1 = (Button)findViewById(R.id.punch1);
        punch2 = (Button)findViewById(R.id.punch2);
        punch3 = (Button)findViewById(R.id.punch3);
        punch4 = (Button)findViewById(R.id.punch4);
        punch5 = (Button)findViewById(R.id.punch5);
        punch6 = (Button)findViewById(R.id.punch6);
        punch7 = (Button)findViewById(R.id.punch7);
        punch8 = (Button)findViewById(R.id.punch8);
        punch9 = (Button)findViewById(R.id.punch9);
        punch10 = (Button)findViewById(R.id.punch10);
        punch11 = (Button)findViewById(R.id.punch11);
        punch12 = (Button)findViewById(R.id.punch12);
        punch13 = (Button)findViewById(R.id.punch13);
        punch14 = (Button)findViewById(R.id.punch14);
        punch15 = (Button)findViewById(R.id.punch15);
        punch16 = (Button)findViewById(R.id.punch16);
        punch17 = (Button)findViewById(R.id.punch17);
        punch18 = (Button)findViewById(R.id.punch18);
        punch19 = (Button)findViewById(R.id.punch19);
        punch20 = (Button)findViewById(R.id.punch20);
        punch21 = (Button)findViewById(R.id.punch21);
        punch22 = (Button)findViewById(R.id.punch22);
        punch23 = (Button)findViewById(R.id.punch23);
        punch24 = (Button)findViewById(R.id.punch24);
        punch25 = (Button)findViewById(R.id.punch25);
        punch26 = (Button)findViewById(R.id.punch26);
        punch27 = (Button)findViewById(R.id.punch27);
        punch28 = (Button)findViewById(R.id.punch28);
        punch29 = (Button)findViewById(R.id.punch29);
        punch30 = (Button)findViewById(R.id.punch30);
        punch31 = (Button)findViewById(R.id.punch31);
        punch32 = (Button)findViewById(R.id.punch32);
        punch33 = (Button)findViewById(R.id.punch33);
        punch34 = (Button)findViewById(R.id.punch34);
        punch35 = (Button)findViewById(R.id.punch35);
        punch36 = (Button)findViewById(R.id.punch36);

        topping1 = (Button)findViewById(R.id.topping1);
        topping2 = (Button)findViewById(R.id.topping2);
        topping3 = (Button)findViewById(R.id.topping3);
        topping4 = (Button)findViewById(R.id.topping4);
        topping5 = (Button)findViewById(R.id.topping5);
        topping6 = (Button)findViewById(R.id.topping6);
        topping7 = (Button)findViewById(R.id.topping7);
        topping8 = (Button)findViewById(R.id.topping8);
        topping9 = (Button)findViewById(R.id.topping9);
        topping10 = (Button)findViewById(R.id.topping10);
        topping11 = (Button)findViewById(R.id.topping11);
        topping12 = (Button)findViewById(R.id.topping12);
        topping13 = (Button)findViewById(R.id.topping13);
        topping14 = (Button)findViewById(R.id.topping14);
        topping15 = (Button)findViewById(R.id.topping15);
        topping16 = (Button)findViewById(R.id.topping16);
        topping17 = (Button)findViewById(R.id.topping17);
        topping18 = (Button)findViewById(R.id.topping18);


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


        txtNo1 = (TextView)findViewById(R.id.txt_no_1);
        txtNo2 = (TextView)findViewById(R.id.txt_no_2);
        txtNo3 = (TextView)findViewById(R.id.txt_no_3);
        txtNo4 = (TextView)findViewById(R.id.txt_no_4);
        txtNo5 = (TextView)findViewById(R.id.txt_no_5);
        txtNo6 = (TextView)findViewById(R.id.txt_no_6);
        txtNo7 = (TextView)findViewById(R.id.txt_no_7);

        txt1 = (TextView)findViewById(R.id.txt_1);
        txt2 = (TextView)findViewById(R.id.txt_2);
        txt3 = (TextView)findViewById(R.id.txt_3);
        txt4 = (TextView)findViewById(R.id.txt_4);
        txt5 = (TextView)findViewById(R.id.txt_5);
        txt6 = (TextView)findViewById(R.id.txt_6);
        txt7 = (TextView)findViewById(R.id.txt_7);
    }
}