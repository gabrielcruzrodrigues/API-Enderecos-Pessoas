package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.repositories.PersonRepository;
import com.gabriel.attornatus.util.OnlyLetters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonServiceTest {

    public static final long ID = 1L;
    public static final String NAME = "Gabriel";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2002, 01, 22);

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    private Person person;
    private Optional<Person> optionalPerson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void shouldAnSuccessAndAnPersonInstance_whenToCallCreate() {
        when(personRepository.save(any())).thenReturn(person);

        Person response = personService.create(person);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void findAllPublicPlaces() {
    }

    private void startPerson() {
        person = new Person(ID, NAME, DATE_OF_BIRTH);
        optionalPerson = Optional.of(new Person(ID, NAME, DATE_OF_BIRTH));
    }
}