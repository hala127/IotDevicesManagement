package com.vodafone.iot.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActionPerformedResponse {

	private boolean actionPerformed = true;
	private String englishMessage = "Action performed successfully";
	private String arabicMessage = "تمت العمليه بنجاح";

}
