package com.example.passwordmanagerv10;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity<CurrentActvity> extends AppCompatActivity
{
    private ArrayList<String> tool1 = new ArrayList<>();
    private ArrayList<String> tool2 = new ArrayList<>();
    private TextView textView1;
    private TextView textView2;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private Button button1;
    private Button button2;

    private SeekBar seekBar;
    private  int progress = 0;
    private int option = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.Password_Generate);
        textView2 = findViewById(R.id.LengthNumber);
        radioButton1 = findViewById(R.id.Option1);
        radioButton2 = findViewById(R.id.Option2);
        button1 = findViewById(R.id.Generate);
        button2 = findViewById(R.id.copy);
        seekBar = findViewById(R.id.Length);
        this.seekBar.setMax(30);
        this.seekBar.setProgress(8);


        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b)
            {
                progress = progressValue;
                textView2.setText(progressValue + "Length");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });

        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(error())
                {
                    if(radioButton1.isChecked())
                    {
                        option = 1;
                        textView1.setText(generatePassword(progress,option));

                    }

                    if (radioButton2.isChecked())
                    {
                        option =2;
                        textView1.setText(generatePassword(progress,option));

                    }

                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ClipboardManager clipboardManager =(ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Eddittext",textView1.getText().toString());
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this,"Text copied", Toast.LENGTH_SHORT).show();



            }
        });
    }



    private boolean error()
    {
        if(!radioButton1.isChecked() && !radioButton2.isChecked())
        {
            Toast.makeText(MainActivity.this,"Error please select your option",Toast.LENGTH_LONG).show();
            return false;
        }

        if(progress >30 || progress < 8)
        {
            Toast.makeText(MainActivity.this,"Error please select your length beetwen 8 and 30",Toast.LENGTH_LONG).show();
            return false;

        }
        return true;
    }

    private String generatePassword(int length,int choix)
    {
        char [] full = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!,@#$%^&*()-=+[]{}<>?0123456789".toCharArray();
        char [] number = "0123456789".toCharArray();
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        if(choix == 1)
        {
            for (int i = 0; i < length; i++) {
                char c = full[r.nextInt(full.length)];
                sb.append(c);
            }
        }
        else
        {
            for (int i = 0; i < length; i++) {
                char c = number[r.nextInt(number.length)];
                sb.append(c);
            }
        }
        return sb.toString();
    }

}