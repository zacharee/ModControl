package com.zacharee1.modcontrol;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Exchanger;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {
    public boolean enabled;
    public boolean minbatsuiBool;
    public boolean minbataodBool;
    public boolean minbatimmBool;
    public boolean widedataBool;
    public Switch batstat;
    public Switch batstatImm;
    public Switch bataod;
    public Switch wideData;
    public RadioGroup radioGroup1;
    public RadioGroup radioGroup2;
    public int mod_enabled;
    public ContentResolver cr;
    public Context cx;

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

        try {
            Process process = Runtime.getRuntime().exec(new String[]{"su", "-", "root"});
        } catch (Exception e) {

        }
//        enableDisable.run();
//        radCoice.run();
        modEnable();
        radEnable();
        minBatSUI();
        minBatAOD();
        minBatImm();
        wideData();
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void modEnable() {
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
                    editor.apply();
                    enabled = false;
                    Toast.makeText(getApplicationContext(), "Restoring defaults...", Toast.LENGTH_SHORT).show();
                    clearRad(radioGroup1);
                    clearRad(radioGroup2);
                    bataod.setChecked(false);
                    batstat.setChecked(false);
                    wideData.setChecked(false);
//                    restoreAll();
                }
            }
        });
    }

    public void radEnable() {
        radioGroup1 = (RadioGroup) findViewById(R.id.color_tool1);
        radioGroup2 = (RadioGroup) findViewById(R.id.color_tool2);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                clearRad(radioGroup2);
                final String installqt = "installqt";
                if (checkedId == R.id.red_tool) {
                    if (enabled) {
                        final String file = "qtred.zip";
//                        Toast.makeText(getApplicationContext(), "Installing Red...", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getApplicationContext(), "Installing White...", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getApplicationContext(), "Installing White...", Toast.LENGTH_SHORT).show();
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
//                    Toast.makeText(getApplicationContext(), "Please enable the app.", Toast.LENGTH_SHORT).show();
                    clearRad(group);
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                clearRad(radioGroup1);
                final String installqt = "installqt";
                if (checkedId == R.id.purple_tool) {
                    if (enabled) {
                        final String file = "qtpurple.zip";
//                        Toast.makeText(getApplicationContext(), "Installing White...", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getApplicationContext(), "Installing Multi...", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getApplicationContext(), "Installing Blue...", Toast.LENGTH_SHORT).show();
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                }
            }
        });
    }

    public void wideData() {
//        final Switch enable = (Switch) findViewById(R.id.minbatstat_switch);
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
                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
//                        install("sui");
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("signal_wide", false);
                    editor.apply();

                    Settings.System.putInt(cr, "wide_data", 1);

                    try {
                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
//                    restore("sui");

                }
                if (!enabled) {
//                    Toast.makeText(getApplicationContext(), "Please enable the app.", Toast.LENGTH_SHORT).show();
                    wideData.setChecked(false);
                }
            }
        });
    }

    public void minBatSUI() {
//        final Switch enable = (Switch) findViewById(R.id.minbatstat_switch);
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
                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
//                        install("sui");
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatsui", false);
                    editor.apply();

                    Settings.System.putInt(cr, "bat_stat_stock", 1);

                    try {
                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
//                    restore("sui");

                }
                if (!enabled) {
//                    Toast.makeText(getApplicationContext(), "Please enable the app.", Toast.LENGTH_SHORT).show();
                    batstat.setChecked(false);
                }
            }
        });
    }

    public void minBatImm() {
//        final Switch enable = (Switch) findViewById(R.id.minbatstat_switch);
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
                            sudo("killall com.android.systemui");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
//                        install("sui");
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbatimm", false);
                    editor.apply();

                    Settings.System.putInt(cr, "battery_min_imm", 1);

                    try {
                        sudo("killall com.android.systemui");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
//                    restore("sui");

                }
                if (!enabled) {
//                    Toast.makeText(getApplicationContext(), "Please enable the app.", Toast.LENGTH_SHORT).show();
                    batstatImm.setChecked(false);
                }
            }
        });
    }

    public void minBatAOD() {
//        final Switch enable = (Switch) findViewById(R.id.minbataod_switch);
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
                            sudo("killall com.lge.signboard");
                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
//                        install("sb");
                    }
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                    editor.putBoolean("minbataod", false);
                    editor.apply();

                    Settings.System.putInt(cr, "min_bat_aod", 1);

                    try {
                        sudo("killall com.lge.signboard");
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
//                    restore("sb");
                }
                if (!enabled) {
//                    Toast.makeText(getApplicationContext(), "Please enable the app.", Toast.LENGTH_SHORT).show();
                    bataod.setChecked(false);
                }
            }
        });
    }

    public void clearRad(RadioGroup group){
        group.setOnCheckedChangeListener(null);
        if(group != null) group.clearCheck();
        radEnable();
    }

    public void restoreAll() {
        String restore = "restoreall.zip";
        try {
            copyZip(restore);
            copyFile2("restoreall", "restoreall");
            copyFile2("restoreqt", "restoreqt");
            copyFile2("restoresui", "restoresui");
            copyFile2("restoresb", "restoresb");
            runScript("dummy", "restoreall", restore);
            sudo("killall com.android.systemui");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void restore(String which) {
        String restore = "restoreall.zip";
        if (which.equals("qt")) {
            try {
                copyZip(restore);
                copyFile2("restoreqt", "restoreqt");
                runScript("dummy", "restoreqt", restore);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (which.equals("sui")) {
            try {
                copyZip(restore);
                copyFile2("restoresui", "restoresui");
                runScript("dummy", "restoresui", restore);
                sudo("killall com.android.systemui");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (which.equals("sb")) {
            try {
                copyZip(restore);
                copyFile2("restoresb", "restoresb");
                runScript("dummy", "restoresb", restore);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void install(String which) {
        String install;
        if (which.equals("sb")) {
            install = "installsb";
            try {
                copyZip(install + ".zip");
                copyFile2(install, install);
                runScript("dummy", install, install + ".zip");
            } catch (Exception e) {

            }
        } else if (which.equals("sui")) {
            install = "installsui";
            try {
                copyZip(install + ".zip");
                copyFile2(install, install);
                runScript("dummy", install, install + ".zip");
                sudo("killall com.android.systemui");
            } catch (Exception e) {

            }
        }
    }

    public void copyZip(String targetFile) throws IOException {
        String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
        AssetManager assetManager = getAssets();
        File modFolder = new File(targetDirectory);

        if (!modFolder.isDirectory()) {
            modFolder.mkdir();
            if (!modFolder.mkdir()) {
                throw new IOException("Could not create nonexistent mod folder. Abort.");
            }
        }
        try (
                InputStream in = assetManager.open(targetFile);
                OutputStream out = new FileOutputStream(targetDirectory + targetFile);
        ) {
            copyFile(in, out);
        }
        try (
                InputStream in =  assetManager.open("zip");
                OutputStream out = new FileOutputStream(targetDirectory + "zip");
        ) {
            copyFile(in, out);
        }
    }

    public void copyFile2(final String targetFile, final String zipFile) throws IOException {
        final String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
        final AssetManager assetManager = getAssets();
        File modFolder = new File(targetDirectory);

        if (!modFolder.isDirectory()) {
            modFolder.mkdir();
            if (!modFolder.mkdir()) {
                throw new IOException("Could not create nonexistent mod folder. Abort.");
            }
        }

        new Thread(new Runnable() {
            public void run() {
                try (
                        InputStream in = assetManager.open(targetFile);
                        OutputStream out = new FileOutputStream(targetDirectory + targetFile);
                ) {
                    copyFile(in, out);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                }
                try (
                        InputStream in = assetManager.open("zip");
                        OutputStream out = new FileOutputStream(targetDirectory + "zip");
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
//        Toast.makeText(getApplicationContext(), "Copy...", Toast.LENGTH_SHORT).show();
        byte[] buffer = new byte[10240];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
//        Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
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
//        sudo("killall com.lge.quicktools");
//        sudo("killall com.lge.signboard");
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
