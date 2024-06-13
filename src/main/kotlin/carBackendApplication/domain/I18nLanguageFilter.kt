package carBackendApplication.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Entity
@Table(name = "I18n")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
data class I18nLanguageFilter (

    @Id
    @GeneratedValue
    internal val id: Long,
    internal val lang: String,
    internal val code: String,
    internal val trans: String
){

}