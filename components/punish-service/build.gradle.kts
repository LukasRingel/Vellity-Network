plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
}
group = "net.vellity.network"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":utilities:utils-httpserver-spring"))
    implementation(project(":utilities:utils-configuration"))
    implementation(project(":utilities:utils-redis"))
    implementation(project(":utilities:utils-mysql"))
    implementation(project(":utilities:utils-audit"))
    implementation(project(":utilities:utils-mojang"))
    implementation(project(":utilities:utils-alert-discord"))
    implementation(project(":utilities:utils-context"))
    implementation(project(":common:punish-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}