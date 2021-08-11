package com.vodafone.iot.config.utils;

import com.vodafone.iot.dtos.DeviceDto;
import com.vodafone.iot.dtos.SimCardDto;
import com.vodafone.iot.dtos.SimCardStatusDto;
import com.vodafone.iot.entities.Device;
import com.vodafone.iot.entities.SimStatus;

public class IotDevicesMapper {

	public static DeviceDto mapDeviceEntityToDto(Device device) {
		if (device == null) {
			return null;
		}

		SimCardStatusDto status = new SimCardStatusDto(device.getSimCard().getStatus().getId(),
				device.getSimCard().getStatus().getName());
		
		return new DeviceDto(device.getId(), device.getTemperature(),
				device.getSimCard() != null
						? new SimCardDto(device.getSimCard().getId(), device.getSimCard().getOperatorCode(),
								device.getSimCard().getCountry(), status)
						: null);
	}
}
