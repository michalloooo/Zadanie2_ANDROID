package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PromptActivity extends AppCompatActivity {


    //private boolean correctAnswer;
    private int currentIndex;
    private Button showCorrectAnswerButton;
    private Button backButton;
    private TextView answerTextView;
    public static final String KEY_EXTRA_ANSWER_SHOWN = "answerShown";

    private void setAnswerShownResult(boolean answerWasShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prompt);
        //correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        currentIndex = getIntent().getIntExtra(MainActivity.KEY_EXTRA_ANSWER, 0);
        showCorrectAnswerButton = findViewById(R.id.show_correct_answer_button);
        answerTextView = findViewById(R.id.prompt_text_answer);
        backButton = findViewById(R.id.back_prompt_button);

        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                int answer = 0;
                if(currentIndex == 0) answer = R.string.p_definicjeaktywnosci;
                if(currentIndex == 1) answer = R.string.p_strings;
                if(currentIndex == 2) answer = R.string.p_intent;
                if(currentIndex == 3) answer = R.string.p_plikr;
                if(currentIndex == 4) answer = R.string.p_wzorzecmvc;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}