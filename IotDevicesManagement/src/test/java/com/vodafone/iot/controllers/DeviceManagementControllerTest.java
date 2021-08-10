package com.vodafone.iot.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.iot.business.DeviceManagementService;
import com.vodafone.iot.config.enums.SimStatusEnum;
import com.vodafone.iot.dtos.DeviceDto;
import com.vodafone.iot.dtos.DevicesListDto;
import com.vodafone.iot.dtos.SimCardDto;
import com.vodafone.iot.dtos.SimCardStatusDto;
import com.vodafone.iot.dtos.requests.PagingRequestDto;

@WebMvcTest(DeviceManagementController.class)
public class DeviceManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	DeviceManagementService deviceManagementService;

	@InjectMocks
	DeviceManagementController controller;

	public static SimCardStatusDto SIM_STATUS_WAITING = new SimCardStatusDto(SimStatusEnum.WaitingForActivation.getId(),
			SimStatusEnum.WaitingForActivation.name());
	public static SimCardStatusDto SIM_STATUS_ACTIVE = new SimCardStatusDto(SimStatusEnum.Active.getId(),
			SimStatusEnum.Active.name());
	public static SimCardDto SIM_1 = new SimCardDto(1, "01013599299", "Egypt", SIM_STATUS_WAITING);
	public static SimCardDto SIM_2 = new SimCardDto(2, "01013599288", "Germany", SIM_STATUS_WAITING);
	public static SimCardDto SIM_3 = new SimCardDto(3, "01013599277", "Italy", SIM_STATUS_ACTIVE);
	public static DeviceDto DEVICE_1 = new DeviceDto(1, 25, SIM_1);
	public static DeviceDto DEVICE_2 = new DeviceDto(2, 80, SIM_2);
	public static DeviceDto DEVICE_3 = new DeviceDto(3, 30, SIM_3);

	@Test
	public void testGetDevicesWaitingForActivation() throws Exception {

		PagingRequestDto pagingRequestDto = new PagingRequestDto(1, 10);
		List<DeviceDto> devicesList = new ArrayList<DeviceDto>(Arrays.asList(DEVICE_1, DEVICE_2));

		DevicesListDto devicesListDto = new DevicesListDto(devicesList.size(), devicesList);

		when(deviceManagementService.getDevicesWaitingForActivation(any(PagingRequestDto.class)))
				.thenReturn(devicesListDto);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/getDevicesWaitingForActivation")
						.content(asJsonString(pagingRequestDto)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.devices").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.devices").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(devicesList.size()));
	}

	@Test
	public void testGetDevicesWaitingForActivation_InvalidInputs() throws Exception {

		PagingRequestDto pagingRequestDto = new PagingRequestDto(0, 10);

		when(deviceManagementService.getDevicesWaitingForActivation(any(PagingRequestDto.class)))
				.thenThrow(IllegalArgumentException.class);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/getDevicesWaitingForActivation")
						.content(asJsonString(pagingRequestDto)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists());
	}

	@Test
	public void testGetDevicesAvailableForSale() throws Exception {

		PagingRequestDto pagingRequestDto = new PagingRequestDto(1, 10);
		List<DeviceDto> devicesList = new ArrayList<DeviceDto>(Arrays.asList(DEVICE_3));

		DevicesListDto devicesListDto = new DevicesListDto(devicesList.size(), devicesList);

		when(deviceManagementService.getDevicesAvailableForSale(any(PagingRequestDto.class)))
				.thenReturn(devicesListDto);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/getDevicesAvailableForSale")
						.content(asJsonString(pagingRequestDto)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.devices").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.devices").isNotEmpty())
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(devicesList.size()));
	}
	
	@Test
	public void testGetDevicesAvailableForSale_InvalidInputs() throws Exception {

		PagingRequestDto pagingRequestDto = new PagingRequestDto(0, 10);

		when(deviceManagementService.getDevicesAvailableForSale(any(PagingRequestDto.class)))
				.thenThrow(IllegalArgumentException.class);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/getDevicesAvailableForSale")
						.content(asJsonString(pagingRequestDto)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(MockMvcResultMatchers.jsonPath("$.errorMessage").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").exists());
	}
	//
	// @Test
	// public void testRemoveDeviceConfiguration() throws Exception {
	//
	// DeviceBaseRequest device = new DeviceBaseRequest(1);
	// doNothing().when(deviceManagementService.removeDeviceSimCard(any(DeviceBaseRequest.class)));
	//
	// this.mockMvc
	// .perform(MockMvcRequestBuilders.post("/getDevicesAvailableForSale")
	// .content(asJsonString(pagingRequestDto)).contentType(MediaType.APPLICATION_JSON)
	// .accept(MediaType.APPLICATION_JSON))
	// .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").exists())
	// .andExpect(MockMvcResultMatchers.jsonPath("$.devices").exists())
	// .andExpect(MockMvcResultMatchers.jsonPath("$.devices").isNotEmpty())
	// .andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").value(devicesList.size()));
	// }

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
