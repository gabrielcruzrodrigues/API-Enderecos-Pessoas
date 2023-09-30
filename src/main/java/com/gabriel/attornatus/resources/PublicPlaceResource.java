package com.gabriel.attornatus.resources;

import com.gabriel.attornatus.domain.DTO.PublicPlaceDTO;
import com.gabriel.attornatus.domain.PublicPlace;
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

    @PostMapping("/{idPerson}")
    public ResponseEntity<PublicPlace> createPublicPlace(@PathVariable Long idPerson, @RequestBody @Valid PublicPlace publicPlaceObj) {
        PublicPlace publicPlace = publicPlaceService.create(publicPlaceObj, idPerson);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(publicPlace.getId()).toUri();
        return ResponseEntity.status(HttpStatus.CREATED).body(publicPlace);
    }

    @GetMapping("/main/{idPerson}")
    public ResponseEntity<PublicPlace> searchMainPublicPlace(@PathVariable Long idPerson) {
        return ResponseEntity.ok().body(publicPlaceService.returnMainPublicPlace(idPerson));
    }

    @PutMapping("/main/{idPerson}")
    public ResponseEntity<PublicPlace> setNewMainPublicPlace(@PathVariable Long idPerson, @RequestBody PublicPlaceDTO id) {
        return ResponseEntity.ok().body(publicPlaceService.changeOfMainPublicPlace(idPerson, id));
    }
}
