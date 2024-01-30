package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Welcome extends AppCompatActivity
{
    ImageView btnLogin;
    ImageView btnSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnLogin = findViewById(R.id.btnSignup);
        btnSignup = findViewById(R.id.btnConfirm);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Welcome.this, Login.class);
                startActivity(i);
                finish();
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Welcome.this, Signup.class);
                startActivity(i);
                finish();
            }
        });
    }
}