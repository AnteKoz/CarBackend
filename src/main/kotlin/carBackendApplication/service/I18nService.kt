package carBackendApplication.service

import carBackendApplication.domain.CarData
import carBackendApplication.repository.I18nRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class I18nService(private val i18nRepository: I18nRepository)

{
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getLanguageDescriptionByCode(carData: CarData, lang: String?): String {
        val i18n = i18nRepository.findByCodeAndLang(carData.InnerCarData().getI18N(), lang)
        log.info("translationLang: {}", carData.InnerCarData().getI18N())
        log.info("getLanguageDescriptionByCode: {}", i18n)
        return i18n.get().trans
    }
}