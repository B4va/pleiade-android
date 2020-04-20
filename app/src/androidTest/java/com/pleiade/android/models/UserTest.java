package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
    private static String id;


    /**
     * Initialise les modèles de tests
     * @throws Exception exceptions diverses
     */
    @Override
    @Before
    public void initializeModels() throws Exception {
        loginUser1();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER1_FIRST_NAME);
        userMap.put("lastName", USER1_LAST_NAME);
        userMap.put("tag", USER1_TAG);
        userMap.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        user = new User();
        user.create(userMap);
        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(fbu);
        id = fbu.getUid();
    }

    /**
     * Teste la création du modèle en base de données
     * @throws Exception exceptions diverses
     */
    @Override
    @Test
    public void testA_Create() throws Exception {
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("users").document(id);
        Task <DocumentSnapshot> t = ref.get();
        Tasks.await(t);
        assumeNotNull(t.getResult());
        user = t.getResult().toObject(User.class);
        assertNotNull("Initialisation de l'utilisateur", user);
        assertEquals(
                "Enregistrement de l'id du document",
                id,
                user.getDocumentId()
        );
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
                "Enregistrement de l'email",
                USER1_EMAIL,
                user.getEmail()
        );
        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(fbu);
        assertEquals(
                "Enregistrement de l'email Firebase Auth",
                USER1_EMAIL,
                fbu.getEmail()
        );
        assertEquals(
                "Enregistrement de l'image de profil",
                USER1_PROFILE_PIC_URI,
                user.getProfilePictureUri()
        );
        assertNotNull(
                "Enregistrement du timestamp de création",
                user.getCreatedAt()
        );
        assertNotNull(
                "Enregistrement du timestamp de modification",
                user.getModifiedAt()
        );
    }

    /**
     * Teste la lecture du modèle en base de données
     * @throws Exception exceptions diverses
     */
    @Override
    @Test
    public void testB_Read() throws Exception {
        user = new User(id);
        Task<DocumentSnapshot> t = user.read();
        Tasks.await(t);
        assumeNotNull(t.getResult());
        user = t.getResult().toObject(User.class);
        assertNotNull("Initialisation de l'utilisateur", user);
        assertEquals(
                "Récupération de l'id du document",
                id,
                user.getDocumentId()
        );
        assertEquals(
                "Récupération du prénom",
                USER1_FIRST_NAME,
                user.getFirstName()
        );
        assertEquals(
                "Récupération du nom",
                USER1_LAST_NAME,
                user.getLastName()
        );
        assertEquals(
                "Récupération du tag",
                USER1_TAG,
                user.getTag()
        );
        assertEquals(
                "Récupération de l'email",
                USER1_EMAIL,
                user.getEmail()
        );
        assertEquals(
                "Récupération de l'image de profil",
                USER1_PROFILE_PIC_URI,
                user.getProfilePictureUri()
        );
        assertNotNull(
                "Récupération du timestamp de création",
                user.getCreatedAt()
        );
        assertNotNull(
                "Récupération du timestamp de création",
                user.getModifiedAt()
        );

    }

    /**
     * Teste la mise à jour du modèle en base de données
     * @throws Exception exceptions diverses
     */
    @Override
    @Test
    public void testC_Update() throws Exception {
        user = new User(id);
        Timestamp creationTimestamp = user.getCreatedAt();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER2_FIRST_NAME);
        userMap.put("lastName", USER2_LAST_NAME);
        userMap.put("tag", USER2_TAG);
        userMap.put("email", USER1_EMAIL_UPDATE);
        userMap.put("profilePictureUri", USER2_PROFILE_PIC_URI);
        user.update(userMap);
        Task <DocumentSnapshot> t = user.read();
        Tasks.await(t);
        assumeNotNull(t.getResult());
        user = t.getResult().toObject(User.class);
        assertNotNull(user);
        assertEquals(
                "Persistance de l'id du document",
                id,
                user.getDocumentId()
        );
        assertEquals(
                "Mise à jour du prénom",
                USER2_FIRST_NAME,
                user.getFirstName()
        );
        assertEquals(
                "Mise à jour du nom",
                USER2_LAST_NAME,
                user.getLastName()
        );
        assertEquals(
                "Mise à jour du tag",
                USER2_TAG,
                user.getTag()
        );
        assertEquals(
                "Mise à jour de l'email",
                USER1_EMAIL_UPDATE,
                user.getEmail()
        );
        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(fbu);
        assertEquals(
                "Mise à jour de l'email Firebase Auth",
                USER1_EMAIL_UPDATE,
                fbu.getEmail()
        );
        assertEquals(
                "Mise à jour de l'image de profil",
                USER2_PROFILE_PIC_URI,
                user.getProfilePictureUri()
        );
        assertNotNull(
                "Persistance du timestamp de création",
                user.getCreatedAt()
        );
        assertNotEquals(
                "Mise à jour du timestamp de modification",
                creationTimestamp,
                user.getModifiedAt()
        );

        // un seul élément
        userMap = new HashMap<>();
        userMap.put("email", USER1_EMAIL);
        user.update(userMap);
        t = user.read();
        Tasks.await(t);
        assumeNotNull(t.getResult());
        user = t.getResult().toObject(User.class);
        assumeNotNull(user);
        assertEquals(
                "Mise à jour de l'email",
                USER1_EMAIL,
                user.getEmail()
        );
        assertEquals(
                "Mise à jour de l'email Firebase Auth",
                USER1_EMAIL,
                fbu.getEmail()
        );
    }

    /**
     * Teste les actions d'un utilisateur authentifié sans l'accès requis
     *
     * @throws Exception exceptions diverses
     */
    @Override
    @Test
    public void testD_WrongAuthActions() throws Exception {

        // Create
        loginUser2();
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("firstName", USER2_FIRST_NAME);
        userMap.put("lastName", USER2_LAST_NAME);
        userMap.put("tag", USER2_TAG);
        userMap.put("profilePictureUri", USER2_PROFILE_PIC_URI);
        new User().create(userMap);
        loginUser1();
        Task <DocumentSnapshot> t = new User(id).read();
        Tasks.await(t);
        assumeNotNull(t.getResult());
        user = t.getResult().toObject(User.class);
        assumeNotNull(user);
        assertEquals(
                "Impossible de créer des données utilisateur",
                USER1_FIRST_NAME,
                user.getFirstName()
        );

        // Read
        loginUser2();
        t = new User(id).read();
        Tasks.await(t);
        assertNotNull(
                "Possible de lire des données utilisateur",
                t.getResult()
        );

        //Update
        Map<String, Object> userMap2 = new HashMap<>();
        userMap.put("firstName", USER2_FIRST_NAME);
        new User(id).update(userMap2);
        loginUser1();
        t = new User(id).read();
        Tasks.await(t);
        assumeNotNull(t.getResult());
        user = t.getResult().toObject(User.class);
        assumeNotNull(user);
        assertEquals(
                "Impossible de modifier des données utilisateur",
                USER1_FIRST_NAME,
                user.getFirstName()
        );

        // Delete
        loginUser2();
        new User(id).delete();
        loginUser1();
        t = new User(id).read();
        Tasks.await(t);
        assertNotNull(
                "Impossible de supprimer des données utilisateur",
                t.getResult()
        );
    }

    /**
     * Teste les actions d'un utilisateur non authentifié
     *
     * @throws Exception exceptions diverses
     */
    @Override
    @Test
    public void testE_NoAuthActions() throws Exception {

        // Create
        logout();
        Map<String, Object> userMap1 = new HashMap<>();
        userMap1.put("firstName", USER1_FIRST_NAME);
        userMap1.put("lastName", USER1_LAST_NAME);
        userMap1.put("tag", USER1_TAG);
        userMap1.put("profilePictureUri", USER1_PROFILE_PIC_URI);
        assertThrows(
                "Impossible de créer un utilisateur",
                Exception.class,
                () -> new User(id).create(userMap1)
        );

        // Read
        assertThrows(
                "Impossible de lire des données utilisateur",
                Exception.class,
                () -> Tasks.await(new User(id).read())
        );

        //Update
        Map<String, Object> userMap2 = new HashMap<>();
        userMap2.put("firstName", USER2_FIRST_NAME);
        new User(id).update(userMap2);
        loginUser1();
        Task <DocumentSnapshot> t = new User(id).read();
        Tasks.await(t);
        assumeNotNull(t.getResult());
        user = t.getResult().toObject(User.class);
        assumeNotNull(user);
        assertEquals(
                "Impossible de modifier des données utilisateur",
                USER1_FIRST_NAME,
                user.getFirstName()
        );

        // Delete
        logout();
        assertThrows(
                "Impossible de lire des données utilisateur",
                Exception.class,
                () -> new User(id).delete()
        );
    }

    /**
     * Teste la suppression du modèle en base de données
     * @throws Exception exceptions diverses
     */
    @Override
    @Test
    public void testF_Delete() throws Exception {
        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(fbu);
        user = new User(fbu.getUid());
        user.delete();
        assertThrows(
                "Suppression des données utilisateur",
                Exception.class,
                () -> Tasks.await(user.read())
        );
        logout();
        assertThrows(
                "Suppression du compte utilisateur",
                Exception.class,
                ModelTest::loginUser1
        );
    }
}
