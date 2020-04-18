package com.pleiade.android.models;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Modèlisation d'un utilisateur
 */
public class User extends Model {

    public static final String TAG = "UserModel";
    private String firstName, lastName, email, tag, profilePictureUri;
    private Timestamp createdAt, modifiedAt;

    /**
     * Constructeur vide, pour création
     */
    public User(){}

    /**
     * Constructeur avec référence pour lecture,
     * mise à jour et suppression
     * @param ref référence de l'utilisateur en base de données
     */
    public User(DocumentReference ref){
        this.ref = ref;
    }

    /**
     * Crée un utilisateur
     * @param modelMap champs et valeurs
     * @return tâche de création de l'utilisateur
     */
    @Override
    public Task<Void> create(Map<String, Object> modelMap) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseFirestore.getInstance()
            .collection("users")
            .document(Objects.requireNonNull(user).getUid());
        modelMap.put("email", user.getEmail());
        addCreatedTimestamps(modelMap);
        firstName = (String) modelMap.get("firstName");
        lastName = (String) modelMap.get("lastName");
        email = (String) modelMap.get("email");
        tag = (String) modelMap.get("tag");
        profilePictureUri = (String) modelMap.get("profilePictureUri");
        createdAt = (Timestamp) modelMap.get("createdAt");
        modifiedAt = (Timestamp) modelMap.get("modifiedAt");
        return ref.set(modelMap);
    }

    /**
     * Accède aux données de l'utilisateur
     */
    @Override
    public void read() {
        Task<DocumentSnapshot> t = ref.get();
        try {
            Tasks.await(t, 10, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            Log.e(TAG, e.toString());
        }
        if (t.getResult() == null){
            Log.e(TAG, "Erreur de lecture");
        } else {
            firstName = (String) t.getResult().get("firstName");
            lastName = (String) t.getResult().get("lastName");
            email = (String) t.getResult().get("email");
            tag = (String) t.getResult().get("tag");
            profilePictureUri = (String) t.getResult().get("profilePictureUri");
            createdAt = (Timestamp) t.getResult().get("createdAt");
            modifiedAt = (Timestamp) t.getResult().get("modifiedAt");
        }
    }

    /**
     * Met à jour l'utilisateur
     * @param modelMap champs et valeurs
     * @return tâche de mise à jour de l'utilisateur
     */
    @Override
    public Task<Void> update(Map<String, Object> modelMap) {
        if (modelMap.containsKey("email")){
            try {
                Tasks.await(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                        .updateEmail((String) Objects.requireNonNull(modelMap.get("email"))),
                        10, TimeUnit.SECONDS);
            } catch (ExecutionException | InterruptedException | TimeoutException e) {
                Log.e(TAG, e.toString());
                return null;
            }
        }
        addModifiedTimestamp(modelMap);
        return ref.update(modelMap);
    }

    /**
     * Supprime l'utilisateur
     * @return tâche de suppression de l'utilisateur
     */
    @Override
    public Task<Void> delete() {
        Task<Void> t = ref.delete();
        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete();
        return t;
    }

    /**
     * Retourne le prénom de l'utilisateur
     * @return prénom de l'utilisateur
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retourne le nom de l'utilisateur
     * @return nom de l'utilisateur
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retourne l'email de l'utilisateur
     * @return email de l'utilisateur
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retourne le tag de l'utilisateur
     * @return tag de l'utilisateur
     */
    public String getTag() {
        return tag;
    }

    /**
     * Retourne l'uri de l'image de profil de l'utilisateur
     * @return uri de l'image de profil de l'utilisateur
     */
    public String getProfilePictureUri() {
        return profilePictureUri;
    }

    /**
     * Retourne la date de création de l'utilisateur
     * @return date de création de l'utilisateur
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Retourne la date de modification de l'utilisateur
     * @return date de modification de l'utilisateur
     */
    public Timestamp getModifiedAt() {
        return modifiedAt;
    }
}
