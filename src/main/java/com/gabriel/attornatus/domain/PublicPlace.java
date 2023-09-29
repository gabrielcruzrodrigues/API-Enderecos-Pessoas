package com.gabriel.attornatus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Public_Place")
public class PublicPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O seu logradouro (PublicPlace) não pode ser vazio.")
    @Column(length = 60, nullable = false)
    @Size(min = 5, max = 60, message = "Seu logradouro (PublicPlace) deve ter mais de 5 caracteres.")
    private String publicPlace;

    @Column(nullable = false, length = 8)
    @Size(min = 8, max = 8, message = "Digite um CEP válido com 8 caracteres, sem pontos ou traços.")
    private String cep;

    @Column(nullable = false, length = 11)
    @NotBlank(message = "A numeração da sua residêcia não pode estar em branco.")
    @Size(min = 1, max = 10, message = "Informe um numero valido!")
    private String number;

    @Column(nullable = false, length = 30)
    @NotBlank(message = "A sua cidade não pode estar em branco.")
    @Size(min = 5, max = 30, message = "Digite uma cidade válida.")
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

    public PublicPlace(String publicPlace, String cep, String number, String city, boolean main, Person person) {
        this.publicPlace = publicPlace;
        this.cep = cep;
        this.number = number;
        this.city = city;
        this.main = main;
        this.person = person;
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
