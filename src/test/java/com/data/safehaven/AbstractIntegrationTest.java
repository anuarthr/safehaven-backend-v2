package com.data.safehaven;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base para los tests de integración. Levanta el contexto completo (seguridad, JPA,
 * Flyway) sobre un puerto aleatorio y se conecta a la base de datos PostgreSQL definida
 * en {@code application.properties} (por defecto la de docker-compose en localhost:5433).
 *
 * <p>Requisito para ejecutarlos: la base de datos debe estar arriba ({@code docker compose up -d}).
 * En un entorno con Docker disponible para la JVM, esta clase puede migrarse a Testcontainers
 * para no depender de una base externa.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {
}
