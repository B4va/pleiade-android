package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

/**
 * Ressources et méthode implémentant le CRUD pour les modèles
 */
public abstract class Model {

    protected DocumentReference ref;

    /**
     * Crée le modèle
     * @param modelMap champs et valeurs
     * @return tâche de création du modèle
     */
    public abstract Task<Void> create(Map<String, Object> modelMap);

    /**
     * Accède aux données du modèles
     * @return tâche d'accès aux données
     */
    public abstract Task<DocumentSnapshot> read();

    /**
     * Met à jour le modèle
     * @param modelMap champs et valeurs
     * @return tâche de mise à jour du modèle
     */
    public abstract Task<Void> update(Map<String, Object> modelMap);

    /**
     * Supprime le modèle
     * @return tâche de suppression du modèle
     */
    public abstract Task<Void> delete();

    /**
     * Retourne la référence Firestore du modèle
     * @return référence Firestore du modèle
     */
    public DocumentReference getRef(){
        return  ref;
    }

    /**
     * Ajoute la date de modification
     * @param modelMap map à insérer dans la bdd
     */
    protected void addModifiedTimestamp(Map<String, Object> modelMap){
        modelMap.put("modifiedAt", Timestamp.now());
    }

    /**
     * Ajoute les dates de modification et de création
     * @param modelMap map à insérer dans la bdd
     */
    protected void addCreatedTimestamps(Map<String, Object> modelMap){
        modelMap.put("createdAt", Timestamp.now());
        modelMap.put("modifiedAt", modelMap.get("createdAt"));
    }
}
