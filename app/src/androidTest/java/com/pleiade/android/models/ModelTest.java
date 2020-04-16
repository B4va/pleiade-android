package com.pleiade.android.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleiade.android.utils.FirebaseTestHelper;

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
        FirebaseTestHelper.initializeFirebaseApp();
        db = FirebaseFirestore.getInstance();
        db = FirebaseTestHelper.initializeFirebaseFirestoreEmulator();
        auth = FirebaseTestHelper.initializeFirebaseAuth();
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

    /**
     * Efface les données de test
     */
    @AfterClass
    public static void tearDown(){
        FirebaseTestHelper.clearFirebaseFirestoreEmulator();
    }

    /**
     * Teste la méthode Create avec des paramètres valides
     */
    public void testCreateV() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Create avec des paramètres invalides
     */
    public void testCreateI() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Read
     */
    public void testRead() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Update avec des paramètres valides
     */
    public void testUpdateV() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Update avec des paramètres invalides
     */
    public void testUpdateI() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

    /**
     * Teste la méthode Delete
     */
    public void testDelete() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

    /**
     * Teste le CRUD sans authentification
     */
    public void testNoAuthActions() throws Exception {
        FirebaseTestHelper.firebaseAuthLogout(auth);
    }

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    public void testGenericAuthActions() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER2_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
    }

}
