plugins {
  `kotlin-dsl`
}

apply {
  from("../gradle/repositories.gradle.kts")
}

kotlinDslPluginOptions {
  experimentalWarning.set(false)
}

dependencies {
  fun kotlin(module:String) = "org.jetbrains.kotlin:kotlin-$module:$embeddedKotlinVersion"

  implementation(kotlin("gradle-plugin"))
  implementation(kotlin("allopen"))

  implementation("org.springframework.boot:spring-boot-gradle-plugin:2.2.6.RELEASE")

}
