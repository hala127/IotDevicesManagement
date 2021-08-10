package com.vodafone.iot.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceConfigurationRequest extends DeviceBaseRequest {

	private int simId;
}
