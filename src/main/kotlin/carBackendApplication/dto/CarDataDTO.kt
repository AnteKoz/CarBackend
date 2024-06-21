package carBackendApplication.dto

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
data class CarDataDTO(
    val id: Long,
    val brand: String,
    val type: String,
    val model: String,
    val code: String,
    val description: String?)
