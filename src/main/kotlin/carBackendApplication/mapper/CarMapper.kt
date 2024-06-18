package carBackendApplication.mapper

import carBackendApplication.domain.CarData
import carBackendApplication.dto.CarDataDTO
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CarMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", ignore = true)
    fun carDataDTOtoCarData(carDataDTO: CarDataDTO): CarData

    @Mapping(target = "id", source = "id")
    @Mapping(target = "description", ignore = true)
    fun carDataToCarDTO(carData: CarData): CarDataDTO
}