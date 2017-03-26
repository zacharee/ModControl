package com.zacharee1.modcontrol;

/**
 * activity.created by Zacha on 3/10/2017.
 */

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class ModsFragment extends Fragment {
    public View view;
    MainActivity activity;

    public SharedPreferences sharedPrefs;
    public boolean enabled;
    public boolean isV20;

    public Switch batstat;
    public Switch bataod;
    public Switch batstatImm;
    public Switch wideData;
    public Switch clockstat;
    public Switch clockaod;
    public Switch clockstatImm;
    public Switch poweronplug;

    public boolean minbatsuiBool;
    public boolean minbatimmBool;
    public boolean minbataodBool;
    public boolean minclocksuiBool;
    public boolean minclockimmBool;
    public boolean minclockaodBool;
    public boolean widedataBool;
    public boolean poweronplugBool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        view = inflater.inflate(R.layout.fragment_mods, container, false);

        sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);

        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        batstat = (Switch) view.findViewById(R.id.minbatstat_switch);
        bataod = (Switch) view.findViewById(R.id.minbataod_switch);
        batstatImm = (Switch) view.findViewById(R.id.minbatstat_imm_switch);
        wideData = (Switch) view.findViewById(R.id.wide_data);
        clockstat = (Switch) view.findViewById(R.id.minclockstat_switch);
        clockaod = (Switch) view.findViewById(R.id.minclockaod_switch);
        clockstatImm = (Switch) view.findViewById(R.id.minclockimm_switch);
        poweronplug = (Switch) view.findViewById(R.id.poweron_plug);

        batstat.setChecked(sharedPrefs.getBoolean("minbatsui", false));
        if (sharedPrefs.getBoolean("minbatsui", false)) {
            minbatsuiBool = true;
        }

        batstatImm.setChecked(sharedPrefs.getBoolean("minbatimm", false));
        if (sharedPrefs.getBoolean("minbatimm", false)) {
            minbatimmBool = true;
        }

        bataod.setChecked(sharedPrefs.getBoolean("minbataod", false));
        if (sharedPrefs.getBoolean("minbataod", false)) {
            minbataodBool = true;
        }

        wideData.setChecked(sharedPrefs.getBoolean("signal_wide", false));
        if (sharedPrefs.getBoolean("signal_wide", false)) {
            widedataBool = true;
        }

        clockstat.setChecked(sharedPrefs.getBoolean("minclocksui", false));
        if (sharedPrefs.getBoolean("minclocksui", false)) {
            minclocksuiBool = true;
        }

        clockstatImm.setChecked(sharedPrefs.getBoolean("minclockimm", false));
        if (sharedPrefs.getBoolean("minclockimm", false)) {
            minclockimmBool = true;
        }

        clockaod.setChecked(sharedPrefs.getBoolean("minclockaod", false));
        if (sharedPrefs.getBoolean("minclockaod", false)) {
            minclockaodBool = true;
        }

        poweronplug.setChecked(sharedPrefs.getBoolean("poweronplug", false));
        if (sharedPrefs.getBoolean("poweronplug", false)) {
            poweronplugBool = true;
        }

        if (sharedPrefs.getBoolean("isv20", false)) {
            isV20 = true;
        } else {
            isV20 = false;
            view.findViewById(R.id.minbatstat_imm_switch).setVisibility(View.GONE);
            view.findViewById(R.id.minclockimm_switch).setVisibility(View.GONE);
        }

        try {
            wideData();
            minBatImm();
            minBatSUI();
            minBatAOD();
            minClockAOD();
            minClockImm();
            minClockSUI();
            powerOnPlug();
        } catch (Exception e) {}

        return view;
    }

    public void powerOnPlug() throws IOException {
        poweronplug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("poweronplug", true);
                        editor.apply();

                        Settings.System.putInt(activity.cr, "usb_plugged", 1);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("poweronplug", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "usb_plugged", 0);
                }
                if (!enabled) {
                    poweronplug.setChecked(false);
                }
            }
        });
    }

    public void wideData() throws IOException {
        wideData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("signal_wide", true);
                        editor.apply();

                        Settings.System.putInt(activity.cr, "wide_data", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("signal_wide", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "wide_data", 1);
                }
                if (!enabled) {
                    wideData.setChecked(false);
                }
            }
        });
    }

    public void minBatSUI() throws IOException {
        batstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbatsui", true);
                        editor.apply();

                        Settings.System.putInt(activity.cr, "bat_stat_stock", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatsui", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "bat_stat_stock", 1);

                }
                if (!enabled) {
                    batstat.setChecked(false);
                }
            }
        });
    }

    public void minBatImm() throws IOException {
        batstatImm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbatimm", true);
                        editor.apply();

                        Settings.System.putInt(activity.cr, "battery_min_imm", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatimm", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "battery_min_imm", 1);
                }
                if (!enabled) {
                    batstatImm.setChecked(false);
                }
            }
        });
    }

    public void minBatAOD() throws IOException {
        bataod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbataod", true);
                        editor.apply();

                        Settings.System.putInt(activity.cr, "min_bat_aod", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbataod", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "min_bat_aod", 1);
                }
                if (!enabled) {
                    bataod.setChecked(false);
                }
            }
        });
    }

    public void minClockSUI() throws IOException {
        clockstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclocksui", true);
                        editor.apply();

                        Settings.System.putInt(activity.cr, "minclocksui", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclocksui", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "minclocksui", 1);
                }
                if (!enabled) {
                    clockstat.setChecked(false);
                }
            }
        });
    }

    public void minClockImm() throws IOException {
        clockstatImm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclockimm", true);
                        editor.apply();

                        Settings.System.putInt(activity.cr, "minclockimm", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclockimm", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "minclockimm", 1);
                }
                if (!enabled) {
                    clockstatImm.setChecked(false);
                }
            }
        });
    }

    public void minClockAOD() throws IOException {
        clockaod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclockaod", true);
                        editor.apply();
                        Settings.System.putInt(activity.cr, "minclockaod", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclockaod", false);
                    editor.apply();

                    Settings.System.putInt(activity.cr, "minclockaod", 1);
                }
                if (!enabled) {
                    clockaod.setChecked(false);
                }
            }
        });
    }
}