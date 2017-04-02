package com.zacharee1.modcontrol;

/**
 * activity.created by Zacha on 3/10/2017.
 */

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    public Switch chargewarning;
    public Switch ssCap;

    public boolean minbatsuiBool;
    public boolean minbatimmBool;
    public boolean minbataodBool;
    public boolean minclocksuiBool;
    public boolean minclockimmBool;
    public boolean minclockaodBool;
    public boolean widedataBool;
    public boolean poweronplugBool;
    public boolean chargewarningBool;
    public boolean ssCapBool;

    public Button rebootSysUI;
    public Button rebootSB;

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
        chargewarning = (Switch) view.findViewById(R.id.warn_charge);
        ssCap = (Switch) view.findViewById(R.id.sb_shot);

        rebootSysUI = (Button) view.findViewById(R.id.restart_sysui);
        rebootSB = (Button) view.findViewById(R.id.restart_sb);

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

        chargewarning.setChecked(sharedPrefs.getBoolean("chargewarning", false));
        if (sharedPrefs.getBoolean("chargewarning", false)) {
            chargewarningBool = true;
        }

        ssCap.setChecked(sharedPrefs.getBoolean("sb_shot", false));
        if (sharedPrefs.getBoolean("sb_shot", false)) {
            ssCapBool = true;
        }

        if (sharedPrefs.getBoolean("isv20", false)) {
            isV20 = true;
        } else {
            isV20 = false;
            view.findViewById(R.id.minbatstat_imm_switch).setVisibility(View.GONE);
            view.findViewById(R.id.minclockimm_switch).setVisibility(View.GONE);
        }

        try {
//            wideData();
//            minBatImm();
//            minBatSUI();
//            minBatAOD();
//            minClockAOD();
//            minClockImm();
//            minClockSUI();
//            powerOnPlug();
//            chargeWarning();

            setSwitchListener(chargewarning, "chargewarning", "charge_warning");
            setSwitchListener(poweronplug, "poweronplug", "usb_plugged");
            setSwitchListener(wideData, "signal_wide", "wide_data");
            setSwitchListener(batstat, "minbatsui", "bat_stat_stock");
            setSwitchListener(batstatImm, "minbatimm", "battery_min_imm");
            setSwitchListener(bataod, "minbataod", "min_bat_aod");
            setSwitchListener(clockstat, "minclocksui", "minclocksui");
            setSwitchListener(clockstatImm, "minclockimm", "minclockimm");
            setSwitchListener(clockaod, "minclockaod", "minclockaod");
            buildProp(ssCap, "sys.capture_signboard.enabled");
            reboot();
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

        return view;
    }

    public void buildProp(Switch toggle, final String key) throws IOException {
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String value;
                SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();

                if (isChecked) {
                    value = "true";
                    editor.putBoolean("sb_shot", true);
                } else {
                    value = "false";
                    editor.putBoolean("sb_shot", false);
                }

                editor.apply();

                try {
                    copyFile2("buildprop");
                } catch (Exception e) {}
                sudo("sh /data/media/0/Zacharee1Mods/buildprop " + key + " " + value + " >> /data/media/0/Zacharee1Mods/output.log 2>&1");
            }
        });

    }

    public void reboot() throws IOException {

        rebootSysUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("ModControl/E", e.getMessage());
                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                        }
                    }
                }).start();
            }
        });

        rebootSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            sudo("killall com.lge.signboard ; killall com.lge.quicktools");
                        } catch (Exception e) {
                            Log.e("ModControl/E", e.getMessage());
                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                        }
                    }
                }).start();
            }
        });
    }

    public void logger(final String sharedP, final String settings, final boolean tralse, int tf) {
        try {
            copyFile2("logstuff");
            sudo("sh /data/media/0/Zacharee1Mods/logstuff " + sharedP + " " + tralse + " " + settings + " " + tf + " >> /data/media/0/Zacharee1Mods/output.log 2>&1");
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
        }
    }

    public void setSwitchListener(final Switch toggle, final String sharedP, final String settings) throws IOException {
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean(sharedP, true);
                        editor.apply();

                        if (toggle == poweronplug) {
                            Settings.System.putInt(activity.cr, settings, 1);
                            logger(sharedP, settings, true, 1);
                        } else {
                            Settings.System.putInt(activity.cr, settings, 0);
                            logger(sharedP, settings, true, 0);
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean(sharedP, false);
                    editor.apply();

                    if (toggle == poweronplug) {
                        Settings.System.putInt(activity.cr, settings, 0);
                        logger(sharedP, settings, false, 0);
                    } else {
                        Settings.System.putInt(activity.cr, settings, 1);
                        logger(sharedP, settings, false, 1);
                    }
                }

                if (!enabled) {
                    toggle.setChecked(false);
                }
            }
        });
    }

//    public void chargeWarning() throws IOException {
//        chargewarning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("chargewarning", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "charge_warning", 0);
//
//                        sudo("echo \"ModControl/I: chargewarning/charge_warning set 0\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("chargewarning", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "charge_warning", 1);
//
//                    sudo("echo \"ModControl/I: chargewarning/charge_warning set 1\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                }
//                if (!enabled) {
//                    chargewarning.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void powerOnPlug() throws IOException {
//        poweronplug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("poweronplug", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "usb_plugged", 1);
//
//                        sudo("echo \"ModControl/I: poweronplug/usb_plugged set 1\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("poweronplug", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "usb_plugged", 0);
//
//                    sudo("echo \"ModControl/I: poweronplug/usb_plugged set 0\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                }
//                if (!enabled) {
//                    poweronplug.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void wideData() throws IOException {
//        wideData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("signal_wide", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "wide_data", 0);
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("signal_wide", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "wide_data", 1);
//                }
//                if (!enabled) {
//                    wideData.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void minBatSUI() throws IOException {
//        batstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("minbatsui", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "bat_stat_stock", 0);
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("minbatsui", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "bat_stat_stock", 1);
//
//                }
//                if (!enabled) {
//                    batstat.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void minBatImm() throws IOException {
//        batstatImm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("minbatimm", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "battery_min_imm", 0);
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("minbatimm", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "battery_min_imm", 1);
//                }
//                if (!enabled) {
//                    batstatImm.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void minBatAOD() throws IOException {
//        bataod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("minbataod", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "min_bat_aod", 0);
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("minbataod", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "min_bat_aod", 1);
//                }
//                if (!enabled) {
//                    bataod.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void minClockSUI() throws IOException {
//        clockstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("minclocksui", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "minclocksui", 0);
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("minclocksui", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "minclocksui", 1);
//                }
//                if (!enabled) {
//                    clockstat.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void minClockImm() throws IOException {
//        clockstatImm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("minclockimm", true);
//                        editor.apply();
//
//                        Settings.System.putInt(activity.cr, "minclockimm", 0);
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("minclockimm", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "minclockimm", 1);
//                }
//                if (!enabled) {
//                    clockstatImm.setChecked(false);
//                }
//            }
//        });
//    }
//
//    public void minClockAOD() throws IOException {
//        clockaod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    if (enabled) {
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putBoolean("minclockaod", true);
//                        editor.apply();
//                        Settings.System.putInt(activity.cr, "minclockaod", 0);
//                    }
//                } else {
//                    SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                    editor.putBoolean("minclockaod", false);
//                    editor.apply();
//
//                    Settings.System.putInt(activity.cr, "minclockaod", 1);
//                }
//                if (!enabled) {
//                    clockaod.setChecked(false);
//                }
//            }
//        });
//    }

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

    public void copyFile2(final String targetFile) throws IOException {
        final String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
        final AssetManager assetManager = activity.getAssets();
        File modFolder = new File(targetDirectory);

        if (!modFolder.isDirectory()) {
            boolean result = modFolder.mkdir();
            if (!result) {
                throw new IOException("Could not create nonexistent mod folder. Abort.");
            }
        }

        try (
                InputStream in = assetManager.open(targetFile);
                OutputStream out = new FileOutputStream(targetDirectory + targetFile)
        ) {
            copyFile(in, out);
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
        }
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[10240];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}