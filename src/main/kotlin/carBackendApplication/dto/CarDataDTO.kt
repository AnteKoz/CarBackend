package carBackendApplication.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
data class CarDataDTO(val id: Long, val brand: String, val code: String, val description: String)
