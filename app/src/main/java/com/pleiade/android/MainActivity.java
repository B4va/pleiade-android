package com.pleiade.android;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

/**
 * Activité principale de l'application
 */
public class MainActivity extends Activity {

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Todo : améliorer la gestion des polices
         */
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "josefin_sans_regular.ttf");
        TextView text = findViewById(R.id.text);
        text.setTypeface(custom_font);
    }
}
