package com.roshanshibu.total;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences countPreferences;
    private SharedPreferences.Editor countPrefsEditor;
    private Boolean savecount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText no2000 = (EditText) findViewById(R.id.count2000);
        final EditText no500 = (EditText) findViewById(R.id.count500);
        final EditText no200 = (EditText) findViewById(R.id.count200);
        final EditText no100 = (EditText) findViewById(R.id.count100);
        final EditText no50 = (EditText) findViewById(R.id.count50);
        final EditText no20 = (EditText) findViewById(R.id.count20);
        final EditText no10 = (EditText) findViewById(R.id.count10);
        final TextView screencap = (TextView) findViewById(R.id.screenshot_button);
        final RelativeLayout screencaplay = (RelativeLayout) findViewById(R.id.screenshot_layout);
        final TextView resetbut = (TextView) findViewById(R.id.reset_button);
        final RelativeLayout resetlay = (RelativeLayout) findViewById(R.id.reset_layout);
        final TextView screendate = (TextView) findViewById(R.id.date);
        final TextView GranntTotalLabel = (TextView) findViewById(R.id.gt_label);
        final TextView GrantTotalValue = (TextView) findViewById(R.id.gt);


        countPreferences = getSharedPreferences("countPrefs",MODE_PRIVATE);
        countPrefsEditor = countPreferences.edit();
        savecount = countPreferences.getBoolean("savecount",true);
        countPrefsEditor.apply();

        if(savecount == true){
            no2000.setText(countPreferences.getString("no2000",""));
            no500.setText(countPreferences.getString("no500",""));
            no200.setText(countPreferences.getString("no200",""));
            no100.setText(countPreferences.getString("no100",""));
            no50.setText(countPreferences.getString("no50",""));
            no20.setText(countPreferences.getString("no20",""));
            no10.setText(countPreferences.getString("no10",""));
            settotal();
        }




        no2000.addTextChangedListener(new TextWatcher() {@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                settotal();}
            @Override public void afterTextChanged(Editable editable) {}});

        no500.addTextChangedListener(new TextWatcher() {@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                settotal();}
            @Override public void afterTextChanged(Editable editable) {}});

        no200.addTextChangedListener(new TextWatcher() {@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                settotal();}
            @Override public void afterTextChanged(Editable editable) {}});

        no100.addTextChangedListener(new TextWatcher() {@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                settotal();}
            @Override public void afterTextChanged(Editable editable) {}});

        no50.addTextChangedListener(new TextWatcher() {@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                settotal();}
            @Override public void afterTextChanged(Editable editable) {}});

        no20.addTextChangedListener(new TextWatcher() {@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                settotal();}
            @Override public void afterTextChanged(Editable editable) {}});

        no10.addTextChangedListener(new TextWatcher() {@Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                settotal();}
            @Override public void afterTextChanged(Editable editable) {}});




        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
        final String formattedDate = df.format(c);
        screendate.setText(formattedDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String filename = sdf.format(c);
        System.out.println("Current time to seconds => " + filename);


        screencap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                screendate.setVisibility(View.VISIBLE);
                screencaplay.setVisibility(View.INVISIBLE);
                resetlay.setVisibility(View.INVISIBLE);
                View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                store(getScreenShot(rootView), filename+".png");
                Toast.makeText(MainActivity.this, "Screenshot saved!", Toast.LENGTH_SHORT).show();
                screendate.setVisibility(View.INVISIBLE);
                screencaplay.setVisibility(View.VISIBLE);
                resetlay.setVisibility(View.VISIBLE);
            }
        });

        resetbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                no2000.setText("");
                no500.setText("");
                no200.setText("");
                no100.setText("");
                no50.setText("");
                no20.setText("");
                no10.setText("");
                settotal();
            }
        });

        //labels and individual total for each denominaton
        //have to add this because we are programatically adding typeface, :(
        final TextView gt2000= (TextView) findViewById(R.id.gt2000);
        final TextView label2000 = (TextView) findViewById(R.id.label2000);
        final TextView gt500= (TextView) findViewById(R.id.gt500);
        final TextView label500 = (TextView) findViewById(R.id.label500);
        final TextView gt200= (TextView) findViewById(R.id.gt200);
        final TextView label200 = (TextView) findViewById(R.id.label200);
        final TextView gt100= (TextView) findViewById(R.id.gt100);
        final TextView label100 = (TextView) findViewById(R.id.label100);
        final TextView gt50= (TextView) findViewById(R.id.gt50);
        final TextView label50 = (TextView) findViewById(R.id.label50);
        final TextView gt20= (TextView) findViewById(R.id.gt20);
        final TextView label20 = (TextView) findViewById(R.id.label20);
        final TextView gt10= (TextView) findViewById(R.id.gt10);
        final TextView label10 = (TextView) findViewById(R.id.label10);



        //define the typeface
        final Typeface coolvetica =Typeface.createFromAsset(getAssets(),"fonts/coolvetica.ttf");
        //now set this everywhere
        screendate.setTypeface(coolvetica); GranntTotalLabel.setTypeface(coolvetica); GrantTotalValue.setTypeface(coolvetica);
        resetbut.setTypeface(coolvetica); screencap.setTypeface(coolvetica);
        no2000.setTypeface(coolvetica); no500.setTypeface(coolvetica); no200.setTypeface(coolvetica); no100.setTypeface(coolvetica); no50.setTypeface(coolvetica); no20.setTypeface(coolvetica); no10.setTypeface(coolvetica);
        gt2000.setTypeface(coolvetica); gt500.setTypeface(coolvetica); gt200.setTypeface(coolvetica); gt100.setTypeface(coolvetica); gt50.setTypeface(coolvetica); gt20.setTypeface(coolvetica); gt10.setTypeface(coolvetica);
        label2000.setTypeface(coolvetica); label500.setTypeface(coolvetica); label200.setTypeface(coolvetica); label100.setTypeface(coolvetica); label50.setTypeface(coolvetica); label20.setTypeface(coolvetica); label10.setTypeface(coolvetica);



        //make the navigation bar transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    public void settotal(){

        ////////////////////////////2000///////////////////////////
        EditText no2000 = (EditText) findViewById(R.id.count2000);
        TextView gt2000 = (TextView) findViewById(R.id.gt2000);

        int num2000 = 0;
        if(!no2000.getText().toString().isEmpty())
            num2000 = Integer.parseInt(no2000.getText().toString());
        else
            num2000 = 0;
        int total2000 = num2000*2000;
        gt2000.setText("= "+total2000+"");

        ////////////////////////////500///////////////////////////
        EditText no500 = (EditText) findViewById(R.id.count500);
        TextView gt500 = (TextView) findViewById(R.id.gt500);

        int num500 = 0;
        if(!no500.getText().toString().isEmpty())
            num500 = Integer.parseInt(no500.getText().toString());
        else
            num500 = 0;
        int total500 = num500*500;
        gt500.setText("= "+total500+"");

        ////////////////////////////200///////////////////////////
        EditText no200 = (EditText) findViewById(R.id.count200);
        TextView gt200 = (TextView) findViewById(R.id.gt200);

        int num200 = 0;
        if(!no200.getText().toString().isEmpty())
            num200 = Integer.parseInt(no200.getText().toString());
        else
            num200 = 0;
        int total200 = num200*200;
        gt200.setText("= "+total200+"");

        ////////////////////////////100///////////////////////////
        EditText no100 = (EditText) findViewById(R.id.count100);
        TextView gt100 = (TextView) findViewById(R.id.gt100);

        int num100 = 0;
        if(!no100.getText().toString().isEmpty())
            num100 = Integer.parseInt(no100.getText().toString());
        else
            num100 = 0;
        int total100 = num100*100;
        gt100.setText("= "+total100+"");

        ////////////////////////////50///////////////////////////
        EditText no50 = (EditText) findViewById(R.id.count50);
        TextView gt50 = (TextView) findViewById(R.id.gt50);

        int num50 = 0;
        if(!no50.getText().toString().isEmpty())
            num50 = Integer.parseInt(no50.getText().toString());
        else
            num50 = 0;
        int total50 = num50*50;
        gt50.setText("= "+total50+"");


        ////////////////////////////20///////////////////////////
        EditText no20 = (EditText) findViewById(R.id.count20);
        TextView gt20 = (TextView) findViewById(R.id.gt20);

        int num20 = 0;
        if(!no20.getText().toString().isEmpty())
            num20 = Integer.parseInt(no20.getText().toString());
        else
            num20 = 0;
        int total20 = num20*20;
        gt20.setText("= "+total20+"");

        ////////////////////////////10///////////////////////////
        EditText no10 = (EditText) findViewById(R.id.count10);
        TextView gt10 = (TextView) findViewById(R.id.gt10);

        int num10 = 0;
        if(!no10.getText().toString().isEmpty())
            num10 = Integer.parseInt(no10.getText().toString());
        else
            num10 = 0;
        int total10 = num10*10;
        gt10.setText("= "+total10+"");

        //////////////////////total////////////////////////////
        TextView gt = (TextView) findViewById(R.id.gt);
        int total = total2000 + total500 + total200 + total100 + total50 + total20 + total10;

        String output = "₹"+total+"";
        gt.setText(output);


        countPrefsEditor.putBoolean("savecount", true);
        countPrefsEditor.putString("no2000", no50.getText().toString());
        countPrefsEditor.putString("no500", no50.getText().toString());
        countPrefsEditor.putString("no200", no50.getText().toString());
        countPrefsEditor.putString("no100", no50.getText().toString());
        countPrefsEditor.putString("no50", no50.getText().toString());
        countPrefsEditor.putString("no20", no20.getText().toString());
        countPrefsEditor.putString("no10", no10.getText().toString());
        countPrefsEditor.commit();

    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }


    public static void store(Bitmap bm, String fileName){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Total";
        File dir = new File(dirPath);

        if(!dir.exists())
           dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
