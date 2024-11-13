package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private int goodAnswers = 0;
    private boolean answerWasShown;

    private static final int REQUEST_CODE_PROMPT = 0;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_CURRENT_AMOUNT = "currentAmount";
    public  static final String KEY_EXTRA_ANSWER = "extraAnswer";


    private Question[] questions = new Question[]{
            new Question(R.string.q_definicjeaktywnosci, true),
            new Question(R.string.q_strings, false),
            new Question(R.string.q_intent, true),
            new Question(R.string.q_plikr, false),
            new Question(R.string.q_wzorzecmvc, true)
    };
    private int currentIndex = 0;

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();

        int resultMessageId = 0;
//zakomentowany fragment odpowiada za wyswietlenie informacji ze odkryto juz odpowiedz
//        if(answerWasShown) {
//            resultMessageId = R.string.answer_was_shown;
//        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                goodAnswers++;
            } else {
                resultMessageId = R.string.wrong_answer;
            }
//        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    private void showResult() {
        String resultMessage = getString(R.string.dobre_odpowiedzi) + goodAnswers;
        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("TAG", "metodaOnSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putInt(KEY_CURRENT_AMOUNT, goodAnswers);
    }

    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "metodaOnStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "metodaOnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "metodaOnDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "metodaOnPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "metodaOnResume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){return;}
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data == null) {return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
            Toast.makeText(this, R.string.prompt_was_used, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TAG", "metodaOnCreate");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            goodAnswers = savedInstanceState.getInt(KEY_CURRENT_AMOUNT);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.prompt_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });


        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this, PromptActivity.class);
//                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
//                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                intent.putExtra(KEY_EXTRA_ANSWER, currentIndex);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown = false;

                if(currentIndex%questions.length ==0){
                    showResult();
                    goodAnswers = 0;
                }
                setNextQuestion();
            }
        });
        setNextQuestion();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}