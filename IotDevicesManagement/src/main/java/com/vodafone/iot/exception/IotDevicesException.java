package com.vodafone.iot.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class IotDevicesException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String errorMessage;
	private int errorCode;

	public IotDevicesException() {
		this.errorCode = 500;
		this.errorMessage = "An error occurred while loading data.";
	}

}
