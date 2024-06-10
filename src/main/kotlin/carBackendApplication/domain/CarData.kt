package carBackendApplication.domain

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Table(name = "Car")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
data class CarData(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    internal val id: Long,
    internal val brand: String,
    internal val code: String,
    internal val description: String
) {
}