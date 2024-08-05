import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm") version "1.9.24"
	kotlin("plugin.spring") version "1.9.24"
	kotlin("plugin.jpa") version "1.9.24"
	kotlin("kapt") version "2.0.0"
	id("org.flywaydb.flyway") version "9.0.1"
	application
}
group = "CarBackend"
version = "1.0.0"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}
extra["testcontainersVersion"] = "1.16.2"

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${property("testcontainersVersion")}")
	}
}
repositories {
	mavenCentral()
	maven {
		url = uri("https://plugins.gradle.org/m2/")
	}
}
base {
	archivesBaseName = "carBackend"
}

dependencies {

	implementation(kotlin("stdlib"))
	implementation("software.amazon.awssdk:s3:2.26.9")
	implementation("software.amazon.awssdk:ec2:2.26.9")
	implementation("software.amazon.awssdk:iam:2.26.9")
	implementation("software.amazon.awssdk:rds:2.26.9")
	implementation("software.amazon.awssdk:route53:2.26.9")
	implementation("software.amazon.awssdk:cloudwatch:2.26.9")
	implementation("software.amazon.awscdk:aws-cdk-lib:2.147.1")
	implementation("software.constructs:constructs:10.x.x")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    compileOnly("org.projectlombok:lombok:1.18.22")
	annotationProcessor("org.projectlombok:lombok:1.18.22")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.postgresql:postgresql:42.2.10")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")

	implementation("org.mapstruct:mapstruct:1.4.2.Final")
	kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")

	implementation("org.jetbrains.kotlin.kapt:org.jetbrains.kotlin.kapt.gradle.plugin:1.9.24")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("org.flywaydb:flyway-core:9.0.1")

	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

	testImplementation("junit:junit:4.13")
	testImplementation("org.mockito:mockito-core:5.12.0")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("io.netty:netty-resolver-dns-native-macos:4.1.75.Final") {
		artifact {
			classifier = "osx-aarch_64"
		}
	}
	testImplementation("io.mockk:mockk:1.10.4")
	testImplementation("com.ninja-squad:springmockk:4.0.0")

	//test-containers
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testImplementation("org.springframework.boot:spring-boot-testcontainers:3.3.0")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets{
	test{
		java {
			setSrcDirs(listOf("src/test/intg", "src/test/unit"))
		}
	}
	main{
		java{
			setSrcDirs(listOf("src/main/kotlin"))
		}
	}
}