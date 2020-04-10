package com.pleiade.android.activities;

import android.app.Activity;
import android.os.Bundle;

import com.pleiade.android.R;

/**
 * Activité principale de l'application
 */
public class MainActivity extends Activity {

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
