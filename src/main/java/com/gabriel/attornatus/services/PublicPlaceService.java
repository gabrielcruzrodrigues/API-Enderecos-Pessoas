package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PublicPlaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicPlaceService {

    @Autowired
    private PublicPlaceRepository publicPlaceRepository;

    @Autowired
    private PersonService personService;

    @Transactional
    public PublicPlace create(PublicPlace publicPlaceObj, Long idPerson) {
        Person person = personService.findById(idPerson);
        PublicPlace publicPlaceForSave = preparesPublicPlaceForCreation(person, publicPlaceObj);
        return publicPlaceRepository.save(publicPlaceForSave);
    }

    private PublicPlace preparesPublicPlaceForCreation(Person person, PublicPlace publicPlace) {
        if (checkIfIsTheFirstPublicPlace(person)) {
            publicPlace.setMain(true);
        } else {
            publicPlace.setMain(false);
        }

        publicPlace.setId(null);
        publicPlace.setPerson(person);
        return publicPlace;
    }

    private boolean checkIfIsTheFirstPublicPlace(Person person) {
         return person.getPublicPlaces().size() == 0;
    }
}
