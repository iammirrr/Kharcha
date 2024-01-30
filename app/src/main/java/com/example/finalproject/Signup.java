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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity
{

    ImageView btnBack;
    ImageView btnLogin;
    ImageView btnConfirm;




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
        setContentView(R.layout.activity_signup);


        btnBack = findViewById(R.id.btnBack);
        btnLogin = findViewById(R.id.btnSignup);
        btnConfirm = findViewById(R.id.btnConfirm);



        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Signup.this, Login.class);
                startActivity(i);
                finish();
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Signup.this, Welcome.class);
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
                TextInputEditText etConfirmPassword = findViewById(R.id.etConfirmPassword);

                String Email = etEmail.getText().toString().trim();
                String Password  = etPassword.getText().toString();
                String ConfirmPassword  = etConfirmPassword.getText().toString();

                if(TextUtils.isEmpty(Email) ) {
                    Toast.makeText(Signup.this, "Email Empty", Toast.LENGTH_SHORT).show();

                }
                else if (!isValidEmail(Email)) {
                    Toast.makeText(Signup.this, "Email Invalid", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Signup.this, "Password is empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ConfirmPassword)) {
                    Toast.makeText(Signup.this, "Confirm Password is empty", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(ConfirmPassword)) {
                    Toast.makeText(Signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else if (!(Password.length() >= 8)) {
                    Toast.makeText(Signup.this, "Passwords Must be 8 Characters Long", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                String WalletName = "Simple";
                                Double WalletAmount = 0.0;
                                HashMap<String,Object> data = new HashMap<>();
                                data.put("walletName",WalletName);
                                data.put("walletAmount", String.valueOf(WalletAmount));
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                                reference.child(firebaseUser.getUid())
                                        .child("Wallets").push().setValue(data);


                                data.clear();


                                String CategoryName = "Default";
                                data.put("categoryName",CategoryName);
                                 firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                 reference = FirebaseDatabase.getInstance().getReference("Users");
                                reference.child(firebaseUser.getUid())
                                        .child("Categories")
                                        .push().setValue(data);



                                Toast.makeText(Signup.this, "Your Account Is Successfully Created", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Signup.this, Login.class);
                                startActivity(i);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Signup.this, "We Encounter Problem", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });


    }
}