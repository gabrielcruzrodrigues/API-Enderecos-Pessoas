package com.gabriel.attornatus.resources;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.services.PersonService;
import com.gabriel.attornatus.services.PublicPlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/publicPlace")
public class PublicPlaceResource {

    @Autowired
    private PublicPlaceService publicPlaceService;

    @Autowired
    private PersonService personService;

    @PostMapping("/{idPerson}")
    public ResponseEntity<PublicPlace> createPublicPlace(@PathVariable Long idPerson, @RequestBody @Valid PublicPlace publicPlace) {
        Person person = personService.findById(idPerson);
        publicPlace.setPerson(person);
        PublicPlace publicPlaceSave = publicPlaceService.create(publicPlace);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(publicPlaceSave.getId()).toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(publicPlaceSave);
    }
}
