group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
    api(project(":utilities:utils-httpclient"))
    api(project(":common:punish-common"))
    implementation(project(mapOf("path" to ":common:punish-common")))
    implementation(project(mapOf("path" to ":common:punish-common")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}