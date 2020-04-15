package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pleiade.android.utils.FirebaseTestManager;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeTrue;

public class UserTest extends ModelTest {

    /**
     * Teste la création valide d'un utilisateur
     */
    @Override
    @Test
    public void testCreateV() throws Exception {
        FirebaseTestManager.firebaseAuthLogout(auth); // Logout
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
    @Test
    public void testCreateI() throws Exception {
        super.testCreateI(); // Auth USER1
        assumeTrue(auth.getCurrentUser() != null);
        assumeTrue(auth.getCurrentUser().getEmail() != null);
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));

    }

    /**
     * Teste la lecture des données d'un utilisateur
     */
    @Override
    @Test
    public void testRead() throws Exception {
        super.testRead(); // Auth USER1
        assumeTrue(auth.getCurrentUser() != null);
        assumeTrue(auth.getCurrentUser().getEmail() != null);
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));
    }

    /**
     * Teste la mise à jour valide d'un utlilisateur
     */
    @Override
    @Test
    public void testUpdateV() throws Exception {
        super.testUpdateV(); // Auth USER1
        assumeTrue(auth.getCurrentUser() != null);
        assumeTrue(auth.getCurrentUser().getEmail() != null);
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));
    }

    /**
     * Teste la mise à jour invalide d'un utilisateur
     */
    @Override
    @Test
    public void testUpdateI() throws Exception {
        super.testUpdateI(); // Auth USER1
        assumeTrue(auth.getCurrentUser() != null);
        assumeTrue(auth.getCurrentUser().getEmail() != null);
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));

    }

    /**
     * Teste la suppression d'un utilisateur
     */
    @Override
    @Test
    public void testDelete() throws Exception {
        super.testDelete(); // Auth USER1
        assumeTrue(auth.getCurrentUser() != null);
        assumeTrue(auth.getCurrentUser().getEmail() != null);
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));

    }

    /**
     * Teste le CRUD sans authentification
     */
    @Override
    @Test
    public void testNoAuthActions() throws Exception {
        super.testNoAuthActions(); // Logout
        assumeTrue(auth.getCurrentUser() == null);
    }

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    @Override
    @Test
    public void testGenericAuthActions() throws Exception {
        super.testGenericAuthActions(); // Auth USER2
        assumeTrue(auth.getCurrentUser() != null);
        assumeTrue(auth.getCurrentUser().getEmail() != null);
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER2_EMAIL));
    }
}
