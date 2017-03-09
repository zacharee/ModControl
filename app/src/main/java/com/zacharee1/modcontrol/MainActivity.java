package com.zacharee1.modcontrol;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
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

    public Switch enableSwitch;

    public boolean minbatsuiBool;
    public boolean minbataodBool;
    public boolean minbatimmBool;
    public boolean widedataBool;
    public boolean minclocksuiBool;
    public boolean minclockaodBool;
    public boolean minclockimmBool;
    public boolean modInstalledBool;

    public boolean firstStartRoot;

    public LinearLayout needsMod;

    public Switch batstat;
    public Switch batstatImm;
    public Switch bataod;
    public Switch wideData;
    public Switch clockstat;
    public Switch clockstatImm;
    public Switch clockaod;
    public Switch modInstalledSwitch;

    public RadioGroup qtGroup1;
    public RadioGroup qtGroup2;
    public RadioGroup qtGroup3;
    public RadioGroup sigGroup1;
    public RadioGroup sigGroup2;
    public RadioGroup aodSigGroup1;
    public RadioGroup aodSigGroup2;

    public Button rebootSysUI;
    public Button rebootSB;

    public ContentResolver cr;

    public SharedPreferences sharedPrefs;

    public static final int WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enableSwitch = (Switch) findViewById(R.id.enable_switch);
        batstat = (Switch) findViewById(R.id.minbatstat_switch);
        bataod = (Switch) findViewById(R.id.minbataod_switch);
        batstatImm = (Switch) findViewById(R.id.minbatstat_imm_switch);
        wideData = (Switch) findViewById(R.id.wide_data);
        clockstat = (Switch) findViewById(R.id.minclockstat_switch);
        clockaod = (Switch) findViewById(R.id.minclockaod_switch);
        clockstatImm = (Switch) findViewById(R.id.minclockimm_switch);
        modInstalledSwitch = (Switch) findViewById(R.id.has_mod_switch);

        needsMod = (LinearLayout) findViewById(R.id.needs_mod);

        rebootSysUI = (Button) findViewById(R.id.restart_sysui);
        rebootSB = (Button) findViewById(R.id.restart_sb);

        qtGroup1 = (RadioGroup) findViewById(R.id.color_tool1);
        qtGroup2 = (RadioGroup) findViewById(R.id.color_tool2);
        qtGroup3 = (RadioGroup) findViewById(R.id.color_tool3);
        sigGroup1 = (RadioGroup) findViewById(R.id.color_sig1);
        sigGroup2 = (RadioGroup) findViewById(R.id.color_sig2);
        aodSigGroup1 = (RadioGroup) findViewById(R.id.color_aod_sig1);
        aodSigGroup2 = (RadioGroup) findViewById(R.id.color_aod_sig2);

        reqPerms();

        sharedPrefs = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);

        cr = getContentResolver();

        enableSwitch.setChecked(sharedPrefs.getBoolean("enabled", true));

        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        batstat.setChecked(sharedPrefs.getBoolean("minbatsui", true));
        if (sharedPrefs.getBoolean("minbatsui", true)) {
            minbatsuiBool = true;
        }

        batstatImm.setChecked(sharedPrefs.getBoolean("minbatimm", true));
        if (sharedPrefs.getBoolean("minbatimm", true)) {
            minbatimmBool = true;
        }

        bataod.setChecked(sharedPrefs.getBoolean("minbataod", true));
        if (sharedPrefs.getBoolean("minbataod", true)) {
            minbataodBool = true;
        }

        wideData.setChecked(sharedPrefs.getBoolean("signal_wide", true));
        if (sharedPrefs.getBoolean("signal_wide", true)) {
            widedataBool = true;
        }

        clockstat.setChecked(sharedPrefs.getBoolean("minclocksui", true));
        if (sharedPrefs.getBoolean("minclocksui", true)) {
            minclocksuiBool = true;
        }

        clockstatImm.setChecked(sharedPrefs.getBoolean("minclockimm", true));
        if (sharedPrefs.getBoolean("minclockimm", true)) {
            minclockimmBool = true;
        }

        clockaod.setChecked(sharedPrefs.getBoolean("minclockaod", true));
        if (sharedPrefs.getBoolean("minclockaod", true)) {
            minclockaodBool = true;
        }

        modInstalledSwitch.setChecked(sharedPrefs.getBoolean("hasmod", true));
        if (sharedPrefs.getBoolean("hasmod", true)) {
            modInstalledBool = true;
            needsMod.setVisibility(View.VISIBLE);
        }

        if (sharedPrefs.getBoolean("firststart", true)) {
            firstStartRoot = true;
        }

        try {
            if (firstStartRoot) firstStart();
            reboot();
            modEnable();
            qtOption();
            minBatSUI();
            minBatAOD();
            minBatImm();
            wideData();
            minClockSUI();
            minClockAOD();
            minClockImm();
            sigOption();
            aodSigOption();
            modInstalled();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//            return true;
//        } else if (id == R.id.action_credits) {
//            startActivity(new Intent(MainActivity.this, CreditsActivity.class));
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public void reqPerms() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
            return;
        }
    }

    public void firstStart() throws IOException {
        Runtime.getRuntime().exec(new String[]{"su", "-", "root"});
        firstStartRoot = false;
        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
        editor.putBoolean("firststart", false);
        editor.apply();
    }

    public void modEnable() throws IOException {
        enableSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                            clearQTRad(qtGroup1);
                            clearQTRad(qtGroup2);
                            clearQTRad(qtGroup3);
                            clearSigRad(sigGroup1);
                            clearSigRad(sigGroup2);
                            clearAODSigRad(aodSigGroup1);
                            clearAODSigRad(aodSigGroup2);
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                    if (modInstalledBool) {
                        bataod.setChecked(false);
                        batstat.setChecked(false);
                        batstatImm.setChecked(false);
                        wideData.setChecked(false);
                        clockstat.setChecked(false);
                        clockstatImm.setChecked(false);
                        clockaod.setChecked(false);
                    }

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

    public void modInstalled() throws IOException {
        modInstalledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("hasmod", true);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Extra mods enabled", Toast.LENGTH_SHORT).show();
                    needsMod.setVisibility(View.VISIBLE);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("hasmod", false);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Extra mods disabled", Toast.LENGTH_SHORT).show();
                    needsMod.setVisibility(View.GONE);
                }
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
                            Log.e("error", e.getMessage());
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
                            sudo("killall com.lge.signboard");
                            sudo("killall com.lge.appwidget.signature");
                            sudo("killall com.lge.quicktools");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                }).start();
            }
        });
    }

    public void sigOption() throws IOException {
        sigGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearSigRad(sigGroup2);
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

        sigGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearSigRad(sigGroup1);
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
                } else if (checkedId == R.id.orange_sig) {
                    if (enabled) {
                        final String file = "sigorange.zip";
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

    public void aodSigOption() throws IOException {
        aodSigGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearAODSigRad(aodSigGroup2);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installsig = "installsigaod";
                if (checkedId == R.id.red_aod_sig) {
                    if (enabled) {
                        final String file = "aodsigred.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.white_aod_sig) {
                    if (enabled) {
                        final String file = "aodsigwhite.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.green_aod_sig) {
                    if (enabled) {
                        final String file = "aodsiggreen.zip";
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
                        clearAODSigRad(group);
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }
        });

        aodSigGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearAODSigRad(aodSigGroup1);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installsig = "installsigaod";
                if (checkedId == R.id.purple_aod_sig) {
                    if (enabled) {
                        final String file = "aodsigpurple.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.orange_aod_sig) {
                    if (enabled) {
                        final String file = "aodsigorange.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.blue_aod_sig) {
                    if (enabled) {
                        final String file = "aodsigblue.zip";
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
                        clearAODSigRad(group);
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }
            }
        });
    }

    public void qtOption() throws IOException {
        qtGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearQTRad(qtGroup2);
                    clearQTRad(qtGroup3);
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

        qtGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearQTRad(qtGroup1);
                    clearQTRad(qtGroup3);
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

        qtGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearQTRad(qtGroup1);
                    clearQTRad(qtGroup2);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installqt = "installqt";
                if (checkedId == R.id.orange_tool) {
                    if (enabled) {
                        final String file = "qtorange.zip";
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
        clockstat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclocksui", true);
                        editor.apply();

                        Settings.System.putInt(cr, "minclocksui", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclocksui", false);
                    editor.apply();

                    Settings.System.putInt(cr, "minclocksui", 1);
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
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclockimm", true);
                        editor.apply();

                        Settings.System.putInt(cr, "minclockimm", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclockimm", false);
                    editor.apply();

                    Settings.System.putInt(cr, "minclockimm", 1);
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
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minclockaod", true);
                        editor.apply();

                        Settings.System.putInt(cr, "minclockaod", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minclockaod", false);
                    editor.apply();

                    Settings.System.putInt(cr, "minclockaod", 1);
                }
                if (!enabled) {
                    clockaod.setChecked(false);
                }
            }
        });
    }

    public void wideData() throws IOException {
        wideData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (enabled) {
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("signal_wide", true);
                        editor.apply();

                        Settings.System.putInt(cr, "wide_data", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("signal_wide", false);
                    editor.apply();

                    Settings.System.putInt(cr, "wide_data", 1);
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
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbatsui", true);
                        editor.apply();

                        Settings.System.putInt(cr, "bat_stat_stock", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatsui", false);
                    editor.apply();

                    Settings.System.putInt(cr, "bat_stat_stock", 1);

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
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbatimm", true);
                        editor.apply();

                        Settings.System.putInt(cr, "battery_min_imm", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatimm", false);
                    editor.apply();

                    Settings.System.putInt(cr, "battery_min_imm", 1);
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
                        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putBoolean("minbataod", true);
                        editor.apply();

                        Settings.System.putInt(cr, "min_bat_aod", 0);
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbataod", false);
                    editor.apply();

                    Settings.System.putInt(cr, "min_bat_aod", 1);
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
        qtOption();
    }

    public void clearSigRad(RadioGroup group) throws IOException {
        group.setOnCheckedChangeListener(null);
        group.clearCheck();
        sigOption();
    }

    public void clearAODSigRad(RadioGroup group) throws IOException {
        group.setOnCheckedChangeListener(null);
        group.clearCheck();
        aodSigOption();
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
