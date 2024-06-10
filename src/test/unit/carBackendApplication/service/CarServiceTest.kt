package CarBackendExample.CarBackend.unit.carBackendApplication.service

import carBackendApplication.domain.CarData
import carBackendApplication.dto.CarDataDTO
import carBackendApplication.mapper.CarMapper
import carBackendApplication.repository.CarRepository
import carBackendApplication.service.CarService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.boot.SpringBootConfiguration
import org.springframework.context.MessageSource
import org.springframework.test.context.ContextConfiguration
import java.util.*

@ContextConfiguration
@SpringBootConfiguration
class CarServiceTest {

    @Test
    fun getDescriptionV1(){
        val mockBookRepository = Mockito.mock(CarRepository::class.java)
        val messageSource = Mockito.mock(MessageSource::class.java)
        val mapper = Mockito.mock(CarMapper::class.java)
        val carDataList = listOf(
            CarData(1, "Mercedes", "11111", "Mercedes_11111_de"))

        Mockito.`when`(mockBookRepository.findByBrandAndCode("Mercedes", "11111")).thenReturn(carDataList)
        Mockito.`when`(messageSource.getMessage(carDataList.first().description, null, Locale("de"))).thenReturn("Tür")

        val expected = "Tür"

        val sut = CarService(mockBookRepository, messageSource, mapper)
        val actual = sut.getDescriptionByBrandAndCodeV1("Mercedes", "11111", "de")
        assertEquals(expected, actual)
    }

    @Test
    fun getDescriptionV2(){
        val mockBookRepository = Mockito.mock(CarRepository::class.java)
        val messageSource = Mockito.mock(MessageSource::class.java)
        val mapper = Mockito.mock(CarMapper::class.java)
        val carDataList = listOf(
            CarData(1, "BMW", "12345", "Tür"))
        val carDataDTO = CarDataDTO(1, "BMW", "12345", "Tür")
        val carData = CarData(1, "BMW", "12345", "Tür")

        Mockito.`when`(mapper.carDataDTOtoCarData(carDataDTO)).thenReturn(carData)
        Mockito.`when`(mockBookRepository.findById( carData.id)).thenReturn(Optional.of(carData))
        Mockito.`when`(messageSource.getMessage(carDataList.first().description, null, Locale("de"))).thenReturn("Tür")

        val expected = "Tür"

        val sut = CarService(mockBookRepository, messageSource, mapper)
        val actual = sut.getDescriptionByBrandAndCodeV2(carDataDTO, "de")
        assertEquals(expected, actual)
    }
}