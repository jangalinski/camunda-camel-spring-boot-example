import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.embeddedKotlinVersion

object Versions {

  const val camunda = "7.12.0"
  const val camundaSpringBoot = "3.4.2"

  const val camel = "3.2.0"
  const val camundaCamel = "0.7.0"

  object Build {
    val kotlin = embeddedKotlinVersion
    val java = JavaVersion.VERSION_1_8.toString()
  }
}
