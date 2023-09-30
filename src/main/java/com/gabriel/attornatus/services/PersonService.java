package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PersonRepository;
import com.gabriel.attornatus.services.Exceptions.DataIntegrityViolationException;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import jdk.jshell.PersistentSnippet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import static com.gabriel.attornatus.util.OnlyLetters.NameContainsOnlyLetters;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Person create(Person personObj) {
        Person personForSave = prepareForCreation(personObj);
        return personRepository.save(personForSave);
    }

    private Person prepareForCreation(Person person) {
        NameContainsOnlyLetters(person, "name");
        if (person.getDateOfBirth() == null) throw new
                DataIntegrityViolationException("A sua data de aniversário (dateOfBirth) não pode ser nula ou inexistente.");

        return person;
    }

    @Transactional
    public Person findById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada"));
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional
    public Person update(Person newPersonObj, Long id) {
        newPersonObj.setId(id);
        Person person = findById(newPersonObj.getId());
        Person personForSave = avoidNullFields(newPersonObj, person);
        return personRepository.save(personForSave);
    }

    private Person avoidNullFields(Person newPersonObj, Person person) {
        if (newPersonObj.getDateOfBirth() == null) {
             newPersonObj.setDateOfBirth(person.getDateOfBirth());
        }
        if (newPersonObj.getName() == null) {
            newPersonObj.setName(person.getName());
        }
        newPersonObj.setPublicPlaces(person.getPublicPlaces());
        return newPersonObj;
    }

    public List<PublicPlace> findAllPublicPlaces(Long id) {
        Person person = findById(id);
        return person.getPublicPlaces();
    }
}
