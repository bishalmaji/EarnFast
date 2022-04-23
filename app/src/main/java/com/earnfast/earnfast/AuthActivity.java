package com.earnfast.earnfast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class AuthActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_acitivity);
        EditText phoneno,name,refercode;
        phoneno=findViewById(R.id.phoneno);
        name=findViewById(R.id.name);
        refercode=findViewById(R.id.refercode);

        Button loginbtn=findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(phoneno.getText())){
                    phoneno.setError("Please Enter Your Phone Number");
                    return ;
                }
                if(TextUtils.isEmpty(name.getText())){
                    name.setError("Please Enter Your Name");
                    return;
                }if(phoneno.getText().toString().trim().length()!=10){
                    phoneno.setHint("10 Digits Required");
                    return;
                }
                Intent i=new Intent(AuthActivity.this,Otp.class);
                i.putExtra("phone","+91"+phoneno.getText().toString().trim());
                i.putExtra("name",name.getText().toString().trim());
                if (!TextUtils.isEmpty(refercode.getText())) {
                    i.putExtra("refer",refercode.getText().toString().trim());
                }else {
                    i.putExtra("refer","0000");

                }
                startActivity(i);


            }
        });



       }


}