package com.pleiade.android.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleiade.android.utils.FirebaseTestManager;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * Ressources et méthodes de base pour le test des modèles
 */
public abstract class ModelTest {

    protected static final String TAG = "DevTest";
    protected static FirebaseFirestore db;
    protected static FirebaseAuth auth;

    /**
     * Met en place les instances Firebase et auhtentifie un
     * utilisateur test
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
     * Teste la méthode Create avec des paramètres valides
     */
    @Test
    public abstract void testCreateV() throws ExecutionException, InterruptedException;

    /**
     * Teste la méthode Create avec des paramètres invalides
     */
    @Test
    public abstract void testCreateI();

    /**
     * Teste la méthode Read
     */
    @Test
    public abstract void testRead();

    /**
     * Teste la méthode Update avec des paramètres valides
     */
    @Test
    public abstract void testUpdateV();

    /**
     * Teste la méthode Update avec des paramètres invalides
     */
    @Test
    public abstract void testUpdateI();

    /**
     * Teste la méthode Delete
     */
    @Test
    public abstract void testDelete();

    /**
     * Teste le CRUD sans authentification
     */
    @Test
    public abstract void testNoAuthActions();

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    @Test
    public abstract void testGenericAuthActions();

    }
