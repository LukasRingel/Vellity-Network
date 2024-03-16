group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
  compileOnly(project(":utilities:utils-context"))
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
  useJUnitPlatform()
}