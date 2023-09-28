package com.gabriel.attornatus.repositories;

import com.gabriel.attornatus.domain.PublicPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicPlaceRepository extends JpaRepository<PublicPlace, Long> {
}
