package com.vodafone.iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.iot.business.DeviceManagementService;
import com.vodafone.iot.dtos.ActionPerformedResponse;
import com.vodafone.iot.dtos.DevicesListDto;
import com.vodafone.iot.dtos.requests.DeviceConfigurationRequest;
import com.vodafone.iot.dtos.requests.PagingRequestDto;

@RestController
public class DeviceManagementController {

	@Autowired
	DeviceManagementService deviceManagementService;

	@PostMapping("/getDevicesWaitingForActivation")
	public DevicesListDto getDevicesWaitingForActivation(@RequestBody PagingRequestDto pagingBody) {

		return deviceManagementService.getDevicesWaitingForActivation(pagingBody);
	}

	@PostMapping("/updateDeviceConfiguration")
	public ActionPerformedResponse updateDeviceConfiguration(@RequestBody DeviceConfigurationRequest configRequest) {

		deviceManagementService.updateDeviceConfiguration(configRequest);
		return new ActionPerformedResponse();
	}


	@PostMapping("/getDevicesAvailableForSale")
	public DevicesListDto getDevicesAvailableForSale(@RequestBody PagingRequestDto pagingBody) {

		return deviceManagementService.getDevicesAvailableForSale(pagingBody);
	}

}
