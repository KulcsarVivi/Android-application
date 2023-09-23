package com.example.playfulmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

public class LoginActivity extends AppCompatActivity {
//TODO hibás felhasználónév jelszó kezelése, ha nem szerepel db-ben
    private TextView registerBtn;
    private EditText loginEmailEditText, loginPasswordEditText;
    private Button loginBtn;
    //Ez lehetne static is?
    private FirebaseAuth mAuth;
    private ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registerBtn = findViewById(R.id.registerTextView);
        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        loginBtn = findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();
        mLoadingBar = new ProgressDialog(LoginActivity.this);

        if(loginBtn != null) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkDatas();
                }
            });
        }

        if(registerBtn != null) {
            registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
    
    //TODO Try-catchal megcsinálni, progress bar
    private void checkDatas() {
        String email = loginEmailEditText.getText().toString();
        String password = loginPasswordEditText.getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            showError(loginEmailEditText, "Az e-mail cím érvénytelen.");
        }
        else if(password.isEmpty() || password.length() < 6) {
            showError(loginPasswordEditText, "A jelszónak legalább 6 karakterből kell állnia.");
        }
        else
        {
            mLoadingBar.setTitle("Bejelentkezés");
            mLoadingBar.setMessage("Kérlek várj!");
            mLoadingBar.setCanceledOnTouchOutside(false);
            mLoadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //TODO Itt kell majd visszaadni a felhasználó adatait
                        Toast.makeText(LoginActivity.this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();
                        Intent intent = new Intent(LoginActivity.this,MenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Helytelen felhasználónév vagy jelszó!", Toast.LENGTH_SHORT).show();
                        mLoadingBar.dismiss();
                    }
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}