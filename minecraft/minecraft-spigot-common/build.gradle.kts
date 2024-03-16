plugins {
    id("io.papermc.paperweight.userdev") version "1.5.5"
}

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
    api(project(":minecraft:minecraft-spigot-gui"))
    api(project(":minecraft:minecraft-spigot-item"))
    paperweight.paperDevBundle("1.20.1-R0.1-SNAPSHOT")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}