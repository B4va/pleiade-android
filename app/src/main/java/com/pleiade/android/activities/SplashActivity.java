package com.pleiade.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pleiade.android.R;
import com.pleiade.android.utils.Tag;

/**
 * Ecran de chargement au lancement de l'activité
 */
public class SplashActivity extends AppCompatActivity {


    private static final int TRANSITION_TIMEOUT = 3000;

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();

        /*
         * Redirige vers LoginActivity si aucun utilisateur connecté,
         * vers HomeActivity si utilisateur connecté
         */
        new Handler().postDelayed(() -> {
            Intent intent;
            if (fbu != null){
                intent = new Intent(getApplicationContext(), HomeActivity.class);
            } else {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            }
            Log.i(Tag.TEST, "Redirection : " + intent.toString());
            startActivity(intent);
            finish();
        }, TRANSITION_TIMEOUT);
    }
}
