package com.gabriel.attornatus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Public_Place")
public class PublicPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 60)
    private String publicPlace;

    @Column(nullable = false, length = 8)
    @Size(min = 8, max = 8)
    private String cep;

    @Column(nullable = false, length = 11)
//    @Size(min = 11, max = 11)
    private String number;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(name = "main", nullable = false)
    private boolean main;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public PublicPlace(String publicPlace, String cep, String number, String city, boolean main) {
        this.publicPlace = publicPlace;
        this.cep = cep;
        this.number = number;
        this.city = city;
        this.main = main;
    }

    public PublicPlace() {}

    public String getPublicPlace() {
        return publicPlace;
    }

    public void setPublicPlace(String publicPlace) {
        this.publicPlace = publicPlace;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }
}
