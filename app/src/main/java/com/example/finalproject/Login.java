package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity
{

    ImageView btnBack;
    ImageView btnSignup;
    ImageView btnConfirm;

    FirebaseAuth authUser;



    public boolean isValidEmail(String email)
    {
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnBack = findViewById(R.id.btnBack);
        btnSignup = findViewById(R.id.btnSignup);
        btnConfirm = findViewById(R.id.btnConfirm);



        btnSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);
                finish();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Login.this, Welcome.class);
                startActivity(i);
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                TextInputEditText etEmail = findViewById(R.id.etEmail);
                TextInputEditText etPassword = findViewById(R.id.etPassword);

                String Email = etEmail.getText().toString().trim();
                String Password  = etPassword.getText().toString();

                if(TextUtils.isEmpty(Email) )
                {
                    Toast.makeText(Login.this, "Email Empty", Toast.LENGTH_SHORT).show();
                    etEmail.setError("Email is Required");
                    etEmail.requestFocus();

                }
                else if (!isValidEmail(Email)) {
                    Toast.makeText(Login.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                    etPassword.setError("Email Must Be Valid");
                    etPassword.requestFocus();
                }
                else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Login.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    etPassword.setError("Password is Required");
                    etPassword.requestFocus();
                }
                else {

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(Email,Password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Login.this, "Your Account Is Successfully Login", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this, Main.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Login.this, "We Encounter Problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
