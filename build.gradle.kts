import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.gradle.ext.*

plugins {
    kotlin("jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.8"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "net.Mirik9724"
version = "0.1.5.10"

repositories {
    mavenCentral()
    maven("https://repo.aikar.co/nexus/content/groups/aikar/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(kotlin("test"))

    compileOnly("com.github.azbh111:craftbukkit-1.7.10:R")
    compileOnly("net.md-5:bungeecord-api:1.8-SNAPSHOT")
    compileOnly("com.velocitypowered:velocity-api:1.0.8")
    kapt("com.velocitypowered:velocity-api:1.0.8")

    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    testImplementation("org.slf4j:slf4j-api:2.0.13")
    testImplementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("org.yaml:snakeyaml:2.2")

    implementation("org.bstats:bstats-bungeecord:3.1.0")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("org.bstats:bstats-velocity:3.1.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}

tasks.processResources{
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"

    filesMatching("plugin.yml") {
        expand(props)
    }
    filesMatching("bungee.yml") {
        expand(props)
    }
}

val templateSource = file("src/main/templates")
val templateDest = layout.buildDirectory.dir("generated/sources/templates")
val generateTemplates = tasks.register<Copy>("generateTemplates") {
    val props = mapOf("version" to project.version)
    inputs.properties(props)

    from(templateSource)
    into(templateDest)
    expand(props)
}

sourceSets.main.configure { java.srcDir(generateTemplates.map { it.outputs }) }

tasks.named<ShadowJar>("shadowJar") {
    archiveClassifier.set("")

    mergeServiceFiles()
    relocate("org.bstats", "net.Mirik9724.api.bstats")
}


