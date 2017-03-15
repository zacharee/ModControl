package com.zacharee1.modcontrol;

/**
 * Created by Zacha on 3/10/2017.
 */

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import static android.content.Context.MODE_PRIVATE;


public class NoModsFragment extends Fragment {
    public View view;
    MainActivity activity;

    public boolean enabled;
    public SharedPreferences sharedPrefs;

    public EditText RedQT;
    public EditText GreenQT;
    public EditText BlueQT;
    public EditText RedSig;
    public EditText GreenSig;
    public EditText BlueSig;
    public EditText RedSigAOD;
    public EditText GreenSigAOD;
    public EditText BlueSigAOD;

    public int redIntQT;
    public int greenIntQT;
    public int blueIntQT;
    public int redIntSig;
    public int greenIntSig;
    public int blueIntSig;
    public int redIntAODSig;
    public int greenIntAODSig;
    public int blueIntAODSig;

    public SeekBar redQTSeek;
    public SeekBar greenQTSeek;
    public SeekBar blueQTSeek;
    public SeekBar redSigSeek;
    public SeekBar greenSigSeek;
    public SeekBar blueSigSeek;
    public SeekBar redAODSigSeek;
    public SeekBar greenAODSigSeek;
    public SeekBar blueAODSigSeek;

    public ImageView QTPreview;
    public ImageView SigPreview;
    public ImageView AODSigPreview;

    public int QTColor;
    public int SigColor;
    public int AODSigColor;

    public Button applyQT;

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

        RedQT = (EditText) view.findViewById(R.id.red_qt_val);
        GreenQT = (EditText) view.findViewById(R.id.green_qt_val);
        BlueQT = (EditText) view.findViewById(R.id.blue_qt_val);
        RedSig = (EditText) view.findViewById(R.id.red_sig_val);
        GreenSig = (EditText) view.findViewById(R.id.green_sig_val);
        BlueSig = (EditText) view.findViewById(R.id.blue_sig_val);
        RedSigAOD = (EditText) view.findViewById(R.id.red_aodsig_val);
        GreenSigAOD = (EditText) view.findViewById(R.id.green_aodsig_val);
        BlueSigAOD = (EditText) view.findViewById(R.id.blue_aodsig_val);

        redQTSeek = (SeekBar) view.findViewById(R.id.redqt_seek);
        greenQTSeek = (SeekBar) view.findViewById(R.id.greenqt_seek);
        blueQTSeek = (SeekBar) view.findViewById(R.id.blueqt_seek);
        redSigSeek = (SeekBar) view.findViewById(R.id.redsig_seek);
        greenSigSeek = (SeekBar) view.findViewById(R.id.greensig_seek);
        blueSigSeek = (SeekBar) view.findViewById(R.id.bluesig_seek);
        redAODSigSeek = (SeekBar) view.findViewById(R.id.redaodsig_seek);
        greenAODSigSeek = (SeekBar) view.findViewById(R.id.greenaodsig_seek);
        blueAODSigSeek = (SeekBar) view.findViewById(R.id.blueaodsig_seek);

        QTPreview = (ImageView) view.findViewById(R.id.colorqt_preview);
        SigPreview = (ImageView) view.findViewById(R.id.colorsig_preview);
        AODSigPreview = (ImageView) view.findViewById(R.id.coloraodsig_preview);

        applyQT = (Button) view.findViewById(R.id.apply_qt_color);

        RedQT.setText(String.valueOf(sharedPrefs.getInt("red", 0)));
        GreenQT.setText(String.valueOf(sharedPrefs.getInt("green", 0)));
        BlueQT.setText(String.valueOf(sharedPrefs.getInt("blue", 0)));
        RedSig.setText(String.valueOf(sharedPrefs.getInt("redsig", 0)));
        GreenSig.setText(String.valueOf(sharedPrefs.getInt("greensig", 0)));
        BlueSig.setText(String.valueOf(sharedPrefs.getInt("bluesig", 0)));
        RedSigAOD.setText(String.valueOf(sharedPrefs.getInt("redsigaod", 0)));
        GreenSigAOD.setText(String.valueOf(sharedPrefs.getInt("greensigaod", 0)));
        BlueSigAOD.setText(String.valueOf(sharedPrefs.getInt("bluesigaod", 0)));

        redQTSeek.setProgress(sharedPrefs.getInt("red", 0));
        greenQTSeek.setProgress(sharedPrefs.getInt("green", 0));
        blueQTSeek.setProgress(sharedPrefs.getInt("blue", 0));
        redSigSeek.setProgress(sharedPrefs.getInt("redsig", 0));
        greenSigSeek.setProgress(sharedPrefs.getInt("greensig", 0));
        blueSigSeek.setProgress(sharedPrefs.getInt("bluesig", 0));
        redAODSigSeek.setProgress(sharedPrefs.getInt("redsigaod", 0));
        greenAODSigSeek.setProgress(sharedPrefs.getInt("greensigaod", 0));
        blueAODSigSeek.setProgress(sharedPrefs.getInt("bluesigaod", 0));

        redIntQT = sharedPrefs.getInt("red", 0);
        greenIntQT = sharedPrefs.getInt("green", 0);
        blueIntQT = sharedPrefs.getInt("blue", 0);
        redIntSig = sharedPrefs.getInt("redsig", 0);
        greenIntSig = sharedPrefs.getInt("greensig", 0);
        blueIntSig = sharedPrefs.getInt("bluesig", 0);
        redIntAODSig = sharedPrefs.getInt("redsigaod", 0);
        greenIntAODSig = sharedPrefs.getInt("greensigaod", 0);
        blueIntAODSig = sharedPrefs.getInt("bluesigaod", 0);

        QTColor = Color.argb(255, redIntQT, greenIntQT, blueIntQT);
        SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
        AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);

        QTPreview.setColorFilter(QTColor);
        SigPreview.setColorFilter(SigColor);
        AODSigPreview.setColorFilter(AODSigColor);

        try {
//            qtOption();
            sigOption();
            aodSigOption();
            buttons();
            sliders();
            textListeners();
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

    public void textListeners() throws IOException {
        final ContentResolver cr = activity.getContentResolver();

        RedQT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (RedQT.getText().toString().length() > 0) {
                    try {
                        redIntQT = Integer.decode(RedQT.getText().toString());

                        Settings.System.putInt(cr, "red", redIntQT);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("red", redIntQT);
                        editor.apply();

                        redQTSeek.setProgress(redIntQT);

                        if (RedQT.isFocused()) {
                            RedQT.setSelection(RedQT.getText().length());
                        }

                        QTColor = Color.argb(255, redIntQT, greenIntQT, blueIntQT);
                        QTPreview.setColorFilter(QTColor);
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        GreenQT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (GreenQT.getText().toString().length() > 0) {
                    try {
                        greenIntQT = Integer.decode(GreenQT.getText().toString());

                        Settings.System.putInt(cr, "green", greenIntQT);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("green", greenIntQT);
                        editor.apply();

                        greenQTSeek.setProgress(greenIntQT);

                        if (GreenQT.isFocused()) {
                            GreenQT.setSelection(GreenQT.getText().length());
                        }

                        QTColor = Color.argb(255, redIntQT, greenIntQT, blueIntQT);
                        QTPreview.setColorFilter(QTColor);
                    } catch (NumberFormatException e) {

                    }
                }
            }
        });

        BlueQT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (BlueQT.getText().toString().length() > 0) {
                    try {
                        blueIntQT = Integer.decode(BlueQT.getText().toString());

                        Settings.System.putInt(cr, "blue", blueIntQT);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("blue", blueIntQT);
                        editor.apply();

                        blueQTSeek.setProgress(blueIntQT);

                        if (BlueQT.isFocused()) {
                            BlueQT.setSelection(BlueQT.getText().length());
                        }

                        QTColor = Color.argb(255, redIntQT, greenIntQT, blueIntQT);
                        QTPreview.setColorFilter(QTColor);
                    } catch(NumberFormatException e) {

                    }
                }
            }
        });

        RedSig.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (RedSig.getText().toString().length() > 0) {
                    try {
                        redIntSig = Integer.decode(RedSig.getText().toString());

                        Settings.System.putInt(cr, "redsig", redIntSig);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("redsig", redIntSig);
                        editor.apply();

                        redSigSeek.setProgress(redIntSig);

                        if (RedSig.isFocused()) {
                            RedSig.setSelection(RedSig.getText().length());
                        }

                        SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
                        SigPreview.setColorFilter(SigColor);
                    } catch(NumberFormatException e) {

                    }
                }
            }
        });

        GreenSig.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (GreenSig.getText().toString().length() > 0) {
                    try {
                        greenIntSig = Integer.decode(GreenSig.getText().toString());

                        Settings.System.putInt(cr, "greensig", greenIntSig);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("greensig", greenIntSig);
                        editor.apply();

                        greenSigSeek.setProgress(greenIntSig);

                        if (GreenSig.isFocused()) {
                            GreenSig.setSelection(GreenSig.getText().length());
                        }

                        SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
                        SigPreview.setColorFilter(SigColor);
                    } catch(NumberFormatException e) {

                    }
                }
            }
        });

        BlueSig.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (BlueSig.getText().toString().length() > 0) {
                    try {
                        blueIntSig = Integer.decode(BlueSig.getText().toString());

                        Settings.System.putInt(cr, "bluesig", blueIntSig);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("bluesig", blueIntSig);
                        editor.apply();

                        blueSigSeek.setProgress(blueIntSig);

                        if (BlueSig.isFocused()) {
                            BlueSig.setSelection(BlueSig.getText().length());
                        }

                        SigColor = Color.argb(255, redIntSig, greenIntSig, blueIntSig);
                        SigPreview.setColorFilter(SigColor);
                    } catch(NumberFormatException e) {

                    }
                }
            }
        });

        RedSigAOD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (RedSigAOD.getText().toString().length() > 0) {
                    try {
                        redIntAODSig = Integer.decode(RedSigAOD.getText().toString());

                        Settings.System.putInt(cr, "redsigaod", redIntAODSig);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("redsigaod", redIntAODSig);
                        editor.apply();

                        redAODSigSeek.setProgress(redIntAODSig);

                        if (RedSigAOD.isFocused()) {
                            RedSigAOD.setSelection(RedSigAOD.getText().length());
                        }

                        AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);
                        AODSigPreview.setColorFilter(AODSigColor);
                    } catch(NumberFormatException e) {

                    }
                }
            }
        });

        GreenSigAOD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (GreenSigAOD.getText().toString().length() > 0) {
                    try {
                        greenIntAODSig = Integer.decode(GreenSigAOD.getText().toString());

                        Settings.System.putInt(cr, "greensigaod", greenIntAODSig);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("greensigaod", greenIntAODSig);
                        editor.apply();

                        greenAODSigSeek.setProgress(greenIntAODSig);

                        if (GreenSigAOD.isFocused()) {
                            GreenSigAOD.setSelection(GreenSigAOD.getText().length());
                        }

                        AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);
                        AODSigPreview.setColorFilter(AODSigColor);
                    } catch(NumberFormatException e) {

                    }
                }
            }
        });

        BlueSigAOD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (BlueSigAOD.getText().toString().length() > 0) {
                    try {
                        blueIntAODSig = Integer.decode(BlueSigAOD.getText().toString());

                        Settings.System.putInt(cr, "bluesigaod", blueIntAODSig);
                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("bluesigaod", blueIntAODSig);
                        editor.apply();

                        blueAODSigSeek.setProgress(blueIntAODSig);

                        if (BlueSigAOD.isFocused()) {
                            BlueSigAOD.setSelection(BlueSigAOD.getText().length());
                        }

                        AODSigColor = Color.argb(255, redIntAODSig, greenIntAODSig, blueIntAODSig);
                        AODSigPreview.setColorFilter(AODSigColor);
                    } catch(NumberFormatException e) {

                    }
                }
            }
        });
    }

    public void sliders() throws IOException {
        redQTSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RedQT.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenQTSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GreenQT.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blueQTSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                BlueQT.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        redSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RedSig.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GreenSig.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blueSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                BlueSig.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        redAODSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RedSigAOD.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenAODSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                GreenSigAOD.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        blueAODSigSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                BlueSigAOD.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void buttons() throws IOException {
        applyQT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    public void run() {
                        ContentResolver cr = activity.getContentResolver();

                        String redString = RedQT.getText().toString();
                        String greenString = GreenQT.getText().toString();
                        String blueString = BlueQT.getText().toString();

                        redIntQT = Integer.decode(redString);
                        greenIntQT = Integer.decode(greenString);
                        blueIntQT = Integer.decode(blueString);

                        Settings.System.putInt(cr, "red", redIntQT);
                        Settings.System.putInt(cr, "green", greenIntQT);
                        Settings.System.putInt(cr, "blue", blueIntQT);

                        SharedPreferences.Editor editor = activity.getSharedPreferences("com.zacharee1.modcontrol", MODE_PRIVATE).edit();
                        editor.putInt("redqt", redIntQT);
                        editor.putInt("greenqt", greenIntQT);
                        editor.putInt("blueqt", blueIntQT);
                        editor.apply();
                    }
                }).start();
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
                        Settings.System.putInt(activity.getContentResolver(), "red", 0xff);
                        Settings.System.putInt(activity.getContentResolver(), "green", 0x0);
                        Settings.System.putInt(activity.getContentResolver(), "blue", 0x0);
//                        final String file = "qtred.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("error", e.getMessage());
//                        }
                    }
                }
//                else if (checkedId == R.id.white_tool) {
//                    if (enabled) {
//                        final String file = "qtwhite.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("error", e.getMessage());
//                        }
//                    }
//                }
                else if (checkedId == R.id.green_tool) {
                    if (enabled) {

                        Settings.System.putInt(activity.getContentResolver(), "red", 0x0);
                        Settings.System.putInt(activity.getContentResolver(), "green", 0xff);
                        Settings.System.putInt(activity.getContentResolver(), "blue", 0x0);
//                        final String file = "qtgreen.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("error", e.getMessage());
//                        }
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
//                if (checkedId == R.id.purple_tool) {
//                    if (enabled) {
//                        final String file = "qtpurple.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("error", e.getMessage());
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
//                            Log.e("error", e.getMessage());
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
//                            Log.e("error", e.getMessage());
//                        }
//                    }
//                }
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
//                if (checkedId == R.id.orange_tool) {
//                    if (enabled) {
//                        final String file = "qtorange.zip";
//                        try {
//                            copyZip(file);
//                            copyFile2(installqt, file);
//                        } catch (Exception e) {
//                            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.e("error", e.getMessage());
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
//                            Log.e("error", e.getMessage());
//                        }
//                    }
//                }
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