package com.zacharee1.modcontrol;

/**
 * Created by Zacha on 3/1/2017.
 */

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.bluetooth.BluetoothA2dp;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jrummyapps.android.colorpicker.ColorPickerDialog;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;

import static android.content.Context.MODE_PRIVATE;


public class NoModsFragment extends Fragment {
    public View view;
    MainActivity activity;

    public boolean enabled;
    public boolean isV20;
    public SharedPreferences sharedPrefs;

//    public TextInputEditText RedQT;
//    public TextInputEditText GreenQT;
//    public TextInputEditText BlueQT;
//    public TextInputEditText RedSig;
//    public TextInputEditText GreenSig;
//    public TextInputEditText BlueSig;
//    public TextInputEditText RedSigAOD;
//    public TextInputEditText GreenSigAOD;
//    public TextInputEditText BlueSigAOD;

//    public int redIntQT_0;
//    public int greenIntQT_0;
//    public int blueIntQT_0;
//    public int redIntQT_1;
//    public int greenIntQT_1;
//    public int blueIntQT_1;
//    public int redIntQT_2;
//    public int greenIntQT_2;
//    public int blueIntQT_2;
//    public int redIntQT_3;
//    public int greenIntQT_3;
//    public int blueIntQT_3;
//    public int redIntQT_4;
//    public int greenIntQT_4;
//    public int blueIntQT_4;
//    public int redIntQT_5;
//    public int greenIntQT_5;
//    public int blueIntQT_5;
//    public int redIntSig;
//    public int greenIntSig;
//    public int blueIntSig;
//    public int redIntAODSig;
//    public int greenIntAODSig;
//    public int blueIntAODSig;

    public Spinner qtIndexSpinner;

//    public SeekBar redQTSeek;
//    public SeekBar greenQTSeek;
//    public SeekBar blueQTSeek;
//    public SeekBar redSigSeek;
//    public SeekBar greenSigSeek;
//    public SeekBar blueSigSeek;
//    public SeekBar redAODSigSeek;
//    public SeekBar greenAODSigSeek;
//    public SeekBar blueAODSigSeek;

    public ImageView QTPreviewOn;
    public ImageView QTPreviewOff;
    public ImageView SigPreview;
    public ImageView AODSigPreview;

    public Button QTPreviewOnB;
    public Button QTPreviewOffB;
    public Button SigPreviewB;
    public Button SigAodPreviewB;
    public Button NavPreviewB;

    public int QTColor;
    public int SigColor;
    public int AODSigColor;
    public int NavColor;

    public Button applyQT;
    public Button applySig;
    public Button applyAODSig;
    public Button applyNav;

    public int qtIndex;

//    public RadioGroup qtGroup1;
//    public RadioGroup qtGroup2;
//    public RadioGroup qtGroup3;
//    public RadioGroup sigGroup1;
//    public RadioGroup sigGroup2;
//    public RadioGroup sigGroup3;
//    public RadioGroup aodSigGroup1;
//    public RadioGroup aodSigGroup2;
//    public RadioGroup aodSigGroup3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if ( getActivity() instanceof MainActivity){
            activity = (MainActivity) getActivity();
        }

        view = inflater.inflate(R.layout.fragment_nomods, container, false);

        try {
//            qtOption();
//            sigOption();
//            aodSigOption();
            setStuffUp();
            applyQT();
            buttons(applySig, "redsig", "greensig", "bluesig", "Signature");
            buttons(applyAODSig, "redsigaod", "greensigaod", "bluesigaod", "\"AOD Signature\"");
            buttons(applyNav, "rednav_0", "greennav_0", "bluenav_0", "NavBar");
            colorPick((Button)view.findViewById(R.id.color_pick_qt));
            colorPick((Button)view.findViewById(R.id.color_pick_qt_1));
            colorPick((Button)view.findViewById(R.id.color_pick_qt_2));
            colorPick((Button)view.findViewById(R.id.color_pick_sig));
            colorPick((Button)view.findViewById(R.id.color_pick_sig_aod));
            colorPick((Button)view.findViewById(R.id.color_pick_sig_1));
            colorPick((Button)view.findViewById(R.id.color_pick_sig_aod_1));
            colorPick(NavPreviewB);
            handleQTPage();
//            sliders(redQTSeek, RedQT);
//            sliders(greenQTSeek, GreenQT);
//            sliders(blueQTSeek, BlueQT);
//            sliders(redSigSeek, RedSig);
//            sliders(greenSigSeek, GreenSig);
//            sliders(blueSigSeek, BlueSig);
//            sliders(redAODSigSeek, RedSigAOD);
//            sliders(greenAODSigSeek, GreenSigAOD);
//            sliders(blueAODSigSeek, BlueSigAOD);
//            textListeners(RedQT, redQTSeek);
//            textListeners(GreenQT, greenQTSeek);
//            textListeners(BlueQT, blueQTSeek);
//            textListeners(RedSig, redSigSeek);
//            textListeners(GreenSig, greenSigSeek);
//            textListeners(BlueSig, blueSigSeek);
//            textListeners(RedSigAOD, redAODSigSeek);
//            textListeners(GreenSigAOD, greenAODSigSeek);
//            textListeners(BlueSigAOD, blueAODSigSeek);
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
        }

        return view;
    }

//    public void clearAll() {
//        try {
//            clearQTRad(qtGroup1);
//            clearQTRad(qtGroup2);
//            clearQTRad(qtGroup3);
//        } catch (Exception e) {}
//    }


    public void colorPick(final Button button) throws IOException {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int dialogID = -1;
                            int color = Color.argb(255, 255, 255, 255);
                            if (button == view.findViewById(R.id.color_pick_qt) || button == view.findViewById(R.id.color_pick_qt_1) || button == view.findViewById(R.id.color_pick_qt_2)) {
                                switch (qtIndex) {
                                    case 0:
                                        dialogID = activity.DIALOG_ID_0;
                                        color = Color.argb(255, sharedPrefs.getInt("redqt_0", 255), sharedPrefs.getInt("greenqt_0", 255), sharedPrefs.getInt("blueqt_0", 255));
                                        break;
                                    case 1:
                                        dialogID = activity.DIALOG_ID_1;
                                        color = Color.argb(255, sharedPrefs.getInt("redqt_1", 255), sharedPrefs.getInt("greenqt_1", 255), sharedPrefs.getInt("blueqt_1", 255));
                                        break;
                                    case 2:
                                        dialogID = activity.DIALOG_ID_2;
                                        color = Color.argb(255, sharedPrefs.getInt("redqt_2", 255), sharedPrefs.getInt("greenqt_2", 255), sharedPrefs.getInt("blueqt_2", 255));
                                        break;
                                    case 3:
                                        dialogID = activity.DIALOG_ID_3;
                                        color = Color.argb(255, sharedPrefs.getInt("redqt_3", 255), sharedPrefs.getInt("greenqt_3", 255), sharedPrefs.getInt("blueqt_3", 255));
                                        break;
                                    case 4:
                                        dialogID = activity.DIALOG_ID_4;
                                        color = Color.argb(255, sharedPrefs.getInt("redqt_4", 255), sharedPrefs.getInt("greenqt_4", 255), sharedPrefs.getInt("blueqt_4", 255));
                                        break;
                                    case 5:
                                        dialogID = activity.DIALOG_ID_5;
                                        color = Color.argb(255, sharedPrefs.getInt("redqt_5", 255), sharedPrefs.getInt("greenqt_5", 255), sharedPrefs.getInt("blueqt_5", 255));
                                        break;
                                }
                            } else if (button == view.findViewById(R.id.color_pick_sig) || button == view.findViewById(R.id.color_pick_sig_1)) {
                                dialogID = activity.DIALOG_ID_6;
                                color = Color.argb(255, sharedPrefs.getInt("redsig", 255), sharedPrefs.getInt("greensig", 255), sharedPrefs.getInt("bluesig", 255));
                            } else if (button == view.findViewById(R.id.color_pick_sig_aod) || button == view.findViewById(R.id.color_pick_sig_aod_1)) {
                                dialogID = activity.DIALOG_ID_7;
                                color = Color.argb(255, sharedPrefs.getInt("redsigaod", 255), sharedPrefs.getInt("greensigaod", 255), sharedPrefs.getInt("bluesigaod", 255));
                            } else if (button == NavPreviewB) {
                                dialogID = activity.DIALOG_ID_8;
                                color = Color.argb(255, sharedPrefs.getInt("rednav_0", 255), sharedPrefs.getInt("greennav_0", 255), sharedPrefs.getInt("bluenav_0", 255));
                            }
                            ColorPickerDialog.newBuilder()
                                    .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                                    .setAllowPresets(true)
                                    .setDialogId(dialogID)
                                    .setColor(color)
                                    .setShowAlphaSlider(false)
                                    .show(activity);
                        }
                    }).start();
                } catch (Exception e) {
                    Log.e("err", e.getMessage());
                }
            }

        });
    }

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleQTPage() {
        qtIndexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                qtIndex = position;

                Log.i("ModControl/I: ", String.valueOf(qtIndex));

                SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                editor.putInt("qtIndex", qtIndex);
                editor.apply();

                switch (qtIndex) {
                    case 0:
//                        RedQT.setText(String.valueOf(sharedPrefs.getInt("redqt_0", 255) + sharedPrefs.getInt("greenqt_0", 255), sharedPrefs.getInt("blueqt_0", 255)));
//                        GreenQT.setText(String.valueOf(greenIntQT_0));
//                        BlueQT.setText(String.valueOf(blueIntQT_0));
//
//                        redQTSeek.setProgress(redIntQT_0);
//                        greenQTSeek.setProgress(greenIntQT_0);
//                        blueQTSeek.setProgress(blueIntQT_0);

                        QTColor = Color.argb(255, sharedPrefs.getInt("redqt_0", 255), sharedPrefs.getInt("greenqt_0", 255), sharedPrefs.getInt("blueqt_0", 255));
                        break;

                    case 1:
//                        RedQT.setText(String.valueOf(redIntQT_1));
//                        GreenQT.setText(String.valueOf(greenIntQT_1));
//                        BlueQT.setText(String.valueOf(blueIntQT_1));
//
//                        redQTSeek.setProgress(redIntQT_1);
//                        greenQTSeek.setProgress(greenIntQT_1);
//                        blueQTSeek.setProgress(blueIntQT_1);

                        QTColor = Color.argb(255, sharedPrefs.getInt("redqt_1", 255), sharedPrefs.getInt("greenqt_1", 255), sharedPrefs.getInt("blueqt_1", 255));
                        break;

                    case 2:
//                        RedQT.setText(String.valueOf(redIntQT_2));
//                        GreenQT.setText(String.valueOf(greenIntQT_2));
//                        BlueQT.setText(String.valueOf(blueIntQT_2));
//
//                        redQTSeek.setProgress(redIntQT_2);
//                        greenQTSeek.setProgress(greenIntQT_2);
//                        blueQTSeek.setProgress(blueIntQT_2);

                        QTColor = Color.argb(255, sharedPrefs.getInt("redqt_2", 255), sharedPrefs.getInt("greenqt_2", 255), sharedPrefs.getInt("blueqt_2", 255));
                        break;

                    case 3:
//                        RedQT.setText(String.valueOf(redIntQT_3));
//                        GreenQT.setText(String.valueOf(greenIntQT_3));
//                        BlueQT.setText(String.valueOf(blueIntQT_3));
//
//                        redQTSeek.setProgress(redIntQT_3);
//                        greenQTSeek.setProgress(greenIntQT_3);
//                        blueQTSeek.setProgress(blueIntQT_3);

                        QTColor = Color.argb(255, sharedPrefs.getInt("redqt_3", 255), sharedPrefs.getInt("greenqt_3", 255), sharedPrefs.getInt("blueqt_3", 255));
                        break;

                    case 4:
//                        RedQT.setText(String.valueOf(redIntQT_4));
//                        GreenQT.setText(String.valueOf(greenIntQT_4));
//                        BlueQT.setText(String.valueOf(blueIntQT_4));
//
//                        redQTSeek.setProgress(redIntQT_4);
//                        greenQTSeek.setProgress(greenIntQT_4);
//                        blueQTSeek.setProgress(blueIntQT_4);

                        QTColor = Color.argb(255, sharedPrefs.getInt("redqt_4", 255), sharedPrefs.getInt("greenqt_4", 255), sharedPrefs.getInt("blueqt_4", 255));
                        break;

                    case 5:
//                        RedQT.setText(String.valueOf(redIntQT_5));
//                        GreenQT.setText(String.valueOf(greenIntQT_5));
//                        BlueQT.setText(String.valueOf(blueIntQT_5));
//
//                        redQTSeek.setProgress(redIntQT_5);
//                        greenQTSeek.setProgress(greenIntQT_5);
//                        blueQTSeek.setProgress(blueIntQT_5);

                        QTColor = Color.argb(255, sharedPrefs.getInt("redqt_5", 255), sharedPrefs.getInt("greenqt_5", 255), sharedPrefs.getInt("blueqt_5", 255));
                        break;
                }
                QTPreviewOn.setColorFilter(QTColor);
                QTPreviewOnB.setBackgroundTintList(ColorStateList.valueOf(QTColor));
                QTPreviewOff.setColorFilter(QTColor);
                QTPreviewOffB.setBackgroundTintList(ColorStateList.valueOf(QTColor));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void applyQT() {
        applyQT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ContentResolver cr = activity.getContentResolver();
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        int redQT_0 = sharedPrefs.getInt("redqt_0", 255);
                        int greenQT_0 = sharedPrefs.getInt("greenqt_0", 255);
                        int blueQT_0 = sharedPrefs.getInt("blueqt_0", 255);
                        int redQT_1 = sharedPrefs.getInt("redqt_1", 255);
                        int greenQT_1 = sharedPrefs.getInt("greenqt_1", 255);
                        int blueQT_1 = sharedPrefs.getInt("blueqt_1", 255);
                        int redQT_2 = sharedPrefs.getInt("redqt_2", 255);
                        int greenQT_2 = sharedPrefs.getInt("greenqt_2", 255);
                        int blueQT_2 = sharedPrefs.getInt("blueqt_2", 255);
                        int redQT_3 = sharedPrefs.getInt("redqt_3", 255);
                        int greenQT_3 = sharedPrefs.getInt("greenqt_3", 255);
                        int blueQT_3 = sharedPrefs.getInt("blueqt_3", 255);
                        int redQT_4 = sharedPrefs.getInt("redqt_4", 255);
                        int greenQT_4 = sharedPrefs.getInt("greenqt_4", 255);
                        int blueQT_4 = sharedPrefs.getInt("blueqt_4", 255);
                        int redQT_5 = sharedPrefs.getInt("redqt_5", 255);
                        int greenQT_5 = sharedPrefs.getInt("greenqt_5", 255);
                        int blueQT_5 = sharedPrefs.getInt("blueqt_5", 255);
                        switch (qtIndex) {
                            case 0:
                                Settings.System.putInt(cr, "redqt_0", redQT_0);
                                Settings.System.putInt(cr, "greenqt_0", greenQT_0);
                                Settings.System.putInt(cr, "blueqt_0", blueQT_0);
//                                editor.putInt("redqt_0", redIntQT_0);
//                                editor.putInt("greenqt_0", greenIntQT_0);
//                                editor.putInt("blueqt_0", blueIntQT_0);

                                logger("redqt_0", "greenqt_0", "blueqt_0", redQT_0, greenQT_0, blueQT_0, "QuickTools");
                                break;

                            case 1:
                                Settings.System.putInt(cr, "redqt_1", redQT_1);
                                Settings.System.putInt(cr, "greenqt_1", greenQT_1);
                                Settings.System.putInt(cr, "blueqt_1", blueQT_1);
//                                editor.putInt("redqt_1", redIntQT_1);
//                                editor.putInt("greenqt_1", greenIntQT_1);
//                                editor.putInt("blueqt_1", blueIntQT_1);

                                logger("redqt_1", "greenqt_1", "blueqt_1", redQT_1, greenQT_1, blueQT_1, "QuickTools");
                                break;

                            case 2:
                                Settings.System.putInt(cr, "redqt_2", redQT_2);
                                Settings.System.putInt(cr, "greenqt_2", greenQT_2);
                                Settings.System.putInt(cr, "blueqt_2", blueQT_2);
//                                editor.putInt("redqt_2", redIntQT_2);
//                                editor.putInt("greenqt_2", greenIntQT_2);
//                                editor.putInt("blueqt_2", blueIntQT_2);

                                logger("redqt_2", "greenqt_2", "blueqt_2", redQT_2, greenQT_2, blueQT_2, "QuickTools");
                                break;

                            case 3:
                                Settings.System.putInt(cr, "redqt_3", redQT_3);
                                Settings.System.putInt(cr, "greenqt_3", greenQT_3);
                                Settings.System.putInt(cr, "blueqt_3", blueQT_3);
//                                editor.putInt("redqt_3", redIntQT_3);
//                                editor.putInt("greenqt_3", greenIntQT_3);
//                                editor.putInt("blueqt_3", blueIntQT_3);

                                logger("redqt_3", "greenqt_3", "blueqt_3", redQT_3, greenQT_3, blueQT_3, "QuickTools");
                                break;

                            case 4:
                                Settings.System.putInt(cr, "redqt_4", redQT_4);
                                Settings.System.putInt(cr, "greenqt_4", greenQT_4);
                                Settings.System.putInt(cr, "blueqt_4", blueQT_4);
//                                editor.putInt("redqt_4", redIntQT_4);
//                                editor.putInt("greenqt_4", greenIntQT_4);
//                                editor.putInt("blueqt_4", blueIntQT_4);

                                logger("redqt_4", "greenqt_4", "blueqt_4", redQT_4, greenQT_4, blueQT_4, "QuickTools");
                                break;

                            case 5:
                                Settings.System.putInt(cr, "redqt_0", redQT_5);
                                Settings.System.putInt(cr, "greenqt_0", greenQT_5);
                                Settings.System.putInt(cr, "blueqt_0", blueQT_5);
//                                editor.putInt("redqt_0", redIntQT_5);
//                                editor.putInt("greenqt_0", greenIntQT_5);
//                                editor.putInt("blueqt_0", blueIntQT_5);

                                logger("redqt_0", "greenqt_0", "blueqt_0", redQT_5, greenQT_5, blueQT_5, "QuickTools");

                                Settings.System.putInt(cr, "redqt_1", redQT_5);
                                Settings.System.putInt(cr, "greenqt_1", greenQT_5);
                                Settings.System.putInt(cr, "blueqt_1", blueQT_5);
//                                editor.putInt("redqt_1", redIntQT_5);
//                                editor.putInt("greenqt_1", greenIntQT_5);
//                                editor.putInt("blueqt_1", blueIntQT_5);

                                logger("redqt_1", "greenqt_1", "blueqt_1", redQT_5, greenQT_5, blueQT_5, "QuickTools");

                                Settings.System.putInt(cr, "redqt_2", redQT_5);
                                Settings.System.putInt(cr, "greenqt_2", greenQT_5);
                                Settings.System.putInt(cr, "blueqt_2", blueQT_5);
//                                editor.putInt("redqt_2", redIntQT_5);
//                                editor.putInt("greenqt_2", greenIntQT_5);
//                                editor.putInt("blueqt_2", blueIntQT_5);

                                logger("redqt_2", "greenqt_2", "blueqt_2", redQT_5, greenQT_5, blueQT_5, "QuickTools");

                                Settings.System.putInt(cr, "redqt_3", redQT_5);
                                Settings.System.putInt(cr, "greenqt_3", greenQT_5);
                                Settings.System.putInt(cr, "blueqt_3", blueQT_5);
//                                editor.putInt("redqt_3", redIntQT_5);
//                                editor.putInt("greenqt_3", greenIntQT_5);
//                                editor.putInt("blueqt_3", blueIntQT_5);

                                logger("redqt_3", "greenqt_3", "blueqt_3", redQT_5, greenQT_5, blueQT_5, "QuickTools");

                                Settings.System.putInt(cr, "redqt_4", redQT_5);
                                Settings.System.putInt(cr, "greenqt_4", greenQT_5);
                                Settings.System.putInt(cr, "blueqt_4", blueQT_5);
//                                editor.putInt("redqt_4", redIntQT_5);
//                                editor.putInt("greenqt_4", greenIntQT_5);
//                                editor.putInt("blueqt_4", blueIntQT_5);

                                logger("redqt_4", "greenqt_4", "blueqt_4", redQT_5, greenQT_5, blueQT_5, "QuickTools");
                                break;
                        }
                        editor.apply();
                        sudo("killall com.lge.quicktools");
                    }
                }).start();
            }
        });
    }

    public void setStuffUp() {
        sharedPrefs = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE);

        if (sharedPrefs.getBoolean("enabled", true)) {
            enabled = true;
        }

        if (activity.isDark) {
            view.findViewById(R.id.qtools).setBackground(activity.getDrawable(R.drawable.layout_bg_dark));
            view.findViewById(R.id.sig).setBackground(activity.getDrawable(R.drawable.layout_bg_dark));
            view.findViewById(R.id.sig_aod).setBackground(activity.getDrawable(R.drawable.layout_bg_dark));
            view.findViewById(R.id.navbar).setBackground(activity.getDrawable(R.drawable.layout_bg_dark));

        } else {
            view.findViewById(R.id.qtools).setBackground(activity.getDrawable(R.drawable.layout_bg_light));
            view.findViewById(R.id.sig).setBackground(activity.getDrawable(R.drawable.layout_bg_light));
            view.findViewById(R.id.sig_aod).setBackground(activity.getDrawable(R.drawable.layout_bg_light));
            view.findViewById(R.id.navbar).setBackground(activity.getDrawable(R.drawable.layout_bg_light));
        }
        view.findViewById(R.id.qtools).setPadding(4, 10, 4, 10);
        view.findViewById(R.id.sig).setPadding(4, 10, 4, 10);
        view.findViewById(R.id.sig_aod).setPadding(4, 10, 4, 10);
        view.findViewById(R.id.navbar).setPadding(4, 10,4,10);

//        qtGroup1 = (RadioGroup) view.findViewById(R.id.color_tool1);
//        qtGroup2 = (RadioGroup) view.findViewById(R.id.color_tool2);
//        qtGroup3 = (RadioGroup) view.findViewById(R.id.color_tool3);
//        sigGroup1 = (RadioGroup) view.findViewById(R.id.color_sig1);
//        sigGroup2 = (RadioGroup) view.findViewById(R.id.color_sig2);
//        sigGroup3 = (RadioGroup) view.findViewById(R.id.color_sig3);
//        aodSigGroup1 = (RadioGroup) view.findViewById(R.id.color_aod_sig1);
//        aodSigGroup2 = (RadioGroup) view.findViewById(R.id.color_aod_sig2);
//        aodSigGroup3 = (RadioGroup) view.findViewById(R.id.color_aod_sig3);

//        RedQT = (TextInputEditText) view.findViewById(R.id.red_qt_val);
//        GreenQT = (TextInputEditText) view.findViewById(R.id.green_qt_val);
//        BlueQT = (TextInputEditText) view.findViewById(R.id.blue_qt_val);
//        RedSig = (TextInputEditText) view.findViewById(R.id.red_sig_val);
//        GreenSig = (TextInputEditText) view.findViewById(R.id.green_sig_val);
//        BlueSig = (TextInputEditText) view.findViewById(R.id.blue_sig_val);
//        RedSigAOD = (TextInputEditText) view.findViewById(R.id.red_aodsig_val);
//        GreenSigAOD = (TextInputEditText) view.findViewById(R.id.green_aodsig_val);
//        BlueSigAOD = (TextInputEditText) view.findViewById(R.id.blue_aodsig_val);

        qtIndexSpinner = (Spinner) view.findViewById(R.id.qt_index);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.qt_index_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtIndexSpinner.setAdapter(adapter);

//        redQTSeek = (SeekBar) view.findViewById(R.id.redqt_seek);
//        greenQTSeek = (SeekBar) view.findViewById(R.id.greenqt_seek);
//        blueQTSeek = (SeekBar) view.findViewById(R.id.blueqt_seek);
//        redSigSeek = (SeekBar) view.findViewById(R.id.redsig_seek);
//        greenSigSeek = (SeekBar) view.findViewById(R.id.greensig_seek);
//        blueSigSeek = (SeekBar) view.findViewById(R.id.bluesig_seek);
//        redAODSigSeek = (SeekBar) view.findViewById(R.id.redaodsig_seek);
//        greenAODSigSeek = (SeekBar) view.findViewById(R.id.greenaodsig_seek);
//        blueAODSigSeek = (SeekBar) view.findViewById(R.id.blueaodsig_seek);

        QTPreviewOn = (ImageView) view.findViewById(R.id.colorqt_preview_on);
        QTPreviewOff = (ImageView) view.findViewById(R.id.colorqt_preview_off);
        SigPreview = (ImageView) view.findViewById(R.id.colorsig_preview);
        AODSigPreview = (ImageView) view.findViewById(R.id.coloraodsig_preview);

        QTPreviewOnB = (Button) view.findViewById(R.id.color_pick_qt_1);
        QTPreviewOffB = (Button) view.findViewById(R.id.color_pick_qt_2);
        SigPreviewB = (Button) view.findViewById(R.id.color_pick_sig_1);
        SigAodPreviewB = (Button) view.findViewById(R.id.color_pick_sig_aod_1);
        NavPreviewB = (Button) view.findViewById(R.id.navbar_pick_1);

        applyQT = (Button) view.findViewById(R.id.apply_qt_color);
        applySig = (Button) view.findViewById(R.id.apply_sig_color);
        applyAODSig = (Button) view.findViewById(R.id.apply_aod_sig_color);
        applyNav = (Button) view.findViewById(R.id.apply_navbar_color);

//        redQTSeek.setProgress(sharedPrefs.getInt("red", 255));
//        greenQTSeek.setProgress(sharedPrefs.getInt("green", 255));
//        blueQTSeek.setProgress(sharedPrefs.getInt("blue", 255));
//        redSigSeek.setProgress(sharedPrefs.getInt("redsig", 255));
//        greenSigSeek.setProgress(sharedPrefs.getInt("greensig", 255));
//        blueSigSeek.setProgress(sharedPrefs.getInt("bluesig", 255));
//        redAODSigSeek.setProgress(sharedPrefs.getInt("redsigaod", 255));
//        greenAODSigSeek.setProgress(sharedPrefs.getInt("greensigaod", 255));
//        blueAODSigSeek.setProgress(sharedPrefs.getInt("bluesigaod", 255));

//        redIntQT_0 = sharedPrefs.getInt("redqt_0", 255);
//        greenIntQT_0 = sharedPrefs.getInt("greenqt_0", 255);
//        blueIntQT_0 = sharedPrefs.getInt("blueqt_0", 255);
//        redIntQT_1 = sharedPrefs.getInt("redqt_1", 255);
//        greenIntQT_1 = sharedPrefs.getInt("greenqt_1", 255);
//        blueIntQT_1 = sharedPrefs.getInt("blueqt_1", 255);
//        redIntQT_2 = sharedPrefs.getInt("redqt_2", 255);
//        greenIntQT_2 = sharedPrefs.getInt("greenqt_2", 255);
//        blueIntQT_2 = sharedPrefs.getInt("blueqt_2", 255);
//        redIntQT_3 = sharedPrefs.getInt("redqt_3", 255);
//        greenIntQT_3 = sharedPrefs.getInt("greenqt_3", 255);
//        blueIntQT_3 = sharedPrefs.getInt("blueqt_3", 255);
//        redIntQT_4 = sharedPrefs.getInt("redqt_4", 255);
//        greenIntQT_4 = sharedPrefs.getInt("greenqt_4", 255);
//        blueIntQT_4 = sharedPrefs.getInt("blueqt_4", 255);
//        redIntQT_5 = sharedPrefs.getInt("redqt_5", 255);
//        greenIntQT_5 = sharedPrefs.getInt("greenqt_5", 255);
//        blueIntQT_5 = sharedPrefs.getInt("blueqt_5", 255);
//        redIntSig = sharedPrefs.getInt("redsig", 255);
//        greenIntSig = sharedPrefs.getInt("greensig", 255);
//        blueIntSig = sharedPrefs.getInt("bluesig", 255);
//        redIntAODSig = sharedPrefs.getInt("redsigaod", 255);
//        greenIntAODSig = sharedPrefs.getInt("greensigaod", 255);
//        blueIntAODSig = sharedPrefs.getInt("bluesigaod", 255);

        qtIndex = sharedPrefs.getInt("qtIndex", 5);
        qtIndexSpinner.setSelection(qtIndex);

        switch (qtIndex) {
            case 0:
                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_0", 255), sharedPrefs.getInt("greenqt_0", 255), sharedPrefs.getInt("blueqt_0", 255));
                break;
            case 1:
                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_1", 255), sharedPrefs.getInt("greenqt_1", 255), sharedPrefs.getInt("blueqt_1", 255));
                break;
            case 2:
                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_2", 255), sharedPrefs.getInt("greenqt_2", 255), sharedPrefs.getInt("blueqt_2", 255));
                break;
            case 3:
                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_3", 255), sharedPrefs.getInt("greenqt_3", 255), sharedPrefs.getInt("blueqt_3", 255));
                break;
            case 4:
                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_4", 255), sharedPrefs.getInt("greenqt_4", 255), sharedPrefs.getInt("blueqt_4", 255));
                break;
            case 5:
                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_5", 255), sharedPrefs.getInt("greenqt_5", 255), sharedPrefs.getInt("blueqt_5", 255));
                break;
        }
        SigColor = Color.argb(255, sharedPrefs.getInt("redsig", 255), sharedPrefs.getInt("greensig", 255), sharedPrefs.getInt("bluesig", 255));
        AODSigColor = Color.argb(255, sharedPrefs.getInt("redsigaod", 255), sharedPrefs.getInt("greensigaod", 255), sharedPrefs.getInt("bluesigaod", 255));
        NavColor = Color.argb(255, sharedPrefs.getInt("rednav_0", 255), sharedPrefs.getInt("greennav_0", 255), sharedPrefs.getInt("bluenav_0", 255));

        QTPreviewOn.setColorFilter(QTColor);
        QTPreviewOnB.setBackgroundTintList(ColorStateList.valueOf(QTColor));
        QTPreviewOff.setColorFilter(QTColor);
        QTPreviewOffB.setBackgroundTintList(ColorStateList.valueOf(QTColor));
        SigPreview.setColorFilter(SigColor);
        SigPreviewB.setBackgroundTintList(ColorStateList.valueOf(SigColor));
        AODSigPreview.setColorFilter(AODSigColor);
        SigAodPreviewB.setBackgroundTintList(ColorStateList.valueOf(AODSigColor));
        NavPreviewB.setBackgroundTintList(ColorStateList.valueOf(NavColor));

//        RedQT.setText(String.valueOf(sharedPrefs.getInt("red", 255)));
//        GreenQT.setText(String.valueOf(sharedPrefs.getInt("green", 255)));
//        BlueQT.setText(String.valueOf(sharedPrefs.getInt("blue", 255)));
//        RedSig.setText(String.valueOf(sharedPrefs.getInt("redsig", 255)));
//        GreenSig.setText(String.valueOf(sharedPrefs.getInt("greensig", 255)));
//        BlueSig.setText(String.valueOf(sharedPrefs.getInt("bluesig", 255)));
//        RedSigAOD.setText(String.valueOf(sharedPrefs.getInt("redsigaod", 255)));
//        GreenSigAOD.setText(String.valueOf(sharedPrefs.getInt("greensigaod", 255)));
//        BlueSigAOD.setText(String.valueOf(sharedPrefs.getInt("bluesigaod", 255)));

        if (sharedPrefs.getBoolean("isv20", true)) {
            isV20 = true;
        } else {
            isV20 = false;
            view.findViewById(R.id.qtools).setVisibility(View.GONE);
            view.findViewById(R.id.sig).setVisibility(View.GONE);
        }
    }

//    public void textListeners(final TextInputEditText text, final SeekBar seekBar) throws IOException {
//        text.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String val = text.getText().toString();
//                if (val.length() > 0); {
//                    try {
//                        int valInt = Integer.decode(val);
//                        if (text == RedQT) {
//                            switch (qtIndex) {
//                                case 0:
////                                    redIntQT_0 = valInt;
//                                    break;
//
//                                case 1:
////                                    redIntQT_1 = valInt;
//                                    break;
//
//                                case 2:
////                                    redIntQT_2 = valInt;
//                                    break;
//
//                                case 3:
////                                    redIntQT_3 = valInt;
//                                    break;
//
//                                case 4:
////                                    redIntQT_4 = valInt;
//                                    break;
//
//                                case 5:
////                                    redIntQT_5 = valInt;
//                                    break;
//                            }
//                        } else if (text == GreenQT) {
//                            switch (qtIndex) {
//                                case 0:
////                                    greenIntQT_0 = valInt;
//                                    break;
//
//                                case 1:
////                                    greenIntQT_1 = valInt;
//                                    break;
//
//                                case 2:
////                                    greenIntQT_2 = valInt;
//                                    break;
//
//                                case 3:
////                                    greenIntQT_3 = valInt;
//                                    break;
//
//                                case 4:
////                                    greenIntQT_4 = valInt;
//                                    break;
//
//                                case 5:
////                                    greenIntQT_5 = valInt;
//                                    break;
//                            }
//                        } else if (text == BlueQT) {
//                            switch (qtIndex) {
//                                case 0:
////                                    blueIntQT_0 = valInt;
//                                    break;
//
//                                case 1:
////                                    blueIntQT_1 = valInt;
//                                    break;
//
//                                case 2:
////                                    blueIntQT_2 = valInt;
//                                    break;
//
//                                case 3:
////                                    blueIntQT_3 = valInt;
//                                    break;
//
//                                case 4:
////                                    blueIntQT_4 = valInt;
//                                    break;
//
//                                case 5:
////                                    blueIntQT_5 = valInt;
//                                    break;
//                            }
//                        } else if (text == RedSig) {
//                            redIntSig = valInt;
//                        } else if (text == GreenSig) {
//                            greenIntSig = valInt;
//                        } else if (text == BlueSig) {
//                            blueIntSig = valInt;
//                        } else if (text == RedSigAOD) {
//                            redIntAODSig = valInt;
//                        } else if (text == GreenSigAOD) {
//                            greenIntAODSig = valInt;
//                        } else if (text == BlueSigAOD) {
//                            blueIntAODSig = valInt;
//                        }
//
//                        seekBar.setProgress(valInt);
//
//                        if (text.isFocused()) text.setSelection(text.getText().length());
//
//                        switch (qtIndex) {
//                            case 0:
//                                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_0", 255), sharedPrefs.getInt("greenqt_0", 255), sharedPrefs.getInt("blueqt_0", 255));
//                                break;
//                            case 1:
//                                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_1", 255), sharedPrefs.getInt("greenqt_1", 255), sharedPrefs.getInt("blueqt_1", 255));
//                                break;
//                            case 2:
//                                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_2", 255), sharedPrefs.getInt("greenqt_2", 255), sharedPrefs.getInt("blueqt_2", 255));
//                                break;
//                            case 3:
//                                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_3", 255), sharedPrefs.getInt("greenqt_3", 255), sharedPrefs.getInt("blueqt_3", 255));
//                                break;
//                            case 4:
//                                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_4", 255), sharedPrefs.getInt("greenqt_4", 255), sharedPrefs.getInt("blueqt_4", 255));
//                                break;
//                            case 5:
//                                QTColor = Color.argb(255, sharedPrefs.getInt("redqt_5", 255), sharedPrefs.getInt("greenqt_5", 255), sharedPrefs.getInt("blueqt_5", 255));
//                                break;
//                        }
//                        QTPreviewOn.setColorFilter(QTColor);
//                        QTPreviewOff.setColorFilter(QTColor);
//                        SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
//                        SigPreview.setColorFilter(SigColor);
//                        AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);
//                        AODSigPreview.setColorFilter(AODSigColor);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                        sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                    }
//                }
//            }
//        });
//
////        RedQT.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (RedQT.getText().toString().length() > 0) {
////                        try {
////                            redIntQT = Integer.decode(RedQT.getText().toString());
////
//////                            Settings.System.putInt(cr, "red", redIntQT);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("red", redIntQT);
//////                            editor.apply();
////
////                            redQTSeek.setProgress(redIntQT);
////
////                            if (RedQT.isFocused()) {
////                                RedQT.setSelection(RedQT.getText().length());
////                            }
////
////                            QTColor = Color.argb(255, redIntQT, greenIntQT, blueIntQT);
////                            QTPreviewOn.setColorFilter(QTColor);
////                            QTPreviewOff.setColorFilter(QTColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        GreenQT.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (GreenQT.getText().toString().length() > 0) {
////                        try {
////                            greenIntQT = Integer.decode(GreenQT.getText().toString());
////
//////                            Settings.System.putInt(cr, "green", greenIntQT);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("green", greenIntQT);
//////                            editor.apply();
////
////                            greenQTSeek.setProgress(greenIntQT);
////
////                            if (GreenQT.isFocused()) {
////                                GreenQT.setSelection(GreenQT.getText().length());
////                            }
////
////                            QTColor = Color.argb(255, redIntQT, greenIntQT, blueIntQT);
////                            QTPreviewOn.setColorFilter(QTColor);
////                            QTPreviewOff.setColorFilter(QTColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        BlueQT.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (BlueQT.getText().toString().length() > 0) {
////                        try {
////                            blueIntQT = Integer.decode(BlueQT.getText().toString());
////
//////                            Settings.System.putInt(cr, "blue", blueIntQT);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("blue", blueIntQT);
//////                            editor.apply();
////
////                            blueQTSeek.setProgress(blueIntQT);
////
////                            if (BlueQT.isFocused()) {
////                                BlueQT.setSelection(BlueQT.getText().length());
////                            }
////
////                            QTColor = Color.argb(255, redIntQT, greenIntQT, blueIntQT);
////                            QTPreviewOn.setColorFilter(QTColor);
////                            QTPreviewOff.setColorFilter(QTColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        RedSig.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (RedSig.getText().toString().length() > 0) {
////                        try {
////                            redIntSig = Integer.decode(RedSig.getText().toString());
//////
//////                            Settings.System.putInt(cr, "redsig", redIntSig);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("redsig", redIntSig);
//////                            editor.apply();
////
////                            redSigSeek.setProgress(redIntSig);
////
////                            if (RedSig.isFocused()) {
////                                RedSig.setSelection(RedSig.getText().length());
////                            }
////
////                            SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
////                            SigPreview.setColorFilter(SigColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        GreenSig.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (GreenSig.getText().toString().length() > 0) {
////                        try {
////                            greenIntSig = Integer.decode(GreenSig.getText().toString());
////
//////                            Settings.System.putInt(cr, "greensig", greenIntSig);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("greensig", greenIntSig);
//////                            editor.apply();
////
////                            greenSigSeek.setProgress(greenIntSig);
////
////                            if (GreenSig.isFocused()) {
////                                GreenSig.setSelection(GreenSig.getText().length());
////                            }
////
////                            SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
////                            SigPreview.setColorFilter(SigColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        BlueSig.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (BlueSig.getText().toString().length() > 0) {
////                        try {
////                            blueIntSig = Integer.decode(BlueSig.getText().toString());
////
//////                            Settings.System.putInt(cr, "bluesig", blueIntSig);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("bluesig", blueIntSig);
//////                            editor.apply();
////
////                            blueSigSeek.setProgress(blueIntSig);
////
////                            if (BlueSig.isFocused()) {
////                                BlueSig.setSelection(BlueSig.getText().length());
////                            }
////
////                            SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
////                            SigPreview.setColorFilter(SigColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        RedSigAOD.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (RedSigAOD.getText().toString().length() > 0) {
////                        try {
////                            redIntAODSig = Integer.decode(RedSigAOD.getText().toString());
////
//////                            Settings.System.putInt(cr, "redsigaod", redIntAODSig);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("redsigaod", redIntAODSig);
//////                            editor.apply();
////
////                            redAODSigSeek.setProgress(redIntAODSig);
////
////                            if (RedSigAOD.isFocused()) {
////                                RedSigAOD.setSelection(RedSigAOD.getText().length());
////                            }
////
////                            AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);
////                            AODSigPreview.setColorFilter(AODSigColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        GreenSigAOD.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (GreenSigAOD.getText().toString().length() > 0) {
////                        try {
////                            greenIntAODSig = Integer.decode(GreenSigAOD.getText().toString());
////
//////                            Settings.System.putInt(cr, "greensigaod", greenIntAODSig);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("greensigaod", greenIntAODSig);
//////                            editor.apply();
////
////                            greenAODSigSeek.setProgress(greenIntAODSig);
////
////                            if (GreenSigAOD.isFocused()) {
////                                GreenSigAOD.setSelection(GreenSigAOD.getText().length());
////                            }
////
////                            AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);
////                            AODSigPreview.setColorFilter(AODSigColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
////
////        BlueSigAOD.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable s) {
////                    if (BlueSigAOD.getText().toString().length() > 0) {
////                        try {
////                            blueIntAODSig = Integer.decode(BlueSigAOD.getText().toString());
////
//////                            Settings.System.putInt(cr, "bluesigaod", blueIntAODSig);
//////                            SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//////                            editor.putInt("bluesigaod", blueIntAODSig);
//////                            editor.apply();
////
////                            blueAODSigSeek.setProgress(blueIntAODSig);
////
////                            if (BlueSigAOD.isFocused()) {
////                                BlueSigAOD.setSelection(BlueSigAOD.getText().length());
////                            }
////
////                            AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);
////                            AODSigPreview.setColorFilter(AODSigColor);
////                        } catch (NumberFormatException e) {
////                            Log.e("ModControl/E", e.getMessage());
////                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
////                        }
////                    }
////            }
////        });
//    }

//    public void sliders(SeekBar seekBar, final TextInputEditText inputEditText) throws IOException {
//
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                inputEditText.setText(String.valueOf(progress));
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
////        redQTSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                RedQT.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        greenQTSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                    GreenQT.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        blueQTSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                BlueQT.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        redSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                RedSig.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        greenSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                GreenSig.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        blueSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                BlueSig.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        redAODSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                RedSigAOD.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        greenAODSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                GreenSigAOD.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
////
////        blueAODSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
////            @Override
////            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
////                BlueSigAOD.setText(String.valueOf(progress));
////            }
////
////            @Override
////            public void onStartTrackingTouch(SeekBar seekBar) {
////
////            }
////
////            @Override
////            public void onStopTrackingTouch(SeekBar seekBar) {
////
////            }
////        });
//    }

    public void buttons(final Button button, final String prefR, final String prefG, final String prefB, final String name) throws IOException {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        int red = sharedPrefs.getInt(prefR, 255);
                        int green = sharedPrefs.getInt(prefG, 255);
                        int blue = sharedPrefs.getInt(prefB, 255);

                        ContentResolver cr = activity.getContentResolver();

                        Settings.System.putInt(cr, prefR, red);
                        Settings.System.putInt(cr, prefG, green);
                        Settings.System.putInt(cr, prefB, blue);

                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt(prefR, red);
                        editor.putInt(prefG, green);
                        editor.putInt(prefB, blue);
                        editor.apply();

                        logger(prefR, prefG, prefB, red, green, blue, name);

                        if (button == applySig || button == applyAODSig) {
                            sudo("killall com.lge.signboard");
                        } else if (button == applyNav) {
                            sudo("killall com.android.systemui");
                        } else {
                            sudo("killall com.lge.quicktools ; killall com.lge.signboard");
                        }
                    }
                };

                new Thread(runnable).start();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        int red = 255;
//                        int green = 255;
//                        int blue = 255;
//                        if (button == applyQT) {
//                            red = redIntQT;
//                            green = greenIntQT;
//                            blue = blueIntQT;
//                        } else if (button == applySig) {
//                            red = redIntSig;
//                            green = greenIntSig;
//                            blue = blueIntSig;
//                        } else if (button == applyAODSig) {
//                            red = redIntAODSig;
//                            green = greenIntAODSig;
//                            blue = blueIntAODSig;
//                        }
//
//                        ContentResolver cr = activity.getContentResolver();
//
//                        Settings.System.putInt(cr, prefR, red);
//                        Settings.System.putInt(cr, prefG, green);
//                        Settings.System.putInt(cr, prefB, blue);
//
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putInt(prefR, red);
//                        editor.putInt(prefG, green);
//                        editor.putInt(prefB, blue);
//                        editor.apply();
//
//                        logger(prefR, prefG, prefB, red, green, blue, name);
//
//                        try {
//                            if (button == applyQT) {
//                                sudo("killall com.lge.quicktools");
//                            } else if (button == applySig) {
//                                sudo("killall com.lge.signboard");
//////                                Intent intent = new Intent("com.lge.signboard.intent.action.SET_WIDGET_SIGNATURE");
//////                                String flag = "flag_set_widget_signature";
//////
//////                                intent = intent.putExtra(flag, true);
//////
//////                                activity.startActivity(intent);
////
////                                String test = "content://com.lge.provider.signboard/signature?notify=true";
////
////                                Uri uri = Uri.parse(test);
////
////                                ContentValues cv = new ContentValues();
////
////                                String value = "value";
////
////                                cv.put(value, "TEST");
////
////                                String item = "item";
////                                String[] on = {"screen_on_sharing"};
////
////                                cr.update(uri, cv, item, null);
////
//                            } else if (button == applyAODSig) {
//                                sudo("killall com.lge.signboard");
//                            } else {
//                                sudo("killall com.lge.quicktools ; killall com.lge.signboard");
//                            }
//                        } catch (Exception e) {
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }).start();
            }
        });

//        applyQT.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new Thread(new Runnable() {
//                    public void run() {
//                        ContentResolver cr = activity.getContentResolver();
//
//                        Settings.System.putInt(cr, "red", redIntQT);
//                        Settings.System.putInt(cr, "green", greenIntQT);
//                        Settings.System.putInt(cr, "blue", blueIntQT);
//
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putInt("red", redIntQT);
//                        editor.putInt("green", greenIntQT);
//                        editor.putInt("blue", blueIntQT);
//                        editor.apply();
//
//                        try {
//                            sudo("killall com.lge.signboard");
//                            sudo("killall com.lge.appwidget.signature");
//                            sudo("killall com.lge.quicktools");
//                        } catch (Exception e) {
//                            Log.e("ModControl/E", e.getMessage());
//                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                        }
//                    }
//                }).start();
//            }
//        });
//
//        applySig.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable () {
//                    public void run() {
//                        ContentResolver cr = activity.getContentResolver();
//
//                        Settings.System.putInt(cr, "redsig", redIntSig);
//                        Settings.System.putInt(cr, "greensig", greenIntSig);
//                        Settings.System.putInt(cr, "bluesig", blueIntSig);
//
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putInt("redsig", redIntSig);
//                        editor.putInt("greensig", greenIntSig);
//                        editor.putInt("bluesig", blueIntSig);
//                        editor.apply();
//
//                        try {
//                            sudo("killall com.lge.signboard");
//                            sudo("killall com.lge.appwidget.signature");
//                            sudo("killall com.lge.quicktools");
//                        } catch (Exception e) {
//                            Log.e("ModControl/E", e.getMessage());
//                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                        }
//                    }
//                }).start();
//            }
//        });
//
//        applyAODSig.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable () {
//                    public void run() {
//                        ContentResolver cr = activity.getContentResolver();
//
//                        Settings.System.putInt(cr, "redsigaod", redIntAODSig);
//                        Settings.System.putInt(cr, "greensigaod", greenIntAODSig);
//                        Settings.System.putInt(cr, "bluesigaod", blueIntAODSig);
//
//                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
//                        editor.putInt("redsigaod", redIntAODSig);
//                        editor.putInt("greensigaod", greenIntAODSig);
//                        editor.putInt("bluesigaod", blueIntAODSig);
//                        editor.apply();
//
//                        try {
//                            sudo("killall com.lge.signboard");
//                            sudo("killall com.lge.appwidget.signature");
//                            sudo("killall com.lge.quicktools");
//                        } catch (Exception e) {
//                            Log.e("ModControl/E", e.getMessage());
//                            sudo("echo \"ModControl/E" + e.getMessage() + "\" >> " + Environment.getExternalStorageDirectory() + "/Zacharee1Mods/output.log");
//                        }
//                    }
//                }).start();
//            }
//        });
    }

//    public void qtOption() throws IOException {
//        qtGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearQTRad(qtGroup2);
//                    clearQTRad(qtGroup3);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installqt = "installqt";
//                if (checkedId == R.id.red_tool) {
//                    if (enabled) {
//                        Settings.System.putInt(activity.getContentResolver(), "red", 0xff);
//                        Settings.System.putInt(activity.getContentResolver(), "green", 0x0);
//                        Settings.System.putInt(activity.getContentResolver(), "blue", 0x0);
//                        final String file = "qtred.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                else if (checkedId == R.id.white_tool) {
//                    if (enabled) {
//                        final String file = "qtwhite.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                else if (checkedId == R.id.green_tool) {
//                    if (enabled) {
//
//                        Settings.System.putInt(activity.getContentResolver(), "red", 0x0);
//                        Settings.System.putInt(activity.getContentResolver(), "green", 0xff);
//                        Settings.System.putInt(activity.getContentResolver(), "blue", 0x0);
//                        final String file = "qtgreen.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearQTRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });

//        qtGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearQTRad(qtGroup1);
//                    clearQTRad(qtGroup3);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installqt = "installqt";
//                if (checkedId == R.id.purple_tool) {
//                    if (enabled) {
//                        final String file = "qtpurple.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.multi_tool) {
//                    if (enabled) {
//                        final String file = "qtmulti.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.blue_tool) {
//                    if (enabled) {
//                        final String file = "qtblue.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearQTRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//
//        qtGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearQTRad(qtGroup1);
//                    clearQTRad(qtGroup2);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installqt = "installqt";
//                if (checkedId == R.id.orange_tool) {
//                    if (enabled) {
//                        final String file = "qtorange.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.yellow_tool) {
//                    if (enabled) {
//                        final String file = "qtyellow.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearQTRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//    }

//    public void sigOption() throws IOException {
//        sigGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearSigRad(sigGroup2);
//                    clearSigRad(sigGroup3);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installsig = "installsig";
//                if (checkedId == R.id.red_sig) {
//                    if (enabled) {
//                        final String file = "sigred.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.white_sig) {
//                    if (enabled) {
//                        final String file = "sigwhite.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.green_sig) {
//                    if (enabled) {
//                        final String file = "siggreen.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearSigRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//
//        sigGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearSigRad(sigGroup1);
//                    clearSigRad(sigGroup3);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installsig = "installsig";
//                if (checkedId == R.id.purple_sig) {
//                    if (enabled) {
//                        final String file = "sigpurple.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.orange_sig) {
//                    if (enabled) {
//                        final String file = "sigorange.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.blue_sig) {
//                    if (enabled) {
//                        final String file = "sigblue.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearSigRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//
//        sigGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearSigRad(sigGroup2);
//                    clearSigRad(sigGroup1);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installsig = "installsig";
//                if (checkedId == R.id.yellow_sig) {
//                    if (enabled) {
//                        final String file = "sigyellow.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearSigRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//    }

//    public void aodSigOption() throws IOException {
//        aodSigGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearAODSigRad(aodSigGroup2);
//                    clearAODSigRad(aodSigGroup3);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installsig = "installsigaod";
//                if (checkedId == R.id.red_aod_sig) {
//                    if (enabled) {
//                        final String file = "aodsigred.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.white_aod_sig) {
//                    if (enabled) {
//                        final String file = "aodsigwhite.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.green_aod_sig) {
//                    if (enabled) {
//                        final String file = "aodsiggreen.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearAODSigRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//
//        aodSigGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearAODSigRad(aodSigGroup1);
//                    clearAODSigRad(aodSigGroup3);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installsig = "installsigaod";
//                if (checkedId == R.id.purple_aod_sig) {
//                    if (enabled) {
//                        final String file = "aodsigpurple.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.orange_aod_sig) {
//                    if (enabled) {
//                        final String file = "aodsigorange.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                } else if (checkedId == R.id.blue_aod_sig) {
//                    if (enabled) {
//                        final String file = "aodsigblue.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearAODSigRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//
//        aodSigGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                try {
//                    clearAODSigRad(aodSigGroup2);
//                    clearAODSigRad(aodSigGroup1);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                final String installsig = "installsigaod";
//                if (checkedId == R.id.purple_aod_sig) {
//                    if (enabled) {
//                        final String file = "aodsigyellow.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installsig, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("ModControl/E", e.getMessage());
//                        }
//                    }
//                }
//                if (!enabled) {
//                    try {
//                        clearAODSigRad(group);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }
//        });
//    }

//    public void clearQTRad(RadioGroup group) throws IOException {
//        group.setOnCheckedChangeListener(null);
//        group.clearCheck();
//        qtOption();
//    }

//    public void clearSigRad(RadioGroup group) throws IOException {
//        group.setOnCheckedChangeListener(null);
//        group.clearCheck();
//        sigOption();
//    }

//    public void clearAODSigRad(RadioGroup group) throws IOException {
//        group.setOnCheckedChangeListener(null);
//        group.clearCheck();
//        aodSigOption();
//    }

//    public void copyZip(String targetFile) throws IOException {
//        String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
//        AssetManager assetManager = activity.getAssets1();
//        File modFolder = new File(targetDirectory);
//
//        if (!modFolder.isDirectory()) {
//            boolean result = modFolder.mkdir();
//            if (!result) {
//                throw new IOException("Could not create nonexistent mod folder. Abort.");
//            }
//        }
//        try (
//                InputStream in = assetManager.open(targetFile);
//                OutputStream out = new FileOutputStream(targetDirectory + targetFile)
//        ) {
//            copyFile(in, out);
//        }
//        try (
//                InputStream in =  assetManager.open("zip");
//                OutputStream out = new FileOutputStream(targetDirectory + "zip")
//        ) {
//            copyFile(in, out);
//        }
//    }

//    public void copyFile2(final String targetFile, final String zipFile) throws IOException {
//        final String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
//        final AssetManager assetManager = activity.getAssets1();
//        File modFolder = new File(targetDirectory);
//
//        if (!modFolder.isDirectory()) {
//            boolean result = modFolder.mkdir();
//            if (!result) {
//                throw new IOException("Could not create nonexistent mod folder. Abort.");
//            }
//        }

//        new Thread(new Runnable() {
//            public void run() {
//                try (
//                        InputStream in = assetManager.open(targetFile);
//                        OutputStream out = new FileOutputStream(targetDirectory + targetFile)
//                ) {
//                    copyFile(in, out);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//                try (
//                        InputStream in = assetManager.open("zip");
//                        OutputStream out = new FileOutputStream(targetDirectory + "zip")
//                ) {
//                    copyFile(in, out);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//            }
//        }).start();
//
//        if (!targetFile.contains("restore")) {
//            new Thread(new Runnable() {
//                public void run() {
//                    try {
//                        runScript(targetDirectory, targetFile, zipFile);
//                    } catch (Exception e) {
//                        Log.e("ModControl/E", e.getMessage());
//                    }
//                }
//            }).start();
//        }
//    }

//    public void copyFile(InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[10240];
//        int read;
//        while((read = in.read(buffer)) != -1){
//            out.write(buffer, 0, read);
//        }
//    }

//    public void runScript(final String targetDirectory, final String targetFile, final String zip) throws IOException{
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    sudo("chmod +x /data/media/0/Zacharee1Mods/" + targetFile);
//                    sudo("chmod 777 /data/media/0/Zacharee1Mods/" + targetFile);
//                    sudo("sh /data/media/0/Zacharee1Mods/" + targetFile + " " + zip);
//                } catch (Exception e) {
//                    Log.e("ModControl/E", e.getMessage());
//                }
//            }
//        }).start();
//    }

    public void logger(final String prefR, final String prefG, final String prefB, final int red, final int green, final int blue, final String name) {
        try {
            copyFile2("logcolor");
            sudo("busybox sh /data/media/0/Zacharee1Mods/logcolor " + prefR + " " + prefG + " " + prefB + " " + red + " " + green + " " + blue + " " + name + " >> /data/media/0/Zacharee1Mods/output.log 2>&1");
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
        }
    }

    public void copyFile2(final String targetFile) throws IOException {
        final String targetDirectory = Environment.getExternalStorageDirectory().toString() + "/Zacharee1Mods/";
        final AssetManager assetManager = activity.getAssets();
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
        } catch (Exception e) {
            Log.e("ModControl/E", e.getMessage());
        }
    }

    public void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[10240];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
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