package com.vodafone.iot.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vodafone.iot.dtos.DeviceDto;
import com.vodafone.iot.dtos.DevicesListDto;
import com.vodafone.iot.dtos.requests.DeviceConfigurationRequest;
import com.vodafone.iot.dtos.requests.PagingRequestDto;
import com.vodafone.iot.entities.Device;
import com.vodafone.iot.entities.SimCard;
import com.vodafone.iot.entities.SimStatus;
import com.vodafone.iot.enums.SimStatusEnum;
import com.vodafone.iot.exception.IotDevicesException;
import com.vodafone.iot.exception.ItemNotFoundException;
import com.vodafone.iot.repos.DeviceRepository;
import com.vodafone.iot.repos.SimCardRepository;
import com.vodafone.iot.repos.SimStatusRepository;
import com.vodafone.iot.utils.IotDevicesMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeviceManagementService {

	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private SimCardRepository simCardRepo;
	@Autowired
	private SimStatusRepository simStatusRepo;

	@Autowired
	Environment env;

	public DevicesListDto getDevicesWaitingForActivation(PagingRequestDto pagingRequestDto) {

		if (pagingRequestDto == null || pagingRequestDto.getPageNumber() <= 0) {
			throw new IllegalArgumentException();
		}

		Pageable pageInfo = PageRequest.of(pagingRequestDto.getPageNumber() - 1, pagingRequestDto.getPageSize());

		Page<Device> devicesPage = deviceRepo
				.findAllBySimCardIdNotNullAndSimCardStatusId(SimStatusEnum.WaitingForActivation.getId(), pageInfo);

		long totalCount = devicesPage.getTotalElements();

		log.info("devices Waiting for activation: " + totalCount);

		List<DeviceDto> devicesDtos = devicesPage.get().map(device -> {
			return IotDevicesMapper.mapDeviceEntityToDto(device);
		}).collect(Collectors.toList());

		return new DevicesListDto(totalCount, devicesDtos);
	}

	public DevicesListDto getDevicesAvailableForSale(PagingRequestDto pagingRequestDto) {

		if (pagingRequestDto == null || pagingRequestDto.getPageNumber() <= 0) {
			throw new IllegalArgumentException();
		}
		Pageable pageInfo = PageRequest.of(pagingRequestDto.getPageNumber() - 1, pagingRequestDto.getPageSize());

		int maxTempAllowed = Integer.parseInt(env.getRequiredProperty("device.temperature.max.value"));
		int minTempAllowed = Integer.parseInt(env.getRequiredProperty("device.temperature.min.value"));

		Page<Device> devicesPage = deviceRepo
				.findAllByTemperatureBetweenAndSimCardIdNotNullOrderByIdDesc(minTempAllowed, maxTempAllowed, pageInfo);

		long totalCount = devicesPage.getTotalElements();
		log.info("devices available for sale: " + totalCount);

		List<DeviceDto> devicesDtos = devicesPage.get().map(device -> {
			return IotDevicesMapper.mapDeviceEntityToDto(device);
		}).collect(Collectors.toList());

		return new DevicesListDto(totalCount, devicesDtos);
	}

	public void updateDeviceConfiguration(DeviceConfigurationRequest configRequest) {

		if (configRequest == null || configRequest.getDeviceId() <= 0) {
			throw new IllegalArgumentException();
		}
		Device device = deviceRepo.findById(configRequest.getDeviceId())
				.orElseThrow(() -> new ItemNotFoundException("Device doesn't exist", 1113));

		device.setTemperature(configRequest.getTemperature());

		if (configRequest.getSimCard() != null) {
			SimCard simCard = simCardRepo.findById(configRequest.getSimCard().getId())
					.orElseThrow(() -> new ItemNotFoundException("Sim card doesn't exist", 1113));

			simCard.setCountry(configRequest.getSimCard().getCountry());
			simCard.setOperatorCode(configRequest.getSimCard().getOperatorCode());

			if (configRequest.getSimCard().getStatus() != null) {
				SimStatus simStatus = simStatusRepo.findById(configRequest.getSimCard().getStatus().getId())
						.orElseThrow(() -> new IotDevicesException("Sim status is not valid", 1112));
				simCard.setStatus(simStatus);
			}

			device.setSimCard(simCard);
		} else {
			device.setSimCard(null);
		}

		try {
			deviceRepo.save(device);
		} catch (DataIntegrityViolationException ex) {
			throw new IotDevicesException("Please, check inserted data", 1110);
		}
	}

}
