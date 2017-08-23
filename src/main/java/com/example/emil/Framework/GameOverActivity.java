package com.example.emil.Framework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.emil.app.R;

public class GameOverActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_game_over);
    }

    public void retryLevel(View view) {
        int level = getIntent().getExtras().getInt("level");
        Intent intent = new Intent (getApplicationContext(), ActivityHandler.class);
        intent.putExtra("ActivityConstant", ActivityConstants.GAME);
        intent.putExtra("level", level);
        startActivity(intent);
        finish();
    }

    public void selectLevel(View view){
        Intent intent = new Intent(getApplicationContext(), ActivityHandler.class);
        intent.putExtra("ActivityConstant", ActivityConstants.SELECTLEVEL);
        startActivity(intent);
        finish();
    }
}