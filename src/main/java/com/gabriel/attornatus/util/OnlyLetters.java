package com.gabriel.attornatus.util;

import com.gabriel.attornatus.services.Exceptions.DataIntegrityViolationException;

public class OnlyLetters {

    public static boolean containsOnlyLetters(String name, String campo) {
        if (name != null && name.matches("^[a-zA-Z]+$")) {
            return true;
        } else {
            throw new DataIntegrityViolationException("O campo " + campo + " deve conter apenas letras.");
        }
    }
}
