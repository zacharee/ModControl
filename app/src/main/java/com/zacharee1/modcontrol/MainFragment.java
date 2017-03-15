package com.zacharee1.modcontrol;

/**
 * Created by Zacha on 3/10/2017.
 */

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    MainActivity activity;
    public View view;
    public boolean modInstalledBool;
    public boolean enabled;
    public Switch enableSwitch;
    NoModsFragment nomods = new NoModsFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        view = inflater.inflate(R.layout.fragment_main, container, false);

        SharedPreferences sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);
        Switch modInstalledSwitch = (Switch) view.findViewById(R.id.has_mod_switch);

        modInstalledSwitch.setChecked(sharedPrefs.getBoolean("hasmod", true));
        if (sharedPrefs.getBoolean("hasmod", true)) {
            modInstalledBool = true;
        }

        modInstalledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("hasmod", true);
                    editor.apply();
                    NavigationView navView = (NavigationView) activity.findViewById(R.id.nav_view);
                    Menu navMenu = navView.getMenu();
                    navMenu.findItem(R.id.nav_mods).setVisible(true);
                    Toast.makeText(activity.getApplicationContext(), "Extra mods enabled", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("hasmod", false);
                    editor.apply();
                    NavigationView navView = (NavigationView) activity.findViewById(R.id.nav_view);
                    Menu navMenu = navView.getMenu();
                    navMenu.findItem(R.id.nav_mods).setVisible(false);
                    Toast.makeText(activity.getApplicationContext(), "Extra mods disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        enableSwitch = (Switch) view.findViewById(R.id.enable_switch);

        enableSwitch.setChecked(sharedPrefs.getBoolean("enabled", true));
        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        try {
            modEnable();
        } catch (Exception e) {}

        return view;
    }

    public void modEnable() throws IOException {
        enableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("enabled", true);
                    editor.apply();
                    Toast.makeText(activity.getApplicationContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    enabled = true;
                    NavigationView navView = (NavigationView) activity.findViewById(R.id.nav_view);
                    Menu navMenu = navView.getMenu();
                    navMenu.findItem(R.id.nav_nomods).setVisible(true);
                    navMenu.findItem(R.id.nav_mods).setVisible(true);
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("enabled", false);
                    editor.putBoolean("minbatsui", false);
                    editor.putBoolean("minbataod", false);
                    editor.putBoolean("signal_wide", false);
                    editor.putBoolean("minclocksui", false);
                    editor.putBoolean("minbatimm", false);
                    editor.putBoolean("minclockimm", false);
                    editor.putBoolean("minclockaod", false);

                    editor.putInt("red", 255);
                    editor.putInt("green", 255);
                    editor.putInt("blue", 255);

                    editor.putInt("redsig", 255);
                    editor.putInt("greensig", 255);
                    editor.putInt("bluesig", 255);

                    editor.putInt("redaodsig", 255);
                    editor.putInt("greenaodsig", 255);
                    editor.putInt("blueaodsig", 255);

                    editor.apply();
                    enabled = false;
                    NavigationView navView = (NavigationView) activity.findViewById(R.id.nav_view);
                    Menu navMenu = navView.getMenu();
                    navMenu.findItem(R.id.nav_nomods).setVisible(false);
                    navMenu.findItem(R.id.nav_mods).setVisible(false);
                    Toast.makeText(activity.getApplicationContext(), "Restoring defaults...", Toast.LENGTH_SHORT).show();
//                    nomods.clearAll();

                    Settings.System.putInt(activity.cr, "wide_data", 1);
                    Settings.System.putInt(activity.cr, "bat_stat_stock", 1);
                    Settings.System.putInt(activity.cr, "battery_min_imm", 1);
                    Settings.System.putInt(activity.cr, "min_bat_aod", 1);
                    Settings.System.putInt(activity.cr, "minclocksui", 1);
                    Settings.System.putInt(activity.cr, "minclockimm", 1);
                    Settings.System.putInt(activity.cr, "minclockaod", 1);

                    Settings.System.putInt(activity.getContentResolver(), "red", 255);
                    Settings.System.putInt(activity.getContentResolver(), "green", 255);
                    Settings.System.putInt(activity.getContentResolver(), "blue", 255);

                    Settings.System.putInt(activity.getContentResolver(), "redsig", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greensig", 255);
                    Settings.System.putInt(activity.getContentResolver(), "bluesig", 255);

                    Settings.System.putInt(activity.getContentResolver(), "redsigaod", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greensigaod", 255);
                    Settings.System.putInt(activity.getContentResolver(), "bluesigaod", 255);


                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                sudo("killall com.android.systemui");
                                sudo("killall com.lge.signboard");
                            } catch (Exception e) {
                                Log.e("error", e.getMessage());
                            }
                        }
                    }).start();
                }
            }
        });
    }

    public void sudo(String...strings) throws IOException {
        try{
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            for (String s : strings) {
                outputStream.writeBytes(s+"\n");
                outputStream.flush();
            }

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            try {
                su.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            outputStream.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}