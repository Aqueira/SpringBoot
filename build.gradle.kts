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
	testImplementation("org.springframework.security:spring-security-test:6.4.1")
	implementation("org.springframework.boot:spring-boot-starter-amqp:3.4.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.session:spring-session-core")
	implementation("org.springframework.boot:spring-boot-starter-web:3.3.5")
	implementation("org.springframework.security:spring-security-core:6.3.2")
	implementation("org.springframework.security:spring-security-web:6.3.4")
	implementation("org.springframework.security:spring-security-config:6.1.4")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.4.0")
	implementation("org.mapstruct:mapstruct:1.6.3")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("jakarta.validation:jakarta.validation-api:2.0.2")
	implementation("org.thymeleaf:thymeleaf:3.1.2.RELEASE")
	implementation("org.hibernate.validator:hibernate-validator:6.2.0.Final")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	annotationProcessor ("org.mapstruct:mapstruct-processor:1.5.2.Final")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
