package carBackendApplication.controller


import carBackendApplication.domain.CarData
import carBackendApplication.service.CarService
import carBackendApplication.service.I18nService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.NestedTestConfiguration
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest
class CarControllerUnitTest(@Autowired val mockMvc: MockMvc, @Autowired i18nService: I18nService) {

        @MockkBean
        lateinit var carService: CarService


        fun retrieveDescription(){

            val carData = CarData(1L, "Mercedes", "PWK","S-Klasse","11111", "")
            every { carService.getDescriptionByBrandAndCodeV1(carData.brand, carData.code, "de") } returns carData

            mockMvc.perform(get("/api/bankAccount?id=2"))
                .andExpect(status().isBadRequest);

        }
}