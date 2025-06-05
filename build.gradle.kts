plugins {
    `maven-publish`
    kotlin("jvm") version "1.9.23"
}

group = "net.Mirik9724"
version = "0.1.1"

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

publishing {
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"])
            groupId = "net.Mirik9724"
            artifactId = "MirikAPI"
            version = version
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Mirik9724/MirikAPI")
            credentials {
                username = System.getenv("GITHUB_USERNAME") ?: "Mirik9724"
                password = System.getenv("GITHUB_TOKEN") ?: file("github_token.txt").readLines().first().trim()
            }
        }
    }
}
