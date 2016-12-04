package com.example.emil.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import Game.GameObject;

public class LevelCleared extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_cleared);
    }

    public void nextLevel(View view){
        int level = getIntent().getExtras().getInt("Level");

        Intent intent = new Intent (this, Board.class);
        intent.putExtra("Level", ++level);
        this.startActivity(intent);
        super.finish();
    }
}
