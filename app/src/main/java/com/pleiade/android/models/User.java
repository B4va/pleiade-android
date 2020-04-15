package com.pleiade.android.models;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

/**
 * Modèlisation d'un utilisateur
 */
public class User extends Model {

    private String firstName, lastName, email, tag, profilePictureUri;
    private Timestamp createdAt, modifiedAt;

    /**
     * Crée un utilisateur
     * @param modelMap champs et valeurs
     * @return tâche de création de l'utilisateur
     */
    @Override
    public Task create(Map<String, Object> modelMap) {
        // todo : création du compte et connexion FireAuth ; cf transactions
        modelMap.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
        this.ref = FirebaseFirestore.getInstance()
                .collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Task<Void> t = ref.set(modelMap);
        t.addOnSuccessListener(aVoid -> {
            firstName = (String) modelMap.get("firstName");
            lastName = (String) modelMap.get("lastName");
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            tag = (String) modelMap.get("tag");
            profilePictureUri = (String) modelMap.get("profilePictureUri");
        });
        return t;
    }

    /**
     * Accède aux données de l'utilisateur
     * @return tâche d'acès aux données de l'utilisateur
     */
    @Override
    public Task read() {
        return null;
    }

    /**
     * Met à jour l'utilisateur
     * @param modelMap champs et valeurs
     * @return tâche de mise à jour de l'utilisateur
     */
    @Override
    public Task update(Map<String, Object> modelMap) {
        return null;
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
