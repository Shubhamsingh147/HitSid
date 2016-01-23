package com.example.shubham.kisssid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView time,score,comment,highest;
    ImageView img,ntbd;
    Button re,next;
    int selection = 2;
    int i = 0;
    int count = 0;
    SharedPreferences sharedpreferences;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = (TextView) findViewById(R.id.time);
        highest = (TextView) findViewById(R.id.highest);
        score = (TextView) findViewById(R.id.score);
        comment = (TextView) findViewById(R.id.comment);
        re = (Button) findViewById(R.id.button);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selection % 4 == 0) {
                    count++;
                    score.setText("Score: " + count);
                    Random rnd = new Random();
                    selection = (rnd.nextInt(8) + 1);
                    final String str = "sid" + selection;
                    img.setImageDrawable(getResources().getDrawable(getResourceID(str, "drawable",
                            getApplicationContext())));
                }
                else{
                    updateHighest();
                    timer.cancel();
                    time.setText("done!");
                    ntbd.setVisibility(View.VISIBLE);
                    img.setVisibility(View.GONE);
                    re.setVisibility(View.VISIBLE);
                    next.setVisibility(View.GONE);
                    comment.setText("You should have kissed him, not next to find me!!! Score: " + count);
                    comment.setTextColor(Color.parseColor("#E74C3C"));
                    i = 0;
                    count = 0;
                }
            }
        });
        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(newIntent);
                finish();
                i = 0;
                count = 0;
            }
        });
        img = (ImageView) findViewById(R.id.imageView);
        ntbd = (ImageView) findViewById(R.id.imageView2);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    timer = new CountDownTimer(30000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            time.setText(millisUntilFinished / 1000 + " secs");

                        }
                        public void onFinish() {
                            updateHighest();
                            time.setText("done!");
                            ntbd.setVisibility(View.VISIBLE);
                            img.setVisibility(View.GONE);
                            re.setVisibility(View.VISIBLE);
                            next.setVisibility(View.GONE);
                        }
                    }.start();
                    i = 1;
                } else {
                    if (selection % 4 != 0) {

                        count++;
                        score.setText("Score: " + count);
                        Random rnd = new Random();
                        selection = (rnd.nextInt(8) + 1);
                        final String str = "sid" + selection;
                        img.setImageDrawable(getResources().getDrawable(getResourceID(str, "drawable",
                                getApplicationContext())));
                    }
                    else
                    {
                        updateHighest();
                        timer.cancel();
                        time.setText("done!");
                        img.setVisibility(View.GONE);
                        ntbd.setVisibility(View.VISIBLE);
                        re.setVisibility(View.VISIBLE);
                        next.setVisibility(View.GONE);
                        comment.setText("You kissed me, and you lost, your score is: " + count);
                        comment.setTextColor(Color.parseColor("#E74C3C"));
                        i = 0;
                        count = 0;
                    }
                }
            }
        });
        sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        updateHighest();
    }
    protected final static int getResourceID(final String resName, final String resType, final Context ctx) {
        final int ResourceID =
                ctx.getResources().getIdentifier(resName, resType,
                        ctx.getApplicationInfo().packageName);
        if (ResourceID == 0)
        {
            throw new IllegalArgumentException
                    (
                            "No resource string found with name " + resName
                    );
        }
        else
        {
            return ResourceID;
        }
    }

    protected void updateHighest(){
        int old = sharedpreferences.getInt("score", 0);
        highest.setText("Max:"+old);
        if(count > old) {
            highest.setText("Highest!!Challenge Friends");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("score", count);
            editor.commit();
        }
    }
}
