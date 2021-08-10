package com.vodafone.iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vodafone.iot.business.DeviceManagementService;
import com.vodafone.iot.config.exception.IotDevicesException;
import com.vodafone.iot.dtos.ActionPerformedResponse;
import com.vodafone.iot.dtos.DevicesListDto;
import com.vodafone.iot.dtos.requests.DeviceBaseRequest;
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

	@PostMapping("/addDeviceConfiguration")
	public ActionPerformedResponse addDeviceConfiguration(@RequestBody DeviceConfigurationRequest configRequest) {

		deviceManagementService.addDeviceSimCard(configRequest);
		return new ActionPerformedResponse();
	}

	@PostMapping("/removeDeviceConfiguration")
	public ActionPerformedResponse removeDeviceConfiguration(@RequestBody DeviceBaseRequest basicRequest)
			throws IotDevicesException {

		deviceManagementService.removeDeviceSimCard(basicRequest);
		return new ActionPerformedResponse();
	}

	@PostMapping("/getDevicesAvailableForSale")
	public DevicesListDto getDevicesAvailableForSale(@RequestBody PagingRequestDto pagingBody) {

		return deviceManagementService.getDevicesAvailableForSale(pagingBody);
	}

}
