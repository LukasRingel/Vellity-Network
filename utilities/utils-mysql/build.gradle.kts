group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
  api("com.zaxxer:HikariCP:5.0.1")
  api("org.mariadb.jdbc:mariadb-java-client:3.1.4")
  implementation("org.xerial:sqlite-jdbc:3.42.0.0")
  implementation("org.apache.commons:commons-lang3:3.12.0")
  compileOnly(project(":utilities:utils-configuration"))
  compileOnly(project(":utilities:utils-context"))
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}