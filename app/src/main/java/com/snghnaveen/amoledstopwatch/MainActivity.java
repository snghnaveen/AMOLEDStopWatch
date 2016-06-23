package com.snghnaveen.amoledstopwatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    ImageButton start,stop,reset,help,option,bright,exit,share;

    private int seconds=0;
    boolean flag =false;
    int key,selection;
    private boolean running;
    TextView time_view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // request for fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // code to resume stopwatch in case screen orientation is changed
        if(savedInstanceState!=null)
        {
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
        }
        // call chkstate() to check setting if exist.
        this.chkstate();

        // Set setting or welcome screen if app launched for forst time
        switch (key)
        {
            case 0:        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



                break;
            case 1:        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



                break;
            default:
                //app eunning for first time or display time out is not set by user
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("namaste")
                        .setMessage("Welcome to AMOLED Stopwatch.\nPlease choose your screen time out preference")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bright.performClick();
                            }

                        }).setNegativeButton("Next Time", null).setIcon(R.drawable.ic_info_outline_white).show();



        }
        // Setting custom font
        Typeface typeface = Typeface.createFromAsset(getAssets(),
                "digital_italic.ttf");
        ((TextView) findViewById(R.id.time_view)).setTypeface(typeface);

        time_view=(TextView) findViewById(R.id.time_view);
        start= (ImageButton) findViewById(R.id.start);
        stop= (ImageButton) findViewById(R.id.stop);
        reset= (ImageButton) findViewById(R.id.reset);
        help= (ImageButton) findViewById(R.id.help);
        option= (ImageButton) findViewById(R.id.options);
        exit= (ImageButton) findViewById(R.id.other);
        bright= (ImageButton) findViewById(R.id.bright);
        share= (ImageButton) findViewById(R.id.share);



        exit.setVisibility(View.INVISIBLE);
        help.setVisibility(View.INVISIBLE);
        bright.setVisibility(View.INVISIBLE);
        share.setVisibility(View.INVISIBLE);


        //setting the screen setting on clicking bright ImageButton
        bright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chkstate();


                final CharSequence[] items = {"System Setting", "Always ON"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Screen time out");

                builder.setSingleChoiceItems(items, key, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if(items[item]=="System Setting") {
                            selection=0;

                        }

                        if(items[item]=="Always ON") {
                            selection=1;


                        }

                    }
                });

                builder.setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (selection==0)
                                    fun1();
                                if (selection==1)
                                    fun2();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        // Long Pressing ImageView to show their property

        time_view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Timer Display",Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        option.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Options Hide/Show",Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        start.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Start timer",Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        reset.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Reset timer",Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        help.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Info",Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        bright.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Screen Timeout",Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        share.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Share Time",Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        exit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Toast.makeText(getApplicationContext(),"Exit",Toast.LENGTH_SHORT).show();

                return true;
            }
        });


        //Hide/Shwoing controls

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                if (flag==false)
                {
                    help.setVisibility(View.VISIBLE);
                    exit.setVisibility(View.VISIBLE);
                    bright.setVisibility(View.VISIBLE);
                    share.setVisibility(View.VISIBLE);

                    option.setImageResource(R.drawable.ic_blur_on_white);

                    flag=true;

                }
                else
                {
                    option.setImageResource(R.drawable.ic_blur_off_white);
                    help.setVisibility(View.INVISIBLE);
                    exit.setVisibility(View.INVISIBLE);
                    bright.setVisibility(View.INVISIBLE);
                    share.setVisibility(View.INVISIBLE);

                    flag=false;

                }

                switch (key)
                {
                    case 0:       bright.setImageResource(R.drawable.ic_brightness_non_white);


                        break;
                    case 1:        bright.setImageResource(R.drawable.ic_brightness_full_white);


                        break;
                    default:


                }
            }
        });
        //Exit button
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exit Application")
                        .setMessage("Are you sure you want to close AMOLED Stopwatch?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        // Sharing the time on time_view
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mString=("My time on AMOLED Stopwatch : "+time_view.getText().toString());
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, mString);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
        });

        //Code for extra
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(":-)")
                        .setMessage("More apps coming soon!!!")
                        .setPositiveButton("Yeah", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setIcon(R.drawable.ic_info_outline_white).show();
            }
        });


        runTimer();
    }


    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putInt("seconds",seconds);
        savedInstanceState.putBoolean("running",running);
    }

    public void onClickStart(View view)
    {
        running=true;

    }

    public void onClickStop(View view)

    {

        running=false;
    }

    public void onClickReset(View view)

    {
        running=false;
        seconds=0;
    }

    private void runTimer()
    {final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int sec=seconds%60;


                String time=String.format("%02d:%02d:%02d",hours,minutes,sec);

                time_view.setText(time);

                if(running)
                {
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });


    }
//Function for checking preferences if exist else providing default value

    private void chkstate()
    {

        SharedPreferences sharedPref= getSharedPreferences("mypref", 0);
        key = sharedPref.getInt("test1",3);
    }
    // setting display timeout accrording to system setting
    //set preference for checking setting for next run
    private void fun1()
    {    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        bright.setImageResource(R.drawable.ic_brightness_non_white);
        SharedPreferences sharedPref= getSharedPreferences("mypref",0);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putInt("test1",0);
        editor.commit();

    }
    // setting display timeout to always on
    //set preference for checking setting for next un
    private void fun2()
    {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        bright.setImageResource(R.drawable.ic_brightness_full_white);
        SharedPreferences sharedPref= getSharedPreferences("mypref",0);
        SharedPreferences.Editor editor= sharedPref.edit();
        editor.putInt("test1",1);
        editor.commit();

    }

}
