package carBackendApplication.service

import carBackendApplication.domain.CarData
import carBackendApplication.dto.CarDataDTO
import carBackendApplication.mapper.CarMapper
import carBackendApplication.repository.CarRepository
import carBackendApplication.repository.I18nRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class I18nService(private val i18nRepository: I18nRepository,
                  private val carRepository: CarRepository,
                  private val mapper: CarMapper)

{
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getLanguageDescriptionByCode(brand: String, code: String, lang: String?): String {
        val carData: List<CarData> = carRepository.findByBrandAndCode(brand,code)
        val i18n = i18nRepository.findByCodeAndLang(carData.first().InnerCarData().getI18N(), lang ?: "en")
        log.info("translationLang: {}", carData.first().InnerCarData().getI18N())
        log.info("getLanguageDescriptionByCode: {}", i18n)
        return i18n.first().trans
    }

    fun getLanguageDescriptionByCarDataDto(carDataDTO: CarDataDTO, lang: String?): String {
        val carData = mapper.carDataDTOtoCarData(carDataDTO)
        val i18n = i18nRepository.findByCodeAndLang(carData.InnerCarData().getI18N(), lang ?: "en")
        log.info("TranslationLang: {}", carData.InnerCarData().getI18N())
        log.info("getLanguageDescriptionByCarDataDto: {}", i18n)
        return i18n.first().trans
    }
}