package com.vodafone.iot.business;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vodafone.iot.config.enums.SimStatusEnum;
import com.vodafone.iot.config.exception.IotDevicesException;
import com.vodafone.iot.config.utils.IotDevicesMapper;
import com.vodafone.iot.dtos.DeviceDto;
import com.vodafone.iot.dtos.DevicesListDto;
import com.vodafone.iot.dtos.requests.DeviceBaseRequest;
import com.vodafone.iot.dtos.requests.DeviceConfigurationRequest;
import com.vodafone.iot.dtos.requests.PagingRequestDto;
import com.vodafone.iot.entities.Device;
import com.vodafone.iot.entities.SimCard;
import com.vodafone.iot.repos.DeviceRepository;
import com.vodafone.iot.repos.SimCardRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeviceManagementService {

	@Autowired
	private DeviceRepository deviceRepo;
	@Autowired
	private SimCardRepository simCardRepo;

	@Autowired
	Environment env;

	public DevicesListDto getDevicesWaitingForActivation(PagingRequestDto pagingDto) {
		Pageable pageInfo = PageRequest.of(pagingDto.getPageNumber(), pagingDto.getPageSize());

		Page<Device> devicesPage = deviceRepo
				.findAllBySimCardIdNotNullAndSimCardStatusId(SimStatusEnum.WaitingForActivation.getId(), pageInfo);

		long totalCount = devicesPage.getTotalElements();

		log.info("devices Waiting for activation: " + totalCount);

		List<DeviceDto> devicesDtos = devicesPage.get().map(device -> {
			return IotDevicesMapper.mapDeviceEntityToDto(device);
		}).collect(Collectors.toList());

		return new DevicesListDto(totalCount, devicesDtos);
	}

	public DevicesListDto getDevicesAvailableForSale(PagingRequestDto pagingDto) {

		Pageable pageInfo = PageRequest.of(pagingDto.getPageNumber(), pagingDto.getPageSize());

		int maxTempAllowed = Integer.parseInt(env.getRequiredProperty("device.temperature.max.value"));
		int minTempAllowed = Integer.parseInt(env.getRequiredProperty("device.temperature.min.value"));

		Page<Device> devicesPage = deviceRepo.findAllByTemperatureBetweenAndSimCardIdNotNull(minTempAllowed,
				maxTempAllowed, pageInfo);

		long totalCount = devicesPage.getTotalElements();
		log.info("devices available for sale: " + totalCount);

		List<DeviceDto> devicesDtos = devicesPage.get().map(device -> {
			return IotDevicesMapper.mapDeviceEntityToDto(device);
		}).collect(Collectors.toList());

		return new DevicesListDto(totalCount, devicesDtos);
	}

	public void addDeviceSimCard(DeviceConfigurationRequest configRequest) {

		if (configRequest.getDeviceId() <= 0 || configRequest.getSimId() <= 0) {
			throw new IllegalArgumentException();
		}
		Optional<Device> deviceOptional = deviceRepo.findById(configRequest.getDeviceId());
		Optional<SimCard> simCardOptional = simCardRepo.findById(configRequest.getSimId());
		if (deviceOptional.isPresent() && simCardOptional.isPresent()) {
			Device device = deviceOptional.get();
			device.setSimCard(simCardOptional.get());
			deviceRepo.save(device);
		} else {
			throw new IotDevicesException("Device or sim card doesn't exist", 1113);
		}
	}

	public void removeDeviceSimCard(DeviceBaseRequest baseRequest) {

		if (baseRequest.getDeviceId() <= 0) {
			throw new IllegalArgumentException();
		}
		Optional<Device> deviceOptional = deviceRepo.findById(baseRequest.getDeviceId());
		if (deviceOptional.isPresent()) {
			Device device = deviceOptional.get();
			device.setSimCard(null);
			deviceRepo.save(device);
		} else {
			throw new IotDevicesException("Device doesn't exist", 1113);
		}
	}
}
