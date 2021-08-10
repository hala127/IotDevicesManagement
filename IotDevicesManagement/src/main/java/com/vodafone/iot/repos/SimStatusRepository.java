package com.vodafone.iot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.iot.entities.SimStatus;

@Repository
public interface SimStatusRepository extends JpaRepository<SimStatus, Integer> {

}
