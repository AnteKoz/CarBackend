package carBackendApplication.service


import carBackendApplication.domain.CarData
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
                 private val mapper: CarMapper
                ) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getDescriptionByBrandAndCodeV1 (brand: String, code: String, locale: String?): CarData {

        val foundCars = repository.findByBrandAndCode(brand, code)
        log.info("FoundCars: {}", foundCars)
        log.info("Property: {}", foundCars[0].InnerCarData().getI18N())
        return foundCars.first()
    }

    fun getDescriptionByBrandAndCodeV2(carDataDTO: CarDataDTO, locale: String? ): CarData {

        log.info("carDataDTO: {}", carDataDTO)
        val mapping = mapper.carDataDTOtoCarData(carDataDTO)
        val carData = repository.findById(mapping.id)
        log.info("CarData: {}", carData.get())
        return carData.get()
    }

    fun getAllCars() : MutableList<CarData> {

        return repository.findAll()
        //carData.forEach { car -> car.description = car.InnerCarData().getI18N() }
        //return carData
    }
}