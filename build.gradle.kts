import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.Mirik9724"
version = "0.1.4.7"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))

    compileOnly("org.slf4j:slf4j-api:2.0.13")
    compileOnly("org.slf4j:slf4j-simple:2.0.13")

    implementation("org.yaml:snakeyaml:2.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")

    // Включаем только SnakeYAML
    dependencies {
        include(dependency("org.yaml:snakeyaml"))
    }
}


