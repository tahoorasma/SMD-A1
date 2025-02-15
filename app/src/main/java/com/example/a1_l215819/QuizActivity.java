package com.example.a1_l215819;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {
    private TextView tv_question, tv_ques_no, btnPrev;
    private RadioButton option1, option2, option3, option4;
    private RadioGroup optionsGroup;
    private Button btnNext;
    private int q_no = 0;
    private List<Question> questionList;
    private Map<Integer, Integer> selectedOptions = new HashMap<>();
    private String userName;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        tv_question = findViewById(R.id.tv_ques);
        tv_ques_no = findViewById(R.id.tv_question_number);
        btnPrev = findViewById(R.id.btn_prev);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        btnNext = findViewById(R.id.btn_next);
        userName = getIntent().getStringExtra("userName");
        questionList = new ArrayList<>();
        questionList.add(new Question("Which number should be there next in this series? 25, 24, 22, 19, 15, ?", "14", "5", "30", "10", 4));
        questionList.add(new Question("Which one of the following is least like the other four?", "Tiger", "Snake", "Bear", "Cow", 2));
        questionList.add(new Question("If you rearrange the letters \"BARBIT,\" you would have the name of a:", "Animal", "Ocean", "Fruit", "Country", 1));
        questionList.add(new Question("Nia, twelve years old, is three times as old as her sister. How old will Nia be when she is twice as old as her sister?", "15", "18", "16", "20", 3));
        questionList.add(new Question("Brother is to sister as niece is to:", "Mother", "Uncle", "Aunt", "Nephew", 4));
        questionList.add(new Question("Milk is to glass as letter is to:", "Stamp", "Pen", "Envelope", "Mail", 3));
        questionList.add(new Question("Two people can make two bicycles in 2 hours. How many people are required to make 12 bicycles in 6 hours?", "6", "4", "2", "1", 2));
        questionList.add(new Question("Which one of the following makes the best comparison? CAACCAC is to 3113313 as CACAACAC is to:", "31313113", "31311313", "31311131", "13133313", 2));
        questionList.add(new Question("Jack is taller than Peter, and Bill is shorter than Jack. Which of the following statements would be more accurate?", "Bill is as tall as Peter.", "It is impossible to tell whether Bill or Peter is taller.", "Bill is taller than Peter.", "Peter is taller than Bill.", 2));
        questionList.add(new Question("The price of an article was cut by 20% for sale. By what percent must the discounted item be increased to again sell the article at the original price?", "15%", "20%", "25%", "30%", 3));

        loadQuestion();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserAnswer();
                if (q_no < questionList.size() - 1) {
                    q_no++;
                    loadQuestion();
                } else {
                    score = calculateScore();
                    Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                    intent.putExtra("userName", userName);
                    intent.putExtra("score", score+"/10");
                    startActivity(intent);
                    //finish();
                }
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q_no > 0) {
                    saveUserAnswer();
                    q_no--;
                    loadQuestion();
                }
            }
        });
    }

    private void loadQuestion() {
        Question currentQuestion = questionList.get(q_no);
        tv_question.setText(currentQuestion.getQuestion());
        option1.setText(currentQuestion.getOption1());
        option2.setText(currentQuestion.getOption2());
        option3.setText(currentQuestion.getOption3());
        option4.setText(currentQuestion.getOption4());
        tv_ques_no.setText((q_no + 1) + "/" + questionList.size());

        // Restore previously selected option
        optionsGroup.clearCheck();
        if (selectedOptions.containsKey(q_no)) {
            int selectedId = selectedOptions.get(q_no);
            if (selectedId == 1) option1.setChecked(true);
            else if (selectedId == 2) option2.setChecked(true);
            else if (selectedId == 3) option3.setChecked(true);
            else if (selectedId == 4) option4.setChecked(true);
        }
        btnPrev.setEnabled(q_no != 0);
        btnNext.setText(q_no == questionList.size() - 1 ? "Finish" : "Next");
    }

    private void saveUserAnswer() {
        int selectedId = optionsGroup.getCheckedRadioButtonId();
        int selectedOption = 0;
        if (selectedId == R.id.option1) selectedOption = 1;
        else if (selectedId == R.id.option2) selectedOption = 2;
        else if (selectedId == R.id.option3) selectedOption = 3;
        else if (selectedId == R.id.option4) selectedOption = 4;
        selectedOptions.put(q_no, selectedOption);
    }

    private int calculateScore() {
        score = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (selectedOptions.containsKey(i) && selectedOptions.get(i) == questionList.get(i).getCorrectAnswer()) {
                score++;
            }
        }
        return score;
    }

    static class Question {
        private final String question;
        private final String option1, option2, option3, option4;
        private final int correctAnswer;

        public Question(String question, String option1, String option2, String option3, String option4, int correctAnswer) {
            this.question = question;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctAnswer = correctAnswer;
        }

        public String getQuestion() { return question; }
        public String getOption1() { return option1; }
        public String getOption2() { return option2; }
        public String getOption3() { return option3; }
        public String getOption4() { return option4; }
        public int getCorrectAnswer() { return correctAnswer; }
    }
}
