package com.example.android.scorekeeper_theavengers;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.String;

import android.widget.ProgressBar;

import java.util.Random;

public class BattleActivity extends MainActivity {

    //Scores
    private int scoreFighter1 = 0;
    private int scoreFighter2 = 0;

    //Last attack numbers
    private int lastAttackNoCharacter1 = 0;
    private int lastAttackNoCharacter2 = 0;

    //Progress bars
    private ProgressBar energyBarFighter1 = null;
    private ProgressBar specialBarFighter1 = null;
    private ProgressBar energyBarFighter2 = null;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set 'activity_battle' as a main design file
        setContentView(R.layout.activity_battle);

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
        ImageView firstFighterImage = findViewById(R.id.fighter_1);
        firstFighterImage.setImageResource(firstFighterId);

        //First fighter animation
        ObjectAnimator image1Y = ObjectAnimator.ofFloat(firstFighterImage, View.TRANSLATION_Y, 70);
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
        ImageView secondFighterImage = findViewById(R.id.fighter_2);
        secondFighterImage.setImageResource(secondFighterId);

        //Second fighter animation
        ObjectAnimator image2Y = ObjectAnimator.ofFloat(secondFighterImage, View.TRANSLATION_Y, 70);
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
                TextView attack4View = findViewById(firstFighter ? R.id.fighter_1_attack_4 : R.id.fighter_2_attack_4);
                attack4View.setVisibility(View.INVISIBLE);

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
            scoreFighter1 = scoreFighter1 + addedScore;
        }
        else {
            scoreFighter2 = scoreFighter2 + addedScore;
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
        TextView scoreView = findViewById(firstFighter ? R.id.fighter_1_score : R.id.fighter_2_score);
        scoreView.setText(String.valueOf(firstFighter ? scoreFighter1 : scoreFighter2));

        //Update progress bar
        updateProgressBars(firstFighter, updateValue);

        //If enough points gained to activate special for an opponent of the current fighter & special wasn't used yet by that opponent
        if ((firstFighter ? specialBar2Value : specialBar1Value) >= MAX_SPECIAL &&
            (firstFighter ? specialUsedFighter2 : specialUsedFighter1) == false) {

            //Set value for 'Special Used' flags to true to not allow using special twice during one fight
            if (firstFighter) {
                specialUsedFighter2 = true;
            }
            else {
                specialUsedFighter1 = true;
            }

            //Make special attack button visible
            TextView attack4View = findViewById(firstFighter ? R.id.fighter_2_attack_4 : R.id.fighter_1_attack_4);
            attack4View.setVisibility(View.VISIBLE);
        }

        //If one of fighters has lost
        if (energyBar1Value <= 0 || energyBar2Value <= 0) {
            //Show 'Victory' image
            CardView victoryView = findViewById(R.id.victory_card_view);
            victoryView.setVisibility(View.VISIBLE);

            //Show message with the winner name
            String character1Id = String.valueOf(energyBar2Value <= 0 ? selectedCharacter1 : selectedCharacter2);
            TextView victoryNameView = findViewById(R.id.victory_character);
            victoryNameView.setText(String.valueOf(characters.get(character1Id).get("name")) + " is the winner!");

            //Show correct winner image
            int firstFighterId = getResources().getIdentifier("victory_" + (energyBar2Value <= 0 ? selectedCharacter1 : selectedCharacter2), "drawable", getPackageName());
            ImageView victoryImageView = findViewById(R.id.victory_image);
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

        ImageView image = findViewById(firstFighter ? R.id.fighter_1 : R.id.fighter_2);
        image.setImageDrawable(mainImageTransition);

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
                energyBar2Value = energyBar2Value - updateValue;
            }
            specialBar2Value = specialBar2Value + updateValue;
            energyBarFighter2.setProgress(energyBar2Value);
            specialBarFighter2.setProgress(specialBar2Value);
        //If the second fighter
        }
        else {
            if (energyBar1Value > 0) {
                energyBar1Value = energyBar1Value - updateValue;
            }
            specialBar1Value = specialBar1Value + updateValue;
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
        CardView victoryView = findViewById(R.id.victory_card_view);
        victoryView.setVisibility(View.INVISIBLE);

        //Show attack buttons (without 'Attack 4')
        showHideAttacks(true, true);
    }

    /**
     * Show all characters data (On Activity load)
     */
    private void showCharactersData() {
        String character1Id = String.valueOf(selectedCharacter1);
        String character2Id = String.valueOf(selectedCharacter2);

        showCharacterData(true, character1Id);
        showCharacterData(false, character2Id);
    }

    /**
     * Show particular character data (On Activity load)
     *
     * @param firstFighter - Flag to say which fighter is affected
     * @param characterId - Character ID
     */
    private void showCharacterData(boolean firstFighter, String characterId) {
        //Character Name
        TextView nameView = findViewById(firstFighter ? R.id.fighter_1_name : R.id.fighter_2_name);
        nameView.setText(String.valueOf(characters.get(characterId).get("name")));

        //Attack 1 Name
        TextView attack1View = findViewById(firstFighter ? R.id.fighter_1_attack_1 : R.id.fighter_2_attack_1);
        attack1View.setText(String.valueOf(characters.get(characterId).get("attack_1")));

        //Attack 2 Name
        TextView attack2View = findViewById(firstFighter ? R.id.fighter_1_attack_2 : R.id.fighter_2_attack_2);
        attack2View.setText(String.valueOf(characters.get(characterId).get("attack_2")));

        //Attack 3 Name
        TextView attack3View = findViewById(firstFighter ? R.id.fighter_1_attack_3 : R.id.fighter_2_attack_3);
        attack3View.setText(String.valueOf(characters.get(characterId).get("attack_3")));

        //Attack 4 Name
        TextView attack4View = findViewById(firstFighter ? R.id.fighter_1_attack_4 : R.id.fighter_2_attack_4);
        attack4View.setText(String.valueOf(characters.get(characterId).get("attack_4")));
    }

    /**
     * Show or hide attack buttons
     *
     * @param show - Flag to say if buttons should be hidden or visible
     * @param excludeSpecial - Flag to say if 'Attack 4' (Special attack) button should be hidden or visible
     */
    private void showHideAttacks(boolean show, boolean excludeSpecial) {
        //Attack 1
        TextView attack1View = findViewById(R.id.fighter_1_attack_1);
        attack1View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        attack1View = findViewById(R.id.fighter_2_attack_1);
        attack1View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        //Attack 2
        TextView attack2View = findViewById(R.id.fighter_1_attack_2);
        attack2View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        attack2View = findViewById(R.id.fighter_2_attack_2);
        attack2View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        //Attack 3
        TextView attack3View = findViewById(R.id.fighter_1_attack_3);
        attack3View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        attack3View = findViewById(R.id.fighter_2_attack_3);
        attack3View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);

        //Attack 4
        if (!excludeSpecial) {
            TextView attack4View = findViewById(R.id.fighter_1_attack_4);
            attack4View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            attack4View = findViewById(R.id.fighter_2_attack_4);
            attack4View.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
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
}