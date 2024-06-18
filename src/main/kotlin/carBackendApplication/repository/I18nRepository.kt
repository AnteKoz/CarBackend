package carBackendApplication.repository

import carBackendApplication.domain.I18nLanguageFilter
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface I18nRepository: JpaRepository<I18nLanguageFilter, Int> {
    fun findByCodeAndLang(code: String, lang: String?): List<I18nLanguageFilter>
}