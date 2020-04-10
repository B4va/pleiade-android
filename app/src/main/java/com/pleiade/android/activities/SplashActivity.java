package com.pleiade.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pleiade.android.R;

/**
 * Ecran de chargement au lancement de l'activité
 */
public class SplashActivity extends Activity {


    private static final int TRANSITION_TIMEOUT = 3000;

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* Redirige vers MainActivity */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, TRANSITION_TIMEOUT);
    }
}
