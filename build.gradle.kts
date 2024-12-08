plugins {
	java
	id("org.springframework.boot") version "3.3.5"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Jakarta EE 10 API (compileOnly because it's provided by the server)
	compileOnly("jakarta.platform:jakarta.jakartaee-api:10.0.0")

	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.5")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.4.0")
	implementation("org.springframework.session:spring-session-core")
	implementation("org.springframework.boot:spring-boot-starter-amqp:3.4.0")
	implementation("org.springframework.security:spring-security-core:6.3.2")
	implementation("org.springframework.security:spring-security-web:6.3.4")
	implementation("org.springframework.security:spring-security-config:6.1.4")

	// JWT library for JSON Web Tokens
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// Hibernate Validator and Jakarta Validation API
	implementation("jakarta.validation:jakarta.validation-api:2.0.2")
	implementation("org.hibernate.validator:hibernate-validator:6.2.0.Final")

	// Database
	runtimeOnly("org.postgresql:postgresql")

	// Lombok for boilerplate code
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// MapStruct for code generation
	implementation("org.mapstruct:mapstruct:1.6.3")
	implementation("org.mapstruct.extensions.spring:mapstruct-spring-extensions:1.1.2")

	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

	// Development tools for Spring Boot
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Testing dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test:6.4.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
