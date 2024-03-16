group = "net.vellity.network"
version = "1.0-SNAPSHOT"

repositories {
  maven {
    name = "papermc"
    url = uri("https://repo.papermc.io/repository/maven-public/")
  }
}

dependencies {
  api(project(":minecraft:minecraft-common"))
  compileOnly("com.velocitypowered:velocity-api:3.1.1")
  annotationProcessor("com.velocitypowered:velocity-api:3.1.1")
  kapt("com.velocitypowered:velocity-api:3.1.1")
}