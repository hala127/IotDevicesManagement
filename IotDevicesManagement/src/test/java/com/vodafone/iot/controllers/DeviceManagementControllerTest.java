package com.vodafone.iot.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vodafone.iot.business.DeviceManagementServiceTest;
import com.vodafone.iot.dtos.requests.PagingRequestDto;

@WebMvcTest(DeviceManagementController.class)
public class DeviceManagementControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	DeviceManagementServiceTest deviceManagementService;


	@Test
	public void testGetDevicesWaitingForActivation() throws Exception {

		PagingRequestDto pagingRequestDto = new PagingRequestDto(1, 10);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/getDevicesWaitingForActivation")
						.content(asJsonString(pagingRequestDto)).contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(MockMvcResultMatchers.jsonPath("$.totalCount").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.devices").exists());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
