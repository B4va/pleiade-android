package com.pleiade.android.utils;

import java.util.Map;

/**
 * Classe utilitaire permettant la validation de données
 */
public class DataValidator {

    private Map<String, Object> modelMap;
    private ValidationMethod<Map<String, Object>> test;

    /**
     * Constructeur complet
     * @param modelMap données à valider
     * @param test méthode de test des données
     */
    public DataValidator(Map<String, Object> modelMap, ValidationMethod<Map<String, Object>> test){
        this.modelMap = modelMap;
        this.test = test;
    }

    /**
     * Teste les données
     * @throws InvalidDataException données invalides
     */
    public void test() throws InvalidDataException {
        if (!test.define(modelMap)) throw new InvalidDataException("Entrée invalide");
    }
}
