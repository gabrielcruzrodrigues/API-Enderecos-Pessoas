package com.gabriel.attornatus.resources;

import com.gabriel.attornatus.domain.Person;
import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PersonRepository;
import com.gabriel.attornatus.services.PersonService;
import com.gabriel.attornatus.services.PublicPlaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody @Valid Person personObj) {
        Person person = personService.create(personObj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).body(person);
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok().body(personService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Person person = personService.findById(id);
        return ResponseEntity.ok().body(person);
    }

    @GetMapping("/publicPlaces/{id}")
    public ResponseEntity<List<PublicPlace>> findAllPublicPlaces(@PathVariable Long id) {
        return ResponseEntity.ok().body(personService.findAllPublicPlaces(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@RequestBody @Valid Person person, @PathVariable Long id) {
        person.setId(id);
        return ResponseEntity.ok().body(personService.update(person));
    }









}
