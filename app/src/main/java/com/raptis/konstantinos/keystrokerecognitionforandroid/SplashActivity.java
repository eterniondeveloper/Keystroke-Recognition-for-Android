package com.raptis.konstantinos.keystrokerecognitionforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.raptis.konstantinos.keystrokerecognitionforandroid.db.DBConnector;

/**
 * Created by konstantinos on 26/5/2016.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set full screen and display without title
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        Thread t = new Thread(new SplashScreen());
        t.start();
    }



    private class SplashScreen implements Runnable {

        //variables

        //constructor
        public SplashScreen() {

        }

        //run
        @Override
        public void run() {
            DBConnector connector = new DBConnector(getApplicationContext(), null, null, DBConnector.DATABASE_VERSION);
            boolean isUserTableEmpty = connector.isUserTableEmpty();
            try {
                Thread.sleep(1600);
            }
            catch(InterruptedException ie) {
                ie.printStackTrace();
            }
            finally {
                Intent i;
                if(!isUserTableEmpty) {
                    i = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    i = new Intent(SplashActivity.this, CreateUserActivity.class);
                }
                startActivity(i);
                finish();
            }
        }

    }

}
