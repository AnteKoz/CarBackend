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
class CarData(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val brand: String,
    val type: String,
    val model: String,
    val code: String,
    @Transient
    var description: String?

) {

    inner class InnerCarData {
        fun getI18N(): String {
            return "i18n.carbackend.code.$code"
        }
    }
}