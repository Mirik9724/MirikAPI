package net.Mirik9724.api

import org.yaml.snakeyaml.Yaml
import java.io.File

private fun flattenMapToString(map: Map<String, Any>, parentKey: String = ""): Map<String, String> {
    val result = mutableMapOf<String, String>()
    for ((key, value) in map) {
        val fullKey = if (parentKey.isEmpty()) key else "$parentKey.$key"
        when (value) {
            is Map<*, *> -> {
                @Suppress("UNCHECKED_CAST")
                result.putAll(flattenMapToString(value as Map<String, Any>, fullKey))
            }
            is List<*> -> result[fullKey] = value.joinToString(",") { it.toString() }
            else -> result[fullKey] = value.toString()
        }
    }
    return result
}

/**
 * Loads a YAML file and returns a flat Map<String, String>
 *
 * Usage:
 * val config = loadYmlFile("config.yml")
 * val host = config["database.host"]
 *
 * @throws IllegalArgumentException if the file is not found
 */
fun loadYmlFile(pathToConfig: String): Map<String, String> {
    val file = File(pathToConfig)
    if (!file.exists()) {
        throw IllegalArgumentException("Файл $pathToConfig не найден!")
    }

    val yaml = Yaml()
    val dataAny = yaml.load(file.inputStream())
    val data = if (dataAny is Map<*, *>) {
        @Suppress("UNCHECKED_CAST")
        dataAny as Map<String, Any>
    } else {
        emptyMap()
    }

    return flattenMapToString(data)
}

/**
 * Updates a YAML file by adding missing keys and comments
 * from the new configuration text.
 *
 * ⚠️ This is a text updater, not a full-fledged YAML merger.
 * Ideal for config.yml.
 */
fun updateYml(
    newConfig: String,
    oldConfig: String,
) {
    val oldConfigFile = File(oldConfig)

    if (!oldConfigFile.exists()) {
        oldConfigFile.parentFile.mkdirs()
        oldConfigFile.writeText(newConfig)
        return
    }

    val oldLines = oldConfigFile.readLines().toMutableList()
    val oldText = oldLines.joinToString("\n")

    val newLines = newConfig.lines()

    val blocks = mutableListOf<List<String>>()
    var currentBlock = mutableListOf<String>()

    for (line in newLines) {
        if (line.trim().startsWith("#") || line.contains(":")) {
            currentBlock.add(line)
        } else if (currentBlock.isNotEmpty()) {
            blocks.add(currentBlock)
            currentBlock = mutableListOf()
        }
    }
    if (currentBlock.isNotEmpty()) blocks.add(currentBlock)

    var changed = false

    for (block in blocks) {
        val keyLine = block.firstOrNull { it.contains(":") } ?: continue
        val key = keyLine.substringBefore(":").trim()

        if (!oldText.contains(Regex("^\\s*$key\\s*:", RegexOption.MULTILINE))) {
            oldLines.add("")
            oldLines.addAll(block)
            changed = true
        }
    }

    if (changed) {
        oldConfigFile.writeText(oldLines.joinToString("\n"))
    }
}

/**
 * Updates a YAML file using the reference config from the resources (JAR).
 *
 * ⚠️ resourcePath — the path INSIDE the JAR (resources)
 */
fun updateYmlFromJar(
    resourcePath: String,
    configFile: String,
    loader: ClassLoader = Thread.currentThread().contextClassLoader
) {
    val inputStream = loader.getResourceAsStream(resourcePath) ?: return
    val newConfigText = inputStream.bufferedReader().use { it.readText() }

    updateYml(newConfigText, configFile)
}
