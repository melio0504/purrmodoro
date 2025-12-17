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

public class TimerActivity extends AppCompatActivity {
    private static final long FOCUS_DURATION = 25 * 60 * 1000; // 25 minutes in milliseconds
    private TextView timerText;
    private TextView statusText;
    private Button btnStart;
    private Button btnPause;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = FOCUS_DURATION;
    private boolean isRunning = false;
    private boolean isPaused = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_timer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timerText = findViewById(R.id.timerText);
        statusText = findViewById(R.id.statusText);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        Button btnStop = findViewById(R.id.btnStop);
        ImageView catImage = findViewById(R.id.catImage);

        // Load GIF into ImageView using Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.focus_cat)
                .into(catImage);

        updateTimerDisplay();
        statusText.setText("The cat is studying hard");

        btnStart.setOnClickListener(v -> startTimer());
        btnPause.setOnClickListener(v -> pauseTimer());
        btnStop.setOnClickListener(v -> stopTimer());

        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            stopTimer();
            Intent intent = new Intent(TimerActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void startTimer() {
        if (isPaused) {
            // Resume from where we left off
            createTimer(timeLeftInMillis);
        } else {
            // Start fresh
            timeLeftInMillis = FOCUS_DURATION;
            createTimer(FOCUS_DURATION);
        }
        isRunning = true;
        isPaused = false;
        btnStart.setEnabled(false);
        btnPause.setEnabled(true);
        statusText.setText("Focusing...");
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
        statusText.setText("Paused. The cat is waiting for you...");
    }

    @SuppressLint("SetTextI18n")
    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = FOCUS_DURATION;
        isRunning = false;
        isPaused = false;
        btnStart.setEnabled(true);
        btnPause.setEnabled(false);
        updateTimerDisplay();
        statusText.setText("Why stop now?");
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
                statusText.setText("Session complete! Great job! Yipeeee!");
                
                // Increment progress
                ProgressHelper.incrementCompletedSessions(TimerActivity.this);
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

