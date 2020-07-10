package com.jjsj.finebodyapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceLogged {

    private SharedPreferences preference;
    private final String keyPreferenceLogged = "logged";

    public PreferenceLogged(Context context){

        this.preference = context.getSharedPreferences(keyPreferenceLogged, Context.MODE_PRIVATE);
    }

    public void setPreference(String idCoach){

        SharedPreferences.Editor editor = preference.edit();
        editor.putString(keyPreferenceLogged, idCoach).apply();
    }

    public String getPreference(){

        return preference.getString(keyPreferenceLogged, null);
    }
}
