package carBackendApplication.controller

import carBackendApplication.domain.CarData
import carBackendApplication.repository.CarRepository
import carBackendApplication.service.CarService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient(timeout = "36000")
@Testcontainers
class CarControllerIntegrationTest() {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var repository: CarRepository

    companion object {
        @Container
        val postgresDB = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine")).apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("secret")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            println(postgresDB.jdbcUrl)
            registry.add("spring.datasource.url", postgresDB::getJdbcUrl)
            registry.add("spring.datasource.username", postgresDB::getUsername)
            registry.add("spring.datasource.password", postgresDB::getPassword)
            registry.add("spring.flyway.url", postgresDB::getJdbcUrl)
            registry.add("spring.flyway.user", postgresDB::getUsername)
            registry.add("spring.flyway.password", postgresDB::getPassword)
        }
    }

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
        val carData = CarData(2L, "BMW", "99999", "BMW_99999_en")
        val saveAll = repository.saveAll(listOf(carData))
    }

    @AfterEach
    fun after() {
        repository.deleteAll()
    }

    @Test
    fun retrieveDescription(){
        val carData = repository.findAll().first()
        val result = webTestClient.get()
            .uri {uriBuilder -> uriBuilder
                .path("/api/v1/equipment")
                .queryParam("brand" , carData.brand)
                .queryParam("code", carData.code)
                .queryParam("lang", "en")
                .build()
            }.exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("steering wheel", result.responseBody)
    }
}