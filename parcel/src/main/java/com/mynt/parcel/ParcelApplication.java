package com.mynt.parcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info=@Info(
				title="Parcel REST API Exam Documentation",
				description="MYNT Parcel REST API Exam Documentation",
				version="v1",
				contact= @Contact(
						name="John Patrick Viray",
						email="jpaviray21@gmail.com"
						)				
				)
		)

public class ParcelApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelApplication.class, args);
	}

}
