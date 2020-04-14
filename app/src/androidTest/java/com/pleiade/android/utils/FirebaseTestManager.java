package com.pleiade.android.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

public class FirebaseTestManager {

    private static final int AUTH_TIMEOUT = 3;
    public static final String USER1_EMAIL = "user1@mail.com";
    public static final String USER2_EMAIL = "user2@mail.com";
    public static final String USER_PASSWORD = "123456";

    public static void initializeFirebaseApp(){
        Context context = ApplicationProvider.getApplicationContext();
        FirebaseApp.initializeApp(context);
    }

    public static FirebaseFirestore initializeFirebaseFirestoreEmulator() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setHost("localhost:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
        return db;
    }

    public static FirebaseAuth initializeFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    public static void firebaseAuthLogin(FirebaseAuth auth, String email, String password) throws Exception {
        auth.signOut();
        Tasks.await(auth.signInWithEmailAndPassword(email, password), AUTH_TIMEOUT, TimeUnit.SECONDS);
        if (auth.getCurrentUser() == null){
            throw new Exception("L'utilisateur n'a pas pu être authentifié");
        }
    }

    public static void firebaseAuthLogout(FirebaseAuth auth){
        if (auth.getCurrentUser() != null){
            auth.signOut();
        }
    }
}
