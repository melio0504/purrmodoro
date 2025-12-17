package com.example.purrmodoro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ShortBreakActivity extends AppCompatActivity {
    private static final long SHORT_BREAK_DURATION = 5 * 60 * 1000; // 5 minutes in milliseconds
    private TextView timerText;
    private TextView statusText;
    private Button btnStart;
    private Button btnPause;
    private Button btnStop;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = SHORT_BREAK_DURATION;
    private boolean isRunning = false;
    private boolean isPaused = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_short_break);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timerText = findViewById(R.id.timerText);
        statusText = findViewById(R.id.statusText);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        ImageView catImage = findViewById(R.id.catImage);

        // Load GIF into ImageView using Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.short_break_cat)
                .into(catImage);

        updateTimerDisplay();
        statusText.setText("Time to take a short break!");

        btnStart.setOnClickListener(v -> startTimer());
        btnPause.setOnClickListener(v -> pauseTimer());
        btnStop.setOnClickListener(v -> stopTimer());

        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            stopTimer();
            Intent intent = new Intent(ShortBreakActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void startTimer() {
        if (isPaused) {
            createTimer(timeLeftInMillis);
        } else {
            timeLeftInMillis = SHORT_BREAK_DURATION;
            createTimer(SHORT_BREAK_DURATION);
        }
        isRunning = true;
        isPaused = false;
        btnStart.setEnabled(false);
        btnPause.setEnabled(true);
        statusText.setText("Stretching break in progress...");
    }

    @SuppressLint("SetTextI18n")
    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
        isPaused = true;
        btnStart.setEnabled(true);
        btnPause.setEnabled(false);
        statusText.setText("Break paused. Take your time!");
    }

    @SuppressLint("SetTextI18n")
    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = SHORT_BREAK_DURATION;
        isRunning = false;
        isPaused = false;
        btnStart.setEnabled(true);
        btnPause.setEnabled(false);
        updateTimerDisplay();
        statusText.setText("Time to stretch with the cat!");
    }

    private void createTimer(long duration) {
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerDisplay();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateTimerDisplay();
                isRunning = false;
                btnStart.setEnabled(true);
                btnPause.setEnabled(false);
                statusText.setText("Break complete! Ready to focus? 🐾");
            }
        }.start();
    }

    private void updateTimerDisplay() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        @SuppressLint("DefaultLocale") String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeLeftFormatted);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

