package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pleiade.android.utils.FirebaseTestManager;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

/**
 * Tests du modèle User
 * @see User
 */
public class UserTest extends ModelTest {

    /**
     * Teste la création valide d'un utilisateur
     */
    @Override
    @Test
    public void testCreateV() throws Exception {
        super.testCreateV(); // Login USER1
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", FirebaseTestManager.USER1_FIRST_NAME);
        userMap.put("lastName", FirebaseTestManager.USER1_LAST_NAME);

        User user = new User();
        user.create(userMap);
        assertNotNull("Accès à la référence de l'utilisateur", user.getRef());
        Task<DocumentSnapshot> t = user.getRef().get();
        Tasks.await(t);

        assertNotNull(t.getResult());
        assertEquals(
                "Enregistrement du prénom",
                FirebaseTestManager.USER1_FIRST_NAME,
                t.getResult().get("firstName"));
        assertEquals(
                "Enregistrement du nom",
                FirebaseTestManager.USER1_LAST_NAME,
                t.getResult().get("lastName"));
        assertEquals("Enregitrement du tag",
                FirebaseTestManager.USER1_DISPLAY_NAME,
                t.getResult().get("tag"));
        assertEquals(
                "Enregistrement de l'image de profil",
                FirebaseTestManager.USER_PHOTO_URI.toString(),
                t.getResult().get("profilePictureUri"));
        assertEquals(
                "Enregistrement de l'email",
                FirebaseTestManager.USER1_EMAIL,
                t.getResult().get("email"));
    }

    /**
     * Teste la création invalide d'un utilisateur
     */
    @Override
    @Test
    public void testCreateI() throws Exception {
        super.testCreateI(); // Login USER1
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));

    }

    /**
     * Teste la lecture des données d'un utilisateur
     */
    @Override
    @Test
    public void testRead() throws Exception {
        super.testRead(); // Login USER1
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));

        DocumentReference ref = db.collection("users").document(auth.getCurrentUser().getUid());
        User user = new User(ref);
        Task<DocumentSnapshot> t = user.read();
        Tasks.await(t);

        assertNotNull(t.getResult());
        assertEquals(
                "Lecture du prénom",
                FirebaseTestManager.USER1_FIRST_NAME,
                t.getResult().get("firstName"));
        assertEquals(
                "Lecture du nom",
                FirebaseTestManager.USER1_LAST_NAME,
                t.getResult().get("lastName"));
        assertEquals("Lecture du tag",
                FirebaseTestManager.USER1_DISPLAY_NAME,
                t.getResult().get("tag"));
        assertEquals(
                "Lecture de l'image de profil",
                FirebaseTestManager.USER_PHOTO_URI.toString(),
                t.getResult().get("profilePictureUri"));
        assertEquals(
                "Lecture de l'email",
                FirebaseTestManager.USER1_EMAIL,
                t.getResult().get("email"));
    }

    /**
     * Teste la mise à jour valide d'un utlilisateur
     */
    @Override
    @Test
    public void testUpdateV() throws Exception {
        super.testUpdateV(); // Login USER1
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));
    }

    /**
     * Teste la mise à jour invalide d'un utilisateur
     */
    @Override
    @Test
    public void testUpdateI() throws Exception {
        super.testUpdateI(); // Login USER1
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER1_EMAIL));

        DocumentReference ref = db.collection("users").document(auth.getCurrentUser().getUid());
        User user = new User(ref);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", FirebaseTestManager.USER2_FIRST_NAME);
        userMap.put("lastName", FirebaseTestManager.USER2_LAST_NAME);
        user.update(userMap);
        Task<DocumentSnapshot> t = user.read();
        Tasks.await(t);

        assertNotNull(t.getResult());
        assertEquals(
                "Lecture du prénom",
                FirebaseTestManager.USER2_FIRST_NAME,
                t.getResult().get("firstName"));
        assertEquals(
                "Lecture du nom",
                FirebaseTestManager.USER2_LAST_NAME,
                t.getResult().get("lastName"));
        assertEquals("Lecture du tag",
                FirebaseTestManager.USER1_DISPLAY_NAME,
                t.getResult().get("tag"));
        assertEquals(
                "Lecture de l'image de profil",
                FirebaseTestManager.USER_PHOTO_URI.toString(),
                t.getResult().get("profilePictureUri"));
        assertEquals(
                "Lecture de l'email",
                FirebaseTestManager.USER1_EMAIL,
                t.getResult().get("email"));
    }

    /**
     * Teste la suppression d'un utilisateur
     */
    @Override
    @Test
    public void testDelete() throws Exception {
        super.testDelete(); // Login USER1
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
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

        // Create
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", FirebaseTestManager.USER1_FIRST_NAME);
        userMap.put("lastName", FirebaseTestManager.USER1_LAST_NAME);

        User user = new User();
        assertThrows(
                "Exception si création",
                Exception.class,
                () -> user.create(userMap));

        // Read
        DocumentReference ref = db.collection("users").document();
        assertThrows(
                "Exception si lecture",
                Exception.class,
                () -> new User(ref).read());

        // Update
        assertThrows(
                "Exception si mise à jour",
                Exception.class,
                () -> new User(ref).update(userMap)
        );
    }

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    @Override
    @Test
    public void testGenericAuthActions() throws Exception {
        super.testGenericAuthActions(); // Login USER2
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(FirebaseTestManager.USER2_EMAIL));
    }
}
