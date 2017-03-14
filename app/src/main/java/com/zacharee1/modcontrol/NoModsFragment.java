package com.zacharee1.modcontrol;

/**
 * Created by Zacha on 3/10/2017.
 */

import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.Context.MODE_PRIVATE;


public class NoModsFragment extends Fragment {
    public View view;
    MainActivity activity;

    public boolean enabled;
    public SharedPreferences sharedPrefs;

    public RadioGroup qtGroup1;
    public RadioGroup qtGroup2;
    public RadioGroup qtGroup3;
    public RadioGroup sigGroup1;
    public RadioGroup sigGroup2;
    public RadioGroup sigGroup3;
    public RadioGroup aodSigGroup1;
    public RadioGroup aodSigGroup2;
    public RadioGroup aodSigGroup3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        view = inflater.inflate(R.layout.fragment_nomods, container, false);

        sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);

        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        qtGroup1 = (RadioGroup) view.findViewById(R.id.color_tool1);
        qtGroup2 = (RadioGroup) view.findViewById(R.id.color_tool2);
        qtGroup3 = (RadioGroup) view.findViewById(R.id.color_tool3);
        sigGroup1 = (RadioGroup) view.findViewById(R.id.color_sig1);
        sigGroup2 = (RadioGroup) view.findViewById(R.id.color_sig2);
        sigGroup3 = (RadioGroup) view.findViewById(R.id.color_sig3);
        aodSigGroup1 = (RadioGroup) view.findViewById(R.id.color_aod_sig1);
        aodSigGroup2 = (RadioGroup) view.findViewById(R.id.color_aod_sig2);
        aodSigGroup3 = (RadioGroup) view.findViewById(R.id.color_aod_sig3);

        try {
            qtOption();
            sigOption();
            aodSigOption();
        } catch (Exception e) {}

        return view;
    }

    public void clearAll() {
        try {
            clearQTRad(qtGroup1);
            clearQTRad(qtGroup2);
            clearQTRad(qtGroup3);
        } catch (Exception e) {}
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("error", e.getMessage());
                        }
                    }
                } else if (checkedId == R.id.yellow_tool) {
                    if (enabled) {
                        final String file = "qtyellow.zip";
                        try {
                            copyZip(file);
                            copyFile2(installqt, file);
                        } catch (Exception e) {
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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

    public void sigOption() throws IOException {
        sigGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearSigRad(sigGroup2);
                    clearSigRad(sigGroup3);
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                    clearSigRad(sigGroup3);
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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

        sigGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearSigRad(sigGroup2);
                    clearSigRad(sigGroup1);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installsig = "installsig";
                if (checkedId == R.id.yellow_sig) {
                    if (enabled) {
                        final String file = "sigyellow.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                    clearAODSigRad(aodSigGroup3);
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                    clearAODSigRad(aodSigGroup3);
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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

        aodSigGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                try {
                    clearAODSigRad(aodSigGroup2);
                    clearAODSigRad(aodSigGroup1);
                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                final String installsig = "installsigaod";
                if (checkedId == R.id.purple_aod_sig) {
                    if (enabled) {
                        final String file = "aodsigyellow.zip";
                        try {
                            copyZip(file);
                            copyFile2(installsig, file);
                        } catch (Exception e) {
                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
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
        AssetManager assetManager = activity.getAssets1();
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
        final AssetManager assetManager = activity.getAssets1();
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