package com.pleiade.android.rules;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.SetOptions;
import com.pleiade.android.utils.FirebaseTestManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

/**
 * Tests des règles FirebaseFirestore pour la collection users
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class UsersRulesTest extends RulesTester  {

    @Test
    public void testValidCreation() throws Exception {
        FirebaseTestManager.firebaseAuthLogin(
                auth,
                FirebaseTestManager.USER1_EMAIL,
                FirebaseTestManager.USER_PASSWORD
        );

        // avec tous les champs
        assumeNotNull(auth.getCurrentUser());
        String uid = auth.getCurrentUser().getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("lastName", "Schmidt");
        user.put("firstName", "Jean-Michel");
        user.put("email", auth.getCurrentUser().getEmail());
        user.put("tag", "michmich");
        // user.put("pofilePictureUri", "");
        user.put("createdAt", Timestamp.now());
        user.put("modifiedAt", Timestamp.now());

        DocumentReference ref = db
                .collection("users")
                .document(auth.getCurrentUser().getUid());
        Log.i(TAG, auth.getCurrentUser().getEmail());
        Tasks.await(ref.set(user), 5, TimeUnit.SECONDS);
        assertTrue(true);

        // sans email

        // sans image

    }

    @Test
    public void testInvalidCreation() {
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
    public void testRead() {
    }

    @Test
    public void testValidUpdate() {
        // tous les champs

        // uniquement prénom

        // uniquement nom

        // uniquement tag

        // uniquement email

        // uniquement image

        // uniquement date de modification
    }

    @Test
    public void testInvalidUpdate() {
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
    public void testDelete() {
    }

    @Test
    public void testNoAuthActions() {
        // creation

        // lecture

        // mise à jour

        // suppression
    }

    @Test
    public void testGenericAuthActions() {
        // creation

        // lecture

        // mise à jour

        // suppression

    }
}