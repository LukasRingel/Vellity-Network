group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
    compileOnly(project(":utilities:utils-configuration"))
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