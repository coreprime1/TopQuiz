package com.example.topquiz.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquiz.R;
import com.example.topquiz.model.Question;
import com.example.topquiz.model.QuestionBank;
import com.example.topquiz.model.User;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private final QuestionBank mQuestionBank = generateQuestionBank();
    private TextView txtQuestion;
    private Button btnReponse1;
    private Button btnReponse2;
    private Button btnReponse3;
    private Button btnReponse4;
    private int mGoodAnswer;
    private int mRemainingQuestionCount;

    private User mUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("USERNAME");
        mUser = new User();
        mUser.setFirstName(userName);

        txtQuestion = findViewById(R.id.game_activity_textview_question);
        txtQuestion.setText("Bonjour prêt pour le jeu?");
        btnReponse1 = findViewById(R.id.game_activity_button_1);
        btnReponse1.setText("Réponse 1");
        btnReponse1.setOnClickListener(this::onClick);
        btnReponse2 = findViewById(R.id.game_activity_button_2);
        btnReponse2.setText("Réponse 2");
        btnReponse2.setOnClickListener(this::onClick);
        btnReponse3 = findViewById(R.id.game_activity_button_3);
        btnReponse3.setText("Réponse 3");
        btnReponse3.setOnClickListener(this::onClick);
        btnReponse4 = findViewById(R.id.game_activity_button_4);
        btnReponse4.setText("Réponse 4");
        btnReponse4.setOnClickListener(this::onClick);

        mRemainingQuestionCount = 3;

        displayQuestion(mQuestionBank.getCurrentQuestion());

    }

    private QuestionBank generateQuestionBank(){
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );

        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );

        Question question3 = new Question(
                "What is the house number of The Simpsons?",
                Arrays.asList(
                        "42",
                        "101",
                        "666",
                        "742"
                ),
                3
        );

        return new QuestionBank(Arrays.asList(question1, question2, question3));
    }

    private void displayQuestion(final Question question) {
        txtQuestion.setText(question.getQuestion());
        btnReponse1.setText(question.getChoiceList().get(0));
        btnReponse2.setText(question.getChoiceList().get(1));
        btnReponse3.setText(question.getChoiceList().get(2));
        btnReponse4.setText(question.getChoiceList().get(3));
        mGoodAnswer = question.getAnswerIndex();
    }

    @Override
    public void onClick(View v) {
        int selectedAnswer = 0;
        switch (v.getId()){
            case R.id.game_activity_button_1 :
                selectedAnswer = 0;
                break;
            case R.id.game_activity_button_2:
                selectedAnswer = 1;
                break;
            case R.id.game_activity_button_3 :
                selectedAnswer = 2;
                break;
            case R.id.game_activity_button_4 :
                selectedAnswer = 3;
                break;
        }

        if (mGoodAnswer == selectedAnswer){
            //success
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            mUser.scoreIncrease();
        } else {
            //erreur
            Toast.makeText(this, "Wrong answer!", Toast.LENGTH_SHORT).show();
            //mUser.scoreDecrease();
        }
        //mise en place de la nouvelle question
        mRemainingQuestionCount--;

        if (mRemainingQuestionCount > 0) {
            displayQuestion(mQuestionBank.getNextQuestion());
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Well done "+mUser.getFirstName()+"!")
                    .setMessage("Your score is " + mUser.getScore())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra(BUNDLE_EXTRA_SCORE, mUser.getScore());
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .create()
                    .show();
        }

    }
}