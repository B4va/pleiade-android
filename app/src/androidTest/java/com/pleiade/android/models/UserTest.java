package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;
import com.pleiade.android.utils.FirebaseTestHelper;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assume.assumeNotNull;

/**
 * Tests du modèle User
 * @see User
 */
@FixMethodOrder(MethodSorters.JVM)
public class UserTest extends ModelTest {

    private static final String USER1_FIRST_NAME = "Charlotte";
    private static final String USER2_FIRST_NAME = "Jean";
    private static final String USER1_LAST_NAME = "Corday";
    private static final String USER2_LAST_NAME = "Jaurès";
    private static final String USER1_PROFILE_PIC_URI = "test/test/test1";
    private static final String USER2_PROFILE_PIC_URI = "test/test/test2";
    private static final String USER1_TAG = "user1";
    private static final String USER2_TAG = "user2";

    private static User user;

    /**
     * Initialise le(s) modèle(s) de test
     */
    @Override
    @Before
    public void initializeModels() throws Exception {
        authentication();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER1_FIRST_NAME);
        userMap.put("lastName", USER1_LAST_NAME);
        userMap.put("tag", USER1_TAG);
        userMap.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        user = new User();
        Task<DocumentSnapshot> t = user.create(userMap);
        Tasks.await(t);
    }

    /**
     * Teste la création valide d'un utilisateur
     */
    @Override
    @Test
    public void testA_CreateV() throws Exception {
        super.testA_CreateV(); // Login USER1

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER1_FIRST_NAME);
        userMap.put("lastName", USER1_LAST_NAME);
        userMap.put("tag", USER1_TAG);
        userMap.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        user = new User();
        Tasks.await(user.create(userMap));

        /* TESTS */
        assertNotNull("Accès à la référence de l'utilisateur", user.getRef());
        Task<DocumentSnapshot> t = user.getRef().get();
        Tasks.await(t);

        assertNotNull("Résultats de la lecture", t.getResult());
        assertEquals(
                "Enregistrement du prénom",
                USER1_FIRST_NAME,
                t.getResult().get("firstName")
        );
        assertEquals(
                "Enregistrement du nom",
                USER1_LAST_NAME,
                t.getResult().get("lastName")
        );
        assertEquals(
                "Enregistrement du tag",
                USER1_TAG,
                t.getResult().get("tag")
        );
        assertEquals(
                "Enregistrement de l'image de profil",
                USER1_PROFILE_PIC_URI,
                t.getResult().get("profilePictureUri")
        );
        assertEquals(
                "Enregistrement de l'email",
                FirebaseTestHelper.USER1_EMAIL,
                t.getResult().get("email")
        );
        assertEquals(
                "Objet initialisé",
                USER1_FIRST_NAME,
                user.getFirstName()
        );
    }

    /**
     * Teste la création invalide d'un utilisateur
     */
    @Override
    @Test
    public void testB_CreateI() throws Exception {
        super.testB_CreateI(); // Login USER1

    }

    /**
     * Teste la lecture des données d'un utilisateur
     */
    @Override
    @Test
    public void testC_Read() throws Exception {
        super.testC_Read(); // Login USER1

        /* TESTS */
        Task<DocumentSnapshot> t = user.read();
        Tasks.await(t);

        assertNotNull("Résultats de la lecture", t.getResult());
        assertEquals(
                "Lecture du prénom",
                USER1_FIRST_NAME,
                t.getResult().get("firstName")
        );
        assertEquals(
                "Lecture du nom",
                USER1_LAST_NAME,
                t.getResult().get("lastName")
        );
        assertEquals(
                "Lecture du tag",
                USER1_TAG,
                t.getResult().get("tag")
        );
        assertEquals(
                "Lecture de l'image de profil",
                USER1_PROFILE_PIC_URI,
                t.getResult().get("profilePictureUri")
        );
        assertEquals(
                "Lecture de l'email",
                FirebaseTestHelper.USER1_EMAIL,
                t.getResult().get("email")
        );
        assertEquals(
                "Objet initialisé",
                USER1_FIRST_NAME,
                user.getFirstName()
        );
    }

    /**
     * Teste la mise à jour valide d'un utlilisateur
     */
    @Override
    @Test
    public void testD_UpdateV() throws Exception {
        super.testD_UpdateV(); // Login USER1

        /* TESTS */
        allFieldsUpdate(user);
        manyFieldsUpdate(user);
        oneFieldUpdate(user);
    }

    /**
     * Teste la mise à jour de tous les champs de l'utilisateur
     * @param user utilisateur à mettre à jour
     * @throws ExecutionException si erreur dans la tâche de mise à jour de l'email
     * @throws InterruptedException si interruption de la tâche de mise à jour de l'email
     */
    private void allFieldsUpdate(User user) throws ExecutionException, InterruptedException {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER2_FIRST_NAME);
        userMap.put("lastName", USER2_LAST_NAME);
        userMap.put("tag", USER2_TAG);
        userMap.put("profilePictureUri", USER2_PROFILE_PIC_URI);
        userMap.put("email", FirebaseTestHelper.USER3_EMAIL);

        Task<DocumentSnapshot> t = user.update(userMap);
        assumeNotNull(t);
        Tasks.await(t);

        assertNotNull("Résultats de la lecture", t.getResult());
        assertEquals(
                "Email modifié dans FirebaseAuth",
                FirebaseTestHelper.USER3_EMAIL,
                Objects.requireNonNull(auth.getCurrentUser()).getEmail()
        );
        assertEquals(
                "Mise à jour du prénom",
                USER2_FIRST_NAME,
                t.getResult().get("firstName")
        );
        assertEquals(
                "Mise à jour du nom",
                USER2_LAST_NAME,
                t.getResult().get("lastName")
        );
        assertEquals(
                "Mise à jour du tag",
                USER2_TAG,
                t.getResult().get("tag")
        );
        assertEquals(
                "Mise à jour de l'image de profil",
                USER2_PROFILE_PIC_URI,
                t.getResult().get("profilePictureUri")
        );
        assertEquals(
                "Mise à jour de l'email",
                FirebaseTestHelper.USER3_EMAIL,
                t.getResult().get("email")
        );
        assertEquals(
                "Objet initialisé",
                USER2_FIRST_NAME,
                user.getFirstName()
        );
        reinitUser(user);
    }


    /**
     * Teste la mise à jour d'un seul champ de l'utilisateur
     * @param user utilisateur à mettre à jour
     * @throws ExecutionException si erreur dans la tâche de mise à jour de l'email
     * @throws InterruptedException si interruption de la tâche de mise à jour de l'email
     */
    private void oneFieldUpdate(User user) throws ExecutionException, InterruptedException {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER2_FIRST_NAME);

        Task<DocumentSnapshot> t = user.update(userMap);
        assumeNotNull(t);
        Tasks.await(t);

        assertNotNull("Résultats de la lecture", t.getResult());
        assertEquals(
                "Mise à jour du prénom",
                USER2_FIRST_NAME,
                t.getResult().get("firstName")
        );
        assertEquals(
                "Conservation du nom",
                USER1_LAST_NAME,
                t.getResult().get("lastName")
        );
        assertEquals(
                "Conservation du tag",
                USER1_TAG,
                t.getResult().get("tag")
        );
        assertEquals(
                "Conservation de l'image de profil",
                USER1_PROFILE_PIC_URI,
                t.getResult().get("profilePictureUri")
        );
        assertEquals(
                "Conservation de l'email",
                FirebaseTestHelper.USER1_EMAIL,
                t.getResult().get("email")
        );
        assertEquals(
                "Objet initialisé",
                USER2_FIRST_NAME,
                user.getFirstName()
        );
        reinitUser(user);
    }

    /**
     * Teste la mise à jour de plusieurs champs de l'utilisateur
     * @param user utilisateur à mettre à jour
     * @throws ExecutionException si erreur dans la tâche de mise à jour de l'email
     * @throws InterruptedException si interruption de la tâche de mise à jour de l'email
     */
    private void manyFieldsUpdate(User user) throws ExecutionException, InterruptedException {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER2_FIRST_NAME);
        userMap.put("lastName", USER2_LAST_NAME);

        Task<DocumentSnapshot> t = user.update(userMap);
        assumeNotNull(t);
        Tasks.await(t);

        assertNotNull("Résultats de la lecture", t.getResult());
        assertEquals(
                "Mise à jour du prénom",
                USER2_FIRST_NAME,
                t.getResult().get("firstName")
        );
        assertEquals(
                "Mise à jour du nom",
                USER2_LAST_NAME,
                t.getResult().get("lastName")
        );
        assertEquals(
                "Conservation du tag",
                USER1_TAG,
                t.getResult().get("tag")
        );
        assertEquals(
                "Conservation de l'image de profil",
                USER1_PROFILE_PIC_URI,
                t.getResult().get("profilePictureUri")
        );
        assertEquals(
                "Conservation de l'email",
                FirebaseTestHelper.USER1_EMAIL,
                t.getResult().get("email")
        );
        assertEquals(
                "Objet initialisé",
                USER2_FIRST_NAME,
                user.getFirstName()
        );
        reinitUser(user);
    }

    /**
     * Réinitialise les données initiales de l'utilisateur
     * @param user utilisateur à mettre à jour
     */
    private void reinitUser(User user){
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER1_FIRST_NAME);
        userMap.put("lastName", USER1_LAST_NAME);
        userMap.put("tag", USER1_TAG);
        userMap.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        userMap.put("email", FirebaseTestHelper.USER1_EMAIL);
        user.update(userMap);
    }

    /**
     * Teste la mise à jour invalide d'un utilisateur
     */
    @Override
    @Test
    public void testE_UpdateI() throws Exception {
        super.testE_UpdateI(); // Login USER1

    }

    /**
     * Teste le CRUD avec l'authentification d'un utilisateur tiers
     */
    @Override
    @Test
    public void testF_WrongAuthActions() throws Exception {
        super.testF_WrongAuthActions(); // Login USER2

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER1_FIRST_NAME);
        userMap.put("lastName", USER1_LAST_NAME);
        userMap.put("tag", USER1_TAG);
        userMap.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        userMap.put("email", FirebaseTestHelper.USER1_EMAIL);

        // Read
        Task<DocumentSnapshot> t = user.read();
        Tasks.await(t);
        assertNotNull(
                "Lecture d'un utilisateur sans authentification",
                t.getResult()
        );

        // Update
        assertThrows(
                "Mise à jour d'un utilisateur sans authentification",
                Exception.class,
                () -> Tasks.await(user.update(userMap))
        );
    }

    /**
     * Teste le CRUD sans authentification
     */
    @Override
    @Test
    public void testG_NoAuthActions() throws Exception {
        super.testG_NoAuthActions(); // Logout

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER1_FIRST_NAME);
        userMap.put("lastName", USER1_LAST_NAME);
        userMap.put("tag", USER1_TAG);
        userMap.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        userMap.put("email", FirebaseTestHelper.USER1_EMAIL);

        // Read
        assertThrows(
                "Lecture d'un utilisateur sans authentification",
                Exception.class,
                () -> Tasks.await(user.read())
        );

        // Update
        assertThrows(
                "Mise à jour d'un utilisateur sans authentification",
                Exception.class,
                () -> Tasks.await(user.update(userMap))
        );
    }

    /**
     * Teste la suppression d'un utilisateur
     */
    @Override
    @Test
    public void testH_Delete() throws Exception {
        super.testH_Delete(); // Login USER1

        /* TESTS */
        user.delete();
        Thread.sleep(8000);
        assertNull("Utilisateur déconnecté", auth.getCurrentUser());
        assertThrows(
                "Erreur lors de la tentative de reconnexion",
                Exception.class,
                () -> FirebaseTestHelper.firebaseAuthLogin(
                        auth,
                        FirebaseTestHelper.USER1_EMAIL,
                        FirebaseTestHelper.USER_PASSWORD
                ));
        assertThrows(
                "Erreur lors de l'accès au données",
                Exception.class,
                () -> {
                    Task <DocumentSnapshot> t = user.read();
                    Tasks.await(t);
                    t.getResult();
                }
        );
    }
}
