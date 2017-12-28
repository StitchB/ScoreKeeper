package com.example.android.scorekeeper_theavengers;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SelectSecondCharacterActivity extends AppCompatActivity {

    //Counter for the last selected character (on character selection)
    private int lastCharacterCounter = 1;

    //Variable to keep first selected character
    private int selectedCharacter1;

    //Variable to keep second selected character
    private int selectedCharacter2;

    //Used to keep activity state
    static final String STATE_LAST_CHARACTER_COUNTER = "lastCharacterCounter";
    static final String STATE_SELECTED_CHARACTER_1 = "selectedCharacter1";
    static final String STATE_SELECTED_CHARACTER_2 = "selectedCharacter2";

    //Character image view
    ImageView character_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

        //Set 'activity_select_second_character' as a main design file
        setContentView(R.layout.activity_select_second_character);

        //Get data set by previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedCharacter1 = extras.getInt("SELECTED_CHARACTER_1");
        }

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

        //Character image view
        character_image = findViewById(R.id.character_image);
    }

    /**
     * Save instance state
     *
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Save the state
        savedInstanceState.putInt(STATE_LAST_CHARACTER_COUNTER, lastCharacterCounter);
        savedInstanceState.putInt(STATE_SELECTED_CHARACTER_1, selectedCharacter1);
        savedInstanceState.putInt(STATE_SELECTED_CHARACTER_2, selectedCharacter2);

        //Call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Restore instance state
     *
     * @param savedInstanceState
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //Call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        //Restore the state from saved instance
        lastCharacterCounter = savedInstanceState.getInt(STATE_LAST_CHARACTER_COUNTER);
        selectedCharacter1 = savedInstanceState.getInt(STATE_SELECTED_CHARACTER_1);
        selectedCharacter2 = savedInstanceState.getInt(STATE_SELECTED_CHARACTER_2);

        //Show correct character image
        int characterId = getResources().getIdentifier("character_" + lastCharacterCounter, "drawable", getPackageName());
        character_image.setImageResource(characterId);
    }

    /**
     * Select first character (fighter)
     */
    private void selectCharacter() {
        //Remember second selected character
        selectedCharacter2 = lastCharacterCounter;

        //Start SelectSecondCharacterActivity.class
        Intent myIntent = new Intent(SelectSecondCharacterActivity.this, SelectArenaActivity.class);
        myIntent.putExtra("SELECTED_CHARACTER_1", selectedCharacter1);
        myIntent.putExtra("SELECTED_CHARACTER_2", selectedCharacter2);
        startActivity(myIntent);
    }

    /**
     * Start character transition
     *
     * @param reversedOrder - Reversed order of transition
     */
    protected void startCharacterTransition(boolean reversedOrder) {
        //First image
        String firstImage = "character_" + lastCharacterCounter;
        int firstImageId = getResources().getIdentifier(firstImage, "drawable", getPackageName());

        //Reversed characters order
        if (reversedOrder) {
            //If first character - Make sure that fifth character is selected after first
            if (lastCharacterCounter == 1) {
                lastCharacterCounter = 5;
            }
            //Else decrease character counter
            else {
                lastCharacterCounter--;
            }

            //Change to fifth character if first was already selected
            if (selectedCharacter1 == 1 && lastCharacterCounter == 1) {
                lastCharacterCounter = 5;
            }

            //Avoid selecting the same character
            if (selectedCharacter1 == lastCharacterCounter) {
                lastCharacterCounter--;
            }
        }
        //Else if standard characters order
        else {
            //If fifth character is selected as a first fighter & fourth character is currently visible - Make sure that first character is selected after fourth
            if (selectedCharacter1 == 5 && lastCharacterCounter == 4) {
                lastCharacterCounter = 1;
            }
            //If fifth character - Make sure that first character is selected after fifth
            else if (lastCharacterCounter == 5) {
                lastCharacterCounter = 1;
            }
            //Else increase character counter
            else {
                lastCharacterCounter++;
            }

            //Change to second character if first was already selected
            if (selectedCharacter1 == 1 && lastCharacterCounter == 1) {
                lastCharacterCounter = 2;
            }

            //Avoid selecting the same character
            if (selectedCharacter1 == lastCharacterCounter) {
                lastCharacterCounter++;
            }
        }

        //Second image
        String secondImage = "character_" + lastCharacterCounter;
        int secondImageId = getResources().getIdentifier(secondImage, "drawable", getPackageName());

        //Prepare resources for transition
        Drawable backgrounds[] = new Drawable[2];
        Resources res = getResources();
        backgrounds[0] = res.getDrawable(firstImageId);
        backgrounds[1] = res.getDrawable(secondImageId);

        //Apply transition into main image view & start animation
        TransitionDrawable mainImageTransition = new TransitionDrawable(backgrounds);

        character_image.setImageDrawable(mainImageTransition);

        mainImageTransition.setCrossFadeEnabled(true);
        mainImageTransition.startTransition(500);
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
