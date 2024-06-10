package carBackendApplication.repository

import carBackendApplication.domain.CarData
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CarRepository: JpaRepository<CarData, Int> {
    fun findByBrandAndCode(brand: String, code: String): List<CarData>
    fun findById(id: Long): Optional<CarData>
}