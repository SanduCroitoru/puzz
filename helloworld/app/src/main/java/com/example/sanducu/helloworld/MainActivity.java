package com.example.sanducu.helloworld;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.camera2.params.BlackLevelPattern;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.view.ViewGroup.LayoutParams;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    public ArrayList<Button> btnList = new ArrayList<>();

    int level=1;
    int Contor_Mutari=300-level*10;
    public static final String PREFS_NAME = "Puzzle_Settings";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        getBtns();
        setButtonValue();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int saved_level = settings.getInt("saved_level", 1);
        level=saved_level;
        int saved_contor_mutari = settings.getInt("saved_contor_mutari", (300-level*10));
        Contor_Mutari=saved_contor_mutari;
        setLevel();
        setContor();
        getDisplaySize();
        ActionBar myActionBar = getSupportActionBar();

        //For hiding android actionbar
        //myActionBar.setLogo(R.mipmap.btcube);
      //  myActionBar.setIcon(R.mipmap.btcube);
        myActionBar.hide();







    }
    public void getBtns(){

        Button b1 =(Button) findViewById(R.id.button1);
        Button b2 =(Button) findViewById(R.id.button2);
        Button b3 =(Button) findViewById(R.id.button3);
        Button b4 =(Button) findViewById(R.id.button4);
        Button b5 =(Button) findViewById(R.id.button5);
        Button b6 =(Button) findViewById(R.id.button6);
        Button b7 =(Button) findViewById(R.id.button7);
        Button b8 =(Button) findViewById(R.id.button8);
        Button b9 =(Button) findViewById(R.id.button9);

        btnList.add(0,b1);
        btnList.add(1,b2);
        btnList.add(2,b3);
        btnList.add(3,b4);
        btnList.add(4,b5);
        btnList.add(5,b6);
        btnList.add(6,b7);
        btnList.add(7,b8);
        btnList.add(8,b9);


    }
    public void setButtonValue(){

        int[] intvalues=new int[9];
        for(int i=0;i<8;i++){
            intvalues[i]=i+1;
        }
        Random rgen = new Random();  // Random number generator

        for (int i=0; i<intvalues.length; i++) {
            int randomPosition = rgen.nextInt(intvalues.length);
            int temp = intvalues[i];
            intvalues[i] = intvalues[randomPosition];
            intvalues[randomPosition] = temp;
        }
        for(int i=0; i<9;i++){
            btnList.get(i).setText(String.valueOf(intvalues[i]));
            if(intvalues[i]==0){
                btnList.get(i).setText("");
            }
        }

        }

    public void getDisplaySize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        for(int i=0; i<9;i++){
            LayoutParams params = btnList.get(i).getLayoutParams();
            params.width=Math.round(width/3);
            params.height= (int) Math.round(56.2*height/300);
            btnList.get(i).setLayoutParams(params);


        }


    }
    public void onClickButton(View v) {

        int pressedBT=findPressedButonInList(v);

        String textBefore= ((Button)v).getText().toString();




        switch (pressedBT){
            case 0:
                moveRight(pressedBT);
                moveDown(pressedBT);
                break;
            case 1:
                moveLeft(pressedBT);
                moveRight(pressedBT);
                moveDown(pressedBT);
                break;
            case 2:
                moveLeft(pressedBT);
                moveDown(pressedBT);
                break;
            case 3:
                moveDown(pressedBT);
                moveUp(pressedBT);
                moveRight(pressedBT);
                break;
            case 4:
                moveUp(pressedBT);
                moveDown(pressedBT);
                moveRight(pressedBT);
                moveLeft(pressedBT);
                break;
            case 5:
                moveUp(pressedBT);
                moveDown(pressedBT);
                moveLeft(pressedBT);
                break;
            case 6:
                moveUp(pressedBT);
                moveRight(pressedBT);
                break;
            case 7:
                moveUp(pressedBT);
                moveRight(pressedBT);
                moveLeft(pressedBT);
                break;
            case 8:
                moveUp(pressedBT);
                moveLeft(pressedBT);
                break;
            default:break;



        }

        String textAfter=((Button)v).getText().toString();
        if(textBefore!="" && textBefore!=textAfter) {
            Contor_Mutari--;
            if(Contor_Mutari<1){
                gameOver();

            }
        }
            TextView textView = (TextView) findViewById(R.id.scor);
            textView.setText(String.valueOf(Contor_Mutari));
            int win = findIfIWin();
            if (win == 8) {
               winner();
            }




    }
    public void onclickReset(View v){
        getBtns();
        setButtonValue();
        setLevel();
        setContor();

    }
    public void setLevel(){
        TextView textView = (TextView) findViewById(R.id.level);
        textView.setText(String.valueOf(level));
    }
    public void setContor(){
        Contor_Mutari=300-level*10;
        TextView textView = (TextView) findViewById(R.id.scor);
        textView.setText(String.valueOf(Contor_Mutari));
    }

    public int findPressedButonInList(View v){
        Button bt=(Button)v;
        int val=0;
        for(int i=0;i<9;i++){
            if(btnList.get(i).getId()==bt.getId())
                val=i;


        }

        return val;


    }
    public void moveLeft(int val){
        
        if(btnList.get(val-1).getText()=="")
        {
            btnList.get(val-1).setText(btnList.get(val).getText());
            btnList.get(val).setText("");
        }

    }
    public void moveRight(int val){
        if(btnList.get(val+1).getText()=="")
        {
            btnList.get(val+1).setText(btnList.get(val).getText());
            btnList.get(val).setText("");
        }

    }
    public void moveUp(int val){
        if(btnList.get(val-3).getText()=="")
        {
            btnList.get(val-3).setText(btnList.get(val).getText());
            btnList.get(val).setText("");
        }

    }
    public void moveDown(int val){
        if(btnList.get(val+3).getText()=="")
        {
            btnList.get(val+3).setText(btnList.get(val).getText());
            btnList.get(val).setText("");
        }

    }
    public int findIfIWin(){

        int contor=0;
        for(int i=0; i<8; i++){

            if(btnList.get(i).getText().toString().equals(String.valueOf(i+1)))
            {
                contor++;
            }
            else {
                contor--;
            }
        }
        return contor;

    }

    public void winner(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        TextView gmwin = new TextView(this);

        gmwin.setBackgroundResource(R.mipmap.youwin);
        gmwin.setWidth(width/2);
        gmwin.setHeight(height/2);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setView(gmwin)
                .setCancelable(false)
                .setNeutralButton("       Replay       "
                ,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        getBtns();
                        setButtonValue();
                        setLevel();
                        setContor();
                        dialog.cancel();
                    }
                })
                .setPositiveButton("  Next Level!  "
                        ,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                level++;
                                getBtns();
                                setButtonValue();
                                setLevel();
                                setContor();
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it

        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#5D52CE"));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setAlpha(1.0f);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);

        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#5D52CE"));
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setAlpha(1.0f);
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.BLACK);



    }
    public void gameOver(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        TextView gmover = new TextView(this);

       gmover.setBackgroundResource(R.mipmap.gameover);
        gmover.setHeight(height/2);
        gmover.setWidth(width/2);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setCancelable(false)
                .setView(gmover)
                .setNeutralButton("     Exit      "
                ,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                        System.exit(0);
                    }
                })
                .setPositiveButton("    Replay!   "
                        ,new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // if this button is clicked, close
                                // current activity
                                getBtns();
                                setButtonValue();
                                setLevel();
                                setContor();
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it

        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#5D52CE"));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setAlpha(1.0f);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);

        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setBackgroundColor(Color.parseColor("#5D52CE"));
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setAlpha(1.0f);
        alertDialog.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(Color.BLACK);



    }




    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());


    }

    @Override
    public void onStop() {
        super.onStop();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
          // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putInt("saved_level", level);
        editor.putInt("saved_contor_mutari", Contor_Mutari);


        // Commit the edits!
        editor.commit();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


}
