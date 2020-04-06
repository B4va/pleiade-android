package com.pleiade.android.utils;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.pleiade.android.BuildConfig;

public class DatabaseManager {

    public static void initialize() {
        boolean firebaseEmulator = BuildConfig.FIREBASE_EMULATOR;
        Log.i(DatabaseManager.class.toString(), firebaseEmulator ? "Activé" : "Désactivé");
        if (firebaseEmulator){
            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                    .setHost("10.0.2.2:8080")
                    .setSslEnabled(false)
                    .setPersistenceEnabled(false)
                    .build();

            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.setFirestoreSettings(settings);
        }
    }
}
