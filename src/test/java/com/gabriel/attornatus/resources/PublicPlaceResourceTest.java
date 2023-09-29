package com.gabriel.attornatus.resources;

import com.gabriel.attornatus.domain.DTO.PublicPlaceDTO;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.services.Exceptions.ObjectNotFoundException;
import com.gabriel.attornatus.services.PersonService;
import com.gabriel.attornatus.services.PublicPlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class PublicPlaceResourceTest {

    public static final long ID = 1L;
    public static final String PUBLIC_PLACE = "Rua j";
    public static final String CEP = "12345678";
    public static final String NUMBER = "64";
    public static final String CITY = "São Paulo";
    public static final boolean MAIN = true;
    public static final boolean FALSE = false;
    public static final String PUBLIC_PLACE_NOT_FOUND = "logradouro não encontrado.";

    @InjectMocks
    private PublicPlaceResource publicPlaceResource;

    @Mock
    private PublicPlaceService publicPlaceService;

    private PublicPlace publicPlace;
    private PublicPlaceDTO publicDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPublicPlace();
    }

    @Test
    void mustReturnSuccessAndAnResponseEntityWithPublicPlaceInstance_whenToCallCreatePublicPlace() {
        when(publicPlaceService.create(publicPlace, ID)).thenReturn(publicPlace);

        ResponseEntity<PublicPlace> response = publicPlaceResource.createPublicPlace(ID, publicPlace);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());

        assertEquals(PUBLIC_PLACE,  response.getBody().getPublicPlace());
        assertEquals(CEP,  response.getBody().getCep());
        assertEquals(NUMBER,  response.getBody().getNumber());
        assertEquals(CITY,  response.getBody().getCity());
        assertTrue(response.getBody().isMain());
    }

    @Test
    void mustReturnSuccessAndAMainPublicPlace_whenToCallSearchMainPublicPlace() {
        when(publicPlaceService.returnMainPublicPlace(ID)).thenReturn(publicPlace);

        ResponseEntity<PublicPlace> response = publicPlaceResource.searchMainPublicPlace(ID);

        assertNotNull(response);
        assertNotNull(response.getHeaders());
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PublicPlace.class, response.getBody().getClass());

        assertEquals(PUBLIC_PLACE,  response.getBody().getPublicPlace());
        assertEquals(CEP,  response.getBody().getCep());
        assertEquals(NUMBER,  response.getBody().getNumber());
        assertEquals(CITY,  response.getBody().getCity());
        assertTrue(response.getBody().isMain());
    }

    @Test
    void mustReturnObjectNotFoundException_whenToCallSearchMainPublicPlace() {
        when(publicPlaceService.returnMainPublicPlace(ID)).thenThrow(new ObjectNotFoundException(PUBLIC_PLACE_NOT_FOUND));

        try {
            publicPlaceResource.searchMainPublicPlace(ID);
        } catch (Exception ex) {
            assertNotNull(ex);
            assertNotNull(ex.getMessage());
            assertEquals(PUBLIC_PLACE_NOT_FOUND, ex.getMessage());
            assertEquals(ObjectNotFoundException.class, ex.getClass());
        }
    }

    @Test
    void mustReturnASuccessAndAInstanceOfPublicPlaceWithNewMain_whenToCallSetNewMainPublicPlace() {
        when(publicPlaceService.changeOfMainPublicPlace(anyLong(), any())).thenReturn(publicPlace);
        when(publicPlaceService.returnMainPublicPlace(anyLong())).thenReturn(publicPlace);

        ResponseEntity<PublicPlace> response = publicPlaceResource.setNewMainPublicPlace(ID, publicDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertNotNull(response.getHeaders());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());

        assertEquals(PUBLIC_PLACE,  response.getBody().getPublicPlace());
        assertEquals(CEP,  response.getBody().getCep());
        assertEquals(NUMBER,  response.getBody().getNumber());
        assertEquals(CITY,  response.getBody().getCity());
        assertTrue(response.getBody().isMain());
    }

    private void startPublicPlace() {
        publicDTO = new PublicPlaceDTO(ID);
        publicPlace = new PublicPlace(PUBLIC_PLACE, CEP, NUMBER, CITY, MAIN);

    }
}