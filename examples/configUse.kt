package com.example

import net.Mirik9724.api.Config
import java.io.File

fun main() {
    val resourcePath = "config/default-config.yml"
    val targetDir = File("plugins/myplugin")

    // Copy the config file from the JAR resources to the target directory
    val configFile = Config.cloneConfigFromJar(resourcePath, targetDir)

    println("Config file copied to: ${configFile.absolutePath}")

    // Load YAML configuration from the copied file
    val configData = Config.loadYamlConfig(configFile)

    // Print loaded config data (usually a Map or List)
    println("Loaded config data: $configData")

    // Copies "scripts/Example.ms" from JAR into the "scripts" folder
    val targetFile = Config.copyFileFromJar("scripts/Example.ms", File("scripts"))

    println("File successfully copied to: ${targetFile.absolutePath}")
}
