package com.pleiade.android.rules;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pleiade.android.utils.FirebaseTestManager;

import org.junit.BeforeClass;
import org.junit.Test;

public abstract class RulesTester {

    public static String TAG = "DevTest";
    protected static FirebaseFirestore db;
    protected static FirebaseAuth auth;

    @BeforeClass
    public static void setup(){
        FirebaseTestManager.initializeFirebaseApp();
        db = FirebaseTestManager.initializeFirebaseFirestoreEmulator();
        auth = FirebaseTestManager.initializeFirebaseAuth();
    }

    @Test
    public abstract void testValidCreation() throws Exception;

    @Test
    public abstract void testInvalidCreation();

    @Test
    public abstract void testRead();

    @Test
    public abstract void testValidUpdate();

    @Test
    public abstract void testInvalidUpdate();

    @Test
    public abstract void testDelete();

    @Test
    public abstract void testNoAuthActions();

    @Test
    public abstract void testGenericAuthActions();

    }
