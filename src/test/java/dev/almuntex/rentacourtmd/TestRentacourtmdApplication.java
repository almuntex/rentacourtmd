package dev.almuntex.rentacourtmd;

import org.springframework.boot.SpringApplication;

public class TestRentacourtmdApplication {

	public static void main(String[] args) {
		SpringApplication.from(RentacourtmdApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
