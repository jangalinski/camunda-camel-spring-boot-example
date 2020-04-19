package io.github.jangalinski.camunda.camel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CamundaCamelApplication {

}

fun main(args:Array<String> ) = runApplication<CamundaCamelApplication>()
  .let { Unit }

