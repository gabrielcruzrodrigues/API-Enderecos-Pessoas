package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.DTO.PublicPlaceDTO;
import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PublicPlaceRepository;
import com.gabriel.attornatus.services.Exceptions.DataIntegrityViolationException;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.gabriel.attornatus.util.OnlyLetters.CityContainsOnlyLetters;

@Service
public class PublicPlaceService {

    @Autowired
    private PublicPlaceRepository publicPlaceRepository;

    @Autowired
    private PersonService personService;

    @Transactional
    public PublicPlace create(PublicPlace publicPlaceObj, Long idPerson) {
        CityContainsOnlyLetters(publicPlaceObj, "city");
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

    public PublicPlace returnMainPublicPlace(Long id) {
        Person person = personService.findById(id);
        if (person == null) {
            return null;
        } else {
            return findMain(person.getPublicPlaces());
        }
    }

    public PublicPlace findMain(List<PublicPlace> publicPlace) {
        for (PublicPlace i : publicPlace) {
            if (i.isMain()) return i;
        }
        return null;
    }

    public PublicPlace findById(Long id) {
        Optional<PublicPlace> publicPlace = publicPlaceRepository.findById(id);
        return publicPlace.orElseThrow(() -> new ObjectNotFoundException("logradouro não encontrado!"));
    }

    public PublicPlace changeOfMainPublicPlace(Long idPerson, PublicPlaceDTO PlaceTDO) {
        verifyForUpdate(idPerson, PlaceTDO.getId());
        PublicPlace publicPlace = returnMainPublicPlace(idPerson);
        Long id = PlaceTDO.getId();
        PublicPlace newPublicPlace = this.findById(id);
        return updateOfPublicPlace(publicPlace, newPublicPlace);
    }

    private void verifyForUpdate(Long id, Long idForUpdate) {
        List<PublicPlace> publicPlaces = personService.findAllPublicPlaces(id);
        if (publicPlaces.size() < idForUpdate) throw new DataIntegrityViolationException("Você não tem um logradouro com este id.");
    }

    public PublicPlace updateOfPublicPlace(PublicPlace publicPlace, PublicPlace newPublicPlace) {
        publicPlace.setMain(false);
        newPublicPlace.setMain(true);
        publicPlaceRepository.save(publicPlace);
        return publicPlaceRepository.save(newPublicPlace);
    }
}


