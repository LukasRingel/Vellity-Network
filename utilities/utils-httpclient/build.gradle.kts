group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
  compileOnly(project(":utilities:utils-configuration"))
  api("io.reactivex.rxjava3:rxjava:3.1.6")
  api("com.squareup.retrofit2:retrofit:2.9.0")
  api("com.squareup.retrofit2:converter-gson:2.9.0")
  api("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
  implementation("com.google.code.gson:gson:2.10.1")
  testImplementation(platform("org.junit:junit-bom:5.9.1"))
  testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
  useJUnitPlatform()
}