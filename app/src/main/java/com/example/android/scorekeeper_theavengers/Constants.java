package com.example.android.scorekeeper_theavengers;

interface Constants {

    //Maximum of 'Energy' & 'Special' Bars
    int MAX_ENERGY = 100;
    int MAX_SPECIAL = 80;

    //Minimum & maximum damage values inflicted by 'Special' attack
    int MIN_RANDOM = 10;
    int MAX_RANDOM = 30;

    //Used to keep activity state
    String STATE_SELECTED_CHARACTER_1 = "selectedCharacter1";
    String STATE_SELECTED_CHARACTER_2 = "selectedCharacter2";
    String STATE_LAST_CHARACTER_COUNTER = "lastCharacterCounter";
    String STATE_SELECTED_ARENA = "selectedArena";
    String STATE_LAST_ARENA_COUNTER = "lastArenaCounter";
    String STATE_SCORE_FIGHTER_1 = "scoreFighter1";
    String STATE_SCORE_FIGHTER_2 = "scoreFighter2";
    String STATE_LAST_ATTACK_NO_CHARACTER_1 = "lastAttackNoCharacter1";
    String STATE_LAST_ATTACK_NO_CHARACTER_2 = "lastAttackNoCharacter2";
    String STATE_ENERGY_BAR_1_VALUE = "energyBar1Value";
    String STATE_ENERGY_BAR_2_VALUE = "energyBar2Value";
    String STATE_SPECIAL_BAR_1_VALUE = "specialBar1Value";
    String STATE_SPECIAL_BAR_2_VALUE = "specialBar2Value";
    String STATE_SPECIAL_USED_FIGHTER_1 = "specialUsedFighter1";
    String STATE_SPECIAL_USED_FIGHTER_2 = "specialUsedFighter2";

    //Hashmap keys
    String CHARACTER_KEY_NAME = "name";
    String CHARACTER_KEY_ATTACK_1 = "attack_1";
    String CHARACTER_KEY_ATTACK_2 = "attack_2";
    String CHARACTER_KEY_ATTACK_3 = "attack_3";
    String CHARACTER_KEY_ATTACK_4 = "attack_4";
}
