package com.sudoku.p8;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by cap-one on 19/11/15.
 */
public class SudokuPrefs {

    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences prefs;
    public static final String PREFS_NAME = "sudokuPrefs";
    public static final String GRILLE_EASY = "sudokuEasyGrille";
    public static final String GRILLE_MED = "sudokuMedGrille";
    public static final String GRILLE_HARD = "sudokuHardGrille";
    public static final String IS_EASY = "existgrilleEasy";
    public static final String IS_MEDIUM = "existgrilleMedium";
    public static final String IS_HARD = "existgrilleHard";
    public static final String RESUME_DIFF = "resumeDifficulty";
    private Context context;

    public SudokuPrefs(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public int getIntPreference(String key) {
        return prefs.getInt(key, 0);
    }

    public String getStringPreference(String key) {
        return prefs.getString(key, null);
    }

    public Set<String> getStringArrayPreference(String key) {
        return prefs.getStringSet(key, null);
    }

    public boolean getBooleanPreference(String key) {
        return prefs.getBoolean(key, false);
    }

    public void saveIntPreference(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public void saveStringPreference(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void saveStringArrayPreference(String key, Set<String> values) {
        prefsEditor.putStringSet(key, values);
        prefsEditor.commit();
    }

    public void saveBooleanPreference(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public void removeSudoku(String difficulty) {
        if (difficulty.equals("easy")) {
            saveStringPreference(GRILLE_EASY, null);
        } else if (difficulty.equals("medium")) {
            saveStringPreference(GRILLE_MED, null);
            saveStringPreference(RESUME_DIFF, null);
            saveBooleanPreference(IS_MEDIUM, false);
        } else if (difficulty.equals("hard")) {
            saveStringPreference(GRILLE_HARD, null);
            saveStringPreference(RESUME_DIFF, null);
            saveBooleanPreference(IS_HARD, false);
        }
    }


    public void saveSudoku(String grille, String difficulty) {
        if (difficulty.equals("easy")) {
            saveStringPreference(GRILLE_EASY, grille);
            saveStringPreference(RESUME_DIFF, difficulty);
            saveBooleanPreference(IS_EASY, true);
        } else if (difficulty.equals("medium")) {
            saveStringPreference(GRILLE_MED, grille);
            saveStringPreference(RESUME_DIFF, difficulty);
            saveBooleanPreference(IS_MEDIUM, true);
        } else if (difficulty.equals("hard")) {
            saveStringPreference(GRILLE_HARD, grille);
            saveStringPreference(RESUME_DIFF, difficulty);
            saveBooleanPreference(IS_HARD, true);
        }
    }

    public boolean canResume() {
        return getBooleanPreference(IS_EASY) || getBooleanPreference(IS_MEDIUM) || getBooleanPreference(IS_HARD);
    }

    public int getSavedDiff() {
        String savedDiff = getStringPreference(RESUME_DIFF);
        if(savedDiff.equals("easy")) {
           return 2;
        }
        else if(savedDiff.equals("medium")) {
            return 3;
        }
        else if(savedDiff.equals("hard")) {
            return 3;
        }

        return 0;
    }


    public String resumeSudoku() {
        String savedDiff = getStringPreference(RESUME_DIFF);
        if(savedDiff.equals("easy")) {
            saveBooleanPreference(IS_MEDIUM, false);
            saveBooleanPreference(IS_HARD, false);
            return getStringPreference(GRILLE_EASY);
        }
        else if(savedDiff.equals("medium")) {
            saveBooleanPreference(IS_EASY, false);
            saveBooleanPreference(IS_HARD, false);
            return getStringPreference(GRILLE_MED);
        }
        else if(savedDiff.equals("hard")) {
            saveBooleanPreference(IS_EASY, false);
            saveBooleanPreference(IS_MEDIUM, false);
            return getStringPreference(GRILLE_HARD);
        }

        return null;
    }

}
