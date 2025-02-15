package com.example.a1_l215819;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    private TextView nameText, scoreText;
    private ImageView btnBack;
    private Button btnShare;
    private String username, score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        nameText = findViewById(R.id.tv_name);
        scoreText = findViewById(R.id.tv_score);
        username = getIntent().getStringExtra("userName");
        score = getIntent().getStringExtra("score");
        nameText.setText(username);
        scoreText.setText(score);
        btnBack = findViewById(R.id.iv_back_btn);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(ResultActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
            }
        });
        btnShare = findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "My Quiz Khelo score is: " + score + "\nFind yours.";
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(shareIntent, "Share your score via"));
            }
        });
    }
}
