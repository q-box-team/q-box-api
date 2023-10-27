import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4"
    id("io.spring.dependency-management") version "1.1.3"
    id("org.jetbrains.kotlinx.kover") version "0.7.3"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
    kotlin("kapt") version "1.8.22"
}

group = "site.q-box"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
extra["snippetsDir"] = file("build/generated-snippets")

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("com.github.maricn:logback-slack-appender:1.6.1")
    implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    //database
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("com.h2database:h2")

    //querydsl
    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    //security
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    //test container
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")

    // rest docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")

    //kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")

    //mockkk
    testImplementation("io.mockk:mockk:1.13.8")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
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

//docs setting
tasks {
    val snippetsDir = file("$buildDir/generated-snippets")

    clean {
        delete("src/main/resources/static/docs")
    }

    test {
        useJUnitPlatform()
        systemProperty("org.springframework.restdocs.outputDir", snippetsDir)
        outputs.dir(snippetsDir)
    }

    asciidoctor {
        dependsOn(test)

        attributes(
            mapOf("snippets" to snippetsDir)
        )
        inputs.dir(snippetsDir)

        doFirst {
            delete("src/main/resources/static/docs")
        }
    }

    register<Copy>("copyDocument") {
        dependsOn(asciidoctor)

        destinationDir = file(".")
        from(asciidoctor.get().outputDir) {
            into("src/main/resources/static/docs")
        }
    }

    bootJar {
        dependsOn(asciidoctor)

        from(asciidoctor.get().outputDir) {
            into("BOOT-INF/classes/static/docs")
        }
    }
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlinx:kover-gradle-plugin:0.7.3")
    }
}

kover {
    excludeJavaCode()
}


koverReport {
    filters {
        includes {
            classes("**.*")
        }
        excludes {
            classes("**.QboxServerApplicationKt")
            classes("**.**Config")
            classes("**.Mail**")
            classes("**.dto.**")
        }
    }
    verify {
        rule {
            isEnabled = true
            entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.APPLICATION
            bound {
                minValue = 100
                metric = kotlinx.kover.gradle.plugin.dsl.MetricType.INSTRUCTION
                aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
            }
        }
        rule {
            isEnabled = true
            entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.CLASS
            bound {
                minValue = 100
                metric = kotlinx.kover.gradle.plugin.dsl.MetricType.BRANCH
                aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
            }
        }

        rule {
            isEnabled = true
            entity = kotlinx.kover.gradle.plugin.dsl.GroupingEntityType.CLASS
            bound {
                minValue = 100
                metric = kotlinx.kover.gradle.plugin.dsl.MetricType.INSTRUCTION
                aggregation = kotlinx.kover.gradle.plugin.dsl.AggregationType.COVERED_PERCENTAGE
            }
        }
    }
}