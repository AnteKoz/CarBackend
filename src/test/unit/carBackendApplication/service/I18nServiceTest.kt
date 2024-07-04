package carBackendApplication.service

import carBackendApplication.domain.CarData
import carBackendApplication.domain.I18nLanguageFilter
import carBackendApplication.dto.CarDataDTO
import carBackendApplication.mapper.CarMapper
import carBackendApplication.repository.CarRepository
import carBackendApplication.repository.I18nRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.springframework.boot.SpringBootConfiguration
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootConfiguration
class I18nServiceTest {

    fun getLanguageDescriptionByCodeWithValidData() {
        val i18nRepository = Mockito.mock(I18nRepository::class.java)
        val carRepository = Mockito.mock(CarRepository::class.java)
        val mapper = Mockito.mock(CarMapper::class.java)

        val carDataDTO = CarDataDTO(1L, "Mercedes", "PWK","S-Klasse","11111", "Lenkrad")

        val i18n = listOf(I18nLanguageFilter(1L, "en", "i18n.carbackend.code.11111", "steering wheel"))
        val carData =  CarData(1L, "Mercedes", "PWK","S-Klasse","11111", "Lenkrad")
        println(carData.InnerCarData().getI18N())
        println(carData)

        Mockito.`when`(i18nRepository.findByCodeAndLang(carData.InnerCarData().getI18N(), null)).thenReturn(i18n)
        Mockito.`when`(carRepository.findByBrandAndCode(carData.brand, carData.code)).thenReturn(listOf(carData))
        Mockito.`when`(mapper.carDataDTOtoCarData(carDataDTO)).thenReturn(carData)

        val sut = I18nService(i18nRepository, carRepository, mapper)
        val actual = sut.getLanguageDescriptionByCode(carData.brand, carData.code, "en")
        assertEquals(actual, i18n.first().trans)

    }

}