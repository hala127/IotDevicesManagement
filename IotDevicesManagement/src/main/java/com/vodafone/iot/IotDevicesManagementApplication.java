package com.vodafone.iot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan(basePackages = { "com.vodafone.iot.*" })
public class IotDevicesManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotDevicesManagementApplication.class, args);
	}

}
