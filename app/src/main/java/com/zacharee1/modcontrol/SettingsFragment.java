package com.zacharee1.modcontrol;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    View view;
    MainActivity activity;

    public Switch darkMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        SharedPreferences sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);


        darkMode = (Switch) view.findViewById(R.id.set_theme);

        if (sharedPrefs.getBoolean("isDark", false)) {
            darkMode.setChecked(true);
        }
        darkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                if (isChecked) {
                    editor.putBoolean("isDark", true);
                    Utils.changeToTheme(activity, 1);

                } else {
                    editor.putBoolean("isDark", false);
                    Utils.changeToTheme(activity, 0);
                }
                editor.apply();
            }
        });

        return view;
    }
}