package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.DTO.PublicPlaceDTO;
import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PublicPlaceRepository;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import com.gabriel.attornatus.util.OnlyLetters;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PublicPlaceServiceTest {

    public static final long ID = 1L;
    public static final String NAME = "Gabriel";
    public static final LocalDate DATE_OF_BIRTH = LocalDate.of(2002, 01, 22);
    public static final String PUBLIC_PLACE = "Rua j";
    public static final String CEP = "12345678";
    public static final String NUMBER = "64";
    public static final String CITY = "SaoPaulo";
    public static final boolean TRUE = true;
    public static final boolean FALSE = false;
    public static final String PERSON_NOT_FOUND = "pessoa não encontrada.";

    @InjectMocks
    private PublicPlaceService publicPlaceService;

    @Mock
    private PublicPlaceRepository publicPlaceRepository;

    @Mock
    private PersonService personService;

    private Person person;
    private Person personWithPublicPlace;
    private PublicPlace publicPlace;
    private PublicPlace placeMain;
    private PublicPlace placeMainPerson;
    private List<PublicPlace> publicPlaces;
    private PublicPlaceDTO placeDTO;
    private Optional<PublicPlace> optionalplace;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPerson();
    }

    @Test
    void mustReturnThePersonMainPublicPlace_whenToCallReturnMainPublicPlace() {
        when(personService.findById(anyLong())).thenReturn(personWithPublicPlace);

        PublicPlace response = publicPlaceService.returnMainPublicPlace(ID);

        assertNotNull(response);
        assertEquals(PublicPlace.class, response.getClass());
        assertTrue(response.isMain());
        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(CITY, response.getCity());
        assertEquals(CEP, response.getCep());
        assertEquals(NUMBER, response.getNumber());
    }

    @Test
    void mustReturnNullValue_whenToCallReturnMainPublicPlace() {
        when(personService.findById(anyLong())).thenReturn(null);

        PublicPlace response = publicPlaceService.returnMainPublicPlace(ID);

        assertNull(response);
    }

    @Test
    void mustReturnNullValue_whenToCallFindMain() {
        when(personService.findById(anyLong())).thenReturn(null);

        PublicPlace response = publicPlaceService.findMain(publicPlaces);

        assertNull(response);
    }

    @Test
    void mustReturnObjectNotFoundException_whenToCallFindById() {
        when(personService.findById(anyLong())).thenThrow(new ObjectNotFoundException(PERSON_NOT_FOUND));

        try {
            publicPlaceService.returnMainPublicPlace(ID);
        } catch (Exception ex) {
            assertNotNull(ex);
            assertNotNull(ex.getMessage());
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(PERSON_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    void mustReturnSuccessAndAnPublicPlaceInstance_whenToCallFindById() {
        when(publicPlaceRepository.findById(anyLong())).thenReturn(optionalplace);

        PublicPlace response = publicPlaceService.findById(ID);

        assertNotNull(response);
        assertEquals(response.getClass(), response.getClass());

        assertEquals(PUBLIC_PLACE, response.getPublicPlace());
        assertEquals(CEP, response.getCep());
        assertEquals(NUMBER, response.getNumber());
        assertEquals(CITY, response.getCity());
    }

    private void startPerson() {
        List<PublicPlace> publicPlacesMain = List.of(new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, TRUE));
        publicPlaces = List.of(new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, FALSE));

        person = new Person(ID, NAME, DATE_OF_BIRTH);
        personWithPublicPlace = new Person(ID, NAME, DATE_OF_BIRTH, publicPlacesMain);
        publicPlace = new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, FALSE);
        optionalplace = Optional.of(new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, FALSE));
        placeMain = new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, TRUE);
        placeMainPerson = new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, TRUE, person);
        placeDTO = new PublicPlaceDTO(ID);
    }
}