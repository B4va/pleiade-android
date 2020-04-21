package com.pleiade.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pleiade.android.R;

/**
 * Ecran de chargement au lancement de l'activité
 */
public class SplashActivity extends AppCompatActivity {

    private static final int TRANSITION_TIMEOUT = 3000;

    private Button loginBtn, signinBtn;
    private ProgressBar progressBar;

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        loginBtn = findViewById(R.id.loginBtn);
        signinBtn = findViewById(R.id.signinBtn);
        progressBar = findViewById(R.id.progressBar);

        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();

        /*
         * Redirige vers HomeActvity si utilisateur connecté, sinon
         * propose la connexion et l'inscription
         */
        new Handler().postDelayed(() -> {
            if (fbu != null){
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                progressBar.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);
                signinBtn.setVisibility(View.VISIBLE);
            }
        }, TRANSITION_TIMEOUT);
    }

    /**
     * Redirige vers la page de connexion
     * @param view vue courante
     */
    public void login(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Redirige vers la page d'inscription
     * @param view vue courante
     */
    public void signin(View view) {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
        finish();
    }
}
