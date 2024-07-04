package carBackendApplication.controller

import carBackendApplication.domain.CarData
import carBackendApplication.domain.I18nLanguageFilter
import carBackendApplication.mapper.CarMapper
import carBackendApplication.repository.CarRepository
import carBackendApplication.repository.I18nRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@ExtendWith(SpringExtension::class)
class CarControllerIntegrationTest() {

    @Autowired
    lateinit var repository: CarRepository

    @Autowired
    lateinit var i18nRepository: I18nRepository

    @Autowired
    lateinit var carController: CarController

    @Autowired
    lateinit var mapper: CarMapper

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
        val carData = CarData(1L, "Mercedes", "PKW", "PWK", "11111","Lenkrad")
        repository.save(carData)

        i18nRepository.deleteAll()
        val i18n = I18nLanguageFilter(1L, "de", "i18n.carbackend.code.11111", "Lenkrad")
        val i18n2 = I18nLanguageFilter(1L, "en", "i18n.carbackend.code.11111", "steering wheel")
        i18nRepository.saveAll(listOf( i18n, i18n2))

    }

    @AfterEach
    fun after() {
        repository.deleteAll()
        i18nRepository.deleteAll()
    }

    @Test
    fun retrieveTranslationFromV1(){
        val carData = repository.findAll().first()
        val result = carController.getTranslationEquipment(carData.brand, carData.code,"en")

        Assertions.assertEquals("steering wheel", result)
        Assertions.assertNotNull(result)
    }

    @Test
    fun retrieveTranslationFromV1WithOutLanguage(){
        val carData = repository.findAll().first()
        val result = carController.getTranslationEquipment(carData.brand,carData.code,null)

        Assertions.assertEquals("steering wheel", result)
        Assertions.assertNotNull(result)
    }


    @Test
    fun retrieveTranslationV2WithoutLanguageSet() {
        val result = carController.getTranslationEquipmentV2(null, mapper.carDataToCarDTO(repository.findAll().first()))
        Assertions.assertEquals(result, "steering wheel")
    }
}