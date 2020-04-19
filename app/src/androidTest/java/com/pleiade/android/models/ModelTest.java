package com.pleiade.android.models;

import android.content.Context;
import android.util.Log;

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
import java.util.concurrent.TimeoutException;

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
     */
    @AfterClass
    public static void tearDown(){
        try{
            loginUser1();
            logout();
            loginUser2();
            logout();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(ModelTest.class.toString(), e.toString());
        }
    }

    /**
     * Initialise les modèles de tests
     * @throws ExecutionException erreur lors de l'authentification
     * @throws InterruptedException tâche interrompue
     */
    protected abstract void initializeModels() throws ExecutionException, InterruptedException;

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
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    public abstract void testA_Create() throws ExecutionException, InterruptedException;

    /**
     * Teste la lecture du modèle en base de données
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     * @throws TimeoutException délai de lecture dépassé
     */
    public abstract void testB_Read() throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * Teste la mise à jour du modèle en base de données
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     * @throws TimeoutException délai de lecture dépassé
     */
    public abstract void testC_Update() throws ExecutionException, InterruptedException, TimeoutException;

    /**
     * Teste la suppression du modèle en base de données
     * @throws InterruptedException interruption pendant l'attente de suppression
     */
    public abstract void testD_Delete() throws InterruptedException;
}
