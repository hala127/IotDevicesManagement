package com.vodafone.iot.dtos.requests;

import com.vodafone.iot.dtos.SimCardDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceConfigurationRequest {

	private int deviceId;
	private int temperature;
	private SimCardDto simCard;
}
