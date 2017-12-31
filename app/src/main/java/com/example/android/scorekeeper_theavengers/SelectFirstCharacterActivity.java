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
import android.widget.ImageView;
import android.view.View.OnClickListener;

public class SelectFirstCharacterActivity extends AppCompatActivity implements OnClickListener {

    //Counter for the last selected character (on character selection)
    private int lastCharacterCounter = 1;

    //Variable to keep first selected character
    private static int selectedCharacter1;

    //Character image view
    private ImageView characterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

        //Set 'activity_select_first_character' as a main design file
        setContentView(R.layout.activity_select_first_character);

        //Set images using Glide
        //Background
        ImageView backgroundImage = findViewById(R.id.background);
        GlideApp.with(this)
                .load(R.drawable.bg)
                .error(R.drawable.error)
                .into(backgroundImage);
        //'Previous', 'Select', 'Next' button images
        ImageView buttonPrev = findViewById(R.id.button_prev);
        GlideApp.with(this)
                .load(R.drawable.selector_prev_button)
                .error(R.drawable.error)
                .into(buttonPrev);
        ImageView buttonSelect = findViewById(R.id.button_select);
        GlideApp.with(this)
                .load(R.drawable.selector_select_button)
                .error(R.drawable.error)
                .into(buttonSelect);
        ImageView buttonNext = findViewById(R.id.button_next);
        GlideApp.with(this)
                .load(R.drawable.selector_next_button)
                .error(R.drawable.error)
                .into(buttonNext);

        //Character image view
        characterImage = findViewById(R.id.character_image);

        //Set on click listener events for buttons
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonSelect.setOnClickListener(this);
    }

    /**
     * Save instance state
     *
     * @param savedInstanceState - Saved instance state
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Save the state
        savedInstanceState.putInt(Constants.STATE_LAST_CHARACTER_COUNTER, lastCharacterCounter);
        savedInstanceState.putInt(Constants.STATE_SELECTED_CHARACTER_1, selectedCharacter1);

        //Call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    /**
     * Restore instance state
     *
     * @param savedInstanceState - Saved instance state
     */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //Call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        //Restore the state from saved instance
        lastCharacterCounter = savedInstanceState.getInt(Constants.STATE_LAST_CHARACTER_COUNTER);
        selectedCharacter1 = savedInstanceState.getInt(Constants.STATE_SELECTED_CHARACTER_1);

        //Show correct character image
        int characterId = getResources().getIdentifier("character_" + lastCharacterCounter, "drawable", getPackageName());
        characterImage.setImageResource(characterId);
    }

    @Override
    public void onClick(View v) {
        //Perform on click action
        switch(v.getId()) {
            case R.id.button_prev:
                //Start character transition
                startCharacterTransition(true);
                break;
            case R.id.button_next:
                //Start character transition
                startCharacterTransition(false);
                break;
            case R.id.button_select:
                //Select character
                selectCharacter();
                break;
        }
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
    private void startCharacterTransition(boolean reversedOrder) {
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

        characterImage.setImageDrawable(mainImageTransition);

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
