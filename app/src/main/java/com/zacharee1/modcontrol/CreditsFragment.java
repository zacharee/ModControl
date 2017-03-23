package com.zacharee1.modcontrol;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_credits, container, false);

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        playStore = (Button) view.findViewById(R.id.play_store);
        playStoreInstaller = (Button) view.findViewById(R.id.play_store_installer);
        XDA = (Button) view.findViewById(R.id.xda_thread);

        SharedPreferences sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);


        try {
            buttons();
        } catch (Exception e) {}

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
}
