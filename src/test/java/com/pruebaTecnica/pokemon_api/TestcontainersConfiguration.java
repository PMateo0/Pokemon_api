package com.pruebaTecnica.pokemon_api;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	public MySQLContainer<?> mysqlContainer() {
		MySQLContainer<?> container = new MySQLContainer<>(
				DockerImageName.parse("mysql:8.0.33")
		)
				.withDatabaseName("pokemon_db")
				.withUsername("root")
				.withPassword("root");
		container.start();
		return container;
	}

}

