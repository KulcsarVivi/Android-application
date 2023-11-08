package com.example.playfulmath;

import static com.example.playfulmath.AnimationHelper.animStartFromX;
import static com.example.playfulmath.AnimationHelper.animStartFromY;
import static com.example.playfulmath.AnimationHelper.zoomAnim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playfulmath.model.GameModel;
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
    private ImageView gameNumber1ImageView, gameOperationImageView, gameNumber2ImageView,
            gameAnswer1ImageView, gameAnswer2ImageView, gameAnswer3ImageView, gameAnswer4ImageView,
            gameGoodImageView, gameBadImageView;

    private TextView gameTaskCounterTextView;
    private int questionNumber = 0;

    private int currentGameCorrectAnswer = 0;
    private int currentGameIncorrectAnswer = 0;

    private String selectedFruit;
    private String selectedDifficulty;
    private String selectedOperation;

    private List<GameModel> questionList;
    private int currentQuestionIndex = -1;
    private int questionsPerGame = 1;
    private int selectedAnswerPosition = -1;
    private int correctAnswerPosition = -1;

    String question1ImageFileName, question2ImageFileName, option1ImageFileName, option2ImageFileName, option3ImageFileName, option4ImageFileName;
    StorageReference baseReference;
    private int ANSWER_OPTIONS = 4;
    boolean[] selectedAnswers = new boolean[ANSWER_OPTIONS];

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

        gameGoodImageView = findViewById(R.id.gameGoodImageView);
        gameBadImageView = findViewById(R.id.gameBadImageView);

        selectedDifficulty = getIntent().getStringExtra("difficulty");
        selectedFruit = getIntent().getStringExtra("fruit");
        selectedOperation = getIntent().getStringExtra("operation");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("game");

        baseReference = FirebaseStorage.getInstance().getReference("fruit/" + selectedFruit);

        databaseReference.child(selectedDifficulty).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                questionList = new ArrayList<>();
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    String operator = questionSnapshot.child("operator").getValue(String.class);

                    if (selectedOperation.equals(operator)) {
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

                        questionList.add(gameModel);

                        if(selectedOperation.equals("+")){
                            String imagePath = "drawable/plus_icon";
                            Picasso.get().load(getResources().getIdentifier(imagePath, "drawable", getPackageName())).into(gameOperationImageView);
                        }
                        else{
                            String imagePath = "drawable/minus_icon";
                            Picasso.get().load(getResources().getIdentifier(imagePath, "drawable", getPackageName())).into(gameOperationImageView);
                        }

                    }
                }
                showNextQuestion();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("GameActivity", "Hiba történt az adatok lekérése során: " + databaseError.getMessage());
                Toast.makeText(GameActivity.this, "Hiba történt az adatok lekérése során: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        gameNextCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNextQuestion();
            }
        });

        gameExitCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleGameEnd();
            }
        });

        gameAnswer1ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(gameAnswer1ImageView, 0);
            }
        });

        gameAnswer2ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(gameAnswer2ImageView, 1);
            }
        });

        gameAnswer3ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(gameAnswer3ImageView, 2);
            }
        });
        gameAnswer4ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(gameAnswer4ImageView, 3);
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



    private void showNextQuestion() {
        if (currentQuestionIndex < questionList.size() - 1) {
            currentQuestionIndex++;
            questionNumber++;
            resetAnswerState();
            GameModel nextQuestion = questionList.get(currentQuestionIndex);
            enableUnanswered();
            loadQuestionImages(nextQuestion);
            updateQuestionNumber();

            animStartFromY(gameNumber1ImageView, -gameNumber1ImageView.getHeight());
            animStartFromY(gameNumber2ImageView, -gameNumber2ImageView.getHeight());
            zoomAnim(gameOperationImageView);
            animStartFromX(gameAnswer1ImageView, -gameAnswer1ImageView.getWidth());
            animStartFromX(gameAnswer2ImageView, gameAnswer2ImageView.getWidth());
            animStartFromX(gameAnswer3ImageView, -gameAnswer3ImageView.getWidth());
            animStartFromX(gameAnswer4ImageView, gameAnswer4ImageView.getWidth());

            /*animateImage(gameNumber1ImageView);
            animateImage(gameOperationImageView);
            animateImage(gameNumber2ImageView);
            animateImage(gameAnswer1ImageView);
            animateImage(gameAnswer2ImageView);
            animateImage(gameAnswer3ImageView);
            animateImage(gameAnswer4ImageView);*/
        } else {
            handleGameEnd();
        }
    }

    private void loadQuestionImages(GameModel gameModel) {
        question1ImageFileName = gameModel.getQuestion1() + ".jpg";
        question2ImageFileName = gameModel.getQuestion2() + ".jpg";
        option1ImageFileName = gameModel.getOption1() + ".jpg";
        option2ImageFileName = gameModel.getOption2() + ".jpg";
        option3ImageFileName = gameModel.getOption3() + ".jpg";
        option4ImageFileName = gameModel.getOption4() + ".jpg";

        int correctAnswer = gameModel.getCorrectAnswer();
        correctAnswerPosition = findCorrectAnswerPosition(correctAnswer);

        manageImage(baseReference.child(question1ImageFileName), gameNumber1ImageView);
        manageImage(baseReference.child(question2ImageFileName), gameNumber2ImageView);
        manageImage(baseReference.child(option1ImageFileName), gameAnswer1ImageView);
        manageImage(baseReference.child(option2ImageFileName), gameAnswer2ImageView);
        manageImage(baseReference.child(option3ImageFileName), gameAnswer3ImageView);
        manageImage(baseReference.child(option4ImageFileName), gameAnswer4ImageView);
    }

    private int findCorrectAnswerPosition(int correctAnswer) {
        for (int i = 0; i < ANSWER_OPTIONS; i++) {
            if (questionList.get(currentQuestionIndex).getOptions().get(i) == correctAnswer) {
                return i;
            }
        }
        return -1;
    }

    private void handleGameEnd() {
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);

        intent.putExtra("currentGameCorrectAnswer", currentGameCorrectAnswer);
        intent.putExtra("currentGameIncorrectAnswer", currentGameIncorrectAnswer);

        intent.putExtra("difficulty", selectedDifficulty);
        intent.putExtra("fruit", selectedFruit);
        intent.putExtra("operation", selectedOperation);

        startActivity(intent);
        finish();
    }

    private void checkAnswer(ImageView selectedAnswerImageView, int selectedAnswerPosition) {
        //a válasz már kiválasztott?
        if (selectedAnswers[selectedAnswerPosition]) {
            return; //ha igen nem kell mégegyszer foglalkozni vele
        }

        //ne lehessen újra kiválasztani választ
        disableAllAnswers();

        selectedAnswers[selectedAnswerPosition] = true;
        if (selectedAnswerPosition == correctAnswerPosition) {
            choseCorrectAnswer(selectedAnswerImageView);
        } else {
            choseIncorrectAnswer(selectedAnswerImageView);
        }
    }


    private ImageView getAnswerImageViewIndex(int position) {
        switch (position) {
            case 0:
                return gameAnswer1ImageView;
            case 1:
                return gameAnswer2ImageView;
            case 2:
                return gameAnswer3ImageView;
            case 3:
                return gameAnswer4ImageView;
            default:
                return null;
        }
    }
    private void disableAllAnswers() {
        for (int i = 0; i < ANSWER_OPTIONS; i++) {

            ImageView answerImageView = getAnswerImageViewIndex(i);
            answerImageView.setEnabled(false);//csak egy válasz jelölhető, így a többit majd le kell ezzel tiltani
        }
    }
    private void resetAnswerState() {
        for (int i = 0; i < ANSWER_OPTIONS; i++) {
            selectedAnswers[i] = false; //állapot törlés
            ImageView answerImageView = getAnswerImageViewIndex(i);

            answerImageView.setBackgroundResource(R.drawable.input_bg);
        }
    }

    private void enableUnanswered() {
        for (int i = 0; i < ANSWER_OPTIONS; i++) {
            if (!selectedAnswers[i]) {
                ImageView answerImageView = getAnswerImageViewIndex(i);
                answerImageView.setEnabled(true);
            }
        }
    }

    private void choseCorrectAnswer(ImageView selectedAnswerImageView) {
        selectedAnswerImageView.setBackgroundResource(R.drawable.easy_bg);
        currentGameCorrectAnswer++;

        showPopupDialog( R.drawable.emoji_good_icon, 2000);
    }

    private void choseIncorrectAnswer(ImageView selectedAnswerImageView) {
        selectedAnswerImageView.setBackgroundResource(R.drawable.hard_bg);
        currentGameIncorrectAnswer++;

        //Helyes válasz megmutatása
        int correctAnswerImageViewPosition = correctAnswerPosition;
        ImageView correctAnswerImageView = getAnswerImageViewIndex(correctAnswerImageViewPosition);
        correctAnswerImageView.setBackgroundResource(R.drawable.easy_bg);

        showPopupDialog(R.drawable.emoji_bad_icon, 2000);
    }

    public void showPopupDialog(int image, long duration) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        if (image != 0) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(image);
            alertDialogBuilder.setView(imageView);

            // Felnagyítási animáció
            Animation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(500); // Animáció időtartama (ms)
            imageView.startAnimation(scaleAnimation);
        }

        final AlertDialog dialog = alertDialogBuilder.create();
        dialog.show();

        if (duration > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, duration);
        }

        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.TOP;
        layoutParams.y = 200; // felső margó
        dialog.getWindow().setAttributes(layoutParams);
    }

    private void updateQuestionNumber() {
        gameTaskCounterTextView = findViewById(R.id.gameTaskCounterTextView);
        gameTaskCounterTextView.setText("5/" + questionNumber);
    }

}
