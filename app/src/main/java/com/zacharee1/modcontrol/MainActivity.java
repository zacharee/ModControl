package com.zacharee1.modcontrol;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    public boolean enabled;

    public boolean minbatsuiBool;
    public boolean minbataodBool;
    public boolean minbatimmBool;
    public boolean widedataBool;
    public boolean minclocksuiBool;
    public boolean minclockaodBool;
    public boolean minclockimmBool;

    public Switch batstat;
    public Switch batstatImm;
    public Switch bataod;
    public Switch wideData;
    public Switch clockstat;
    public Switch clockstatImm;
    public Switch clockaod;

    public RadioGroup radioGroup1;
    public RadioGroup radioGroup2;
    public RadioGroup radioGroup3;
    public RadioGroup radioGroup4;

    public Button rebootSysUI;
    public Button rebootSB;

    public ContentResolver cr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Switch enableSwitch = (Switch) findViewById(R.id.enable_switch);
        Switch minbatsui = (Switch) findViewById(R.id.minbatstat_switch);
        Switch minbataod = (Switch) findViewById(R.id.minbataod_switch);
        Switch minbatimm = (Switch) findViewById(R.id.minbatstat_imm_switch);
        Switch widedata = (Switch) findViewById(R.id.wide_data);
        Switch minclocksui = (Switch) findViewById(R.id.minclockstat_switch);
        Switch minclockaod = (Switch) findViewById(R.id.minclockaod_switch);
        Switch minclockimm = (Switch) findViewById(R.id.minclockimm_switch);

        SharedPreferences sharedPrefs = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);

        cr = getContentResolver();

        enableSwitch.setChecked(sharedPrefs.getBoolean("enabled", true));

        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        minbatsui.setChecked(sharedPrefs.getBoolean("minbatsui", true));
        if (sharedPrefs.getBoolean("minbatsui", true)) {
            minbatsuiBool = true;
        }

        minbatimm.setChecked(sharedPrefs.getBoolean("minbatimm", true));
        if (sharedPrefs.getBoolean("minbatimm", true)) {
            minbatimmBool = true;
        }

        minbataod.setChecked(sharedPrefs.getBoolean("minbataod", true));
        if (sharedPrefs.getBoolean("minbataod", true)) {
            minbataodBool = true;
        }

        widedata.setChecked(sharedPrefs.getBoolean("signal_wide", true));
        if (sharedPrefs.getBoolean("signal_wide", true)) {
            widedataBool = true;
        }

        minclocksui.setChecked(sharedPrefs.getBoolean("minclocksui", true));
        if (sharedPrefs.getBoolean("minclocksui", true)) {
            minclocksuiBool = true;
        }

        minclockimm.setChecked(sharedPrefs.getBoolean("minclockimm", true));
        if (sharedPrefs.getBoolean("minclockimm", true)) {
            minclockimmBool = true;
        }

        minclockaod.setChecked(sharedPrefs.getBoolean("minclockaod", true));
        if (sharedPrefs.getBoolean("minclockaod", true)) {
            minclockaodBool = true;
        }

        try {
            Runtime.getRuntime().exec(new String[]{"su", "-", "root"});
            modEnable();
            radEnable();
            minBatSUI();
            minBatAOD();
            minBatImm();
            wideData();
            minClockSUI();
            minClockAOD();
            minClockImm();
            sigOption();
            reboot();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_credits) {
            startActivity(new Intent(MainActivity.this, CreditsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void modEnable() throws IOException {
        Switch enable = (Switch) findViewById(R.id.enable_switch);
        enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("enabled", true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Enabled", Toast.LENGTH_SHORT).show();
                    enabled = true;
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("enabled", false);
                    editor.putBoolean("minbatsui", false);
                    editor.putBoolean("minbataod", false);
                    editor.putBoolean("signal_wide", false);
                    editor.putBoolean("minclocksui", false);
                    editor.putBoolean("minbatimm", false);
                    editor.putBoolean("minclockimm", false);
                    editor.putBoolean("minclockaod", false);
                    editor.apply();
                    enabled = false;
                    Toast.makeText(getApplicationContext(), "Restoring defaults...", Toast.LENGTH_SHORT).show();
                    if (!enabled) {
                        try {
                            clearQTRad(radioGroup1);
                            clearQTRad(radioGroup2);
                            clearSigRad(radioGroup3);
                            clearSigRad(radioGroup4);
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                    bataod.setChecked(false);
                    batstat.setChecked(false);
                    batstatImm.setChecked(false);
                    wideData.setChecked(false);
                    clockstat.setChecked(false);
                    clockstatImm.setChecked(false);
                    clockaod.setChecked(false);

                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                copyZip("qtwhite.zip");
                                copyFile2("installqt", "qtwhite.zip");
                                Thread.sleep(2000);
                                copyZip("sigwhite.zip");
                                copyFile2("installsig", "sigwhite.zip");
                            } catch (Exception e) {
                                Log.e("error", e.getMessage());
                            }
                        }
                    }).start();
                }
            }
        });
    }

    public void reboot() throws IOException {
        rebootSysUI = (Button) findViewById(R.id.restart_sysui);
        rebootSB = (Button) findViewById(R.id.restart_sb);

        rebootSysUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sudo("killall com.android.systemui");
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
        });

        rebootSB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sudo("killall com.lge.signboard");
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
            }
        });
    }

    public void sigOption() throws IOException {
        radioGroup3 = (RadioGroup) findViewById(R.id.color_sig1);
        radioGroup4 = (RadioGroup) findViewById(R.id.color_sig2);
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearSigRad(radioGroup4);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installsig = "installsig";
                if (checkedId == R.id.red_sig) {
                    if (enabled) {
                        final String file = "sigred.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.white_sig) {
                    if (enabled) {
                        final String file = "sigwhite.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.green_sig) {
                    if (enabled) {
                        final String file = "siggreen.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                }
                if (!enabled) {
                    try {
                        clearSigRad(group);
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }
        });

        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearSigRad(radioGroup3);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installsig = "installsig";
                if (checkedId == R.id.purple_sig) {
                    if (enabled) {
                        final String file = "sigpurple.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.dummy_sig) {
                    if (enabled) {
                        final String file = "sigdummy.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.blue_sig) {
                    if (enabled) {
                        final String file = "sigblue.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                }
                if (!enabled) {
                    try {
                        clearSigRad(group);
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }
        });
    }

    public void radEnable() throws IOException {
        radioGroup1 = (RadioGroup) findViewById(R.id.color_tool1);
        radioGroup2 = (RadioGroup) findViewById(R.id.color_tool2);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearQTRad(radioGroup2);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installqt = "installqt";
                if (checkedId == R.id.red_tool) {
                    if (enabled) {
                        final String file = "qtred.zip";
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.white_tool) {
                    if (enabled) {
                        final String file = "qtwhite.zip";
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.green_tool) {
                    if (enabled) {
                        final String file = "qtgreen.zip";
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                }
                if (!enabled) {
                    try {
                        clearQTRad(group);
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearQTRad(radioGroup1);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installqt = "installqt";
                if (checkedId == R.id.purple_tool) {
                    if (enabled) {
                        final String file = "qtpurple.zip";
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.multi_tool) {
                    if (enabled) {
                        final String file = "qtmulti.zip";
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.blue_tool) {
                    if (enabled) {
                        final String file = "qtblue.zip";
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                }
                if (!enabled) {
                    try {
                        clearQTRad(group);
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }
        });
    }

    public void minClockSUI() throws IOException {
        clockstat = (Switch) findViewById(R.id.minclockstat_switch);
        clockstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclocksui", true);
                        editor.apply();

                        Settings.System.putInt(cr, "minclocksui", 0);

                        try {
//                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclocksui", false);
                    editor.apply();

                    Settings.System.putInt(cr, "minclocksui", 1);

                    try {
//                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
                if (!enabled) {
                    clockstat.setChecked(false);
                }
            }
        });
    }

    public void minClockImm() throws IOException {
        clockstatImm = (Switch) findViewById(R.id.minclockimm_switch);
        clockstatImm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclockimm", true);
                        editor.apply();

                        Settings.System.putInt(cr, "minclockimm", 0);

                        try {
//                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclockimm", false);
                    editor.apply();

                    Settings.System.putInt(cr, "minclockimm", 1);

                    try {
//                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
                if (!enabled) {
                    clockstatImm.setChecked(false);
                }
            }
        });
    }

    public void minClockAOD() throws IOException {
        clockstat = (Switch) findViewById(R.id.minclockaod_switch);
        clockstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclockaod", true);
                        editor.apply();

                        Settings.System.putInt(cr, "minclockaod", 0);

                        try {
//                            sudo("killall com.lge.signboard");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclockaod", false);
                    editor.apply();

                    Settings.System.putInt(cr, "minclockaod", 1);

                    try {
//                        sudo("killall com.lge.signboard");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
                if (!enabled) {
                    clockstat.setChecked(false);
                }
            }
        });
    }

    public void wideData() throws IOException {
        wideData = (Switch) findViewById(R.id.wide_data);
        wideData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("signal_wide", true);
                        editor.apply();

                        Settings.System.putInt(cr, "wide_data", 0);

                        try {
//                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("signal_wide", false);
                    editor.apply();

                    Settings.System.putInt(cr, "wide_data", 1);

                    try {
//                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
                if (!enabled) {
                    wideData.setChecked(false);
                }
            }
        });
    }

    public void minBatSUI() throws IOException {
        batstat = (Switch) findViewById(R.id.minbatstat_switch);
        batstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbatsui", true);
                        editor.apply();

                        Settings.System.putInt(cr, "bat_stat_stock", 0);

                        try {
//                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatsui", false);
                    editor.apply();

                    Settings.System.putInt(cr, "bat_stat_stock", 1);

                    try {
//                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }

                }
                if (!enabled) {
                    batstat.setChecked(false);
                }
            }
        });
    }

    public void minBatImm() throws IOException {
        batstatImm = (Switch) findViewById(R.id.minbatstat_imm_switch);
        batstatImm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbatimm", true);
                        editor.apply();

                        Settings.System.putInt(cr, "battery_min_imm", 0);

                        try {
//                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatimm", false);
                    editor.apply();

                    Settings.System.putInt(cr, "battery_min_imm", 1);

                    try {
//                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }

                }
                if (!enabled) {
                    batstatImm.setChecked(false);
                }
            }
        });
    }

    public void minBatAOD() throws IOException {
        bataod = (Switch) findViewById(R.id.minbataod_switch);
        bataod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbataod", true);
                        editor.apply();

                        Settings.System.putInt(cr, "min_bat_aod", 0);

                        try {
//                            sudo("killall com.lge.signboard");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbataod", false);
                    editor.apply();

                    Settings.System.putInt(cr, "min_bat_aod", 1);

                    try {
//                        sudo("killall com.lge.signboard");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
                if (!enabled) {
                    bataod.setChecked(false);
                }
            }
        });
    }

    public void clearQTRad(RadioGroup group) throws IOException {
        group.setOnCheckedChangeListener(null);
        group.clearCheck();
        radEnable();
    }

    public void clearSigRad(RadioGroup group) throws IOException {
        group.setOnCheckedChangeListener(null);
        group.clearCheck();
        sigOption();
    }

    public void copyZip(String targetFile) throws IOException {
        String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
        AssetManager assetManager = getAssets();
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
        }
        try (
                InputStream in =  assetManager.open("zip");
                OutputStream out = new FileOutputStream(targetDirectory + "zip")
        ) {
            copyFile(in, out);
        }
    }

    public void copyFile2(final String targetFile, final String zipFile) throws IOException {
        final String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
        final AssetManager assetManager = getAssets();
        File modFolder = new File(targetDirectory);

        if (!modFolder.isDirectory()) {
            boolean result = modFolder.mkdir();
            if (!result) {
                throw new IOException("Could not create nonexistent mod folder. Abort.");
            }
        }

        new Thread(new Runnable() {
            public void run() {
                try (
                        InputStream in = assetManager.open(targetFile);
                        OutputStream out = new FileOutputStream(targetDirectory + targetFile)
                ) {
                    copyFile(in, out);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                try (
                        InputStream in = assetManager.open("zip");
                        OutputStream out = new FileOutputStream(targetDirectory + "zip")
                ) {
                    copyFile(in, out);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        }).start();

        if (!targetFile.contains("restore")) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        runScript(targetDirectory, targetFile, zipFile);
                    } catch (Exception e) {
                        Log.e("ERROR", e.getMessage());
                    }
                }
            }).start();
        }
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[10240];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public void runScript(final String targetDirectory, final String targetFile, final String zip) throws IOException{
        new Thread(new Runnable() {
            public void run() {
                try {
                    sudo("chmod +x /data/media/0/Zacharee1Mods/" + targetFile);
                    sudo("chmod 777 /data/media/0/Zacharee1Mods/" + targetFile);
                    sudo("sh /data/media/0/Zacharee1Mods/" + targetFile + " " + zip);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        }).start();
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
