package com.vodafone.iot.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagingRequestDto {

	private int pageNumber;
	private int pageSize;
}
