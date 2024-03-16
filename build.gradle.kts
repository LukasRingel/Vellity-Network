plugins {
  kotlin("jvm") version "1.8.20"
  id("maven-publish")
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.vellity"
version = "1.0-SNAPSHOT"

allprojects {
  plugins.apply("java")
  plugins.apply("kotlin")
  plugins.apply("java-library")
  plugins.apply("maven-publish")
  plugins.apply("com.github.johnrengelman.shadow")
  plugins.apply("kotlin-kapt")

  repositories {
    mavenCentral()
    mavenLocal()
    maven {
      url = uri("https://git.vellity.dev/api/v4/projects/2/packages/maven")
      name = "GitLab"
      credentials(HttpHeaderCredentials::class) {
        if (System.getenv().containsKey("CI_JOB_TOKEN")) {
          name = "Job-Token"
          value = System.getenv("CI_JOB_TOKEN")
        } else {
          name = "Private-Token"
          value = findProperty("vellityRegistryToken") as String?
        }
      }
      authentication {
        create("header", HttpHeaderAuthentication::class)
      }
    }
  }

  tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
      jvmTarget = "17"
    }
  }

  kotlin {
    jvmToolchain(17)
  }

  publishing {
    publications {
      create<MavenPublication>("maven") {
        from(components["java"])
      }
    }
    repositories {
      maven {
        url = uri("https://git.vellity.dev/api/v4/projects/2/packages/maven")
        name = "Package"
        credentials(HttpHeaderCredentials::class) {
          name = "Job-Token"
          value = System.getenv("CI_JOB_TOKEN")
        }
        authentication {
          create("header", HttpHeaderAuthentication::class)
        }
      }
    }
  }
}

