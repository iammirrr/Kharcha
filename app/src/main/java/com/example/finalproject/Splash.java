package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                if(FirebaseAuth.getInstance().getCurrentUser() != null)
                {
                    Toast.makeText(Splash.this, "Your Account Is Already Login", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Splash.this, Main.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(Splash.this, Welcome.class);
                    startActivity(i);
                    finish();
                }


            }
        },2000);

    }



}