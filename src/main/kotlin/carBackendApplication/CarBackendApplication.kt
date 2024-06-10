package carBackendApplication

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
@EnableAutoConfiguration
class CarBackendApplication

fun main(args: Array<String>) {
	runApplication<CarBackendApplication>(*args)
}
