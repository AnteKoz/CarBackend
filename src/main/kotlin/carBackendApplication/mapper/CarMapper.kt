package carBackendApplication.mapper

import carBackendApplication.domain.CarData
import carBackendApplication.dto.CarDataDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CarMapper {

    fun carToCarDTO(carData: CarData): CarDataDTO

    @Mapping(target = "id", source = "id")
    fun carDataDTOtoCarData(carDataDTO: CarDataDTO): CarData
}