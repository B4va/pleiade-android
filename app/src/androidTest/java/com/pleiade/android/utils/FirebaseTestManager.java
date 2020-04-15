package com.pleiade.android.utils;

import android.content.Context;
import android.net.Uri;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Classe utilitaire d'initialisation et de paramétrage
 * des instances Firebase
 */
public class FirebaseTestManager {

    private static final int AUTH_TIMEOUT = 3;
    public static final String USER1_FIRST_NAME = "Charlotte";
    public static final String USER2_FIRST_NAME = "Jean";
    public static final String USER1_LAST_NAME = "Corday";
    public static final String USER2_LAST_NAME = "Jaurès";
    public static final String USER1_EMAIL = "user1@mail.com";
    public static final String USER2_EMAIL = "user2@mail.com";
    public static final String USER_PASSWORD = "123456";
    public static final Uri USER_PHOTO_URI = Uri.parse("test/test/test");
    public static final String USER1_DISPLAY_NAME = "user1";
    public static final String USER2_DISPLAY_NAME = "user2";

    /**
     * Initialise l'application Firebase
     */
    public static void initializeFirebaseApp(){
        Context context = ApplicationProvider.getApplicationContext();
        FirebaseApp.initializeApp(context);
    }

    /**
     * Initialise l'émulateur FirebaseFirestore
     * @return instance de l'émulateur
     */
    public static FirebaseFirestore initializeFirebaseFirestoreEmulator() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setHost("localhost:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
        return db;
    }

    /**
     * Ecrase les données du simulateur FirebaseFirestore
     */
    public static void clearFirebaseFirestoreEmulator() {
        FirebaseFirestore.getInstance().clearPersistence();
    }

    /**
     * Initialise FirebaseAuth
     * @return instance FirebaseAuth
     */
    public static FirebaseAuth initializeFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    /**
     * Connecte un utilisateur
     * @param auth instance FirebaseAuth
     * @param email email de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @throws Exception si tâche interrompue
     */
    public static void firebaseAuthLogin(FirebaseAuth auth, String email, String password) throws Exception {
        if (auth.getCurrentUser() == null){
            Tasks.await(auth.signInWithEmailAndPassword(email, password), AUTH_TIMEOUT, TimeUnit.SECONDS);
            if (auth.getCurrentUser() == null){
                throw new Exception("L'utilisateur n'a pas pu être authentifié");
            }
        } else {
            if (!Objects.equals(auth.getCurrentUser().getEmail(), email)){
                firebaseAuthLogout(auth);
                firebaseAuthLogin(auth, email, password);
            }
        }
    }

    /**
     * Déconnecte l'utilisateur authentifié
     * @param auth instance FirebaseAuth
     */
    public static void firebaseAuthLogout(FirebaseAuth auth){
        if (auth.getCurrentUser() != null){
            auth.signOut();
        }
    }
}
