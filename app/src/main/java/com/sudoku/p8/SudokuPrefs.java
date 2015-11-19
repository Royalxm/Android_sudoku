package com.sudoku.p8;

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

    public void saveIntPreference(String key, int value){
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public void saveStringPreference(String key, String value){
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public void saveStringArrayPreference(String key, Set<String> values){
        prefsEditor.putStringSet(key, values);
        prefsEditor.commit();
    }

    public void saveBooleanPreference(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

}
