group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
  api("com.google.code.gson:gson:2.8.9")
  testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
  implementation(kotlin("stdlib-jdk8"))
}

tasks.named<Test>("test") {
  useJUnitPlatform()

  maxHeapSize = "1G"

  testLogging {
    events("passed")
  }
}