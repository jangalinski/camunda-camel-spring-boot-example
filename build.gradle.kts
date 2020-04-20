import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.plugin.SpringBootPlugin

plugins {
  id("base")
  id("idea")
  id("java-test-fixtures")

  kotlin("jvm")
  kotlin("plugin.spring")
  id("org.springframework.boot")
}

apply {
  from("${rootProject.rootDir}/gradle/repositories.gradle.kts")
}

dependencies {
  fun springBoot(module: String) = "org.springframework.boot:spring-boot-$module"
  fun camel(module: String) = "org.apache.camel.springboot:camel-$module"

  implementation(platform(SpringBootPlugin.BOM_COORDINATES))
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation(platform("${camel("spring-boot-dependencies")}:${Versions.camel}"))

  implementation(springBoot("starter-web"))
  implementation(springBoot("starter-actuator"))

  implementation(camel("spring-boot-starter"))

  implementation("org.camunda.bpm.springboot:camunda-bpm-spring-boot-starter-webapp:${Versions.camundaSpringBoot}")
  implementation("org.camunda.bpm.extension.camel:camunda-bpm-camel-spring:${Versions.camundaCamel}")

  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))

  implementation("com.h2database:h2")

  // Use the Kotlin test library.
  testImplementation("org.jetbrains.kotlin:kotlin-test")

  // Use the Kotlin JUnit integration.
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}


tasks {
  withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = Versions.Build.java
      freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
    }
  }
}
