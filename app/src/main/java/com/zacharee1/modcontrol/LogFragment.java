package com.zacharee1.modcontrol;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Zacha on 3/24/2017.
 */

public class LogFragment extends Fragment {
    View view;
    MainActivity activity;
    Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log, container, false);
        ctx = getContext();

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        try {
            buttons();
            logView();
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

        return view;
    }

    public void buttons() throws IOException {
        Button refresh = (Button) view.findViewById(R.id.refresh_log);
        Button scrolldown = (Button) view.findViewById(R.id.scroll_bottom);
        Button delLog = (Button) view.findViewById(R.id.delete_log);
        final ScrollView scroll = (ScrollView) view.findViewById(R.id.log_scroll);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    logView();
                } catch (Exception e) {
                    Log.e("ModControl/E", e.getMessage());
                    sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                }
            }
        });

        scrolldown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                scroll.fullScroll(View.FOCUS_DOWN);
                            } catch (Exception e) {
                                Log.e("ModControl/E", e.getMessage());
                                sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("ModControl/E", e.getMessage());
                    sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                }
            }
        });

        delLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new AlertDialog.Builder(ctx)
                            .setIcon(R.drawable.ic_warning)
                            .setTitle("Deleting Log")
                            .setMessage("Are you sure you want to delete the log?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                        sudo("rm /data/media/0/Zacharee1Mods/output.log");
                                        LogFragment fragment = new LogFragment();
                                        FragmentManager fragmentManager = getFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.content_main, fragment).commit();
                                        activity.findViewById(R.id.reboot_buttons).setVisibility(View.GONE);
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                } catch (Exception e) {
                    Log.e("ModControl/E", e.getMessage());
                    sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
                }
            }
        });
    }

    public void logView() throws IOException {
        String log = getStringFromFile(Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        TextView logView = (TextView) view.findViewById(R.id.log_text);
        log = log.replace("\n", "<br />");
        log = log.replace("try", "<font color='#FFA500'>try</font>");
        log = log.replace("mkdir", "<font color='#FFA500'>mkdir</font>");
        log = log.replace("cp", "<font color='#FFA500'>cp</font>");
        log = log.replace("chmod", "<font color='#FFA500'>chmod</font>");
        log = log.replace("Archive", "<font color='#FFA500'>Archive</font>");
        log = log.replace("inflating", "<font color='#FFA500'>inflating</font>");
        log = log.replace("<br /><br /><br />", "<br />");
        int ind = log.lastIndexOf("<br />");
        if( ind >= 0 ) log = new StringBuilder(log).replace(ind, ind + ("<br />".length()), "").toString();
        log = log.replace("true", "<font color='#00ff00'>true</font>");
        log = log.replace("false", "<font color='#ff0000'>false</font>");

        Spanned html = Html.fromHtml(log, Html.FROM_HTML_MODE_COMPACT);

        logView.setText(html);
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws IOException {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
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
