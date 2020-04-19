package com.pleiade.android.models;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

/**
 * Prototype de test des modèles
 */
public abstract class ModelTest {

    static final String USER1_EMAIL = "user1@mail.com";
    static final String USER1_EMAIL_UPDATE = "user1update@mail.com";
    static final String USER2_EMAIL = "user2@mail.com";
    private static final String USER_PASSWORD = "123456";

    /**
     * Met en place les instances de test
     */
    @BeforeClass
    public static void setUp() throws Exception {
        initializeFirebaseApp();
        initializeFirestoreEmulator();
        initializeAuthenticatedUsers();
    }

    /**
     * Initialise l'application Firebase
     */
    private static void initializeFirebaseApp(){
        Context context = ApplicationProvider.getApplicationContext();
        FirebaseApp.initializeApp(context);
    }

    /**
     * Initialise l'émulateur Firestore
     */
    private static void initializeFirestoreEmulator(){
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setHost("10.0.2.2:8080")
                .setSslEnabled(false)
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
    }

    /**
     * Crée des utilisateurs authentifiables
     * @throws ExecutionException Erreur d'éxecution de la tâche
     * @throws InterruptedException Tâche interrompue
     */
    private static void initializeAuthenticatedUsers() throws ExecutionException, InterruptedException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        try{
            loginUser1();
        } catch (Exception e){
            try{
                Tasks.await(auth.createUserWithEmailAndPassword(USER1_EMAIL, USER_PASSWORD));
            } catch (Exception e2){
                loginUser1Update();
                Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete();
                initializeAuthenticatedUsers();
            }
        }
        try{
            loginUser2();
        } catch (Exception e3){
            Tasks.await(auth.createUserWithEmailAndPassword(USER2_EMAIL, USER_PASSWORD));
        }
    }

    /**
     * Supprime les utilisateurs créés pour les tests
     * @throws ExecutionException erreur d'authentification
     * @throws InterruptedException interruption de la tâche
     */
    @AfterClass
    public static void tearDown() throws ExecutionException, InterruptedException {
        loginUser1();
        logout();
        loginUser2();
        logout();
    }

    /**
     * Initialise les modèles de tests
     * @throws Exception exceptions diverses
     */
    public abstract void initializeModels() throws Exception;

    /**
     * Réinitialise l'émulateur Firestore
     */
    @After
    public void reinitializeFirebaseFirestoreEmulator(){
        FirebaseFirestore.getInstance().clearPersistence();
    }

    /**
     * Authentifie le premier utilisateur
     * @throws ExecutionException Erreur d'éxecution de la tâche
     * @throws InterruptedException Tâche interrompue
     */
    static void loginUser1() throws ExecutionException, InterruptedException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Tasks.await(auth.signInWithEmailAndPassword(USER1_EMAIL, USER_PASSWORD));
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(USER1_EMAIL));
    }

    /**
     * Authentifie le second utilisateur
     * @throws ExecutionException Erreur d'éxecution de la tâche
     * @throws InterruptedException Tâche interrompue
     */
    static void loginUser2() throws ExecutionException, InterruptedException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Tasks.await(auth.signInWithEmailAndPassword(USER2_EMAIL, USER_PASSWORD));
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(USER2_EMAIL));
    }

    /**
     * Authentifie le premier utilisateur
     * @throws ExecutionException Erreur d'éxecution de la tâche
     * @throws InterruptedException Tâche interrompue
     */
    private static void loginUser1Update() throws ExecutionException, InterruptedException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Tasks.await(auth.signInWithEmailAndPassword(USER1_EMAIL_UPDATE, USER_PASSWORD));
        assumeNotNull(auth.getCurrentUser());
        assumeNotNull(auth.getCurrentUser().getEmail());
        assumeTrue(auth.getCurrentUser().getEmail().equals(USER1_EMAIL_UPDATE));
    }

    /**
     * Déconnecte l'utilisateur courant
     */
    static void logout(){
        FirebaseAuth.getInstance().signOut();
        assumeTrue(FirebaseAuth.getInstance().getCurrentUser() == null);
    }

    /**
     * Teste la création du modèle en base de données
     * @throws Exception exceptions diverses
     */
    public abstract void testA_Create() throws Exception;

    /**
     * Teste la lecture du modèle en base de données
     * @throws Exception exceptions diverses
     */
    public abstract void testB_Read() throws Exception;

    /**
     * Teste la mise à jour du modèle en base de données
     * @throws Exception exceptions diverses
     */
    public abstract void testC_Update() throws Exception;

    /**
     * Teste les actions d'un utilisateur authentifié sans l'accès requis
     * @throws Exception exceptions diverses
     */
    public abstract void testD_WrongAuthActions() throws Exception;

    /**
     * Teste les actions d'un utilisateur non authentifié
     * @throws Exception exceptions diverses
     */
    public abstract void testE_NoAuthActions() throws Exception;

    /**
     * Teste la suppression du modèle en base de données
     * @throws Exception exceptions diverses
     */
    public abstract void testF_Delete() throws Exception;
}
