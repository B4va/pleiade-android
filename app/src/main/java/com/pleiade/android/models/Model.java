package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
     * Accède aux données de l'utilisateur
     * @throws InterruptedException interruption de la tâche
     * @throws ExecutionException erreur de lecture
     * @throws TimeoutException délai de lecture dépassé
     */
    public abstract void read() throws InterruptedException, ExecutionException, TimeoutException;

    /**
     * Met à jour l'utilisateur
     * @param modelMap champs et valeurs
     * @return tâche de mise à jour de l'utilisateur
     * @throws InterruptedException interruption de la tâche
     * @throws ExecutionException erreur dans la mise à jour FirebaseAuth (user)
     * @throws TimeoutException délai de mise à jour dépassé
     */
    public abstract Task<Void> update(Map<String, Object> modelMap) throws InterruptedException, ExecutionException, TimeoutException;

    /**
     * Supprime le modèle
     * @return
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
