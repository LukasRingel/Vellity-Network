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
    implementation(project(":common:babbel-common"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.3")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.6.0.202305301015-r")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}