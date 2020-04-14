package com.pleiade.android;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.pleiade.android.models.User;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

/**
 * Tests du modèle User
 * @see User
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class UserDbTest {

    private static FirebaseFirestore db;
    private static FirebaseAuth auth;

    @BeforeClass
    public static void firebaseAppInitialisation(){
        Context context = ApplicationProvider.getApplicationContext();
        FirebaseApp.initializeApp(context);
    }

    @BeforeClass
    public static void firebaseFirestoreInitialisation() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setHost("localhost:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(false)
                .build();
        db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
    }

    @BeforeClass
    public static void firebaseAuthInitialisation(){
        auth = FirebaseAuth.getInstance();
    }

    private static void firebaseAuthLogin() throws InterruptedException {
        if (auth.getCurrentUser() == null){
            auth.signInWithEmailAndPassword("test@mail.com", "123456");
        }
        Thread.sleep(3000);
        assumeNotNull(auth.getCurrentUser());
    }

    private static void firebaseAuthLogout(){
        if (auth.getCurrentUser() != null){
            auth.signOut();
        }
        assumeTrue(auth.getCurrentUser() == null);
    }

    @Test
    public void testValidUserCreation() throws InterruptedException {
        firebaseAuthLogin();
        assertNotNull("test raté", FirebaseAuth.getInstance());

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