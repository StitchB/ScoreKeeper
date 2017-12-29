package com.example.android.scorekeeper_theavengers;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.String;

import android.widget.ProgressBar;

import java.util.HashMap;
import java.util.Random;

public class BattleActivity extends AppCompatActivity {

    //Constants for maximum of 'Energy' & 'Special' Bars
    private static final int MAX_ENERGY = 100;
    private static final int MAX_SPECIAL = 80;

    //Constants for minimum & maximum damage values inflicted by 'Special' attack
    private static final int MIN_RANDOM = 10;
    private static final int MAX_RANDOM = 30;

    //Variables to keep first selected characters
    private int selectedCharacter1, selectedCharacter2;

    //Variable to keep selected arena
    private static int selectedArena;

    //Scores
    private int scoreFighter1 = 0;
    private int scoreFighter2 = 0;

    //Last attack numbers
    private int lastAttackNoCharacter1 = 0;
    private int lastAttackNoCharacter2 = 0;

    //Progress bars
    private ProgressBar energyBarFighter1 = null;
    private ProgressBar energyBarFighter2 = null;
    private ProgressBar specialBarFighter1 = null;
    private ProgressBar specialBarFighter2 = null;

    //'Energy' bars values (Set to maximum)
    private int energyBar1Value = MAX_ENERGY;
    private int energyBar2Value = MAX_ENERGY;

    //'Special' bars values (set to 0)
    private int specialBar1Value = 0;
    private int specialBar2Value = 0;

    //Variables to remember if special was used by any of fighters
    private boolean specialUsedFighter1 = false;
    private boolean specialUsedFighter2 = false;

    //Hashmap for keeping characters
    private final HashMap<String, HashMap<String, String>> characters = new HashMap<>();

    //Used to keep activity state
    private static final String STATE_SELECTED_CHARACTER_1 = "selectedCharacter1";
    private static final String STATE_SELECTED_CHARACTER_2 = "selectedCharacter2";
    private static final String STATE_SELECTED_ARENA = "selectedArena";
    private static final String STATE_SCORE_FIGHTER_1 = "scoreFighter1";
    private static final String STATE_SCORE_FIGHTER_2 = "scoreFighter2";
    private static final String STATE_LAST_ATTACK_NO_CHARACTER_1 = "lastAttackNoCharacter1";
    private static final String STATE_LAST_ATTACK_NO_CHARACTER_2 = "lastAttackNoCharacter2";
    private static final String STATE_ENERGY_BAR_1_VALUE = "energyBar1Value";
    private static final String STATE_ENERGY_BAR_2_VALUE = "energyBar2Value";
    private static final String STATE_SPECIAL_BAR_1_VALUE = "specialBar1Value";
    private static final String STATE_SPECIAL_BAR_2_VALUE = "specialBar2Value";
    private static final String STATE_SPECIAL_USED_FIGHTER_1 = "specialUsedFighter1";
    private static final String STATE_SPECIAL_USED_FIGHTER_2 = "specialUsedFighter2";

    //Attack button views
    private Button fighter1Attack1View, fighter1Attack2View, fighter1Attack3View, fighter1Attack4View,
                   fighter2Attack1View, fighter2Attack2View, fighter2Attack3View, fighter2Attack4View;

    //Score text views
    private TextView fighter1ScoreView, fighter2ScoreView;

    //Victory card view
    private CardView victoryView;
    private TextView victoryNameView;
    private ImageView victoryImageView;

    //Fighter names
    private TextView fighter1NameView, fighter2NameView;

    //Fighter images
    private ImageView fighter1Image, fighter2Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide action bar
        hideActionBar();

        //Set 'activity_battle' as a main design file
        setContentView(R.layout.activity_battle);

        //Attack button views
        fighter1Attack1View = findViewById(R.id.fighter_1_attack_1);
        fighter1Attack2View = findViewById(R.id.fighter_1_attack_2);
        fighter1Attack3View = findViewById(R.id.fighter_1_attack_3);
        fighter1Attack4View = findViewById(R.id.fighter_1_attack_4);
        fighter2Attack1View = findViewById(R.id.fighter_2_attack_1);
        fighter2Attack2View = findViewById(R.id.fighter_2_attack_2);
        fighter2Attack3View = findViewById(R.id.fighter_2_attack_3);
        fighter2Attack4View = findViewById(R.id.fighter_2_attack_4);

        //Score text views
        fighter1ScoreView = findViewById(R.id.fighter_1_score);
        fighter2ScoreView = findViewById(R.id.fighter_2_score);

        //Victory card view
        victoryView = findViewById(R.id.victory_card_view);
        victoryView.setCardBackgroundColor(Color.TRANSPARENT);
        victoryView.setCardElevation(0);
        victoryNameView = findViewById(R.id.victory_character);
        victoryImageView = findViewById(R.id.victory_image);

        //Fighter names
        fighter1NameView = findViewById(R.id.fighter_1_name);
        fighter2NameView = findViewById(R.id.fighter_2_name);

        //Fighter images
        fighter1Image = findViewById(R.id.fighter_1_image);
        fighter2Image = findViewById(R.id.fighter_2_image);

        //Get data set by previous activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedCharacter1 = extras.getInt("SELECTED_CHARACTER_1");
            selectedCharacter2 = extras.getInt("SELECTED_CHARACTER_2");
            selectedArena = extras.getInt("SELECTED_ARENA");
        }

        //Progress bar views
        energyBarFighter1 = findViewById(R.id.fighter_1_energy_bar);
        specialBarFighter1 = findViewById(R.id.fighter_1_special_bar);
        energyBarFighter2 = findViewById(R.id.fighter_2_energy_bar);
        specialBarFighter2 = findViewById(R.id.fighter_2_special_bar);
        energyBarFighter1.setMax(MAX_ENERGY);
        energyBarFighter1.setProgress(MAX_ENERGY);
        energyBarFighter2.setMax(MAX_ENERGY);
        energyBarFighter2.setProgress(MAX_ENERGY);
        specialBarFighter1.setMax(MAX_SPECIAL);
        specialBarFighter2.setMax(MAX_SPECIAL);

        //Use selected background
        int backgroundId = getResources().getIdentifier("arena_" + selectedArena, "drawable", getPackageName());
        ImageView backgroundImage = findViewById(R.id.background);
        backgroundImage.setImageResource(backgroundId);

        //Use first selected fighter
        int firstFighterId = getResources().getIdentifier("fighter_" + selectedCharacter1 + "_waiting", "drawable", getPackageName());
        fighter1Image.setImageResource(firstFighterId);

        //First fighter animation
        ObjectAnimator image1Y = ObjectAnimator.ofFloat(fighter1Image, View.TRANSLATION_Y, 70);
        image1Y.setDuration(2500);
        image1Y.setRepeatMode(ValueAnimator.REVERSE);
        image1Y.setRepeatCount(ValueAnimator.INFINITE);
        image1Y.setInterpolator(new LinearInterpolator());

        //Start animation
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.play(image1Y);
        animationSet.start();

        //Use second selected fighter
        int secondFighterId = getResources().getIdentifier("fighter_" + selectedCharacter2 + "_waiting_flipped", "drawable", getPackageName());
        fighter2Image.setImageResource(secondFighterId);

        //Second fighter animation
        ObjectAnimator image2Y = ObjectAnimator.ofFloat(fighter2Image, View.TRANSLATION_Y, 70);
        image2Y.setDuration(2500);
        image2Y.setRepeatMode(ValueAnimator.REVERSE);
        image2Y.setRepeatCount(ValueAnimator.INFINITE);
        image2Y.setInterpolator(new LinearInterpolator());

        //Start animation
        AnimatorSet animationSet2 = new AnimatorSet();
        animationSet2.play(image2Y);
        animationSet2.start();

        //Show correct characters data
        showCharactersData();
    }

    /**
     * Save instance state
     *
     * @param savedInstanceState - Saved instance state
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Save the state
        savedInstanceState.putInt(STATE_SELECTED_CHARACTER_1, selectedCharacter1);
        savedInstanceState.putInt(STATE_SELECTED_CHARACTER_2, selectedCharacter2);
        savedInstanceState.putInt(STATE_SELECTED_ARENA, selectedArena);
        savedInstanceState.putInt(STATE_SCORE_FIGHTER_1, scoreFighter1);
        savedInstanceState.putInt(STATE_SCORE_FIGHTER_2, scoreFighter2);
        savedInstanceState.putInt(STATE_LAST_ATTACK_NO_CHARACTER_1, lastAttackNoCharacter1);
        savedInstanceState.putInt(STATE_LAST_ATTACK_NO_CHARACTER_2, lastAttackNoCharacter2);
        savedInstanceState.putInt(STATE_ENERGY_BAR_1_VALUE, energyBar1Value);
        savedInstanceState.putInt(STATE_ENERGY_BAR_2_VALUE, energyBar2Value);
        savedInstanceState.putInt(STATE_SPECIAL_BAR_1_VALUE, specialBar1Value);
        savedInstanceState.putInt(STATE_SPECIAL_BAR_2_VALUE, specialBar2Value);
        savedInstanceState.putBoolean(STATE_SPECIAL_USED_FIGHTER_1, specialUsedFighter1);
        savedInstanceState.putBoolean(STATE_SPECIAL_USED_FIGHTER_2, specialUsedFighter2);

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
        selectedCharacter1 = savedInstanceState.getInt(STATE_SELECTED_CHARACTER_1);
        selectedCharacter2 = savedInstanceState.getInt(STATE_SELECTED_CHARACTER_2);
        selectedArena = savedInstanceState.getInt(STATE_SELECTED_ARENA);
        scoreFighter1 = savedInstanceState.getInt(STATE_SCORE_FIGHTER_1);
        scoreFighter2 = savedInstanceState.getInt(STATE_SCORE_FIGHTER_2);
        lastAttackNoCharacter1 = savedInstanceState.getInt(STATE_LAST_ATTACK_NO_CHARACTER_1);
        lastAttackNoCharacter2 = savedInstanceState.getInt(STATE_LAST_ATTACK_NO_CHARACTER_2);
        energyBar1Value = savedInstanceState.getInt(STATE_ENERGY_BAR_1_VALUE);
        energyBar2Value = savedInstanceState.getInt(STATE_ENERGY_BAR_2_VALUE);
        specialBar1Value = savedInstanceState.getInt(STATE_SPECIAL_BAR_1_VALUE);
        specialBar2Value = savedInstanceState.getInt(STATE_SPECIAL_BAR_2_VALUE);
        specialUsedFighter1 = savedInstanceState.getBoolean(STATE_SPECIAL_USED_FIGHTER_1);
        specialUsedFighter2 = savedInstanceState.getBoolean(STATE_SPECIAL_USED_FIGHTER_2);

        //Set correct scores
        fighter1ScoreView.setText(String.valueOf(scoreFighter1));
        fighter2ScoreView.setText(String.valueOf(scoreFighter2));

        //Set correct character graphics
        startFighterTransition(true, lastAttackNoCharacter1);
        startFighterTransition(false, lastAttackNoCharacter2);

        //Show Special Attack buttons
        showSpecialAttackButton(true, true);
        showSpecialAttackButton(false, true);

        //Show Victory View
        showVictoryView();
    }

    /**
     * Add points to score for Fighter
     *
     * @param v - Triggering View
     */
    public void addPointsForFighter(View v) {
        //Attack ID
        String attackId = v.getTag().toString();

        //Obtain fighter & attack numbers from the attack ID
        String[] split = attackId.split("_");
        int fighterNo = Integer.parseInt(split[0]);
        int attackNo = Integer.parseInt(split[1]);

        //Declare variable which will be used later to do actions either for first or second fighter
        boolean firstFighter;
        firstFighter = fighterNo == 1;

        //Last attack number
        if (firstFighter) {
            lastAttackNoCharacter1 = attackNo;
        }
        else {
            lastAttackNoCharacter2 = attackNo;
        }

        //Start fighter transition
        startFighterTransition(firstFighter, attackNo);

        //Depending on attack number
        int addedScore = 0;
        switch (attackNo) {
            case 1:
            case 2:
            case 3:
                //Set value for added score
                addedScore = attackNo * 2;
                break;
            case 4:
                //Set random value for added score
                addedScore = getRandomAttackValue();

                //Hide special attack button
                if(firstFighter) {
                    fighter1Attack4View.setVisibility(View.INVISIBLE);
                }
                else {
                    fighter2Attack4View.setVisibility(View.INVISIBLE);
                }

                //Set special bar progress to 0
                if (firstFighter) {
                    specialBar1Value = 0;
                    specialBarFighter1.setProgress(0);
                }
                else {
                    specialBar2Value = 0;
                    specialBarFighter2.setProgress(0);
                }
                break;
        }

        //Add score for a correct fighter
        if (firstFighter) {
            scoreFighter1 += addedScore;
        }
        else {
            scoreFighter2 += addedScore;
        }

        //Display score change
        displayScoreChange(firstFighter, addedScore);
    }

    /**
     * Displays the new score
     *
     * @param firstFighter - Flag to say which fighter is affected
     * @param updateValue - Score update value
     */
    private void displayScoreChange(boolean firstFighter, int updateValue) {
        //Set score for one of the fighters
        if(firstFighter) {
            fighter1ScoreView.setText(String.valueOf(scoreFighter1));
        }
        else {
            fighter2ScoreView.setText(String.valueOf(scoreFighter2));
        }

        //Update progress bar
        updateProgressBars(firstFighter, updateValue);

        //Show Special Attack button
        showSpecialAttackButton(firstFighter, false);

        //Show Victory View
        showVictoryView();
    }

    /**
     * Show Special Attack Button
     */
    private void showSpecialAttackButton(boolean firstFighter, boolean onRestoreInstanceState) {
        //If enough points gained to activate special for an opponent of the current fighter & special wasn't used yet by that opponent
        if ((firstFighter ? specialBar2Value : specialBar1Value) >= MAX_SPECIAL &&
             (!(firstFighter ? specialUsedFighter2 : specialUsedFighter1) || onRestoreInstanceState)) {

            //Set value for 'Special Used' flags to true to not allow using special twice during one fight
            if (firstFighter) {
                specialUsedFighter2 = true;
            }
            else {
                specialUsedFighter1 = true;
            }

            //Make special attack button visible
            if(firstFighter) {
                fighter2Attack4View.setVisibility(View.VISIBLE);
            }
            else {
                fighter1Attack4View.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * Show Victory View
     */
    private void showVictoryView() {
        //If one of fighters has lost
        if (energyBar1Value <= 0 || energyBar2Value <= 0) {
            //Show 'Victory' image
            victoryView.setVisibility(View.VISIBLE);

            //Show message with the winner name
            String character1Id = String.valueOf(energyBar2Value <= 0 ? selectedCharacter1 : selectedCharacter2);
            victoryNameView.setText(String.valueOf(characters.get(character1Id).get("name")) + " is the winner!");

            //Show correct winner image
            int firstFighterId = getResources().getIdentifier("victory_" + (energyBar2Value <= 0 ? selectedCharacter1 : selectedCharacter2), "drawable", getPackageName());
            victoryImageView.setImageResource(firstFighterId);

            //Hide all attack buttons
            showHideAttacks(false, false);
        }
    }

    /**
     * Start fighter transition
     *
     * @param firstFighter - Flag to say which fighter is affected
     * @param attackNo - Attack number
     */
    private void startFighterTransition(Boolean firstFighter, int attackNo) {
        //First image
        String firstImage;
        if ((firstFighter && lastAttackNoCharacter1 > 0) ||
                (!firstFighter && lastAttackNoCharacter2 > 0)) {
            firstImage = "fighter_" + (firstFighter ? selectedCharacter1 : selectedCharacter2) + "_attack_" +
                    (firstFighter ? lastAttackNoCharacter1 : lastAttackNoCharacter2);
        }
        else {
            firstImage = "fighter_" + (firstFighter ? selectedCharacter1 : selectedCharacter2) + "_waiting";
        }
        firstImage = firstImage + (!firstFighter ? "_flipped" : "");
        int firstImageId = getResources().getIdentifier(firstImage, "drawable", getPackageName());

        //Second image
        String secondImage;
        if (attackNo == 0) {
            secondImage = "fighter_" + (firstFighter ? selectedCharacter1 : selectedCharacter2) + "_waiting";
        }
        else {
            secondImage = "fighter_" + (firstFighter ? selectedCharacter1 : selectedCharacter2) + "_attack_" + attackNo;
        }
        secondImage = secondImage + (!firstFighter ? "_flipped" : "");
        int secondImageId = getResources().getIdentifier(secondImage, "drawable", getPackageName());

        //Prepare resources for transition
        Drawable backgrounds[] = new Drawable[2];
        Resources res = getResources();
        backgrounds[0] = res.getDrawable(firstImageId);
        backgrounds[1] = res.getDrawable(secondImageId);

        //Apply transition into main image view & change character
        TransitionDrawable mainImageTransition = new TransitionDrawable(backgrounds);

        if(firstFighter) {
            fighter1Image.setImageDrawable(mainImageTransition);
        }
        else {
            fighter2Image.setImageDrawable(mainImageTransition);
        }

        mainImageTransition.setCrossFadeEnabled(true);
        mainImageTransition.startTransition(500);
    }

    /**
     * Update progress bars
     *
     * @param firstFighter - Flag to say which fighter is affected
     * @param updateValue - Score update value
     */
    private void updateProgressBars(boolean firstFighter, int updateValue) {
        //If the first fighter
        if (firstFighter) {
            if (energyBar2Value > 0) {
                energyBar2Value -= updateValue;
            }
            specialBar2Value += updateValue;
            energyBarFighter2.setProgress(energyBar2Value);
            specialBarFighter2.setProgress(specialBar2Value);
        }
        //If the second fighter
        else {
            if (energyBar1Value > 0) {
                energyBar1Value -= updateValue;
            }
            specialBar1Value += updateValue;
            energyBarFighter1.setProgress(energyBar1Value);
            specialBarFighter1.setProgress(specialBar1Value);
        }
    }

    /**
     * Reset fight
     *
     * @param v - Triggering View
     */
    public void resetFight(View v) {
        //Set scores to 0
        scoreFighter1 = 0;
        scoreFighter2 = 0;
        displayScoreChange(true, 0);
        displayScoreChange(false, 0);

        //Reset characters
        startFighterTransition(true, 0);
        startFighterTransition(false, 0);

        //Reset progress bars
        energyBar1Value = energyBar2Value = MAX_ENERGY;
        specialBar1Value = specialBar2Value = 0;
        energyBarFighter1.setProgress(MAX_ENERGY);
        specialBarFighter1.setProgress(0);
        energyBarFighter2.setProgress(MAX_ENERGY);
        specialBarFighter2.setProgress(0);

        //Set 'Special Used' flags to false
        specialUsedFighter1 = false;
        specialUsedFighter2 = false;

        //Hide Victory view
        victoryView.setVisibility(View.INVISIBLE);

        //Show attack buttons (without 'Attack 4')
        showHideAttacks(true, true);
    }

    /**
     * Show all characters data (On Activity load)
     */
    private void showCharactersData() {
        //Set characters data
        setCharactersData();

        //Selected character IDs
        String character1Id = String.valueOf(selectedCharacter1);
        String character2Id = String.valueOf(selectedCharacter2);

        //Show data for each character
        showCharacterData(true, character1Id);
        showCharacterData(false, character2Id);
    }

    /**
     * Set characters data
     */
    private void setCharactersData() {
        //Thor
        HashMap<String, String> value = new HashMap<>();
        value.put("name", "Thor");
        value.put("attack_1", "Mjolnir Hit");
        value.put("attack_2", "Mjolnir Smash");
        value.put("attack_3", "Might of Mjolnir");
        value.put("attack_4", "Thunderstruck");
        characters.put("1", value);

        //Black Widow
        value = new HashMap<>();
        value.put("name", "Black Widow");
        value.put("attack_1", "Perfect Punch");
        value.put("attack_2", "Unexpected Attack");
        value.put("attack_3", "Power Sticks");
        value.put("attack_4", "Widow's Revenge");
        characters.put("2", value);

        //Iron Man
        value = new HashMap<>();
        value.put("name", "Iron Man");
        value.put("attack_1", "Light Beam");
        value.put("attack_2", "The Charge");
        value.put("attack_3", "Jet Propelled");
        value.put("attack_4", "Full Power");
        characters.put("3", value);

        //Hulk
        value = new HashMap<>();
        value.put("name", "Hulk");
        value.put("attack_1", "Strong Punch");
        value.put("attack_2", "Angry Fists");
        value.put("attack_3", "Monster Jump");
        value.put("attack_4", "Hulk Smash");
        characters.put("4", value);

        //Captain America
        value = new HashMap<>();
        value.put("name", "Captain America");
        value.put("attack_1", "Low Strike");
        value.put("attack_2", "Vigorous Attack");
        value.put("attack_3", "Agressive Attitude");
        value.put("attack_4", "Shield Throw");
        characters.put("5", value);
    }

    /**
     * Show particular character data (On Activity load)
     *
     * @param firstFighter - Flag to say which fighter is affected
     * @param characterId - Character ID
     */
    private void showCharacterData(boolean firstFighter, String characterId) {
        //Character Name
        if(firstFighter) {
            fighter1NameView.setText(String.valueOf(characters.get(characterId).get("name")));
        }
        else {
            fighter2NameView.setText(String.valueOf(characters.get(characterId).get("name")));
        }

        //Attack 1 Name
        if(firstFighter) {
            fighter1Attack1View.setText(String.valueOf(characters.get(characterId).get("attack_1")));
        }
        else {
            fighter2Attack1View.setText(String.valueOf(characters.get(characterId).get("attack_1")));
        }

        //Attack 2 Name
        if(firstFighter) {
            fighter1Attack2View.setText(String.valueOf(characters.get(characterId).get("attack_2")));
        }
        else {
            fighter2Attack2View.setText(String.valueOf(characters.get(characterId).get("attack_2")));
        }

        //Attack 3 Name
        if(firstFighter) {
            fighter1Attack3View.setText(String.valueOf(characters.get(characterId).get("attack_3")));
        }
        else {
            fighter2Attack3View.setText(String.valueOf(characters.get(characterId).get("attack_3")));
        }

        //Attack 4 Name
        if(firstFighter) {
            fighter1Attack4View.setText(String.valueOf(characters.get(characterId).get("attack_4")));
        }
        else {
            fighter2Attack4View.setText(String.valueOf(characters.get(characterId).get("attack_4")));
        }
    }

    /**
     * Show or hide attack buttons
     *
     * @param show - Flag to say if buttons should be hidden or visible
     * @param excludeSpecial - Flag to say if 'Attack 4' (Special attack) button should be hidden or visible
     */
    private void showHideAttacks(boolean show, boolean excludeSpecial) {
        //Attack 1
        fighter1Attack1View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        fighter2Attack1View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        //Attack 2
        fighter1Attack2View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        fighter2Attack2View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        //Attack 3
        fighter1Attack3View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        fighter2Attack3View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        //Attack 4
        if (!excludeSpecial) {
            fighter1Attack4View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            fighter2Attack4View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /**
     * Get random number between 10 and 30
     *
     * @return Returns random attack value (for 'Attack 4' - Special Attack)
     */
    private static int getRandomAttackValue() {
        Random r = new Random();
        return r.nextInt((MAX_RANDOM - MIN_RANDOM) + 1) + MIN_RANDOM;
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