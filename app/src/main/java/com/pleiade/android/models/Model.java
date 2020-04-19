package com.pleiade.android.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

public abstract class Model {

    @DocumentId
    protected String documentId;
    protected Timestamp createdAt, modifiedAt;

    /**
     * Retourne la date de création de l'utilisateur
     * @return date de création de l'utilisateur
     */
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Retourne la date de modification de l'utilisateur
     * @return
     */
    public Timestamp getModifiedAt() {
        return modifiedAt;
    }

    /**
     * Enregistre l'id du document associé à l'utilisateur en base de données
     * @param documentId id du document associé à l'utilisateur
     */
    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    /**
     * Enregistre la date de création de l'utilisateur
     * @param createdAt date de création de l'utilisateur
     */
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Enregistre la date de modification de l'utilisateur
     * @param modifiedAt date de modification de l'utilisateur
     */
    public void setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
