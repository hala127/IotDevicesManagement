package com.vodafone.iot.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DevicesListDto {

	private long totalCount;
	private List<DeviceDto> devices;
}
