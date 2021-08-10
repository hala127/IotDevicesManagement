package com.vodafone.iot.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.vodafone.iot.config.enums.SimStatusEnum;
import com.vodafone.iot.dtos.DevicesListDto;
import com.vodafone.iot.dtos.requests.PagingRequestDto;
import com.vodafone.iot.entities.Device;
import com.vodafone.iot.entities.SimCard;
import com.vodafone.iot.entities.SimStatus;
import com.vodafone.iot.repos.DeviceRepository;
import com.vodafone.iot.repos.SimCardRepository;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class DeviceManagementServiceTest {

	@Mock
	DeviceRepository deviceRepo;

	@Mock
	SimCardRepository simCardRepo;

	@InjectMocks
	DeviceManagementService service;

	public static SimStatus SIM_STATUS_WAITING = new SimStatus(SimStatusEnum.WaitingForActivation.getId(),
			SimStatusEnum.WaitingForActivation.name());
	public static SimStatus SIM_STATUS_ACTIVE = new SimStatus(SimStatusEnum.Active.getId(),
			SimStatusEnum.WaitingForActivation.name());
	public static SimCard SIM_1 = new SimCard(1, "01013599299", "Egypt", SIM_STATUS_WAITING, null);
	public static SimCard SIM_2 = new SimCard(2, "01013599288", "Egypt", SIM_STATUS_WAITING, null);
	public static SimCard SIM_3 = new SimCard(2, "01013599288", "Egypt", SIM_STATUS_ACTIVE, null);
	public static Device DEVICE_1 = new Device(1, SIM_1, 25);
	public static Device DEVICE_2 = new Device(2, SIM_2, 80);
	public static Device DEVICE_3 = new Device(3, SIM_3, 20);

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetDevicesWaitingForActivation() {
		Page<Device> devices = new PageImpl<>(new ArrayList<Device>(Arrays.asList(DEVICE_1, DEVICE_2)),
				PageRequest.of(1, 10), 2);

		when(deviceRepo.findAllBySimCardIdNotNullAndSimCardStatusId(anyInt(), any(Pageable.class))).thenReturn(devices);

		DevicesListDto result = service.getDevicesWaitingForActivation(new PagingRequestDto(1, 10));

		assertEquals(devices.getTotalElements(), result.getTotalCount());
		assertEquals(result.getDevices().get(0).getId(), DEVICE_1.getId());
	}
}
