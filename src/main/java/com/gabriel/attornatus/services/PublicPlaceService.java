package com.gabriel.attornatus.services;

import com.gabriel.attornatus.domain.PublicPlace;
import com.gabriel.attornatus.repositories.PublicPlaceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublicPlaceService {

    @Autowired
    private PublicPlaceRepository publicPlaceRepository;

    @Transactional
    public PublicPlace create(PublicPlace publicPlace) {
        publicPlace.setId(null);
        return publicPlaceRepository.save(publicPlace);
    }
}
