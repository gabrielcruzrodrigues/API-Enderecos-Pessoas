package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PersonRepository;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import com.gabriel.attornatus.util.StringToLocalDateConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gabriel.attornatus.util.dateFormatter;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static com.gabriel.attornatus.util.OnlyLetters.NameContainsOnlyLetters;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public Person create(Person person) {
        NameContainsOnlyLetters(person, "name");
//        person.setDateOfBirth(dateFormatter.formattedDate(person.getDateOfBirth().toString()));
//        person.setDateOfBirth(StringToLocalDateConverter.convert(person.getDateOfBirth().toString()));;
        return personRepository.save(person);
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
    public Person update(Person personObj) {
        findById(personObj.getId());
        return personRepository.save(personObj);
    }

    public List<PublicPlace> findAllPublicPlaces(Long id) {
        Person person = findById(id);
        return person.getPublicPlaces();
    }
}
