package com.pleiade.android.activities;

import android.app.Activity;
import android.os.Bundle;

import com.pleiade.android.R;

/**
 * Ecran de chargement au lancement de l'activité
 */
public class SplashActivity extends Activity {

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
