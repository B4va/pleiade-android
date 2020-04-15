package com.pleiade.android.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleiade.android.utils.FirebaseTestManager;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Ressources et méthodes de base pour le test des modèles
 */
public abstract class ModelTest {

    private static final String TAG = "DevTest";
    private static FirebaseFirestore db;
    private static FirebaseAuth auth;
    private static FirebaseUser user;

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
        user = auth.getCurrentUser();
    }

    /**
     * Teste la méthode Create
     */
    @Test
    public abstract void testCreate();

    /**
     * Teste la méthode Read
     */
    @Test
    public abstract void testRead();

    /**
     * Teste la méthode Update
     */
    @Test
    public abstract void testUpdate();

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
