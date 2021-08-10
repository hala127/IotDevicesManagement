package com.vodafone.iot.config.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class IotDevicesExceptionHandler {

	@ExceptionHandler(IotDevicesException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public IotDevicesErrorResponse processBusinessError(IotDevicesException ex) {
		log.error("IotDevicesException: ", ex);
		ex.printStackTrace();

		return new IotDevicesErrorResponse(ex.getErrorMessage(), ex.getErrorCode(), new Date());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public IotDevicesErrorResponse processIllegalArguments(IllegalArgumentException ex) {
		log.error("IllegalArgumentException: ", ex);
		ex.printStackTrace();

		return new IotDevicesErrorResponse("Incorrect input parameters", 1112, new Date());

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public IotDevicesErrorResponse processGenericError(Exception ex) {
		log.error("IotDevicesException: ", ex);
		ex.printStackTrace();

		return new IotDevicesErrorResponse("An internal error has occurred.", 1111, new Date());

	}

}