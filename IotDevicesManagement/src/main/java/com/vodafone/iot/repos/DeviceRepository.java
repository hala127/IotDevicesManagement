package com.vodafone.iot.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vodafone.iot.entities.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

	public Page<Device> findAllByTemperatureBetweenAndSimCardIdNotNull(int minTemp, int maxTemp, Pageable pageInfo);

	public Page<Device> findAllBySimCardIdNotNullAndSimCardStatusId(int statusId, Pageable pageInfo);

}
