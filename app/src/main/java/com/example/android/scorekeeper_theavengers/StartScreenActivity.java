package com.example.android.scorekeeper_theavengers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

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

    /**
     * Hide action bar
     */
    private void hideActionBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
