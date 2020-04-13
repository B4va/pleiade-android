package com.pleiade.android;

import com.pleiade.android.models.User;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests du modèle User
 * @see User
 */
public class UserTest {

    /**
     * Tests du modèle en base de données et des règles de sécurité
     */

    @Test
    public void validUserCreation() {
        // avec tous les champs

        // sans email

        // sans image

    }

    @Test
    public void invalidUserCreation() {
        // sans authentification

        // sans appartenance à l'utilisateur authentifié

        // sans prénom

        // sans nom

        // sans tag

        // sans date de création

        // sans date de modification

        // avec un champ non existant

        // avec un prénom non conforme

        // avec un nom non conforme

        // avec un email non conforme

        // avec un tag non conforme
    }

    @Test
    public void validUserUpdate() {
        // tous les champs

        // uniquement prénom

        // uniquement nom

        // uniquement tag

        // uniquement email

        // uniquement image

        // uniquement date de modification
    }

    @Test
    public void invalidUserUpdate() {

    }

    @Test
    public void validUserDeletion() {

    }

    @Test
    public void invalidUserDeletion() {

    }

    @Test
    public void validUserRead() {

    }

    @Test
    public void invalidUserRead() {

    }

    /**
     * Tests des méthodes objet
     */
}