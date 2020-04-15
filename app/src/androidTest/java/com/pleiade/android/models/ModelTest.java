package com.pleiade.android.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleiade.android.utils.FirebaseTestManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * Ressources et méthodes de base pour le test des modèles
 */
public abstract class ModelTest {

    protected static final String TAG = "DevTest";
    protected static FirebaseFirestore db;
    protected static FirebaseAuth auth;

    /**
     * Met en place les instances de test
     */
    @BeforeClass
    public static void setup() throws Exception {
        FirebaseTestManager.initializeFirebaseApp();
        db = FirebaseFirestore.getInstance();
        db = FirebaseTestManager.initializeFirebaseFirestoreEmulator();
        auth = FirebaseTestManager.initializeFirebaseAuth();
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

    /**
     * Efface les données de test
     */
    @AfterClass
    public static void tearDown(){
        FirebaseTestManager.clearFirebaseFirestoreEmulator();
    }

    /**
     * Teste la méthode Create avec des paramètres valides
     */
    public void testCreateV() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Create avec des paramètres invalides
     */
    public void testCreateI() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Read
     */
    public void testRead() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Update avec des paramètres valides
     */
    public void testUpdateV() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Update avec des paramètres invalides
     */
    public void testUpdateI() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Delete
     */
    public void testDelete() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

    /**
     * Teste le CRUD sans authentification
     */
    public void testNoAuthActions() throws Exception {
        FirebaseTestManager.firebaseAuthLogout(auth);
    }

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    public void testGenericAuthActions() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER2_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );
    }

}
