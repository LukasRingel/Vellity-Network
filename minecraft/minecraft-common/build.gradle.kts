group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
  api(project(":utilities:utils-configuration"))
  api(project(":utilities:utils-context"))
  api(project(":utilities:utils-redis"))
  api(project(":clients:access-client"))
  api(project(":clients:babbel-client"))
  api(project(":clients:config-client"))
  api(project(":clients:economy-client"))
  api(project(":clients:explorer-client"))
  api(project(":clients:friends-client"))
  api(project(":clients:guardian-client"))
  api(project(":clients:usercache-client"))
  api(project(":clients:punish-client"))
  api("net.kyori:adventure-text-minimessage:4.14.0")
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}