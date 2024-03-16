group = "net.vellity.network"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("club.minnced:discord-webhooks:0.8.3")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}