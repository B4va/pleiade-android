package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pleiade.android.utils.FirebaseTestManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assume.*;
import static org.junit.Assert.*;

public class UserTest extends ModelTest {

    /**
     * Teste la création d'un utilisateur avec :
     * paramètres valides,
     * paramètres invalides,
     * paramètres manquants
     */
    @Override
    public void testCreate() throws ExecutionException, InterruptedException {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", "Charlotte");
        userMap.put("lastName", "Corday");
        userMap.put("tag", "charlotte");
        userMap.put("profilePictureUri", "profilePictures/charlotte.png");

        // Create valide

        User user = new User();
        user.create(userMap);
        Task<DocumentSnapshot> t = user.getRef().get();
        Tasks.await(t);
        assertNotNull(t.getResult());
        assertEquals("Enregistrement du prénom", "Charlotte", t.getResult().get("firstName"));
        assertEquals("Enregistrement du nom", "Corday", t.getResult().get("lastName"));
        assertEquals("Enregitrement du tag", "charlotte", t.getResult().get("tag"));
        assertEquals( "Enregistrement de l'image de profil", "profilePictures/charlotte.png", t.getResult().get("profilePictureUri"));
        assertEquals("Enregistrement de l'email", FirebaseTestManager.USER1_EMAIL, t.getResult().get("email"));
    }

    /**
     * Teste la lecture des données d'un utilisateur
     */
    @Override
    public void testRead() {

    }

    /**
     * Teste la mise à jour d'un utilisateur avec :
     * paramètres valides,
     * paramètres invalides
     */
    @Override
    public void testUpdate() {

    }

    /**
     * Teste la suppression d'un utilisateur
     */
    @Override
    public void testDelete() {

    }

    /**
     * Teste le CRUD sans authentification
     */
    @Override
    public void testNoAuthActions() {

    }

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    @Override
    public void testGenericAuthActions() {

    }
}
