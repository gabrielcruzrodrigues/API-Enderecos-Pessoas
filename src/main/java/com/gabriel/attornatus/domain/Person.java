package com.gabriel.attornatus.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O seu nome não pode estar em branco")
    @NotNull
    @Column(nullable = false, length = 60)
    @Size(min = 3, max = 60, message = "O seu nome deve conter de 3 a 60 caracteres.")
    private String name;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PublicPlace> publicPlaces;

    public Person(Long id, String name, LocalDate dateOfBirth) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(Long id, String name, LocalDate dateOfBirth, List<PublicPlace> publicPlaces) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.publicPlaces = publicPlaces;
    }

    public Person() {
    }

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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<PublicPlace> getPublicPlaces() {
        return publicPlaces;
    }

    public void setPublicPlaces(List<PublicPlace> publicPlaces) {
        this.publicPlaces = publicPlaces;
    }
}
