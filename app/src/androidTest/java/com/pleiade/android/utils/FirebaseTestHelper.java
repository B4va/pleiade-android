package com.pleiade.android.utils;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Classe utilitaire d'initialisation et de paramétrage
 * des instances Firebase
 */
public class FirebaseTestHelper {

    private static final int AUTH_TIMEOUT = 3;

    public static final String USER1_EMAIL = "user1@mail.com";
    public static final String USER2_EMAIL = "user2@mail.com";
    public static final String USER3_EMAIL = "user3@mail.com";
    public static final String USER_PASSWORD = "123456";

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
    public static FirebaseFirestore initializeFirebaseFirestoreEmulator() throws SocketException, UnknownHostException {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setHost("10.0.2.2:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
        Task<DocumentSnapshot> t = db.collection("users").document("test").get();
        try {
            Tasks.await(t);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        t.getResult();
        return db;
    }

    /**
     * Ecrase les données du simulateur FirebaseFirestore
     */
    public static void clearFirebaseFirestoreEmulator() {
        FirebaseFirestore.getInstance().clearPersistence();
    }

    /**
     * Initialise FirebaseAuth et crée deux utilisateurs
     * @return instance FirebaseAuth
     */
    public static FirebaseAuth initializeFirebaseAuth() throws ExecutionException, InterruptedException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        try{
            Tasks.await(auth.signInWithEmailAndPassword(USER1_EMAIL, USER_PASSWORD));
        } catch (Exception e){
            Tasks.await(auth.createUserWithEmailAndPassword(USER1_EMAIL, USER_PASSWORD));
        }
        try{
            Tasks.await(auth.signInWithEmailAndPassword(USER2_EMAIL, USER_PASSWORD));
        } catch (Exception e){
            Tasks.await(auth.createUserWithEmailAndPassword(USER2_EMAIL, USER_PASSWORD));
        }
        return auth;
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
