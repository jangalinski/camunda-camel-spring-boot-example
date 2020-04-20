package io.github.jangalinski.camunda.camel

import org.apache.camel.CamelContext
import org.apache.camel.builder.RouteBuilder
import org.camunda.bpm.camel.component.CamundaBpmComponent
import org.camunda.bpm.camel.component.CamundaBpmConstants
import org.camunda.bpm.camel.spring.CamelServiceImpl
import org.camunda.bpm.engine.ProcessEngine
import org.camunda.bpm.model.bpmn.Bpmn
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener

@SpringBootApplication
@EnableProcessApplication
class CamundaCamelApplication {

  companion object {
    val log = LoggerFactory.getLogger(CamundaCamelApplication::class.java)
  }

  @Autowired
  lateinit var camelContext: CamelContext

  @Bean
  fun camel(processEngine:ProcessEngine, camelContext: CamelContext) = CamelServiceImpl().apply {
    setCamelContext(camelContext)
    setProcessEngine(processEngine)

    camelContext.addComponent(CamundaBpmConstants.CAMUNDA_BPM_CAMEL_URI_SCHEME, CamundaBpmComponent(processEngine))
  }

  @EventListener
  fun init(evt: PostDeployEvent) {

    evt.processEngine.repositoryService.createDeployment()
      .addModelInstance("dummy.bpmn",
        Bpmn.createExecutableProcess("process_dummy")
          .startEvent()
          .userTask("task")
          .endEvent()
          .done()
      )
      .deploy()

    log.info("Successfully started Camel with components: " + camelContext.getComponentNames());
    log.info("=======================");
  }

  @Bean
  fun processStarter() = object: RouteBuilder() {
    override fun configure() {
      from("timer:hello?period=1000&delay=5000")
        .description("starts an instance of process_dummy every second")
        .routeId("dummy")
        .routeGroup("processes")
        .process { e -> e.getIn().body = "Hello Camel!" }
        .to("log:io.github.jangalinski?level=INFO")
        .to("camunda-bpm://start?processDefinitionKey=process_dummy")
    }
  }
}

fun main(args:Array<String> ) = runApplication<CamundaCamelApplication>()
  .let { Unit }

