package com.zacharee1.modcontrol;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.ListAdapter;

/**
 * Created by Zacha on 3/4/2017.
 */

public class HandlePrefs {
    PreferenceFragment pf;
    Context c;
    PreferenceManager pm;
    String spName;
    SharedPreferences prefs;
    SharedPreferences.Editor ed;
    ContentResolver cr;
    ListAdapter adapter;
    boolean isOutOfBounds;

    HandlePrefs(Context context, PreferenceFragment pf, String spName) {
        this.pf = pf;
        this.c = context;
        this.spName = spName;
        pm = pf.getPreferenceManager();
        pm.setSharedPreferencesName(spName);
        prefs = pm.getSharedPreferences();
        ed = prefs.edit();
        cr = c.getContentResolver();
        int id = c.getResources().getIdentifier(spName, "xml", c.getPackageName());
        pf.addPreferencesFromResource(id);
    }
}
