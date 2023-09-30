package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PersonRepository;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonServiceTest {

    public static final long ID = 1L;
    public static final String NAME = "Gabriel";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2002, 01, 22);
    public static final String PERSON_NOT_FOUND = "pessoa não encontrada.";
    public static final String PUBLIC_PLACE = "Rua j";
    public static final String CEP = "12345678";
    public static final String NUMBER = "64";
    public static final String CITY = "São Paulo";
    public static final boolean MAIN = true;

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    private Person person;
    private Person personWithPublicPlace;
    private Optional<Person> optionalPerson;
    private Optional<Person> optionalPersonWithPublicPlace;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void mustReturnAnSuccessAndAnPersonInstance_whenToCallCreate() {
        when(personRepository.save(any())).thenReturn(person);

        Person response = personService.create(person);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());

        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());
    }

    @Test
    void mustReturnAnSuccessAndAnPersonInstance_whenToCallFindById() {
        when(personRepository.findById(anyLong())).thenReturn(optionalPerson);

        Person response = personService.findById(ID);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());
        assertNull(response.getPublicPlaces());
    }

    @Test
    void mustReturnAnObjectNotFoundException_whenToCallFindById() {
        when(personRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException(PERSON_NOT_FOUND));

        try {
            personService.findById(ID);
        } catch (Exception ex) {
            assertNotNull(ex);
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(PERSON_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void mustReturnAnSuccessAndAnPersonInstanceWithPublicPlace_whenToCallFindById() {
        when(personRepository.findById(anyLong())).thenReturn(optionalPersonWithPublicPlace);

        Person response = personService.findById(ID);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());
        assertEquals(PublicPlace.class, response.getPublicPlaces().get(0).getClass());

        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());
        assertEquals(1, response.getPublicPlaces().size());

        assertEquals(PUBLIC_PLACE,  response.getPublicPlaces().get(0).getPublicPlace());
        assertEquals(CEP,  response.getPublicPlaces().get(0).getCep());
        assertEquals(NUMBER,  response.getPublicPlaces().get(0).getNumber());
        assertEquals(CITY,  response.getPublicPlaces().get(0).getCity());
        assertTrue(response.getPublicPlaces().get(0).isMain());
    }

    @Test
    void mustReturnASuccessAndAListOfPerson_whenToCallFindAll() {
        when(personRepository.findAll()).thenReturn(List.of(person));

        List<Person> response = personService.findAll();

        assertNotNull(response);
        assertEquals(Person.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(DATE_OF_BIRTH, response.get(0).getDateOfBirth());
        assertNull(response.get(0).getPublicPlaces());
    }

    @Test
    void mustReturnASuccessAndAListOfPersonWithPublicPlace_whenToCallFindAll() {
        when(personRepository.findAll()).thenReturn(List.of(personWithPublicPlace));

        List<Person> response = personRepository.findAll();

        assertNotNull(response);
        assertEquals(Person.class, response.get(0).getClass());
        assertEquals(1, response.size());
        assertEquals(ID, response.get(0).getId());
        assertEquals(NAME, response.get(0).getName());
        assertEquals(DATE_OF_BIRTH, response.get(0).getDateOfBirth());
        assertEquals(1, response.get(0).getPublicPlaces().size());

        assertEquals(PUBLIC_PLACE, response.get(0).getPublicPlaces().get(0).getPublicPlace());
        assertEquals(CEP, response.get(0).getPublicPlaces().get(0).getCep());
        assertEquals(NUMBER, response.get(0).getPublicPlaces().get(0).getNumber());
        assertEquals(CITY, response.get(0).getPublicPlaces().get(0).getCity());
        assertTrue(response.get(0).getPublicPlaces().get(0).isMain());
    }

    @Test
    void mustReturnASuccessAndAPersonInstance_whenToCallUpdate() {
        when(personRepository.save(person)).thenReturn(person);
        when(personRepository.findById(any())).thenReturn(optionalPerson);

        Person response = personService.update(person, ID);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());
        assertNull(response.getPublicPlaces());
    }

    @Test
    void mustReturnASuccessAndAPersonInstanceWithPublicPlace_whenToCallUpdate() {
        when(personRepository.save(personWithPublicPlace)).thenReturn(personWithPublicPlace);
        when(personRepository.findById(anyLong())).thenReturn(optionalPersonWithPublicPlace);

        Person response = personService.update(personWithPublicPlace, ID);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DATE_OF_BIRTH, response.getDateOfBirth());

        assertEquals(PUBLIC_PLACE, response.getPublicPlaces().get(0).getPublicPlace());
        assertEquals(CEP, response.getPublicPlaces().get(0).getCep());
        assertEquals(NUMBER, response.getPublicPlaces().get(0).getNumber());
        assertEquals(CITY, response.getPublicPlaces().get(0).getCity());
        assertTrue(response.getPublicPlaces().get(0).isMain());
    }

    @Test
    void mustReturnAnSuccessAndAnListOfTheUsersPublicPlaces_whenToCallFindAllPublicPlaces() {
        when(personRepository.findById(anyLong())).thenReturn(optionalPersonWithPublicPlace);

        List<PublicPlace> response = personService.findAllPublicPlaces(ID);

        assertNotNull(response);
        assertEquals(PublicPlace.class, response.get(0).getClass());

        assertEquals(PUBLIC_PLACE, response.get(0).getPublicPlace());
        assertEquals(CEP, response.get(0).getCep());
        assertEquals(NUMBER, response.get(0).getNumber());
        assertEquals(CITY, response.get(0).getCity());
        assertEquals(CITY, response.get(0).getCity());
        assertTrue(response.get(0).isMain());
    }

    @Test
    void mustReturnAnSuccessAndNullOfPublicPlaces_whenToCallFindAllPublicPlaces() {
        when(personRepository.findById(anyLong())).thenReturn(optionalPerson);

        List<PublicPlace> response = personService.findAllPublicPlaces(ID);

        assertNull(response);
    }

    private void startPerson() {
        List<PublicPlace> publicPlaces = List.of(new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, MAIN));

        person = new Person(ID, NAME, DATE_OF_BIRTH);
        personWithPublicPlace = new Person(ID, NAME, DATE_OF_BIRTH, publicPlaces);

        optionalPerson = Optional.of(new Person(ID, NAME, DATE_OF_BIRTH));
        optionalPersonWithPublicPlace = Optional.of(new Person(ID, NAME, DATE_OF_BIRTH, publicPlaces));
    }
}