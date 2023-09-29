package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.DTO.PublicPlaceDTO;
import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PublicPlaceRepository;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.gabriel.attornatus.util.OnlyLetters.containsOnlyLetters;

@Service
public class PublicPlaceService {

    @Autowired
    private PublicPlaceRepository publicPlaceRepository;

    @Autowired
    private PersonService personService;

    @Transactional
    public PublicPlace create(PublicPlace publicPlaceObj, Long idPerson) {
        containsOnlyLetters(publicPlaceObj.getCity(), "city");

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
         return person.getPublicPlaces().isEmpty();
    }

    public PublicPlace findMainPublicPlace(Long id) {
        Person person = personService.findById(id);
        for (PublicPlace i : person.getPublicPlaces()) {
            if (i.isMain()) return i;
        }
        return null;
    }

    public PublicPlace changeOfMainPublicPlace(Long idPerson, PublicPlaceDTO PlaceTDO) {
        PublicPlace publicPlace = findMainPublicPlace(idPerson);
        Long id = PlaceTDO.getId();
        PublicPlace newPublicPlace = this.findById(id);

        return updateOfPublicPlace(publicPlace, newPublicPlace);
    }

    public PublicPlace findById(Long id) {
        Optional<PublicPlace> publicPlace = publicPlaceRepository.findById(id);
        return publicPlace.orElseThrow(() -> new ObjectNotFoundException("logradouro não encontrado!"));
    }

    private PublicPlace updateOfPublicPlace(PublicPlace publicPlace, PublicPlace newPublicPlace) {
        publicPlace.setMain(false);
        newPublicPlace.setMain(true);
        publicPlaceRepository.save(publicPlace);
        return publicPlaceRepository.save(newPublicPlace);
    }
}


