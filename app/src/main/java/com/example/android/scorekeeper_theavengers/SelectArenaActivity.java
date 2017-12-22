package com.example.android.scorekeeper_theavengers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SelectArenaActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set 'activity_select_arena' as a main design file
        setContentView(R.layout.activity_select_arena);

        //Add on click listener event for the 'Previous Arena' button
        ImageButton buttonPrev = findViewById(R.id.button_prev);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //Start arena transition
            startArenaTransition(true);
            }
        });

        //Add on click listener event for the 'Next Arena' button
        ImageButton buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //Start arena transition
            startArenaTransition(false);
            }
        });

        //Add on click listener event for the 'Select Arena' button
        ImageButton buttonSelect = findViewById(R.id.button_select);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //Select arena
            selectArena();
            }
        });
    }

    /**
     * Select arena
     */
    private void selectArena() {
        //Set selected arena
        selectedArena = lastArenaCounter;

        // Start BattleActivity.class
        Intent myIntent = new Intent(SelectArenaActivity.this, BattleActivity.class);
        startActivity(myIntent);
    }
}
