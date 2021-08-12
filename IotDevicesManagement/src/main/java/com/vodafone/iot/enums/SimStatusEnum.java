package com.vodafone.iot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SimStatusEnum {

	Active(1), WaitingForActivation(2), Blocked(3), Deactivated(4);

	private int id;
}
