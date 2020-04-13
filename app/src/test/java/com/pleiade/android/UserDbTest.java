package com.pleiade.android;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.api.ContextOrBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.pleiade.android.models.User;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests du modèle User
 * @see User
 */
public class UserDbTest {

    private static FirebaseFirestore db;
    private static FirebaseUser user;

    @BeforeAll
    public static void dbEmulatorInitialisation() {
        // todo
//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setHost("http://localhost:8080")
//                .setSslEnabled(false)
//                .setPersistenceEnabled(false)
//                .build();
//
//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        firestore.setFirestoreSettings(settings);
//        db = firestore;
    }

    @BeforeAll
    public static void authenticatedUserIntitialisation(){
        // todo
    }

    @Test
    public void validUserCreation() {
        // avec tous les champs

        // sans email

        // sans image

    }

    @Test
    public void invalidUserCreation() {
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
    public void validUserUpdate() {
        // tous les champs

        // uniquement prénom

        // uniquement nom

        // uniquement tag

        // uniquement email

        // uniquement image

        // uniquement date de modification
    }

    @Test
    public void invalidUserUpdate() {
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
    public void validUserDeletion() {

    }

    @Test
    public void invalidUserDeletion() {
        // sans authentification

        // sans appartenance à l'utilisateur authentifié
    }

    @Test
    public void validUserRead() {
        // avec appartenance à l'utilisateur authentifié

        // sans appartenance à l'utilisateur authentifié
    }

    @Test
    public void invalidUserRead() {
        // sans authentification

    }
}