package carBackendApplication.domain

import jakarta.servlet.Filter
import org.springframework.stereotype.Component
import jakarta.servlet.FilterChain
import jakarta.servlet.FilterConfig
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse

@Component
class ThreadLocalCleanupFilter : Filter {
    override fun init(filterConfig: FilterConfig?) {
        // Initialisierungslogik falls nötig
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        try {
            chain?.doFilter(request, response)
        } finally {
            ThreadLocalContext.userThreadLocal.remove()
        }
    }

    override fun destroy() {
        // Aufräumlogik falls nötig
    }
}