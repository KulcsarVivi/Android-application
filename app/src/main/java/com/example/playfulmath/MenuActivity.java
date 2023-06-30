package com.example.playfulmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    Button logoutButton;
    FirebaseAuth mAuth;

    TextView helloUsername;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        logoutButton = findViewById(R.id.logoutButton);
        mAuth = FirebaseAuth.getInstance();

        //helloUsername = findViewById(R.id.helloTextView);           //hello
        //username = getIntent().getExtras().getString("Username");//hello
        //helloUsername.setText("Hello" + username);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MenuActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}