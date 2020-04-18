package com.pleiade.android.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleiade.android.utils.FirebaseTestHelper;

import org.junit.After;
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
    @After
    public void tearDown(){
        FirebaseTestHelper.clearFirebaseFirestoreEmulator();
    }

    /**
     * Initialise les modèles de test pour chaque test
     */
    public abstract void initializeModels() throws Exception;

    /**
     * Teste la méthode Create avec des paramètres valides
     */
    public void testA_CreateV() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Create avec des paramètres invalides
     */
    public void testB_CreateI() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Read
     */
    public void testC_Read() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Update avec des paramètres valides
     */
    public void testD_UpdateV() throws Exception {
        authentication();
    }

    /**
     * Teste la méthode Update avec des paramètres invalides
     */
    public void testE_UpdateI() throws Exception {
        authentication();
    }

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    public void testF_WrongAuthActions() throws Exception {
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
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    public void testG_NoAuthActions() throws Exception {
        FirebaseTestHelper.firebaseAuthLogout(auth);
        assumeTrue(FirebaseAuth.getInstance().getCurrentUser() == null);
    }

    /**
     * Teste la méthode Delete
     */
    public void testH_Delete() throws Exception {
        authentication();
    }

    /**
     * Authentifie un utilisateur
     * @throws Exception erreur d'authentification
     */
    void authentication() throws Exception {
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
