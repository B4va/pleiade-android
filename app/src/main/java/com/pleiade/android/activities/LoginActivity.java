package com.pleiade.android.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pleiade.android.R;

/**
 * Vue de connexion
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // todo: reprendre la vue en scrollView, refactoriser et styliser editText

    }
}
