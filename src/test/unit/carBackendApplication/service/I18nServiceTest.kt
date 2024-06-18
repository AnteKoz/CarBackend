package carBackendApplication.service

import carBackendApplication.domain.CarData
import carBackendApplication.domain.I18nLanguageFilter
import carBackendApplication.repository.CarRepository
import carBackendApplication.repository.I18nRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.SpringBootConfiguration
import org.springframework.test.context.ContextConfiguration
import java.util.*
import org.junit.jupiter.api.Assertions.*

@ContextConfiguration
@SpringBootConfiguration
class I18nServiceTest {

    fun getLanguageDescriptionByCodeWithValidData() {
        val i18nRepository = Mockito.mock(I18nRepository::class.java)
        val i18n = listOf(I18nLanguageFilter(1L, "en", "i18n.carbackend.code.11111", "steering wheel"))
        val carData =  CarData(1L, "Mercedes", "11111", "Lenkrad")
        println(carData.InnerCarData().getI18N())
        println(carData)

        Mockito.`when`(i18nRepository.findByCodeAndLang(carData.InnerCarData().getI18N(), null)).thenReturn(i18n)
        val sut = I18nService(i18nRepository)
        val actual = sut.getLanguageDescriptionByCode(carData, null)
        assertEquals(actual, i18n.first().trans)

    }

}