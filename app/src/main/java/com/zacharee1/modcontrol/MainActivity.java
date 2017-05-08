package com.zacharee1.modcontrol;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;
import com.jrummyapps.android.colorpicker.ColorPickerDialog;
import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ColorPickerDialogListener {
    public static final int DIALOG_ID_0 = 0;
    public static final int DIALOG_ID_1 = 1;
    public static final int DIALOG_ID_2 = 2;
    public static final int DIALOG_ID_3 = 3;
    public static final int DIALOG_ID_4 = 4;
    public static final int DIALOG_ID_5 = 5;
    public static final int DIALOG_ID_6 = 6;
    public static final int DIALOG_ID_7 = 7;
    public static final int DIALOG_ID_8 = 8;
    public boolean enabled;
    public boolean isV20;

    Handler mHandler;

    NoModsFragment nomods;
    MainFragment mainf;
    ModsFragment mods;
    LogFragment logfrag;

    public IInAppBillingService mService;
    public ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name,
                                       IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
        }
    };

    public int id;

    public boolean modEnabledBool;
    public boolean firstStartRoot;

    public Button rebootSysUI;
    public Button rebootSB;

    public ContentResolver cr;

    public SharedPreferences sharedPrefs;

    public static final int WRITE_EXTERNAL_STORAGE = 1;

    public boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefs = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);
        isDark = sharedPrefs.getBoolean("isDark", false);
        if (isDark) {
            setTheme(R.style.DARK2_NoAppBar);
        } else {
            setTheme(R.style.AppTheme_NoActionBar);
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sudo("pm grant com.zacharee1.systemuituner android.permission.WRITE_SETTINGS ; pm grant com.zacharee1.systemuituner android.permission.WRITE_SECURE_SETTINGS ; pm grant com.zacharee1.systemuituner android.permission.WRITE_EXTERNAL_STORAGE");

        Intent serviceIntent =
                new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        bindService(serviceIntent, mServiceConn, Context.BIND_AUTO_CREATE);

        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("donate");
        final Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle skuDetails = mService.getSkuDetails(3,
                            getPackageName(), "inapp", querySkus);
                } catch (Exception e) {}
            }
        }).start();

        mods = new ModsFragment();
        nomods = new NoModsFragment();
        mainf = new MainFragment();
        logfrag = new LogFragment();
        mHandler = new Handler();

        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        if (sharedPrefs.getBoolean("hasmod", true)) {
            modEnabledBool = true;
        }

        if (sharedPrefs.getBoolean("isv20", true)) {
            isV20 = true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rebootSysUI = (Button) findViewById(R.id.restart_sysui);
        rebootSB = (Button) findViewById(R.id.restart_sb);

        reqPerms();

        cr = getContentResolver();

        if (sharedPrefs.getBoolean("firststart", true)) {
            firstStartRoot = true;
        }

        if (!enabled) {
            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
            Menu navMenu = navView.getMenu();
            navMenu.findItem(R.id.nav_nomods).setVisible(false);
            navMenu.findItem(R.id.nav_mods).setVisible(false);
        } else {
            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
            Menu navMenu = navView.getMenu();
            navMenu.findItem(R.id.nav_nomods).setVisible(true);
            navMenu.findItem(R.id.nav_mods).setVisible(true);
        }

//        if (modEnabledBool) {
//            NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
//            Menu navMenu = navView.getMenu();
//            navMenu.findItem(R.id.nav_mods).setVisible(true);
//        }

        try {
            if (firstStartRoot) firstStart();
            reboot();
            buttons();
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

        id = sharedPrefs.getInt("navkey", R.id.nav_main);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        Menu navMenu = navView.getMenu();
//        if (id != 0) navMenu.findItem(id).setChecked(true);

        if (id == R.id.nav_main) {
            MainFragment fragment = new MainFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
            findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
        } else if (id == R.id.nav_nomods) {
            NoModsFragment fragment = new NoModsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
            findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
        } else if (id == R.id.nav_mods) {
            ModsFragment fragment = new ModsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
            findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
        } else if (id == R.id.nav_settings) {
            SettingsFragment fragment = new SettingsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
            findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
        } else if (id == R.id.nav_credits) {
            CreditsFragment fragment = new CreditsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
            findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_log) {
            LogFragment fragment = new LogFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
            findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
        } else {
            MainFragment fragment = new MainFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
            findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
        }
    }

    public void buttons() {
        FloatingActionButton donate = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override public void onColorSelected(int dialogId, int color) {
        String colorString = Integer.toHexString(color);
        int colorRed = Integer.decode("0x" + colorString.substring(2, 4));
        int colorGreen = Integer.decode("0x" + colorString.substring(4, 6));
        int colorBlue = Integer.decode("0x" + colorString.substring(6));
        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
        switch (dialogId) {
            case DIALOG_ID_0:
                // We got result from the dialog that is shown when clicking on the icon in the action bar.
                Toast.makeText(MainActivity.this, "Selected Color 0: " + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redqt_0", colorRed);
                editor.putInt("greenqt_0", colorGreen);
                editor.putInt("blueqt_0", colorBlue);
                break;
            case DIALOG_ID_1:
                Toast.makeText(MainActivity.this, "Selected Color 1: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redqt_1", colorRed);
                editor.putInt("greenqt_1", colorGreen);
                editor.putInt("blueqt_1", colorBlue);
                break;
            case DIALOG_ID_2:
                Toast.makeText(MainActivity.this, "Selected Color 2: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redqt_2", colorRed);
                editor.putInt("greenqt_2", colorGreen);
                editor.putInt("blueqt_2", colorBlue);
                break;
            case DIALOG_ID_3:
                Toast.makeText(MainActivity.this, "Selected Color 3: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redqt_3", colorRed);
                editor.putInt("greenqt_3", colorGreen);
                editor.putInt("blueqt_3", colorBlue);
                break;
            case DIALOG_ID_4:
                Toast.makeText(MainActivity.this, "Selected Color 4: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redqt_4", colorRed);
                editor.putInt("greenqt_4", colorGreen);
                editor.putInt("blueqt_4", colorBlue);
                break;
            case DIALOG_ID_5:
                Toast.makeText(MainActivity.this, "Selected Color 5: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redqt_5", colorRed);
                editor.putInt("greenqt_5", colorGreen);
                editor.putInt("blueqt_5", colorBlue);
                break;
            case DIALOG_ID_6:
                Toast.makeText(MainActivity.this, "Selected Color 6: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redsig", colorRed);
                editor.putInt("greensig", colorGreen);
                editor.putInt("bluesig", colorBlue);
                break;
            case DIALOG_ID_7:
                Toast.makeText(MainActivity.this, "Selected Color 7: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("redsigaod", colorRed);
                editor.putInt("greensigaod", colorGreen);
                editor.putInt("bluesigaod", colorBlue);
                break;
            case DIALOG_ID_8:
                Toast.makeText(MainActivity.this, "Selected Color 8: #" + colorRed + colorGreen + colorBlue, Toast.LENGTH_SHORT).show();
                editor.putInt("rednav_0", colorRed);
                editor.putInt("greennav_0", colorGreen);
                editor.putInt("bluenav_0", colorBlue);
                break;
        }
        editor.apply();

        NoModsFragment fragment = new NoModsFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
        findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
        findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
    }

    @Override public void onDialogDismissed(int dialogId) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (!drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.openDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
        editor.putInt("navkey", id);
        editor.apply();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        final int mainCont = R.id.content_main;

        if (id == R.id.nav_main) {
            final MainFragment fragment = new MainFragment();
            final FragmentManager fragmentManager = getFragmentManager();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(mainCont, fragment).commit();
                    findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
                    findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
                }
            }, 350);
        } else if (id == R.id.nav_settings) {
            final SettingsFragment fragment = new SettingsFragment();
            final FragmentManager fragmentManager = getFragmentManager();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(mainCont, fragment).commit();
                    findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
                    findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
                }
            }, 350);
        } else if (id == R.id.nav_nomods) {
            final NoModsFragment fragment = new NoModsFragment();
            final FragmentManager fragmentManager = getFragmentManager();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(mainCont, fragment).commit();
                    findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
                    findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
                }
            }, 350);
        } else if (id == R.id.nav_mods) {
            final ModsFragment fragment = new ModsFragment();
            final FragmentManager fragmentManager = getFragmentManager();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(mainCont, fragment).commit();
                    findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
                    findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
                }
            }, 350);
        } else if (id == R.id.nav_credits) {
            final CreditsFragment fragment = new CreditsFragment();
            final FragmentManager fragmentManager = getFragmentManager();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(mainCont, fragment).commit();
                    findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
                    findViewById(R.id.floatingActionButton).setVisibility(View.VISIBLE);
                }
            }, 350);
        } else if (id == R.id.nav_log) {
            final LogFragment fragment = new LogFragment();
            final FragmentManager fragmentManager = getFragmentManager();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(mainCont, fragment).commit();
                    findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
                    findViewById(R.id.floatingActionButton).setVisibility(View.GONE);
                }
            }, 350);
        }
        return true;
    }

    public void reqPerms() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
        }
    }

    public void firstStart() throws IOException {
        Runtime.getRuntime().exec(new String[]{"su", "-", "root"});
        firstStartRoot = false;
        SharedPreferences.Editor editor = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
        editor.putBoolean("firststart", false);
        editor.apply();
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
                            sudo("killall com.lge.signboard");
                            sudo("killall com.lge.appwidget.signature");
                            sudo("killall com.lge.quicktools");
                        } catch (Exception e) {
                            Log.e("ModControl/E", e.getMessage());
                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                        }
                    }
                }).start();
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
