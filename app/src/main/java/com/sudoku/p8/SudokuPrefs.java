package com.sudoku.p8;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

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
    public static final String CURRSCORE_EASY = "sudokuCurrscoreEasy";
    public static final String CURRSCORE_MED = "sudokuCurrscoreMed";
    public static final String CURRSCORE_HARD = "sudokuCurrscoreHard";
    public static final String BESTSCORE_EASY = "sudokuBestscoreEasy";
    public static final String BESTSCORE_MED = "sudokuBestscoreMed";
    public static final String BESTSCORE_HARD = "sudokuBestscoreHard";
    public static final String IS_EASY = "existgrilleEasy";
    public static final String IS_MEDIUM = "existgrilleMedium";
    public static final String IS_HARD = "existgrilleHard";
    public static final String RESUME_DIFF = "resumeDifficulty";
    public static final String LANG = "sudokuLocale";

    public static final String SOUND = "sudokuSon";
    private Context context;

    public SudokuPrefs(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    public int getIntPreference(String key) {
        return prefs.getInt(key, 0);
    }

    public long getLongPreference(String key) {
        return prefs.getLong(key, 0);
    }

    public void saveLongPreference(String key, long value) {
        prefsEditor.putLong(key, value);
        prefsEditor.commit();
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

    public void saveSoundPreference(Boolean value)
    {
        saveBooleanPreference(SOUND,value);
    }

    public Boolean getSoundPreference()
    {

        return getBooleanPreference(SOUND);
    }

    public void saveStringArrayPreference(String key, Set<String> values) {
        prefsEditor.putStringSet(key, values);
        prefsEditor.commit();
    }

    public void saveBooleanPreference(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
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

    public void deletelvl(int diff) {
        switch(diff) {
            case 2: saveBooleanPreference(IS_EASY, false); break;
            case 3: saveBooleanPreference(IS_MEDIUM, false); break;
            case 5: saveBooleanPreference(IS_HARD, false); break;
            default: break;
        }
    }

    public boolean existsE() {
        return getBooleanPreference(IS_EASY);
    }
    public boolean existsM() {
        return getBooleanPreference(IS_MEDIUM);
    }
    public boolean existsH() {
        return getBooleanPreference(IS_HARD);
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
            return 5;
        }

        return 0;
    }


    public String resumeSudoku() {
        String savedDiff = getStringPreference(RESUME_DIFF);
        if(savedDiff.equals("easy")) {
//            saveBooleanPreference(IS_MEDIUM, false);
//            saveBooleanPreference(IS_HARD, false);
            return getStringPreference(GRILLE_EASY);
        }
        else if(savedDiff.equals("medium")) {
//            saveBooleanPreference(IS_EASY, false);
//            saveBooleanPreference(IS_HARD, false);
            return getStringPreference(GRILLE_MED);
        }
        else if(savedDiff.equals("hard")) {
//            saveBooleanPreference(IS_EASY, false);
//            saveBooleanPreference(IS_MEDIUM, false);
            return getStringPreference(GRILLE_HARD);
        }

        return null;
    }

    public String getScore(int diff) {
        switch(diff) {
            case 2: return getStringPreference(CURRSCORE_EASY);
            case 3: return getStringPreference(CURRSCORE_MED);
            case 5: return getStringPreference(CURRSCORE_HARD);
            default: break;
        }

        return "";
    }

    public int minToSecs(String s) {
        String[] time = s.split(":");
        int min = Integer.parseInt(time[0]);
        int sec = Integer.parseInt(time[1]);
        return (60*min)+sec;
    }

    public void saveScore(String s, boolean gamewon, int difficulty) {

        Log.d("score time", "tiirme: " + s);

        int timeL = minToSecs(s);

        switch (difficulty) {
            case 2:
                if(!gamewon) saveStringPreference(CURRSCORE_EASY, s);
                else {
                    if(getStringPreference(BESTSCORE_EASY) != null) {
                        if(timeL< minToSecs(getStringPreference(BESTSCORE_EASY)))
                            saveStringPreference(BESTSCORE_EASY, s);
                    }
                    else saveStringPreference(BESTSCORE_EASY, s);
                }
                break;
            case 3:
                if(!gamewon) saveStringPreference(CURRSCORE_MED, s);
                else {
                    if(getStringPreference(BESTSCORE_MED) != null) {
                        if(timeL< minToSecs(getStringPreference(BESTSCORE_MED)))
                            saveStringPreference(BESTSCORE_MED, s);
                    }
                    else saveStringPreference(BESTSCORE_MED, s);
                }
                break;
            case 5:
                if(!gamewon) saveStringPreference(CURRSCORE_HARD, s);
                else {
                    if(getStringPreference(BESTSCORE_HARD) != null) {
                        if(timeL< minToSecs(getStringPreference(BESTSCORE_HARD)))
                            saveStringPreference(BESTSCORE_HARD, s);
                    }
                    else saveStringPreference(BESTSCORE_HARD, s);
                }
                break;
            default: break;
        }

    }


    public void setLocale(String lang) {
        saveStringPreference(LANG, lang);
    }

    public String getLocale() {
        return getStringPreference(LANG);
    }
}
