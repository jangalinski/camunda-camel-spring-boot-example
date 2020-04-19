import java.util.concurrent.TimeUnit.SECONDS

repositories {
  mavenLocal()
  mavenCentral()
  jcenter()

  gradlePluginPortal()
}


configurations.all {
  with(resolutionStrategy) {
    cacheChangingModulesFor(0, SECONDS)
    cacheDynamicVersionsFor(0, SECONDS)
  }
}
