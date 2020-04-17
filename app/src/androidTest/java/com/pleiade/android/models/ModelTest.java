package com.pleiade.android.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleiade.android.utils.FirebaseTestHelper;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

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
        db = FirebaseTestHelper.initializeFirebaseFirestoreEmulator();
        auth = FirebaseTestHelper.initializeFirebaseAuth();
    }

    /**
     * Efface les données de test
     */
    @AfterClass
    public static void tearDown(){
        FirebaseTestHelper.clearFirebaseFirestoreEmulator();
    }

    /**
     * Initialise les modèles de test pour chaque test
     */
    public abstract Object initializeModels();

    /**
     * Teste la méthode Create avec des paramètres valides
     */
    public void testCreateV() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Create avec des paramètres invalides
     */
    public void testCreateI() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Read
     */
    public void testRead() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Update avec des paramètres valides
     */
    public void testUpdateV() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Update avec des paramètres invalides
     */
    public void testUpdateI() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Delete
     */
    public void testDelete() throws Exception {
        authentication();
    }

    /**
     * Teste le CRUD sans authentification
     */
    public void testNoAuthActions() throws Exception {
        FirebaseTestHelper.firebaseAuthLogout(auth);
        assumeTrue(auth.getCurrentUser() == null);
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
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestHelper.USER2_EMAIL));
    }

    /**
     * Authentifie un utilisateur
     * @throws Exception erreur d'authentification
     */
    private void authentication() throws Exception {
        FirebaseTestHelper.firebaseAuthLogin(
                auth,
                FirebaseTestHelper.USER1_EMAIL,
                FirebaseTestHelper.USER_PASSWORD
        );
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestHelper.USER1_EMAIL));
    }

}
