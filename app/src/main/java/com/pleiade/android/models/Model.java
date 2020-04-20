package com.pleiade.android.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Teste la présence d'une clé dans une map de données
     * @param modelMap map de données
     * @param key clé testée
     * @return true si la clé existe
     */
    static boolean exists(Map<String, Object> modelMap, String key){
        return modelMap.containsKey(key);
    }

    /**
     * Teste la classe d'un objet
     * @param cl classe attendue
     * @param object objet testé
     * @return true si l'objet est du type attendu
     */
    static boolean is(Class cl, Object object){
        return object == null || object.getClass() == cl;
    }

    /**
     * Teste une chaîne de caractère via une expression régulière
     * @param pattern expression régulière
     * @param str chaîne à tester
     * @return true si la chaîne est valide
     */
    static boolean matches(String pattern, Object str){
        return str == null || Pattern.compile(pattern).matcher((CharSequence) str).matches();
    }

    /**
     * Teste l'absence de clé n'appartenant pas à la liste définie
     * dans une map de données
     * @param modelMap map de données
     * @param allowedKeys liste de clé autorisées
     * @return true si la map ne contient pas de clés non autorisées
     */
    static boolean hasNoExtraKey(Map<String, Object> modelMap, String[] allowedKeys){
        return new ArrayList<String>(Arrays.asList(allowedKeys)).containsAll(modelMap.keySet());
    }
}
