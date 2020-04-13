package com.pleiade.android;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.pleiade.android.models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests du modèle User
 * @see User
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class UserDbTest {

    private Context context = ApplicationProvider.getApplicationContext();
    private static FirebaseFirestore db;
    private static FirebaseAuth auth;

    @Before
    public void firebaseAppInitialisation(){
        FirebaseApp.initializeApp(context);
    }

    @Before
    public void firebaseFirestoreInitialisation() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setHost("localhost:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(false)
                .build();
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
    }

    @Before
    public void firebaseAuthIntitialisation(){
        // todo
    }

    @Test
    public void testValidUserCreation() {
        // avec tous les champs

        // sans email

        // sans image

    }

    @Test
    public void testInvalidUserCreation() {
        // sans authentification

        // sans appartenance à l'utilisateur authentifié

        // sans prénom

        // sans nom

        // sans tag

        // sans date de création

        // sans date de modification

        // avec un champ non existant

        // avec un prénom non conforme

        // avec un nom non conforme

        // avec un email non conforme

        // avec un tag non conforme
    }

    @Test
    public void testValidUserUpdate() {
        // tous les champs

        // uniquement prénom

        // uniquement nom

        // uniquement tag

        // uniquement email

        // uniquement image

        // uniquement date de modification
    }

    @Test
    public void testInvalidUserUpdate() {
        // sans authentification

        // sans appartenance à l'utilisateur authentifié

        // sans prénom

        // sans nom

        // sans tag

        // avec date de création

        // sans date de modification

        // avec un champ non existant

        // avec un prénom non conforme

        // avec un nom non conforme

        // avec un email non conforme

        // avec un tag non conforme
    }

    @Test
    public void testValidUserDeletion() {

    }

    @Test
    public void testInvalidUserDeletion() {
        // sans authentification

        // sans appartenance à l'utilisateur authentifié
    }

    @Test
    public void testValidUserRead() {
        // avec appartenance à l'utilisateur authentifié

        // sans appartenance à l'utilisateur authentifié
    }

    @Test
    public void testInvalidUserRead() {
        // sans authentification

    }
}