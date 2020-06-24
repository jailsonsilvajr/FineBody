package com.jjsj.finebodyapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceFirstLogin {

    private SharedPreferences preference;
    private final String keyPreferenceFirstLogin = "firstLogin";

    public PreferenceFirstLogin(Context context){

        this.preference = context.getSharedPreferences(keyPreferenceFirstLogin, Context.MODE_PRIVATE);
    }

    public void setPreference(boolean x){

        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(keyPreferenceFirstLogin, x).apply();
    }

    public boolean getPreference(){

        return preference.getBoolean(keyPreferenceFirstLogin, true);
    }
}
