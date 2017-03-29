package com.zacharee1.modcontrol;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Zacha on 3/11/2017.
 */

public class CreditsFragment extends Fragment {
    View view;
    MainActivity activity;

    Button playStore;
    Button playStoreInstaller;
    Button XDA;

    public TextView versionName;
    public TextView versionNum;
    public TextView buildDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_credits, container, false);

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        playStore = (Button) view.findViewById(R.id.play_store);
        playStoreInstaller = (Button) view.findViewById(R.id.play_store_installer);
        XDA = (Button) view.findViewById(R.id.xda_thread);

        versionName = (TextView) view.findViewById(R.id.vername);
        versionNum = (TextView) view.findViewById(R.id.vernum);
        buildDate = (TextView) view.findViewById(R.id.build_date);

        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            String version = pInfo.versionName;
            int verCode = pInfo.versionCode;
            versionName.append(version);
            versionNum.append(String.valueOf(verCode));

//            ApplicationInfo ai = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), 0);
//            ZipFile zf = new ZipFile(ai.sourceDir);
//            ZipEntry ze = zf.getEntry("AndroidManifest.xml");
//            long time = ze.getTime();
//            String s = SimpleDateFormat.getInstance().format(new java.util.Date(time));
//            zf.close();
            Date buildD = new Date(BuildConfig.TIMESTAMP);
            buildDate.append(String.valueOf(buildD));
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

        SharedPreferences sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);


        try {
            buttons();
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

        return view;
    }

    public void buttons() {
        playStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=com.zacharee1.modcontrol"));
                        startActivity(intent);
                    }
                }).start();
            }
        });

        playStoreInstaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("market://details?id=zacharee1.com.modinstaller"));
                        startActivity(intent);
                    }
                }).start();
            }
        });

        XDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://forum.xda-developers.com/v20/themes/mod-aosp-signal-bars-t3551350"));
                        startActivity(intent);
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
