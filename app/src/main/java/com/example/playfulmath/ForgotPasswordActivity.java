package com.example.playfulmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText forgotPasswordEmailEditText;
    TextView forgotPasswordLoginTextViewButton;
    Button forgotPasswordButton;
    FirebaseAuth mAuth;
    String strEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgotPasswordEmailEditText = findViewById(R.id.forgotPasswordEmailEditText);
        forgotPasswordLoginTextViewButton = findViewById(R.id.forgotPasswordLoginTextViewButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);

        mAuth = FirebaseAuth.getInstance();

        if(forgotPasswordLoginTextViewButton != null) {
            forgotPasswordLoginTextViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = forgotPasswordEmailEditText.getText().toString().trim();
                if(strEmail.isEmpty() || !strEmail.contains("@")){
                    showError(forgotPasswordEmailEditText, "Az e-mail cím érvénytelen.");
                }
                else {
                    resetPassword();
                }
            }
        });
    }

    private void resetPassword() {
        mAuth.sendPasswordResetEmail(strEmail)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ForgotPasswordActivity.this, "A megadott e-mail címre kiküldésre került a link.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("ForgotPasswordActivity", "Hiba történt a jelszó visszaállításához szükséges link kiküldésekor: " + e.getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, "Hiba történt a link kiküldésekor.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}