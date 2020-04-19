package com.pleiade.android.models;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentId;
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

    private String firstName, lastName, email, tag, profilePictureUri;

    /**
     * Contructeur vide, en usage par la base de données Firestore
     */
    public User(){}

    /**
     * Constructeur complet, en usage par la base de données Firestore
     * @param firstName prénom
     * @param lastName nom
     * @param email adresse mail
     * @param tag nom d'affichage
     * @param profilePictureUri uri de l'image de profil
     * @param createdAt date de création
     * @param modifiedAt date de modification
     */
    public User(String firstName, String lastName, String email, String tag,
                String profilePictureUri, Timestamp createdAt, Timestamp modifiedAt){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tag = tag;
        this.profilePictureUri = profilePictureUri;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    /**
     * Contructeur modulable en usage direct
     * @param userMap paramètres de création de l'utilisateur
     */
    public User(Map<String, Object> userMap){
        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(fbu);
        this.firstName = (String) userMap.get("firstName");
        this.lastName = (String) userMap.get("lastName");
        this.email = fbu.getEmail();
        this.tag = (String) userMap.get("tag");
        this.profilePictureUri = (String) userMap.get("profilePictureUri");
    }

    /**
     * Contructeur via l'id permettant d'accéder aux données
     * @param id id du document en base de données
     */
    public User(String id){
        documentId = id;
    }

    /**
     * Crée un utilisateur en base de données, à partir des attributs
     * de l'objet
     * @return tâche de création
     */
    public Task<Void> create(){
        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(fbu);
        email = fbu.getEmail();
        setCreatedAt(Timestamp.now());
        setModifiedAt(Timestamp.now());
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("users").document(fbu.getUid());
        return ref.set(this);
    }

    /**
     * Accès aux informations d'un utilisateur en base de données, sur la base
     * de l'id du document
     * @return
     */
    public Task<DocumentSnapshot> read(){
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("users").document(documentId);
        return ref.get();
    }

    /**
     * Met à jour les informations utilisateur en base de données, à partir
     * des paramètres passés en argument
     * @param modelMap paramètres de mise à jour de l'utilisateur
     * @return tâche de mise à jour
     * @throws InterruptedException interruption de l'éventuelle modification de l'email
     * @throws ExecutionException erreur d'éxecution de l'éventuelle modification de l'email
     * @throws TimeoutException délai de l'éventuelle modification de l'email dépassé
     */
    public Task<Void> update(Map<String, Object> modelMap) throws InterruptedException, ExecutionException, TimeoutException {
        if (modelMap.containsKey("email")){
            FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
            Objects.requireNonNull(fbu);
            Tasks.await(fbu.updateEmail((String) Objects.requireNonNull(modelMap.get("email"))),
                    10, TimeUnit.SECONDS);
            }
        modelMap.put("modifiedAt", Timestamp.now());
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("users").document(documentId);
        return ref.update(modelMap);
    }

    /**
     * Supprime un utilisateur de la base de données
     * @return tâche de suppression
     * @throws InterruptedException interruption de la suppression du compte utilisateur
     * @throws ExecutionException erreur d'éxection pendant la suppression du compte utilisateur
     * @throws TimeoutException délai de suppression du compte utilisateur dépassé
     */
    public Task<Void> delete() throws InterruptedException, ExecutionException, TimeoutException {
        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
        Objects.requireNonNull(fbu);
        Tasks.await(fbu.delete(), 10, TimeUnit.SECONDS);
        DocumentReference ref = FirebaseFirestore.getInstance()
                .collection("users").document(documentId);
        Task<Void> t = ref.delete();
        return t;
    }

    /**
     * Retourne l'id du document associé à l'utilisateur en base de données
     * @return id du document associé à l'utilisateur
     */
    public String getDocumentId() {
        return documentId;
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
     * Retourne le nom d'affichage de l'utilisateur
     * @return nom d'affichage de l'utilisateur
     */
    public String getTag() {
        return tag;
    }

    /**
     * Retourne l'adresse de l'image de profil de l'utilisateur
     * @return adresse de l'image de profil de l'utilisateur
     */
    public String getProfilePictureUri() {
        return profilePictureUri;
    }

    /**
     * Enregistre le prénom de l'utilisateur
     * @param firstName prénom de l'utilisateur
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Enregistre le nom de l'utilisateur
     * @param lastName nom de l'utilisateur
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Enregistre l'email de l'utilisateur
     * @param email email de l'utilisateur
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Enregistre le nom d'affichage de l'utilisateur
     * @param tag nom d'affichage de l'utilisateur
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * Enregistre l'adresse de l'image de profil de l'utilisateur
     * @param profilePictureUri adresse de l'image de profil de l'utilisateur
     */
    public void setProfilePictureUri(String profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }
}
