package carBackendApplication.controller


import carBackendApplication.service.CarService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient


//@WebMvcTest(controllers = [CarController::class])
//@AutoConfigureWebTestClient
//@ActiveProfiles("test")
class CarControllerUnitTest {

    //@Autowired
    //lateinit var webTestClient: WebTestClient
    //@MockkBean
    //private lateinit var carServiceMock: CarService

    @Test
    fun retrieveDescription(){
        //Mockito.`when`(carServiceMock.getDescriptionByBrandAndCodeV1("BMW", "99999", "en")).thenReturn("steering wheel")
        //every { carServiceMock.getDescriptionByBrandAndCodeV1(any(), any(), any()) } returns ""
       // val result = webTestClient.get()
         //   .uri {uriBuilder -> uriBuilder
           //     .path("/api/v1/equipment")
           //     .queryParam("brand" , "BMW")
           //     .queryParam("code", "99999")
           //     .queryParam("lang", "en")
           //     .build()
           // }.exchange()
           // .expectStatus().is2xxSuccessful
           // .expectBody(String::class.java)
           // .returnResult()

        //Assertions.assertEquals("steering wheel", result.responseBody)
    }
}