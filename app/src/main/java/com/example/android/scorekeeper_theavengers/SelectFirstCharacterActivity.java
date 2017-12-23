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

public class SelectFirstCharacterActivity extends AppCompatActivity {

    //Counter for the last selected character (on character selection)
    private int lastCharacterCounter = 1;

    //Variable to keep first selected character
    private static int selectedCharacter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

        //Set 'activity_select_first_character' as a main design file
        setContentView(R.layout.activity_select_first_character);

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
        //Remember first selected character
        selectedCharacter1 = lastCharacterCounter;

        // Start SelectSecondCharacterActivity.class
        Intent myIntent = new Intent(SelectFirstCharacterActivity.this, SelectSecondCharacterActivity.class);

        myIntent.putExtra("SELECTED_CHARACTER_1", selectedCharacter1);
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
            //If fifth character - Make sure that first character is selected after fifth
            if (lastCharacterCounter == 5) {
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

        ImageView image = findViewById(R.id.character_image);
        image.setImageDrawable(mainImageTransition);

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
