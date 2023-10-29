package com.example.playfulmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.playfulmath.model.GameModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private CardView gameNextCardView, gameExitCardView;
    private DatabaseReference dbRef;
    private ImageView gameNumber1ImageView, gameOperationImageView, gameNumber2ImageView,
            gameAnswer1ImageView, gameAnswer2ImageView, gameAnswer3ImageView, gameAnswer4ImageView, imageViewLoading;
    private List<GameModel> questionList;
    private int currentQuestionIndex = 0;
    private int questionsPerGame = 1;
    private int selectedAnswerPosition = -1;
    private int correctAnswerPosition = -1;
    private TextView gameTaskCounterTextView;

    private String selectedFruit;
    private String selectedDifficulty;
    private String selectedOperator;

    String question1ImageFileName;
    String question2ImageFileName;
    String option1ImageFileName;
    String option2ImageFileName;
    String option3ImageFileName;
    String option4ImageFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        gameNextCardView = (CardView) findViewById(R.id.gameNextCardView);
        gameExitCardView = (CardView) findViewById(R.id.gameExitCardView);

        gameNumber1ImageView = findViewById(R.id.gameNumber1ImageView);
        gameOperationImageView = findViewById(R.id.gameOperationImageView);
        gameNumber2ImageView = findViewById(R.id.gameNumber2ImageView);
        gameAnswer1ImageView = findViewById(R.id.gameAnswer1ImageView);
        gameAnswer2ImageView = findViewById(R.id.gameAnswer2ImageView);
        gameAnswer3ImageView = findViewById(R.id.gameAnswer3ImageView);
        gameAnswer4ImageView = findViewById(R.id.gameAnswer4ImageView);
        imageViewLoading = findViewById(R.id.imageViewLoading);

        selectedDifficulty = getIntent().getStringExtra("difficulty");
        selectedFruit = getIntent().getStringExtra("fruit");
        selectedOperator = getIntent().getStringExtra("operation");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("game");

        StorageReference baseReference = FirebaseStorage.getInstance().getReference("fruit/" + selectedFruit);

        databaseReference.child(selectedDifficulty).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageViewLoading.setVisibility(View.VISIBLE);

                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    String operator = questionSnapshot.child("operator").getValue(String.class);

                    if (selectedOperator.equals(operator)) {
                        int question1 = questionSnapshot.child("question1").getValue(Integer.class);
                        int question2 = questionSnapshot.child("question2").getValue(Integer.class);
                        int option1 = questionSnapshot.child("option1").getValue(Integer.class);
                        int option2 = questionSnapshot.child("option2").getValue(Integer.class);
                        int option3 = questionSnapshot.child("option3").getValue(Integer.class);
                        int option4 = questionSnapshot.child("option4").getValue(Integer.class);

                        GameModel gameModel = new GameModel();
                        gameModel.setQuestion1(question1);
                        gameModel.setOperator(operator);
                        gameModel.setQuestion2(question2);
                        gameModel.setOption1(option1);
                        gameModel.setOption2(option2);
                        gameModel.setOption3(option3);
                        gameModel.setOption4(option4);

                        question1ImageFileName = gameModel.getQuestion1() + ".jpg";
                        question2ImageFileName = gameModel.getQuestion2() + ".jpg";
                        option1ImageFileName = gameModel.getOption1() + ".jpg";
                        option2ImageFileName = gameModel.getOption2() + ".jpg";
                        option3ImageFileName = gameModel.getOption3() + ".jpg";
                        option4ImageFileName = gameModel.getOption4() + ".jpg";


                        StorageReference question1ImageReference = baseReference.child(question1ImageFileName);
                        manageImage(question1ImageReference, gameNumber1ImageView);

                        StorageReference question2ImageReference = baseReference.child(question2ImageFileName);
                        manageImage(question2ImageReference, gameNumber2ImageView);

                        StorageReference option1ImageReference = baseReference.child(option1ImageFileName);
                        manageImage(option1ImageReference, gameAnswer1ImageView);

                        StorageReference option2ImageReference = baseReference.child(option2ImageFileName);
                        manageImage(option2ImageReference, gameAnswer2ImageView);

                        StorageReference option3ImageReference = baseReference.child(option3ImageFileName);
                        manageImage(option3ImageReference, gameAnswer3ImageView);

                        StorageReference option4ImageReference = baseReference.child(option4ImageFileName);
                        manageImage(option4ImageReference, gameAnswer4ImageView);

                        if (selectedOperator.equals("+")){
                            String imagePath = "drawable/plus_icon";
                            Picasso.get().load(getResources().getIdentifier(imagePath, "drawable", getPackageName())).into(gameOperationImageView);
                        }
                        else{
                            String imagePath = "drawable/minus_icon";
                            Picasso.get().load(getResources().getIdentifier(imagePath, "drawable", getPackageName())).into(gameOperationImageView);
                        }

                    }
                }
                imageViewLoading.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // hibakezelés
            }
        });

        gameNextCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                startActivity(intent);
                finish(); //TODO Kövi kérdésre vinni
            }
        });

        gameExitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void manageImage(final StorageReference imageReference, final ImageView imageView) {
        imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String imageUrl = uri.toString();

                Picasso.get().load(imageUrl).into(imageView);
            }
        });
    }

    private void checkAnswer(int selectedAnswerPosition, int correctAnswerPosition) {
        if (selectedAnswerPosition == correctAnswerPosition) {
            //GameModel gameModel?
        } else {
            // :'(
        }
    }

    /*private void loadImageForGameModel(GameModel gameModel, String imageFileName, ImageView imageView) {
        StorageReference imageReference = FirebaseStorage.getInstance().getReference("fruit/" + selectedFruit + "/" + imageFileName);
        manageImage(imageReference, imageView);
    }*/
}
