package com.vodafone.iot.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class IotDevicesException extends RuntimeException {

	private String errorMessage;
	private int errorCode;

	public IotDevicesException() {
		this.errorCode = 500;
		this.errorMessage = "An error occurred while loading data.";
	}
	
}
