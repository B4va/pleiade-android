package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;

import java.util.Map;

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
        return null;
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
