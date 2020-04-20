package com.pleiade.android.utils;

/**
 * Exception relative aux données à envoyer en base de données
 */
public class InvalidDataException extends Exception {

    /**
     * Constructeur avec message
     * @param message message
     */
    public InvalidDataException(String message){
        super(message);
    }
}
