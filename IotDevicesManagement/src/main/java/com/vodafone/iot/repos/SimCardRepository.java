package com.vodafone.iot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.iot.entities.SimCard;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, Integer> {

}
