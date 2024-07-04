package carBackendApplication.domain

object ThreadLocalContext {
    val userThreadLocal = object : ThreadLocal<String>() {
        override fun initialValue(): String {
            return ""
        }
    }
}