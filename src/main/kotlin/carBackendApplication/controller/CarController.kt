package carBackendApplication.controller

import carBackendApplication.dto.CarDataDTO
import carBackendApplication.service.CarService
import carBackendApplication.service.I18nService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.parameters.RequestBody
import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CarController(val service: CarService,
                    val i18nService: I18nService) {

    private val log = LoggerFactory.getLogger(this.javaClass)


    @GetMapping("/api/v1/equipment")
    @Operation(summary = "Find Equipment by Brand and Code")
    fun getTranslationEquipment(@RequestParam(name = "brand", required = true) brand: String,
            @RequestParam(name = "code", required = true) code: String,
            @RequestParam(name = "lang", required = false) lang: String?
            ): String? {
        log.info("Calling Endpoint api/v1/equipment with Version V1")
        return i18nService.getLanguageDescriptionByCode(service.getDescriptionByBrandAndCodeV1(brand, code, lang ), lang)
    }

    @GetMapping("/api/v2/equipment")
    @Operation(summary = "Find Equipment by Brand and Code")
    fun get(@RequestParam(name = "lang", required = false) lang: String?,
            @RequestBody carDataDTO: CarDataDTO
    ): String? {
        log.info("Calling Endpoint /api/v2/equipment with Version V2")
        return i18nService.getLanguageDescriptionByCode(service.getDescriptionByBrandAndCodeV2(carDataDTO, lang), lang)
    }
}