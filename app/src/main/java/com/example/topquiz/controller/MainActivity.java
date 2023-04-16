package com.example.topquiz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquiz.R;
import com.example.topquiz.model.User;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;

    private TextView mTvGreating;
    //bonjour
    private String testVariable;
    private EditText mEdtNom;
    private Button mBtnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvGreating = findViewById(R.id.mai_tv_greeting);
        mEdtNom = findViewById(R.id.main_edt_name);
        mBtnPlay = findViewById(R.id.main_btn_play);

        mBtnPlay.setEnabled(false);

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                gameActivityIntent.putExtra("USERNAME", mEdtNom.getText().toString());
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
            }
        });


        mEdtNom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // This is where we'll check the user input
                mBtnPlay.setEnabled(!s.toString().isEmpty());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode) {
            // Fetch the score from the Intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE, 0);
        }

    }
}