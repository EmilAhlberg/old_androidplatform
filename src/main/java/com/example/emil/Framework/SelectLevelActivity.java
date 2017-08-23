package com.example.emil.Framework;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.emil.app.R;

public class SelectLevelActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        setContentView(R.layout.activity_select_level);
    }

    public void playLevel(View v) {
        String stringLevel = v.getTag().toString();
        int level = Integer.parseInt(stringLevel);
        Intent intent = new Intent(getApplicationContext(), ActivityHandler.class);
        intent.putExtra("ActivityConstant", ActivityConstants.GAME);
        intent.putExtra("level", level);
        startActivity(intent);
        finish();
    }
}
