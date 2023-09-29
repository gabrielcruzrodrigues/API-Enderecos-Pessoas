package com.gabriel.attornatus.util;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.services.Exceptions.DataIntegrityViolationException;

public class OnlyLetters {

    public static boolean CityContainsOnlyLetters(PublicPlace publicPlaceObj, String campo) {
        if (publicPlaceObj.getCity() != null && publicPlaceObj.getCity().matches("^[a-zA-Z]+$")) {
            return true;
        } else {
            throw new DataIntegrityViolationException("O campo " + campo + " deve conter apenas letras.");
        }
    }

    public static boolean NameContainsOnlyLetters(Person personObj, String campo) {
        if (personObj.getName() != null && personObj.getName().matches("^[a-zA-Z]+$")) {
            return true;
        } else {
            throw new DataIntegrityViolationException("O campo " + campo + " deve conter apenas letras.");
        }
    }
}
