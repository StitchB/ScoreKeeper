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
import android.view.View.OnClickListener;

public class SelectArenaActivity extends AppCompatActivity implements OnClickListener {

    //Counter for the last selected arena (on arena selection)
    private int lastArenaCounter = 1;

    //Variables to keep first selected characters
    private int selectedCharacter1, selectedCharacter2;

    //Arena image view
    private ImageView arena_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

        //Set 'activity_select_arena' as a main design file
        setContentView(R.layout.activity_select_arena);

        //Get data set by previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedCharacter1 = extras.getInt("SELECTED_CHARACTER_1");
            selectedCharacter2 = extras.getInt("SELECTED_CHARACTER_2");
        }

        //Image buttons
        ImageButton buttonPrev = findViewById(R.id.button_prev);
        ImageButton buttonNext = findViewById(R.id.button_next);
        ImageButton buttonSelect = findViewById(R.id.button_select);

        //Set on click listener events for buttons
        buttonPrev.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
        buttonSelect.setOnClickListener(this);

        //Arena image view
        arena_image = findViewById(R.id.arena_image);
    }

    /**
     * Save instance state
     *
     * @param savedInstanceState - Saved instance state
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Save the state
        savedInstanceState.putInt(Constants.STATE_LAST_ARENA_COUNTER, lastArenaCounter);
        savedInstanceState.putInt(Constants.STATE_SELECTED_CHARACTER_1, selectedCharacter1);
        savedInstanceState.putInt(Constants.STATE_SELECTED_CHARACTER_2, selectedCharacter2);

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
        lastArenaCounter = savedInstanceState.getInt(Constants.STATE_LAST_ARENA_COUNTER);
        selectedCharacter1 = savedInstanceState.getInt(Constants.STATE_SELECTED_CHARACTER_1);
        selectedCharacter2 = savedInstanceState.getInt(Constants.STATE_SELECTED_CHARACTER_2);

        //Show correct arena image
        int arenaId = getResources().getIdentifier("arena_" + lastArenaCounter + "_small", "drawable", getPackageName());
        arena_image.setImageResource(arenaId);
    }

    @Override
    public void onClick(View v) {
        //Perform on click action
        switch(v.getId()) {
            case R.id.button_prev:
                //Start arena transition
                startArenaTransition(true);
                break;
            case R.id.button_next:
                //Start arena transition
                startArenaTransition(false);
                break;
            case R.id.button_select:
                //Select arena
                selectArena();
                break;
        }
    }

    /**
     * Select arena
     */
    private void selectArena() {
        // Start BattleActivity.class
        Intent myIntent = new Intent(SelectArenaActivity.this, BattleActivity.class);
        myIntent.putExtra("SELECTED_CHARACTER_1", selectedCharacter1);
        myIntent.putExtra("SELECTED_CHARACTER_2", selectedCharacter2);
        myIntent.putExtra("SELECTED_ARENA", lastArenaCounter);
        startActivity(myIntent);
    }

    /**
     * Start arena transition
     *
     * @param reversedOrder - Reverse order of transition
     */
    private void startArenaTransition(boolean reversedOrder) {
        //First image
        String firstImage = "arena_" + lastArenaCounter + "_small";
        int firstImageId = getResources().getIdentifier(firstImage, "drawable", getPackageName());

        //Reversed arenas order
        if (reversedOrder) {
            //If first arena - Make sure that seventh arena is selected after first
            if (lastArenaCounter == 1) {
                lastArenaCounter = 7;
            }
            //Else decrease arena counter
            else {
                lastArenaCounter--;
            }
        }
        //Else if standard arenas order
        else {
            //If seventh arena - Make sure that first arena is selected after seventh
            if (lastArenaCounter == 7) {
                lastArenaCounter = 1;
            }
            //Else increase arena counter
            else {
                lastArenaCounter++;
            }
        }

        //Second image
        String secondImage = "arena_" + lastArenaCounter + "_small";
        int secondImageId = getResources().getIdentifier(secondImage, "drawable", getPackageName());

        //Prepare resources for transition
        Drawable backgrounds[] = new Drawable[2];
        Resources res = getResources();
        backgrounds[0] = res.getDrawable(firstImageId);
        backgrounds[1] = res.getDrawable(secondImageId);

        //Apply transition into main image view & change arena
        TransitionDrawable mainImageTransition = new TransitionDrawable(backgrounds);

        arena_image.setImageDrawable(mainImageTransition);
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
