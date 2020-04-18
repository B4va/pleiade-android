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
    public Task<DocumentSnapshot> create(Map<String, Object> modelMap) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseFirestore.getInstance()
            .collection("users")
            .document(Objects.requireNonNull(user).getUid());
        modelMap.put("email", user.getEmail());
        addCreatedTimestamps(modelMap);
        ref.set(modelMap);
        return read();
    }

    /**
     * Accède aux données de l'utilisateur
     * @return tâche d'acès aux données de l'utilisateur
     */
    @Override
    public Task<DocumentSnapshot> read() {
        Task<DocumentSnapshot> t = ref.get();
        t.addOnSuccessListener(documentSnapshot -> {
            firstName = (String) documentSnapshot.get("firstName");
            lastName = (String) documentSnapshot.get("lastName");
            email = (String) documentSnapshot.get("email");
            tag = (String) documentSnapshot.get("tag");
            profilePictureUri = (String) documentSnapshot.get("profilePictureUri");
            createdAt = (Timestamp) documentSnapshot.get("createdAt");
            modifiedAt = (Timestamp) documentSnapshot.get("modifiedAt");
        });
        return t;
    }

    /**
     * Met à jour l'utilisateur
     * @param modelMap champs et valeurs
     * @return tâche de mise à jour de l'utilisateur
     */
    @Override
    public Task<DocumentSnapshot> update(Map<String, Object> modelMap) {
        if (modelMap.containsKey("email")){
            try {
                Tasks.await(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser())
                        .updateEmail((String) Objects.requireNonNull(modelMap.get("email"))));
            } catch (ExecutionException | InterruptedException e) {
                Log.e(TAG, e.toString());
                return null;
            }
        }
        addModifiedTimestamp(modelMap);
        ref.update(modelMap);
        return this.read();
    }

    /**
     * Supprime l'utilisateur
     */
    @Override
    public void delete() {
        Task<Void> t = ref.delete();
        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).delete();
        FirebaseAuth.getInstance().signOut();
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
