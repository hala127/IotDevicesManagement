package com.vodafone.iot.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class ItemNotFoundException extends RuntimeException {

	private String errorMessage;
	private int errorCode;

	public ItemNotFoundException() {
		this.errorCode = 404;
		this.errorMessage = "Requested item doesn't exist";
	}
	
}