package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

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
 * Test du modèle User
 * @see User
 */
@FixMethodOrder(MethodSorters.JVM)
public class UserTest extends ModelTest {

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
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    @Test
    public void testA_Create() throws ExecutionException, InterruptedException {
        loginUser1();

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
                USER1_EMAIL,
                t.getResult().get("email")
        );
    }

    /**
     * Teste la lecture du modèle en base de données
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    @Test
    public void testB_Read() throws ExecutionException, InterruptedException {
        loginUser1();

        user.read();
        assertEquals(
                "Enregistrement du prénom",
                USER1_FIRST_NAME,
                user.getFirstName()
        );
        assertEquals(
                "Enregistrement du nom",
                USER1_LAST_NAME,
                user.getLastName()
        );
        assertEquals(
                "Enregistrement du tag",
                USER1_TAG,
                user.getTag()
        );
        assertEquals(
                "Enregistrement de l'image de profil",
                USER1_PROFILE_PIC_URI,
                user.getProfilePictureUri()
        );
        assertEquals(
                "Enregistrement de l'email",
                USER1_EMAIL,
                user.getEmail()
        );

    }

    /**
     * Teste la mise à jour du modèle en base de données
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    @Override
    @Test
    public void testC_Update() throws ExecutionException, InterruptedException {
        loginUser1();

        // All fields
        Map<String, Object> userMap1 = new HashMap<>();
        userMap1.put("firstName", USER2_FIRST_NAME);
        userMap1.put("lastName", USER2_LAST_NAME);
        userMap1.put("tag", USER2_TAG);
        userMap1.put("profilePictureUri", USER2_PROFILE_PIC_URI);
        userMap1.put("email", USER1_EMAIL_UPDATE);
        user.update(userMap1);

        user.read();
        assertEquals(
                "Enregistrement du prénom",
                USER2_FIRST_NAME,
                user.getFirstName()
        );
        assertEquals(
                "Enregistrement du nom",
                USER2_LAST_NAME,
                user.getLastName()
        );
        assertEquals(
                "Enregistrement du tag",
                USER2_TAG,
                user.getTag()
        );
        assertEquals(
                "Enregistrement de l'image de profil",
                USER2_PROFILE_PIC_URI,
                user.getProfilePictureUri()
        );
        assertEquals(
                "Enregistrement de l'email",
                USER1_EMAIL_UPDATE,
                user.getEmail()
        );
        assumeNotNull(FirebaseAuth.getInstance().getCurrentUser());
        assertEquals(
                "Enregistrement de l'email Firebase Auth",
                USER1_EMAIL_UPDATE,
                FirebaseAuth.getInstance().getCurrentUser().getEmail()
        );

        // Single field
        Map<String, Object> userMap2 = new HashMap<>();
        userMap2.put("email", USER1_EMAIL);
        user.update(userMap2);

        user.read();
        assertEquals(
                "Enregistrement de l'email",
                USER1_EMAIL,
                user.getEmail()
        );
        assumeNotNull(FirebaseAuth.getInstance().getCurrentUser());
        assertEquals(
                "Enregistrement de l'email Firebase Auth",
                USER1_EMAIL,
                FirebaseAuth.getInstance().getCurrentUser().getEmail()
        );
    }

    /**
     * Teste la suppression du modèle en base de données
     * @throws InterruptedException interruption pendant l'attente de suppression
     */
    @Override
    @Test
    public void testD_Delete() throws InterruptedException {
        user.delete();
        Thread.sleep(3000);
        assertThrows(
                "Erreur lors de l'accès au données",
                Exception.class,
                () -> user.read()
        );
        logout();
        assertThrows(
                "Erreur lors de la tentative de reconnexion",
                Exception.class,
                ModelTest::loginUser1
        );
    }
}
