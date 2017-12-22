package com.example.android.scorekeeper_theavengers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SelectSecondCharacterActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set 'activity_select_second_character' as a main design file
        setContentView(R.layout.activity_select_second_character);

        //If Character 1 (Thor) was selected as a first fighter
        if(selectedCharacter1 == 1)
        {
            //Show Character 2 (Black Widow) on Activity load
            lastCharacterCounter = 2;
            ImageView characterImage = findViewById(R.id.character_image);
            characterImage.setImageResource(R.drawable.character_2);
        }

        //Add on click listener event for the 'Previous Character' button
        ImageButton buttonPrev = findViewById(R.id.button_prev);
        buttonPrev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //Start character transition
            startCharacterTransition(true);
            }
        });

        //Add on click listener event for the 'Next Character' button
        ImageButton buttonNext = findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //Start character transition
            startCharacterTransition(false);
            }
        });

        //Add on click listener event for the 'Select Character' button
        ImageButton buttonSelect = findViewById(R.id.button_select);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            //Select character
            selectCharacter();
            }
        });
    }

    /**
     * Select first character (fighter)
     */
    private void selectCharacter() {
        //Remember second selected character
        selectedCharacter2 = lastCharacterCounter;

        //Start SelectSecondCharacterActivity.class
        Intent myIntent = new Intent(SelectSecondCharacterActivity.this, SelectArenaActivity.class);
        startActivity(myIntent);
    }
}
