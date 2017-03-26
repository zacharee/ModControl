package com.zacharee1.modcontrol;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log, container, false);

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        SharedPreferences sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);


        try {
            buttons();
            logView();
        } catch (Exception e) {
            Log.e("ModControl", e.getMessage());
        }

        return view;
    }

    public void buttons() throws IOException {
        Button refresh = (Button) view.findViewById(R.id.refresh_log);
        Button scrolldown = (Button) view.findViewById(R.id.scroll_bottom);
        final ScrollView scroll = (ScrollView) view.findViewById(R.id.log_scroll);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    logView();
//                    scroll.fullScroll(View.FOCUS_DOWN);
                } catch (Exception e) {
                    Log.e("ModControl", e.getMessage());
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
                                Log.e("ModControl", e.getMessage());
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.e("ModControl", e.getMessage());
                }
            }
        });
    }

    public void logView() throws IOException {
        String log = getStringFromFile(Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        TextView logView = (TextView) view.findViewById(R.id.log_text);
        final ScrollView scroll = (ScrollView) view.findViewById(R.id.log_scroll);

        logView.setText(log);

//        scroll.post(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    scroll.fullScroll(View.FOCUS_DOWN);
//                } catch (Exception e) {
//                    Log.e("ModControl", e.getMessage());
//                }
//            }
//        });
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
}
