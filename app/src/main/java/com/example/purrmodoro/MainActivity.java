package com.example.purrmodoro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnStartFocus = findViewById(R.id.btnStartFocus);
        Button btnShortBreak = findViewById(R.id.btnShortBreak);
        Button btnLongBreak = findViewById(R.id.btnLongBreak);
        Button btnViewProgress = findViewById(R.id.btnViewProgress);

        ImageView homeCatImage = findViewById(R.id.homeCatImage);
        Glide.with(this)
                .asGif()
                .load(R.drawable.cat_studying) // Use the underscore version
                .into(homeCatImage);

        btnStartFocus.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        });

        btnShortBreak.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ShortBreakActivity.class);
            startActivity(intent);
        });

        btnLongBreak.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LongBreakActivity.class);
            startActivity(intent);
        });

        btnViewProgress.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
            startActivity(intent);
        });
    }
}
