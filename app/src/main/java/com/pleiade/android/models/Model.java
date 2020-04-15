package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.util.Map;

public abstract class Model {

    protected DocumentReference ref;

    /**
     * Constructeur vide, pour création du modèles
     */
    public Model(){ }

    /**
     * Constructeur référencé, pour lecture, mise à jour
     * et suppression du modèle
     * @param ref référence Firestore
     */
    public Model(DocumentReference ref){
        this.ref = ref;
    }

    /**
     * Crée le modèle
     * @param modelMap champs et valeurs
     * @return tâche de création du modèle
     */
    public abstract Task create(Map<String, Object> modelMap);

    /**
     * Accède aux données du modèles
     * @return tâche d'accès aux données
     */
    public abstract Task read();

    /**
     * Met à jour le modèle
     * @param modelMap champs et valeurs
     * @return tâche de mise à jour du modèle
     */
    public abstract Task update(Map<String, Object> modelMap);

    /**
     * Supprime le modèle
     * @return tâche de suppression du modèle
     */
    public abstract Task delete();

    /**
     * Retourne la référence Firestore du modèle
     * @return référence Firestore du modèle
     */
    public DocumentReference getRef(){
        return  ref;
    }
}
