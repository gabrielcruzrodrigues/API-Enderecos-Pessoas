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
    public PublicPlace create(PublicPlace publicPlace, Long idPerson) {
        Person person = personService.findById(idPerson);
        publicPlace.setId(null);
        publicPlace.setPerson(person);
        return publicPlaceRepository.save(publicPlace);
    }
}
