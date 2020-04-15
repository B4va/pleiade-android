package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pleiade.android.utils.FirebaseTestManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

public class UserTest extends ModelTest {

    /**
     * Teste la création valide d'un utilisateur
     */
    @Override
    public void testCreateV() throws ExecutionException, InterruptedException {
        FirebaseTestManager.firebaseAuthLogout(auth);
        assumeTrue(auth.getCurrentUser() == null);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", "Charlotte");
        userMap.put("lastName", "Corday");
        userMap.put("tag", "charlotte");
        userMap.put("profilePictureUri", "profilePictures/charlotte.png");

        User user = new User();
        user.create(userMap);
        Task<DocumentSnapshot> t = user.getRef().get();
        Tasks.await(t);

        assertNotNull(auth.getCurrentUser());
        assertNotNull(t.getResult());
        assertEquals(
                "Enregistrement du prénom -",
                "Carlotte",
                t.getResult().get("firstName"));
        assertEquals(
                "Enregistrement du nom -",
                "Corday",
                t.getResult().get("lastName"));
        assertEquals("Enregitrement du tag -",
                "charlotte",
                t.getResult().get("tag"));
        assertEquals(
                "Enregistrement de l'image de profil -",
                "profilePictures/charlotte.png",
                t.getResult().get("profilePictureUri"));
        assertEquals(
                "Enregistrement de l'email -",
                FirebaseTestManager.USER1_EMAIL,
                t.getResult().get("email"));
        assertEquals(
                "Mise à jour du displayName utilisateur -",
                auth.getCurrentUser().getDisplayName(),
                t.getResult().get("tag"));
        assertEquals(
                "Mise à jour de la photoUri utilisateur -",
                String.valueOf(auth.getCurrentUser().getPhotoUrl()),
                t.getResult().get("profilePictureUri"));
        assertEquals("Mise à jour de l'email -",
                auth.getCurrentUser().getEmail(),
                t.getResult().get("email"));
    }

    /**
     * Teste la création invalide d'un utilisateur
     */
    @Override
    public void testCreateI(){

    }

    /**
     * Teste la lecture des données d'un utilisateur
     */
    @Override
    public void testRead() {

    }

    /**
     * Teste la mise à jour valide d'un utlilisateur
     */
    @Override
    public void testUpdateV() {

    }

    /**
     * Teste la mise à jour invalide d'un utilisateur
     */
    @Override
    public void testUpdateI(){

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
