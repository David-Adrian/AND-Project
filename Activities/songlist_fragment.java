package com.example.andproject;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by whit e on 24-11-2017.
 */

public class songlist_fragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        //Load prefs from the preferences file, xml packet
        addPreferencesFromResource(R.xml.preferences);
    }
}

