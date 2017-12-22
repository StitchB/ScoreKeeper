package com.example.android.scorekeeper_theavengers;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //Constants for maximum of 'Energy' & 'Special' Bars
    protected static final int MAX_ENERGY = 100;
    protected static final int MAX_SPECIAL = 80;

    //Constants for minimum & maximum damage values inflicted by 'Special' attack
    protected static final int MIN_RANDOM = 10;
    protected static final int MAX_RANDOM = 30;

    //Counter for the last selected character (on character selection)
    protected int lastCharacterCounter = 1;

    //Variables to keep selected characters
    protected static int selectedCharacter1;
    protected static int selectedCharacter2;

    //Counter for the last selected arena (on arena selection)
    protected int lastArenaCounter = 1;

    //Variable to keep selected arena
    protected static int selectedArena;

    protected HashMap<String, HashMap<String, String>> characters = new HashMap<String, HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

        //Set characters data
        setCharactersData();
    }

    /**
     * Hide action bar
     */
    protected void hideActionBar() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
     * Start arena transition
     *
     * @param reversedOrder - Reverse order of transition
     */
    protected void startArenaTransition(boolean reversedOrder) {
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

        ImageView image = findViewById(R.id.arena_image);
        image.setImageDrawable(mainImageTransition);
    }

    /**
     * Set characters data
     */
    protected void setCharactersData() {
        //Thor
        HashMap<String, String> value = new HashMap<String, String>();
        value.put("name", "Thor");
        value.put("attack_1", "Mjolnir Hit");
        value.put("attack_2", "Mjolnir Smash");
        value.put("attack_3", "Might of Mjolnir");
        value.put("attack_4", "Thunderstruck");
        characters.put("1", value);

        //Black Widow
        value = new HashMap<String, String>();
        value.put("name", "Black Widow");
        value.put("attack_1", "Perfect Punch");
        value.put("attack_2", "Unexpected Attack");
        value.put("attack_3", "Power Sticks");
        value.put("attack_4", "Widow's Revenge");
        characters.put("2", value);

        //Iron Man
        value = new HashMap<String, String>();
        value.put("name", "Iron Man");
        value.put("attack_1", "Light Beam");
        value.put("attack_2", "The Charge");
        value.put("attack_3", "Jet Propelled");
        value.put("attack_4", "Full Power");
        characters.put("3", value);

        //Hulk
        value = new HashMap<String, String>();
        value.put("name", "Hulk");
        value.put("attack_1", "Strong Punch");
        value.put("attack_2", "Angry Fists");
        value.put("attack_3", "Monster Jump");
        value.put("attack_4", "Hulk Smash");
        characters.put("4", value);

        //Captain America
        value = new HashMap<String, String>();
        value.put("name", "Captain America");
        value.put("attack_1", "Low Strike");
        value.put("attack_2", "Vigorous Attack");
        value.put("attack_3", "Agressive Attitude");
        value.put("attack_4", "Shield Throw");
        characters.put("5", value);
    }
}
