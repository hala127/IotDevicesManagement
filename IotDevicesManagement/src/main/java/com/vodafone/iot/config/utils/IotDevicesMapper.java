package com.vodafone.iot.config.utils;

import com.vodafone.iot.dtos.DeviceDto;
import com.vodafone.iot.dtos.SimCardDto;
import com.vodafone.iot.entities.Device;

public class IotDevicesMapper {

	public static DeviceDto mapDeviceEntityToDto(Device device) {
		if (device == null) {
			return null;
		}
		return new DeviceDto(device.getId(), device.getTemperature(),
				device.getSimCard() != null
						? new SimCardDto(device.getSimCard().getId(), device.getSimCard().getOperatorCode(),
								device.getSimCard().getCountry(), device.getSimCard().getStatus().getId(),
								device.getSimCard().getStatus().getName())
						: null);
	}
}
