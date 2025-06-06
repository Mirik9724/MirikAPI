plugins {
    kotlin("jvm") version "1.9.23"
}

group = "net.Mirik9724"
version = "0.1.2"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:slf4j-simple:2.0.13")

    implementation("org.yaml:snakeyaml:2.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

