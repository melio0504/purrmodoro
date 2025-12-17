package com.example.purrmodoro;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class ProgressActivity extends AppCompatActivity {
    private TextView sessionsCountText;
    private LinearLayout pawPrintsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_progress);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sessionsCountText = findViewById(R.id.sessionsCountText);

        // Load GIF into ImageView using Glide
        ImageView progressCatImage = findViewById(R.id.progressCatImage);
        Glide.with(this)
                .asGif()
                .load(R.drawable.cat_progress)
                .into(progressCatImage);

        Button btnBackToHome = findViewById(R.id.btnBackToHome);
        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(ProgressActivity.this, MainActivity.class);
            startActivity(intent);
        });

        updateProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateProgress();
    }

    private void updateProgress() {
        int completedSessions = ProgressHelper.getCompletedSessions(this);
        sessionsCountText.setText(String.valueOf(completedSessions));
    }
}

