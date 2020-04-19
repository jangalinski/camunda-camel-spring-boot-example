import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.embeddedKotlinVersion

object Versions {

  const val camunda = "7.12.0"
  const val camundaSpringBoot = "3.4.2"

  object Build {
    val kotlin = embeddedKotlinVersion
    val java = JavaVersion.VERSION_1_8.toString()
  }
}
