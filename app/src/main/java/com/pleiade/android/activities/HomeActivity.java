package com.pleiade.android.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pleiade.android.R;
import com.pleiade.android.utils.UIHelper;

public class HomeActivity extends AppCompatActivity {

    /**
     * Exécutée à la création de l'instance
     * @param savedInstanceState instance sauvegardée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.setActionBar(this);
        setContentView(R.layout.activity_home);
    }
}
