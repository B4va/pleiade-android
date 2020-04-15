package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

/**
 * Modèlisation d'un utilisateur
 */
public class User extends Model {

    private static final String TAG = "UserModel";
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
    public Task create(Map<String, Object> modelMap) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseFirestore.getInstance()
            .collection("users")
            .document(Objects.requireNonNull(user).getUid());

        modelMap.put("email", user.getEmail());
        modelMap.put("tag", user.getDisplayName());
        modelMap.put("profilePictureUri", Objects.requireNonNull(user.getPhotoUrl()).toString());
        addCreatedTimestamps(modelMap);

        Task<Void> t = ref.set(modelMap);
        t.addOnSuccessListener(aVoid -> {
            firstName = (String) modelMap.get("firstName");
            lastName = (String) modelMap.get("lastName");
            email = (String) modelMap.get("email");
            tag = (String) modelMap.get("tag");
            profilePictureUri = (String) modelMap.get("profilePictureUri");
            createdAt = (Timestamp) modelMap.get("createdAt");
            modifiedAt = (Timestamp) modelMap.get("modifiedAt");
        });
        return t;
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
    public Task update(Map<String, Object> modelMap) {
        addModifiedTimestamp(modelMap);
        Task<Void> t = ref.update(modelMap);
        t.addOnSuccessListener(aVoid -> {
            if (modelMap.get("firstName") != null) firstName = (String) modelMap.get("firstName");
            if (modelMap.get("lastName") != null)lastName = (String) modelMap.get("lastName");
            modifiedAt = (Timestamp) modelMap.get("modifiedAt");
        });
        return t;
    }

    /**
     * Supprime l'utilisateur
     * @return tâche de suppression de l'utilisateur
     */
    @Override
    public Task delete() {
        return null;
    }
}
