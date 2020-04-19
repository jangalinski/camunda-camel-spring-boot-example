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

  implementation(platform(SpringBootPlugin.BOM_COORDINATES))
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

  implementation(springBoot("starter-web"))
  implementation(kotlin("stdlib-jdk8"))
  implementation(kotlin("reflect"))

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
