package com.zacharee1.modcontrol;

/**
 * Created by Zacha on 3/10/2017.
 */

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    MainActivity activity;
    public View view;
    public boolean modInstalledBool;
    public boolean enabled;
    public boolean isV20;
    public Switch enableSwitch;

    public RadioGroup modelType;
    public RadioButton modelG5;
    public RadioButton modelV20;

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

        modelType = (RadioGroup) view.findViewById(R.id.model_type);

        modelG5 = (RadioButton) view.findViewById(R.id.model_g5);
        modelV20 = (RadioButton) view.findViewById(R.id.model_v20);

        enableSwitch = (Switch) view.findViewById(R.id.enable_switch);

        enableSwitch.setChecked(sharedPrefs.getBoolean("enabled", true));
        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        if (sharedPrefs.getBoolean("isv20", true)) {
            isV20 = true;
            modelV20.setChecked(true);
        } else {
            isV20 = false;
            modelG5.setChecked(true);
        }

        try {
            modEnable();
            modelType();
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

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

                    editor.putInt("redqt_0", 255);
                    editor.putInt("greenqt_0", 255);
                    editor.putInt("blueqt_0", 255);
                    editor.putInt("redqt_1", 255);
                    editor.putInt("greenqt_1", 255);
                    editor.putInt("blueqt_1", 255);
                    editor.putInt("redqt_2", 255);
                    editor.putInt("greenqt_2", 255);
                    editor.putInt("blueqt_2", 255);
                    editor.putInt("redqt_3", 255);
                    editor.putInt("greenqt_3", 255);
                    editor.putInt("blueqt_3", 255);
                    editor.putInt("redqt_4", 255);
                    editor.putInt("greenqt_4", 255);
                    editor.putInt("blueqt_4", 255);

                    editor.putInt("redsig", 255);
                    editor.putInt("greensig", 255);
                    editor.putInt("bluesig", 255);

                    editor.putInt("redsigaod", 255);
                    editor.putInt("greensigaod", 255);
                    editor.putInt("bluesigaod", 255);

                    editor.putBoolean("poweronplug", false);
                    editor.putBoolean("chargewarning", false);

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

                    Settings.System.putInt(activity.getContentResolver(), "redqt_0", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greenqt_0", 255);
                    Settings.System.putInt(activity.getContentResolver(), "blueqt_0", 255);
                    Settings.System.putInt(activity.getContentResolver(), "redqt_1", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greenqt_1", 255);
                    Settings.System.putInt(activity.getContentResolver(), "blueqt_1", 255);
                    Settings.System.putInt(activity.getContentResolver(), "redqt_2", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greenqt_2", 255);
                    Settings.System.putInt(activity.getContentResolver(), "blueqt_2", 255);
                    Settings.System.putInt(activity.getContentResolver(), "redqt_3", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greenqt_3", 255);
                    Settings.System.putInt(activity.getContentResolver(), "blueqt_3", 255);
                    Settings.System.putInt(activity.getContentResolver(), "redqt_4", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greenqt_4", 255);
                    Settings.System.putInt(activity.getContentResolver(), "blueqt_4", 255);

                    Settings.System.putInt(activity.getContentResolver(), "redsig", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greensig", 255);
                    Settings.System.putInt(activity.getContentResolver(), "bluesig", 255);

                    Settings.System.putInt(activity.getContentResolver(), "redsigaod", 255);
                    Settings.System.putInt(activity.getContentResolver(), "greensigaod", 255);
                    Settings.System.putInt(activity.getContentResolver(), "bluesigaod", 255);

                    Settings.System.putInt(activity.getContentResolver(), "poweronplug", 0);
                    Settings.System.putInt(activity.getContentResolver(), "chargewarning", 1);

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                sudo("killall com.android.systemui ; killall com.lge.signboard ; killall com.lge.quicktools");
                            } catch (Exception e) {
                                Log.e("ModControl/E", e.getMessage());
                                sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                            }
                        }
                    }).start();
                }
            }
        });
    }

    public void modelType() throws IOException {
        modelType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.model_g5) {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("isv20", false);
                    editor.apply();
                } else if (checkedId == R.id.model_v20) {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("isv20", true);
                    editor.apply();
                }
            }
        });
    }

    public void sudo(String...strings) {
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