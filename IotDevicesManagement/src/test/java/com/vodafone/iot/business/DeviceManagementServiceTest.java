package com.vodafone.iot.business;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vodafone.iot.config.enums.SimStatusEnum;
import com.vodafone.iot.dtos.DevicesListDto;
import com.vodafone.iot.dtos.SimCardDto;
import com.vodafone.iot.dtos.requests.DeviceConfigurationRequest;
import com.vodafone.iot.dtos.requests.PagingRequestDto;
import com.vodafone.iot.entities.Device;
import com.vodafone.iot.entities.SimCard;
import com.vodafone.iot.entities.SimStatus;
import com.vodafone.iot.repos.DeviceRepository;
import com.vodafone.iot.repos.SimCardRepository;
import com.vodafone.iot.repos.SimStatusRepository;

//@ExtendWith(MockitoExtension.class)
public class DeviceManagementServiceTest {

	@Mock
	DeviceRepository deviceRepo;

	@Mock
	SimCardRepository simCardRepo;

	@Mock
	SimStatusRepository simStatusRepo;

	@Mock
	Environment env;

	@InjectMocks
	DeviceManagementService service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	public static SimStatus SIM_STATUS_WAITING = new SimStatus(SimStatusEnum.WaitingForActivation.getId(),
			SimStatusEnum.WaitingForActivation.name());
	public static SimStatus SIM_STATUS_ACTIVE = new SimStatus(SimStatusEnum.Active.getId(),
			SimStatusEnum.WaitingForActivation.name());
	public static SimCard SIM_1 = new SimCard(1, "01013599299", "Egypt", SIM_STATUS_WAITING);
	public static SimCard SIM_2 = new SimCard(2, "01013599288", "Germany", SIM_STATUS_WAITING);
	public static SimCard SIM_3 = new SimCard(2, "01013599277", "Italy", SIM_STATUS_ACTIVE);
	public static Device DEVICE_1 = new Device(1, 25, SIM_1);
	public static Device DEVICE_2 = new Device(2, 80, SIM_2);
	public static Device DEVICE_3 = new Device(3, 20, SIM_3);

	@Test
	public void testGetDevicesWaitingForActivation_WithResultList() {
		Page<Device> devices = new PageImpl<>(new ArrayList<Device>(Arrays.asList(DEVICE_1, DEVICE_2)),
				PageRequest.of(0, 10), 2);

		when(deviceRepo.findAllBySimCardIdNotNullAndSimCardStatusId(anyInt(), any(Pageable.class))).thenReturn(devices);

		DevicesListDto result = service.getDevicesWaitingForActivation(new PagingRequestDto(1, 10));

		assertNotNull(result);
		assertEquals(devices.getTotalElements(), result.getTotalCount());
		assertEquals(result.getDevices().get(0).getId(), DEVICE_1.getId());
	}

	@Test
	public void testGetDevicesWaitingForActivation_EmptyOutput() {
		Page<Device> devices = new PageImpl<>(new ArrayList<Device>(), PageRequest.of(0, 10), 0);

		when(deviceRepo.findAllBySimCardIdNotNullAndSimCardStatusId(anyInt(), any(Pageable.class))).thenReturn(devices);

		DevicesListDto result = service.getDevicesWaitingForActivation(new PagingRequestDto(1, 10));

		assertNotNull(result);
		assertEquals(devices.getTotalElements(), result.getTotalCount());
	}

	@Test
	public void testGetDevicesWaitingForActivation_InvalidInputs() {

		assertThrows(IllegalArgumentException.class,
				() -> service.getDevicesWaitingForActivation(new PagingRequestDto(0, 10)));
	}

	@Test
	public void testGetDevicesWaitingForActivation_EmptyBody() {

		assertThrows(IllegalArgumentException.class, () -> service.getDevicesWaitingForActivation(null));
	}

	@Test
	public void testGetDevicesAvailableForSale_WithResultList() {
		Page<Device> devices = new PageImpl<>(new ArrayList<Device>(Arrays.asList(DEVICE_3)), PageRequest.of(0, 10), 1);

		when(env.getRequiredProperty("device.temperature.max.value")).thenReturn("85");
		when(env.getRequiredProperty("device.temperature.min.value")).thenReturn("25");
		when(deviceRepo.findAllByTemperatureBetweenAndSimCardIdNotNullOrderByIdDesc(anyInt(), anyInt(),
				any(Pageable.class))).thenReturn(devices);

		DevicesListDto result = service.getDevicesAvailableForSale(new PagingRequestDto(1, 10));

		assertNotNull(result);
		assertEquals(devices.getTotalElements(), result.getTotalCount());
		assertEquals(result.getDevices().get(0).getId(), DEVICE_3.getId());
	}

	@Test
	public void testGetDevicesAvailableForSale_EmptyOutput() {
		Page<Device> devices = new PageImpl<>(new ArrayList<Device>(), PageRequest.of(0, 10), 0);

		when(env.getRequiredProperty("device.temperature.max.value")).thenReturn("85");
		when(env.getRequiredProperty("device.temperature.min.value")).thenReturn("25");
		when(deviceRepo.findAllByTemperatureBetweenAndSimCardIdNotNullOrderByIdDesc(anyInt(), anyInt(), any(Pageable.class))).thenReturn(devices);

		DevicesListDto result = service.getDevicesAvailableForSale(new PagingRequestDto(1, 10));

		assertNotNull(result);
		assertEquals(devices.getTotalElements(), result.getTotalCount());
	}

	@Test
	public void testGetDevicesAvailableForSale_InvalidInputs() {

		assertThrows(IllegalArgumentException.class,
				() -> service.getDevicesAvailableForSale(new PagingRequestDto(0, 10)));
	}

	@Test
	public void testGetDevicesAvailableForSale_EmptyBody() {

		assertThrows(IllegalArgumentException.class, () -> service.getDevicesAvailableForSale(null));
	}

	@Test
	public void testupdateDeviceConfiguration() {
		DeviceConfigurationRequest deviceConfigRequest = new DeviceConfigurationRequest();
		deviceConfigRequest.setDeviceId(DEVICE_1.getId());
		deviceConfigRequest.setTemperature(DEVICE_1.getTemperature());
		SimCardDto simCardDto = new SimCardDto();
		simCardDto.setId(SIM_1.getId());
		deviceConfigRequest.setSimCard(simCardDto);

		when(deviceRepo.findById(anyInt())).thenReturn(Optional.of(DEVICE_1));
		when(simCardRepo.findById(anyInt())).thenReturn(Optional.of(SIM_1));
		when(simStatusRepo.findById(anyInt())).thenReturn(Optional.of(SIM_STATUS_WAITING));

		assertThatCode(() -> service.updateDeviceConfiguration(deviceConfigRequest)).doesNotThrowAnyException();
	}

	@Test
	public void testupdateDeviceConfiguration_InvalidDeviceId() {
		DeviceConfigurationRequest deviceConfigRequest = new DeviceConfigurationRequest();
		deviceConfigRequest.setDeviceId(-1);

		assertThrows(IllegalArgumentException.class, () -> service.updateDeviceConfiguration(deviceConfigRequest));
	}

	@Test
	public void testupdateDeviceConfiguration_InvalidSimCardId() {
		DeviceConfigurationRequest deviceConfigRequest = new DeviceConfigurationRequest();
		SimCardDto simCard = new SimCardDto();
		simCard.setId(0);
		deviceConfigRequest.setSimCard(simCard);

		assertThrows(IllegalArgumentException.class, () -> service.updateDeviceConfiguration(deviceConfigRequest));
	}

	@Test
	public void testupdateDeviceConfiguration_EmptyBody() {

		assertThrows(IllegalArgumentException.class, () -> service.updateDeviceConfiguration(null));
	}

}
