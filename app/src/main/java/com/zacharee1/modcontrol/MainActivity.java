package com.zacharee1.modcontrol;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public boolean enabled;
    public boolean isV20;

    Handler mHandler;

    NoModsFragment nomods;
    MainFragment mainf;
    ModsFragment mods;
    LogFragment logfrag;

    public int id;

    public boolean modEnabledBool;
    public boolean firstStartRoot;

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

        mods = new ModsFragment();
        nomods = new NoModsFragment();
        mainf = new MainFragment();
        logfrag = new LogFragment();
        mHandler = new Handler();

        sharedPrefs = getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);

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
//        drawer.setDrawerListener(toggle);
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
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

        id = sharedPrefs.getInt("navkey", 0);

        if (id == R.id.nav_main) {
            MainFragment fragment = new MainFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
        } else if (id == R.id.nav_nomods) {
            NoModsFragment fragment = new NoModsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
        } else if (id == R.id.nav_mods) {
            ModsFragment fragment = new ModsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_settings) {
            SettingsFragment fragment = new SettingsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
        } else if (id == R.id.nav_credits) {
            CreditsFragment fragment = new CreditsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
        } else if (id == R.id.nav_log) {
            LogFragment fragment = new LogFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
        } else {
            MainFragment fragment = new MainFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
            findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
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
                }
            }, 350);
        } else if (id == R.id.nav_mods) {
            final ModsFragment fragment = new ModsFragment();
            final FragmentManager fragmentManager = getFragmentManager();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    fragmentManager.beginTransaction().replace(mainCont, fragment).commit();
                    findViewById(R.id.reboot_buttons).setVisibility(View.VISIBLE);
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
