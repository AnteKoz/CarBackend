package carBackendApplication.controller

import carBackendApplication.domain.CarData
import carBackendApplication.domain.I18nLanguageFilter
import carBackendApplication.repository.CarRepository
import carBackendApplication.repository.I18nRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
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

    @Autowired
    lateinit var i18nRepository: I18nRepository

    companion object {
        @Container
        @ServiceConnection
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

        @JvmStatic
        @AfterAll
        fun afterAll(): Unit {
            postgresDB.stop()
        }
    }

    @BeforeEach
    fun setUp() {

        repository.deleteAll()
        val carData = CarData(1L, "Mercedes", "11111", "Lenkrad")
        val saveAll = repository.save(carData)

        i18nRepository.deleteAll()
        val i18n = I18nLanguageFilter(1L, "de", "i18n.carbackend.code.11111", "Lenkrad")
        val saveAllI18N = i18nRepository.saveAll(listOf( i18n))

    }

    @AfterEach
    fun after() {
        repository.deleteAll()
        i18nRepository.deleteAll()
    }

    @Test
    fun retrieveDescription(){
        val carData = repository.findAll().first()
        val result = webTestClient.get()
            .uri {uriBuilder -> uriBuilder
                .path("/api/v1/equipment")
                .queryParam("brand" , carData.brand)
                .queryParam("code", carData.code)
                .queryParam("lang", "de")
                .build()
            }.exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()
        Assertions.assertEquals("Lenkrad", result.responseBody)
    }
}