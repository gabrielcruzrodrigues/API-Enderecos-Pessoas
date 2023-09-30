package com.gabriel.attornatus.resources;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.services.Exceptions.DataIntegrityViolationException;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import com.gabriel.attornatus.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class PersonResourceTest {

    public static final long ID = 1L;
    public static final String NAME = "Gabriel";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2002, 01, 22);
    public static final String PERSON_NOT_FOUND = "pessoa não encontrada.";
    public static final String PUBLIC_PLACE = "Rua j";
    public static final String CEP = "12345678";
    public static final String NUMBER = "64";
    public static final String CITY = "São Paulo";
    public static final boolean MAIN = true;
    public static final boolean FALSE = false;
    public static final String INVALID_DATA = "Dados invalidos";

    @InjectMocks
    private PersonResource personResource;

    @Mock
    private PersonService personService;

    private Person person;
    private Person personWithPublicPlace;
    private PublicPlace publicPlace;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void mustReturnAResponseEntityWithStatusCreated_whenToCallCreate() {
        when(personService.create(person)).thenReturn(person);

        ResponseEntity<Person> response = personResource.create(person);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
        assertNotNull(response.getHeaders().get(("Location")));

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(DATE_OF_BIRTH, response.getBody().getDateOfBirth());
    }

    @Test
    void mustReturnAResponseEntityWithStatusOk_whenToCallFindById() {
        when(personService.findById(anyLong())).thenReturn(personWithPublicPlace);

        ResponseEntity<Person> response = personResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Person.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(DATE_OF_BIRTH, response.getBody().getDateOfBirth());

        assertEquals(PUBLIC_PLACE,  response.getBody().getPublicPlaces().get(0).getPublicPlace());
        assertEquals(CEP,  response.getBody().getPublicPlaces().get(0).getCep());
        assertEquals(NUMBER,  response.getBody().getPublicPlaces().get(0).getNumber());
        assertEquals(CITY,  response.getBody().getPublicPlaces().get(0).getCity());
        assertTrue(response.getBody().getPublicPlaces().get(0).isMain());
    }

    @Test
    void mustReturnObjectNotFoundException_whenToCallFindById() {
        when(personService.findById(anyLong())).thenThrow(new ObjectNotFoundException(PERSON_NOT_FOUND));

        try {
            personResource.findById(ID);
        } catch (Exception ex) {
            assertNotNull(ex);
            assertNotNull(ex.getMessage());
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(PERSON_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void mustReturnAListOfPerson_whenToCallFindAll() {
        when(personService.findAll()).thenReturn(List.of(personWithPublicPlace));

        ResponseEntity<List<Person>> response = personResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Person.class, response.getBody().get(0).getClass());

        assertEquals(ID, response.getBody().get(0).getId());
        assertEquals(NAME, response.getBody().get(0).getName());
        assertEquals(DATE_OF_BIRTH, response.getBody().get(0).getDateOfBirth());

        assertEquals(PUBLIC_PLACE,  response.getBody().get(0).getPublicPlaces().get(0).getPublicPlace());
        assertEquals(CEP,  response.getBody().get(0).getPublicPlaces().get(0).getCep());
        assertEquals(NUMBER,  response.getBody().get(0).getPublicPlaces().get(0).getNumber());
        assertEquals(CITY,  response.getBody().get(0).getPublicPlaces().get(0).getCity());
        assertTrue(response.getBody().get(0).getPublicPlaces().get(0).isMain());
    }

    @Test
    void mustReturnAListOfPublicPlaces_whenToCallFindAllPublicPlaces() {
        when(personService.findAllPublicPlaces(anyLong())).thenReturn(List.of(publicPlace));

        ResponseEntity<List<PublicPlace>> response = personResource.findAllPublicPlaces(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(PublicPlace.class, response.getBody().get(0).getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());

        assertEquals(PUBLIC_PLACE, response.getBody().get(0).getPublicPlace());
        assertEquals(CEP, response.getBody().get(0).getCep());
        assertEquals(NUMBER, response.getBody().get(0).getNumber());
        assertEquals(CITY, response.getBody().get(0).getCity());
    }

    @Test
    void mustReturnAInstancePersonWithStatusOk_whenToCallUpdate() {
        when(personService.update(any(), anyLong())).thenReturn(personWithPublicPlace);

        ResponseEntity<Person> response = personResource.update(personWithPublicPlace, ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Person.class, response.getBody().getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(DATE_OF_BIRTH, response.getBody().getDateOfBirth());

        assertEquals(PUBLIC_PLACE,  response.getBody().getPublicPlaces().get(0).getPublicPlace());
        assertEquals(CEP,  response.getBody().getPublicPlaces().get(0).getCep());
        assertEquals(NUMBER,  response.getBody().getPublicPlaces().get(0).getNumber());
        assertEquals(CITY,  response.getBody().getPublicPlaces().get(0).getCity());
        assertTrue(response.getBody().getPublicPlaces().get(0).isMain());
    }

    @Test
    void mustReturnADataIntegrityViolationException_whenToCallUpdate() {
        when(personService.update(any(), anyLong())).thenThrow(new DataIntegrityViolationException(INVALID_DATA));

        try {
            personResource.update(personWithPublicPlace, ID);
        } catch (Exception ex) {
            assertNotNull(ex);
            assertNotNull(ex.getMessage());
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
        }
    }

    private void startPerson() {
        List<PublicPlace> publicPlaces = List.of(new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, MAIN));

        person = new Person(ID, NAME, DATE_OF_BIRTH);
        personWithPublicPlace = new Person(ID, NAME, DATE_OF_BIRTH, publicPlaces);
        publicPlace = new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, FALSE);
    }
}