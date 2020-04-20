package com.pleiade.android.utils;

/**
 * Permet la création de méthodes de test à la volée
 * @param <Map> données à tester
 */
public interface ValidationMethod<Map> {

    /**
     * Définit la méthode de test
     * @param modelMap données à tester
     * @return true si la méthode valide les données
     */
    boolean define(Map modelMap);
}
