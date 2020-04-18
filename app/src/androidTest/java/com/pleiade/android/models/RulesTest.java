package com.pleiade.android.models;

import java.util.concurrent.ExecutionException;

/**
 * Prototype de test des règles appliquées aux modèles en base de données
 */
public abstract class RulesTest extends ModelTest {

    /**
     * Teste les actions d'un utilisateur authentifié sans l'accès requis
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    public abstract void testE_WrongAuthActions() throws ExecutionException, InterruptedException;

    /**
     * Teste les actions d'un utilisateur non authentifié
     * @throws ExecutionException erreur lors de la lecture des données
     * @throws InterruptedException interruption de la tâche
     */
    public abstract void testF_NoAuthActions() throws ExecutionException, InterruptedException;
}
