package com.pleiade.android.models;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeNotNull;

/**
 * Tests des règles appliquées à User en base de données
 * @see User
 */
@FixMethodOrder(MethodSorters.JVM)
public class UserRulesTest extends RulesTest {

    private static final String USER1_FIRST_NAME = "Charlotte";
    private static final String USER1_LAST_NAME = "Corday";
    private static final String USER1_PROFILE_PIC_URI = "test/test/test1";
    private static final String USER1_TAG = "user1";
    private static final String USER2_FIRST_NAME = "Jean";
    private static final String USER2_LAST_NAME = "Jaurès";
    private static final String USER2_PROFILE_PIC_URI = "test/test/test2";
    private static final String USER2_TAG = "user2";

    private static User user;

    /**
     * Initialise les modèles de tests
     * @throws ExecutionException erreur lors de l'authentification
     * @throws InterruptedException tâche interrompue
     */
    @Override
    @Before
    public void initializeModels() throws ExecutionException, InterruptedException {
        loginUser1();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER1_FIRST_NAME);
        userMap.put("lastName", USER1_LAST_NAME);
        userMap.put("tag", USER1_TAG);
        userMap.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        user = new User();
        user.create(userMap);
    }

    /**
     * Teste la création du modèle en base de données
     *
     * @throws ExecutionException   erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    public void testA_Create() throws ExecutionException, InterruptedException {

    }

    /**
     * Teste la lecture du modèle en base de données
     *
     * @throws ExecutionException   erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    public void testB_Read() throws ExecutionException, InterruptedException {

    }

    /**
     * Teste la mise à jour du modèle en base de données
     *
     * @throws ExecutionException   erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    public void testC_Update() throws ExecutionException, InterruptedException {

    }

    /**
     * Teste la suppression du modèle en base de données
     *
     * @throws InterruptedException interruption pendant l'attente de suppression
     */
    @Override
    public void testD_Delete() throws InterruptedException {

    }

    /**
     * Teste les actions d'un utilisateur authentifié sans l'accès requis
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    @Test
    public void testE_WrongAuthActions() throws ExecutionException, InterruptedException {
        assumeNotNull(FirebaseAuth.getInstance().getCurrentUser());
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid);

        // Create
        logout();
        loginUser2();
        Map<String, Object> userMap1 = new HashMap<>();
        userMap1.put("firstName", USER1_FIRST_NAME);
        userMap1.put("lastName", USER1_LAST_NAME);
        userMap1.put("tag", USER1_TAG);
        userMap1.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        new User(ref).create(userMap1);
        loginUser1();
        user.read();
        assertEquals(
                "Création d'un utilisateur sans authentification",
                USER1_FIRST_NAME,
                user.getFirstName()
        );


        // Read (autorisé)
        logout();
        loginUser2();
        user.read();
        assertEquals(
                "Lecture d'un utilisateur sans authentification",
                USER1_FIRST_NAME,
                user.getFirstName()
        );

        // Update
        Map<String, Object> userMap2 = new HashMap<>();
        userMap2.put("firstName", USER2_FIRST_NAME);
        user = new User(ref);
        user.update(userMap2);
        loginUser1();
        user.read();
        assertEquals(
                "Mise à jour d'un utilisateur sans authentification",
                user.getFirstName(),
                USER1_FIRST_NAME
        );

        // Delete
        logout();
        loginUser2();
        user = new User(ref);
        user.delete();
        loginUser1();
        user.read();
        assertNotNull(
                "Création d'un utilisateur sans authentification",
                user.getFirstName()
        );
    }

    /**
     * Teste les actions d'un utilisateur non authentifié
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    @Test
    public void testF_NoAuthActions() throws ExecutionException, InterruptedException {
        assumeNotNull(FirebaseAuth.getInstance().getCurrentUser());
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid);

        // Create
        logout();
        Map<String, Object> userMap1 = new HashMap<>();
        userMap1.put("firstName", USER1_FIRST_NAME);
        userMap1.put("lastName", USER1_LAST_NAME);
        userMap1.put("tag", USER1_TAG);
        userMap1.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        assertThrows(
                "Lecture d'un utilisateur sans authentification",
                Exception.class,
                () -> new User().create(userMap1)
        );

        // Read
        logout();
        assertThrows(
                "Lecture d'un utilisateur sans authentification",
                Exception.class,
                () -> new User(ref).read()
        );

        // Update
        Map<String, Object> userMap2 = new HashMap<>();
        userMap2.put("firstName", USER2_FIRST_NAME);
        user = new User(ref);
        user.update(userMap2);
        loginUser1();
        user.read();
        assertEquals(
                "Mise à jour d'un utilisateur sans authentification",
                user.getFirstName(),
                USER1_FIRST_NAME
        );

        // Delete
        user = new User();
        user.create(userMap1);
        user.read();
        logout();
        assertThrows(
                "Suppression d'un utilisateur sans authentification",
                Exception.class,
                () -> user.delete()
        );
    }
}
