import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.graalvm.buildtools.native") version "0.9.28"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "com.santannaf.rinha-backend-2024-web"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("app")
            configurationFileDirectories.from(file("src/main/resources/META-INF/native-image"))
            mainClass.set("org.example.rinhabackend2024kotlinweb.ApplicationKt")
            buildArgs.add("--color=always")
            buildArgs.add("--report-unsupported-elements-at-runtime")
            buildArgs.add("--allow-incomplete-classpath")
            buildArgs.add("--enable-preview")
            buildArgs.add("--verbose")
            buildArgs.add("--initialize-at-build-time=ch.qos.logback.classic.Logger,ch.qos.logback.core.status.InfoStatus,ch.qos.logback.core.util.Loader,ch.qos.logback.core.util.StatusPrinter,org.slf4j.LoggerFactory,ch.qos.logback.core.status.StatusBase,ch.qos.logback.classic.Level")
        }
    }
}
