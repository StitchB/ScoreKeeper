package com.example.android.scorekeeper_theavengers;

public interface Constants {

    //Maximum of 'Energy' & 'Special' Bars
    public static final int MAX_ENERGY = 100;
    public static final int MAX_SPECIAL = 80;

    //Minimum & maximum damage values inflicted by 'Special' attack
    public static final int MIN_RANDOM = 10;
    public static final int MAX_RANDOM = 30;

    //Used to keep activity state
    public static final String STATE_SELECTED_CHARACTER_1 = "selectedCharacter1";
    public static final String STATE_SELECTED_CHARACTER_2 = "selectedCharacter2";
    public static final String STATE_LAST_CHARACTER_COUNTER = "lastCharacterCounter";
    public static final String STATE_SELECTED_ARENA = "selectedArena";
    public static final String STATE_LAST_ARENA_COUNTER = "lastArenaCounter";
    public static final String STATE_SCORE_FIGHTER_1 = "scoreFighter1";
    public static final String STATE_SCORE_FIGHTER_2 = "scoreFighter2";
    public static final String STATE_LAST_ATTACK_NO_CHARACTER_1 = "lastAttackNoCharacter1";
    public static final String STATE_LAST_ATTACK_NO_CHARACTER_2 = "lastAttackNoCharacter2";
    public static final String STATE_ENERGY_BAR_1_VALUE = "energyBar1Value";
    public static final String STATE_ENERGY_BAR_2_VALUE = "energyBar2Value";
    public static final String STATE_SPECIAL_BAR_1_VALUE = "specialBar1Value";
    public static final String STATE_SPECIAL_BAR_2_VALUE = "specialBar2Value";
    public static final String STATE_SPECIAL_USED_FIGHTER_1 = "specialUsedFighter1";
    public static final String STATE_SPECIAL_USED_FIGHTER_2 = "specialUsedFighter2";
}
