package com.gabriel.attornatus.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

//    @Column(nullable = false)
//    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "person")
    private List<PublicPlace> publicPlaces;

    public Person(Long id, String name, LocalDate dateOfBirth, String publicPlace, String cep, String number, String city) {
        this.id = id;
        this.name = name;
//        this.dateOfBirth = dateOfBirth;
    }

    public Person() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public LocalDate getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) {
//        this.dateOfBirth = dateOfBirth;
//    }

    public List<PublicPlace> getPublicPlaces() {
        return publicPlaces;
    }

    public void setPublicPlaces(List<PublicPlace> publicPlaces) {
        this.publicPlaces = publicPlaces;
    }
}
