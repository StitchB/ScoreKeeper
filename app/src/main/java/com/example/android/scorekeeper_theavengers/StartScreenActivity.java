package com.example.android.scorekeeper_theavengers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

        //Set 'activity_start_screen' as a main design file
        setContentView(R.layout.activity_start_screen);

        //Set images using Glide
        //Background
        ImageView backgroundImage = findViewById(R.id.background);
        GlideApp.with(this)
                .load(R.drawable.bg)
                .error(R.drawable.error)
                .into(backgroundImage);
        //Main logo
        ImageView logoImage = findViewById(R.id.main_logo);
        GlideApp.with(this)
                .load(R.drawable.main_logo)
                .error(R.drawable.error)
                .into(logoImage);
        //'Continue' button image
        ImageView buttonContinue = findViewById(R.id.button_continue);
        GlideApp.with(this)
                .load(R.drawable.selector_continue_button)
                .error(R.drawable.error)
                .into(buttonContinue);

        // Capture button clicks
        buttonContinue.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            // Start SelectFirstCharacterActivity.class
            Intent myIntent = new Intent(StartScreenActivity.this, SelectFirstCharacterActivity.class);
            startActivity(myIntent);
            finish();
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
