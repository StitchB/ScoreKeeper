package com.example.android.scorekeeper_theavengers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class StartScreenActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set 'activity_start_screen' as a main design file
        setContentView(R.layout.activity_start_screen);

        // Continue button
        ImageButton buttonContinue = findViewById(R.id.button_continue);

        // Capture button clicks
        buttonContinue.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            // Start SelectFirstCharacterActivity.class
            Intent myIntent = new Intent(StartScreenActivity.this, SelectFirstCharacterActivity.class);
            startActivity(myIntent);
            }
        });
    }
}
