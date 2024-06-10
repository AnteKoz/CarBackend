package carBackendApplication.service


import carBackendApplication.dto.CarDataDTO
import carBackendApplication.mapper.CarMapper
import carBackendApplication.repository.CarRepository
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.context.NoSuchMessageException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarService(private val repository: CarRepository,
                 private val messageSource: MessageSource,
                 private val mapper: CarMapper
                ) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getDescriptionByBrandAndCodeV1 (brand: String, code: String, locale: String?): String? {

        val language = if (locale != null) Locale(locale) else Locale.getDefault()
        val foundCars = repository.findByBrandAndCode(brand, code)
        log.info("FoundCars: {}", foundCars)
        var description = ""
        foundCars.map {
            if(locale?.let { e -> it.description.endsWith(locale) } == true){
              description = it.description
            }
        }
        if (description == "") {
            foundCars.map {
                if(locale?.let { e -> it.description.endsWith("en") } == true){
                    description = it.description
                }
            }
        }
        log.info("Description: {}", description)
        return try {
            messageSource.getMessage(description, null, language)
        } catch (e: NoSuchMessageException) {
            messageSource.getMessage(description, null, Locale.getDefault())
        }

    }

    fun getDescriptionByBrandAndCodeV2(carDataDTO: CarDataDTO, locale: String? ): String? {

        val language = if (locale != null) Locale(locale) else Locale.getDefault()
        log.info("carDataDTO: {}", carDataDTO)
        val mapping = mapper.carDataDTOtoCarData(carDataDTO) // COntroller
        log.info("mapping: {}", mapping)
        val carData = repository.findById(mapping.id)
        log.info("CarData: {}", carData.get())
        return messageSource.getMessage( carData.get().description ?: "error", null, language)
    }
}